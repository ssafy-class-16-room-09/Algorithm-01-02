import assert from 'node:assert/strict';
import { spawnSync } from 'node:child_process';
import fs from 'node:fs';
import os from 'node:os';
import path from 'node:path';
import { afterEach, beforeEach, test } from 'node:test';
import { report, run } from './check-submission.mjs';
import { createMockGithub, makeCore, prContext } from './mock-github.mjs';

const OWNER = 'owner';
const REPO = 'repo';
const PROBLEM_DIR = 'solutions/week-01/pgs-43163';

let workspace;
let originalWorkspace;

beforeEach(() => {
  workspace = fs.mkdtempSync(path.join(os.tmpdir(), 'algo-check-test-'));
  fs.mkdirSync(path.join(workspace, PROBLEM_DIR), { recursive: true });
  fs.writeFileSync(
    path.join(workspace, PROBLEM_DIR, '.problem.json'),
    JSON.stringify({ issue: 6, platformLabel: '프로그래머스', number: '43163', title: '단어 변환' }),
  );
  originalWorkspace = process.env.GITHUB_WORKSPACE;
  process.env.GITHUB_WORKSPACE = workspace;
});

afterEach(() => {
  if (originalWorkspace === undefined) delete process.env.GITHUB_WORKSPACE;
  else process.env.GITHUB_WORKSPACE = originalWorkspace;
  delete process.env.JAVA_COMPILE_RESULT;
  delete process.env.PYTHON_COMPILE_RESULT;
  delete process.env.JAVA_COMPILE_LOG;
  delete process.env.PYTHON_COMPILE_LOG;
  delete process.env.JAVASCRIPT_COMPILE_RESULT;
  delete process.env.JAVASCRIPT_COMPILE_LOG;
  fs.rmSync(workspace, { recursive: true, force: true });
});

