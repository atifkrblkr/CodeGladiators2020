package solutions.practice.problem1;/*
 * Enter your code here. Read input from STDIN. Print your output to STDOUT.
 * Your class should be named solutions.practice.problem1.CandidateCode.
 */

import java.util.HashMap;
import java.util.Objects;
import java.util.Scanner;

public class CandidateCode {
    public static void main(String[] args) throws Exception {
        try {
            process(acceptInput());
        } catch (Exception ex) {
            System.out.println("Exception while running solution :: " + ex.getMessage());
            ex.printStackTrace();
            throw ex;
        }
    }

    public static HashMap<Integer, TestCase> acceptInput() throws Exception {
        Scanner scanner = new Scanner(System.in);
        int testCaseCount = Integer.parseInt(scanner.nextLine());
        if (testCaseCount < 1 || testCaseCount > 10) {
            throw new Exception("Input contract violation :: 1<=testCaseCount<=10");
        }
        HashMap<Integer, TestCase> testCases = new HashMap<>(testCaseCount);
        for (int i = 1; scanner.hasNext(); i++) {
            String line = scanner.nextLine();
            TestCase testCase = new TestCase(line);
            testCases.put(i, testCase);
        }
        if (testCaseCount != testCases.size()) {
            throw new Exception("testCaseCount should match the number of testcases that follow");
        }
        return testCases;
    }

    public static void process(HashMap<Integer, TestCase> testCases) {
        testCases.forEach(CandidateCode::processEachTestCase);
    }

    private static void processEachTestCase(Integer i, TestCase t) {
        t.process();
    }

    private static final class TestCase {
        private int v;
        private int c;
        private int p;
        private boolean isValid;
        private String line;

        public TestCase(String line) {
            this.line = line;
            this.v = -1;
            this.c = -1;
            this.p = -1;
            this.isValid = false;
        }

        public void process() {
            validate();
            if (isValid) {
                count();
                setProduct();
                println();
            }
        }

        public void println() {
            System.out.println(v + " " + c + " " + p);
        }

        private void validate() {
            if (!(line == null || line.length() == 0 || line.length() > 1000)) {
                v = 0;
                c = 0;
                p = 0;
                isValid = true;
            }
        }

        private void count() {
            for (int j = 0; j < line.length(); j++) {
                char x = line.toLowerCase().charAt(j);
                if (isVowel(x)) {
                    v++;
                } else if (isAlpha(x)) {
                    c++;
                }
            }
        }

        private boolean isVowel(char x) {
            switch (x) {
                case 'a':
                case 'e':
                case 'i':
                case 'o':
                case 'u':
                    return true;
                default:
                    return false;
            }
        }

        private boolean isAlpha(char x) {
            return ((int) x > 64 && (int) x < 91) || ((int) x > 96 && (int) x < 123);
        }

        private void setProduct() {
            this.p = this.c * this.v;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            TestCase testCase = (TestCase) o;

            if (v != testCase.v) return false;
            if (c != testCase.c) return false;
            if (p != testCase.p) return false;
            if (isValid != testCase.isValid) return false;
            return Objects.equals(line, testCase.line);
        }

        @Override
        public int hashCode() {
            int result = v;
            result = 31 * result + c;
            result = 31 * result + p;
            result = 31 * result + (isValid ? 1 : 0);
            result = 31 * result + (line != null ? line.hashCode() : 0);
            return result;
        }
    }
}
