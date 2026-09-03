import assert from 'node:assert/strict';
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
  assert.equal(core._outputs.__failed, undefined);

  process.env.PYTHON_COMPILE_RESULT = 'success';
  await report({ github, context, core });

  const comment = github._state.comments.find((item) => item.issue_number === 1);
  assert.match(comment.body, /Python 문법 검사: ✅ 통과/);
});

test('LeetCode Java와 Python 풀이를 모두 제출 경로로 검사한다', async () => {
  const github = createMockGithub();
  const context = prContext(OWNER, REPO, 1, 'alice');
  const core = makeCore();
  const problemDir = 'solutions/week-01/leetcode-1';
  const javaSolution = `${problemDir}/alice/Solution.java`;
  const pythonSolution = `${problemDir}/alice/Solution.py`;
  github._state.prFiles = [
    { filename: javaSolution, status: 'added' },
    { filename: pythonSolution, status: 'added' },
  ];
  await github.rest.issues.create({ title: 'LeetCode 풀이 PR' });

  await run({ github, context, core });

  assert.equal(core._outputs.java_count, '1');
  assert.equal(core._outputs.python_count, '1');
  assert.equal(core._outputs.__failed, undefined);
});

