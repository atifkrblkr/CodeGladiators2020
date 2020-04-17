package solutions.practice.problem3;/*
 * Enter your code here. Read input from STDIN. Print your output to STDOUT.
 * Your class should be named solutions.practice.problem1.CandidateCode.
 */

import java.util.ArrayList;
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
        String cipher = "";
        String key;
        for (int i = 0; scanner.hasNext(); i++) {
            String line = scanner.nextLine();
            if (i % 2 == 0) {
                cipher = line;
            } else {
                key = line;
                TestCase testCase = new TestCase(key, cipher);
                testCases.add(testCase);
            }
        }
        if (testCaseCount != testCases.size()) {
            throw new Exception("testCaseCount should match the number of testcases that follow");
        }
        return testCases;
    }

    private static final class TestCase {
        private boolean isValid;
        private String key;
        private String cipher;
        private char[] decipher;

        public TestCase(String key, String cipher) {
            this.key = key;
            this.cipher = cipher;
            isValid = false;
        }

        public void process() {
            validate();
            if (isValid) {
                decipher();
                println();
            }
        }

        public void println() {
            System.out.println(String.valueOf(decipher));
        }

        private void validate() {
            if (!(cipher == null || cipher.length() == 0 || cipher.length() > 100)) {
                if (!(key == null || key.length() == 0 || key.length() > cipher.length())) {
                    isValid = true;
                    decipher = new char[cipher.length()];
                }
            }
        }

        private void decipher() {
            for (int j = 0; j < cipher.length(); j++) {
                char x = cipher.charAt(j);
                int y = Integer.parseInt(String.valueOf(key.charAt(j)));
                decipher[j] = shiftChar(x, y);
            }
        }

        private char shiftChar(char x, int y) {
            if (y % 2 == 0) {
                int z = (int) x + y;
                if (z > 122) {
                    z -= 26;
                }
                return (char) z;
            } else {
                int z = (int) x - y;
                if (z < 97) {
                    z += 26;
                }
                return (char) z;
            }
        }

    }
}