test('Python 풀이를 검사 대상으로 분류하고 성공 결과를 보고한다', async () => {
  const github = createMockGithub();
  const context = prContext(OWNER, REPO, 1, 'alice');
  const core = makeCore();
  const solution = `${PROBLEM_DIR}/alice/Solution.py`;
  github._state.prFiles = [{ filename: solution, status: 'added' }];
  await github.rest.issues.create({ title: 'Python 풀이 PR' });

  await run({ github, context, core });

  assert.equal(core._outputs.java_count, '0');
  assert.equal(core._outputs.python_count, '1');
  assert.equal(core._outputs.python_files, solution);
  assert.equal(core._outputs.javascript_count, '0');
  assert.equal(core._outputs.javascript_files, '');
  assert.equal(core._outputs.__failed, undefined);

  process.env.PYTHON_COMPILE_RESULT = 'success';
  await report({ github, context, core });

  const comment = github._state.comments.find((item) => item.issue_number === 1);
  assert.match(comment.body, /Python 문법 검사: ✅ 통과/);
  assert.match(comment.body, /## ✅ 제출 검사 통과/);
  assert.doesNotMatch(comment.body, /JavaScript 문법 검사:/);
});

test('LeetCode Java, Python, JavaScript 풀이를 모두 제출 경로로 검사한다', async () => {
  const github = createMockGithub();
  const context = prContext(OWNER, REPO, 1, 'alice');
  const core = makeCore();
  const problemDir = 'solutions/week-01/leetcode-1';
  const javaSolution = `${problemDir}/alice/Solution.java`;
  const pythonSolution = `${problemDir}/alice/Solution.py`;
  const javascriptSolution = `${problemDir}/alice/Solution.js`;
  github._state.prFiles = [
    { filename: javaSolution, status: 'added' },
    { filename: pythonSolution, status: 'added' },
    { filename: javascriptSolution, status: 'added' },
  ];
  await github.rest.issues.create({ title: 'LeetCode 풀이 PR' });

  await run({ github, context, core });

  assert.equal(core._outputs.java_count, '1');
  assert.equal(core._outputs.python_count, '1');
  assert.equal(core._outputs.javascript_count, '1');
  assert.equal(core._outputs.python_files, pythonSolution);
  assert.equal(core._outputs.javascript_files, javascriptSolution);
  assert.equal(core._outputs.__failed, undefined);

  process.env.JAVA_COMPILE_RESULT = 'success';
  process.env.PYTHON_COMPILE_RESULT = 'success';
  process.env.JAVASCRIPT_COMPILE_RESULT = 'failure';
  await report({ github, context, core });

  const comment = github._state.comments.find((item) => item.issue_number === 1);
  assert.match(comment.body, /Java 컴파일: ✅ 통과/);
  assert.match(comment.body, /Python 문법 검사: ✅ 통과/);
  assert.match(comment.body, /JavaScript 문법 검사: ❌ 실패/);
  assert.equal(core._outputs.__failed, '풀이 검사 실패');
});

for (const [name, source, expectedStatus] of [
  ['정상 JavaScript는 실행 없이 문법 검사에 통과한다', 'throw new Error("풀이가 실행되면 안 됩니다");', 0],
  ['문법 오류 JavaScript는 실패 로그와 함께 보고한다', 'function solution( {', 1],
]) {
  test(name, async () => {
    const github = createMockGithub();
    const context = prContext(OWNER, REPO, 1, 'alice');
    const core = makeCore();
    const solution = `${PROBLEM_DIR}/alice/Solution with spaces.js`;
    fs.mkdirSync(path.dirname(path.join(workspace, solution)), { recursive: true });
    fs.writeFileSync(path.join(workspace, solution), source);
    github._state.prFiles = [{ filename: solution, status: 'added' }];
    await github.rest.issues.create({ title: 'JavaScript 풀이 PR' });

    await run({ github, context, core });

    assert.equal(core._outputs.java_count, '0');
    assert.equal(core._outputs.python_count, '0');
    assert.equal(core._outputs.javascript_count, '1');
    assert.equal(core._outputs.javascript_files, solution);
    const state = JSON.parse(fs.readFileSync(path.join(workspace, 'check-state.json'), 'utf8'));
    assert.deepEqual(state.warnings, []);
    assert.deepEqual(state.javascriptFiles, [solution]);
    assert.equal(state.javascriptCount, 1);

    const result = spawnSync(process.execPath, ['--check', solution], { cwd: workspace, encoding: 'utf8' });
    assert.equal(result.status, expectedStatus, result.stderr);
    process.env.JAVASCRIPT_COMPILE_RESULT = result.status === 0 ? 'success' : 'failure';
    process.env.JAVASCRIPT_COMPILE_LOG = result.stderr;
    await report({ github, context, core });

    const comment = github._state.comments.find((item) => item.issue_number === 1);
    assert.match(comment.body, /JavaScript 1개/);
    if (expectedStatus === 0) {
      assert.match(comment.body, /JavaScript 문법 검사: ✅ 통과/);
      assert.match(comment.body, /## ✅ 제출 검사 통과/);
      assert.equal(core._outputs.__failed, undefined);
    } else {
      assert.match(comment.body, /JavaScript 문법 검사: ❌ 실패/);
      assert.match(comment.body, /JavaScript 문법 검사 출력/);
      assert.match(comment.body, /SyntaxError/);
      assert.equal(core._outputs.__failed, '풀이 검사 실패');
    }
  });
}

test('JavaScript 검사 결과가 없거나 건너뛰어졌으면 통과로 보고하지 않는다', async () => {
  const github = createMockGithub();
  const context = prContext(OWNER, REPO, 1, 'alice');
  const core = makeCore();
  github._state.prFiles = [{ filename: `${PROBLEM_DIR}/alice/Solution.js`, status: 'added' }];
  await github.rest.issues.create({ title: 'JavaScript 풀이 PR' });
  await run({ github, context, core });

  for (const outcome of [undefined, 'skipped', 'cancelled']) {
    if (outcome === undefined) delete process.env.JAVASCRIPT_COMPILE_RESULT;
    else process.env.JAVASCRIPT_COMPILE_RESULT = outcome;
    await report({ github, context, core });
    const comment = github._state.comments.find((item) => item.issue_number === 1);
    assert.match(comment.body, /## ❌ 풀이 검사 실패/);
    assert.equal(core._outputs.__failed, '풀이 검사 실패');
  }
});

test('삭제된 JavaScript와 다른 사람의 JavaScript는 검사 목록에 넣지 않는다', async () => {
  const github = createMockGithub();
  const context = prContext(OWNER, REPO, 1, 'alice');
  const core = makeCore();
  github._state.prFiles = [
    { filename: `${PROBLEM_DIR}/alice/removed.js`, status: 'removed' },
    { filename: `${PROBLEM_DIR}/bob/Solution.js`, status: 'added' },
  ];
  await github.rest.issues.create({ title: '잘못된 풀이 PR' });
  await run({ github, context, core });
  assert.equal(core._outputs.javascript_count, '0');
  assert.equal(core._outputs.javascript_files, '');
  assert.equal(core._outputs.__failed, '제출 경로 규칙 위반');
});

