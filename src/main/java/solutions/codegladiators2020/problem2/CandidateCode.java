package solutions.codegladiators2020.problem2;/*
 * Enter your code here. Read input from STDIN. Print your output to STDOUT.
 * Your class should be named solutions.practice.problem1.CandidateCode.
 */

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.*;

public class CandidateCode {
    public static void main(String[] args) throws Exception {
        try {
            ArrayList<TestCaseWorker> testCaseWorkers = acceptInput();
            ExecutorService executorService = Executors.newFixedThreadPool(10);
            List<Future<TestCase>> processedTestCases = executorService.invokeAll(testCaseWorkers);
            executorService.awaitTermination(800, TimeUnit.MILLISECONDS);
            executorService.shutdown();
            ArrayList<TestCase> testCases = new ArrayList<>(testCaseWorkers.size());
            for (Future<TestCase> f : processedTestCases) {
                TestCase testCase = f.get();
                testCases.add(testCase);
            }
            testCases.sort(Comparator.naturalOrder());
            for (TestCase t : testCases) {
                t.println();
            }
        } catch (Exception ex) {
            System.out.println("Exception while running solution :: " + ex.getMessage());
            ex.printStackTrace();
            throw ex;
        }
    }

    public static ArrayList<TestCaseWorker> acceptInput() throws Exception {
        ArrayList<TestCaseWorker> testCases;
        try (Scanner scanner = new Scanner(System.in)) {
            String line = scanner.nextLine();
            int testCaseCount = Integer.parseInt(line);
            if (testCaseCount < 1 || testCaseCount > 100000) {
                throw new Exception("Input contract violation :: 1 <= T <= 100000");
            }
            testCases = new ArrayList<>(testCaseCount);
            for (int i = 0; i < testCaseCount; i++) {
                line = scanner.nextLine();
                int memberCount = Integer.parseInt(line);
                if (memberCount < 1 || memberCount > 100000) {
                    throw new Exception("Input contract violation :: 1 <= N <= 100000");
                }
                line = scanner.nextLine();
                String[] split1 = line.split(" ");
                line = scanner.nextLine();
                String[] split2 = line.split(" ");
                ArrayList<Long> teamGPowers = new ArrayList<>(memberCount);
                ArrayList<Long> teamOPowers = new ArrayList<>(memberCount);
                for (int j = 0; j < memberCount; j++) {
                    long powerOfBB1 = Long.parseLong(split1[j]);
                    long powerOfBB2 = Long.parseLong(split2[j]);
                    if (powerOfBB1 < 0 || powerOfBB2 < 0) {
                        throw new Exception("Input contract violation :: 0 <= Power of Beyblade <= LLONG_MAX");
                    }
                    teamGPowers.add(powerOfBB1);
                    teamOPowers.add(powerOfBB2);
                }
                TestCase testCase = new TestCase(i, memberCount, teamGPowers, teamOPowers);
                TestCaseWorker testCaseWorker = new TestCaseWorker(testCase);
                testCases.add(testCaseWorker);
            }
            if (testCaseCount != testCases.size()) {
                throw new Exception("Input contract violation :: testCaseCount should match the number of test-cases that follow");
            }
        }
        return testCases;
    }

    private static final class TestCase implements Comparable<TestCase> {
        private int srNum;
        private int memberCount;
        private int winCountPrediction;
        private ArrayList<Long> teamGPowers;
        private ArrayList<Long> teamOPowers;

        public TestCase(int srNum, int memberCount, ArrayList<Long> teamGPowers, ArrayList<Long> teamOPowers) {
            this.srNum = srNum;
            this.memberCount = memberCount;
            this.teamGPowers = teamGPowers;
            this.teamOPowers = teamOPowers;
            winCountPrediction = 0;
        }

        public void process() {
            optimize();
        }

        public void println() {
            System.out.println(winCountPrediction);
        }

        private void optimize() {
            teamGPowers.sort(Comparator.naturalOrder());
            teamOPowers.sort(Comparator.reverseOrder());
            ArrayList<Integer> pastMatchedOpponents = new ArrayList<>(memberCount);
            for (int i = 0; i < memberCount; i++) {
                long gPow = teamGPowers.get(i);
                for (int j = 0; j < memberCount; j++) {
                    long oPow = teamOPowers.get(j);
                    if (!pastMatchedOpponents.contains(j) && gPow > oPow) {
                        pastMatchedOpponents.add(j);
                        winCountPrediction++;
                        break;
                    }
                }
            }
        }

        @Override
        public int compareTo(TestCase testCase) {
            return Integer.valueOf(this.srNum).compareTo(testCase.srNum);
        }
    }

    private static final class TestCaseWorker implements Callable<TestCase> {
        TestCase testCase;

        TestCaseWorker(TestCase testCase) {
            this.testCase = testCase;
        }

        @Override
        public TestCase call() {
            testCase.process();
            return testCase;
        }
    }
}