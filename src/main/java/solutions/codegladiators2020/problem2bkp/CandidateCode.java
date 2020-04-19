package solutions.codegladiators2020.problem2bkp;/*
 * Enter your code here. Read input from STDIN. Print your output to STDOUT.
 * Your class should be named solutions.practice.problem1.CandidateCode.
 */

import java.util.*;

public class CandidateCode {
    public static void main(String[] args) throws Exception {
        try {
            ArrayList<TestCase> testCases = acceptInput();
            testCases.forEach(TestCase::process);
        } catch (Exception ex) {
            System.out.println("Exception while running solution :: " + ex.getMessage());
            ex.printStackTrace();
            throw ex;
        }
    }

    public static ArrayList<TestCase> acceptInput() throws Exception {
        ArrayList<TestCase> testCases;
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
                TestCase testCase = new TestCase(memberCount, teamGPowers, teamOPowers);
                testCases.add(testCase);
            }
            if (testCaseCount != testCases.size()) {
                throw new Exception("Input contract violation :: testCaseCount should match the number of test-cases that follow");
            }
        }
        return testCases;
    }

    private static final class TestCase {
        private int memberCount;
        private int winCountPrediction;
        private ArrayList<Long> teamGPowers;
        private ArrayList<Long> teamOPowers;

        public TestCase(int memberCount, ArrayList<Long> teamGPowers, ArrayList<Long> teamOPowers) {
            this.memberCount = memberCount;
            this.teamGPowers = teamGPowers;
            this.teamOPowers = teamOPowers;
            winCountPrediction = 0;
        }

        public void process() {
            optimize();
            println();
        }

        public void println() {
            System.out.println(winCountPrediction);
        }

        private void optimize() {
            teamGPowers.sort(Comparator.naturalOrder());
            teamOPowers.sort(Comparator.reverseOrder());
            HashSet<Integer> pastMatchedOpponents = new HashSet<>(memberCount);
            for (int i = 0; i < memberCount; i++) {
                long gPow = teamGPowers.get(i);
                for (int j = 0; j < memberCount; j++) {
                    long oPow = teamOPowers.get(j);
                    if ((gPow > oPow) && !pastMatchedOpponents.contains(j)) {
                        pastMatchedOpponents.add(j);
                        winCountPrediction++;
                        break;
                    }
                }
            }
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            TestCase testCase = (TestCase) o;

            if (memberCount != testCase.memberCount) return false;
            if (winCountPrediction != testCase.winCountPrediction) return false;
            if (!Objects.equals(teamGPowers, testCase.teamGPowers))
                return false;
            return Objects.equals(teamOPowers, testCase.teamOPowers);
        }

        @Override
        public int hashCode() {
            int result = memberCount;
            result = 31 * result + winCountPrediction;
            result = 31 * result + (teamGPowers != null ? teamGPowers.hashCode() : 0);
            result = 31 * result + (teamOPowers != null ? teamOPowers.hashCode() : 0);
            return result;
        }
    }
}