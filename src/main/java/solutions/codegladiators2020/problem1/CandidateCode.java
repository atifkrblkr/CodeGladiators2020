package solutions.codegladiators2020.problem1;/*
 * Enter your code here. Read input from STDIN. Print your output to STDOUT.
 * Your class should be named solutions.practice.problem1.CandidateCode.
 */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

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
        Scanner scanner = new Scanner(System.in);
        int testCaseCount = Integer.parseInt(scanner.nextLine());
        if (testCaseCount < 1 || testCaseCount > 10) {
            throw new Exception("Input contract violation :: 1<=testCaseCount<=10");
        }
        ArrayList<TestCase> testCases = new ArrayList<>(testCaseCount);
        int empCount = 0;
        String empNames;
        for (int i = 0; scanner.hasNext(); i++) {
            String line = scanner.nextLine();
            if (i % 2 == 0) {
                empCount = Integer.parseInt(line);
            } else {
                empNames = line;
                TestCase testCase = new TestCase(empCount, empNames);
                testCases.add(testCase);
            }
        }
        if (testCaseCount != testCases.size()) {
            throw new Exception("testCaseCount should match the number of testcases that follow");
        }
        return testCases;
    }

    private static final class TestCase {
        private int empCount;
        private String empNames;
        private boolean isValid;
        private char[] anagram;
        private HashMap<Character, Integer> trace;
        private char lonely;

        public TestCase(int empCount, String empNames) {
            this.empCount = empCount;
            this.empNames = empNames;
            isValid = false;
            lonely = 0;
        }

        public void process() {
            validate();
            if (isValid) {
                play();
                println();
            }
        }

        public void println() {
            System.out.println(String.valueOf(anagram));
            System.out.println("I am Alone " + lonely);
        }

        private void validate() {
            if (!(empNames == null || empNames.length() == 0 || empNames.length() > 1000)) {
                if (!(empCount < 3 || empCount > 1001)) {
                    isValid = true;
                    anagram = new char[empCount];
                    trace = new HashMap<>(empCount);
                }
            }
        }

        private void play() {
            String[] names = empNames.split(" ");
            for (int j = 0; j < names.length; j++) {
                char x = names[j].charAt(0);
                anagram[j] = x;
                if (!trace.containsKey(x)) {
                    trace.put(x, 1);
                } else {
                    int k = trace.get(x);
                    trace.put(x, k + 1);
                }
            }
            trace.forEach((k, v) -> {
                if (v == 1) {
                    lonely = k;
                }
            });
        }

    }
}
