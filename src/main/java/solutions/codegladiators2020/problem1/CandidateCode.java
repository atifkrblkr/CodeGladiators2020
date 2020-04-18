package solutions.codegladiators2020.problem1;/*
 * Enter your code here. Read input from STDIN. Print your output to STDOUT.
 * Your class should be named solutions.practice.problem1.CandidateCode.
 */

import java.util.HashSet;
import java.util.Scanner;

public class CandidateCode {
    private static HashSet<Long> make = null;

    public static void main(String[] args) throws Exception {
        try {
            acceptInput();
            System.out.println(make.stream().min(Long::compareTo).get());
        } catch (Exception ex) {
            System.out.println("Exception while running solution :: " + ex.getMessage());
            ex.printStackTrace();
            throw ex;
        }
    }

    public static void acceptInput() throws Exception {
        try (Scanner scanner = new Scanner(System.in)) {
            String line = scanner.nextLine();
            int n = Integer.parseInt(line);
            if (n < 1 || n > 10000000) {
                throw new Exception("Input contract violation :: 1<= N <=10000000 (1e7)");
            }
            make = new HashSet<>(n);
            line = scanner.nextLine();
            String[] reqQuants = line.split(" ");
            line = scanner.nextLine();
            String[] availQuants = line.split(" ");
            for (int i = 0; i < n; i++) {
                long reqQuant = Long.parseLong(reqQuants[i]);
                long availQuant = Long.parseLong(availQuants[i]);
                if (reqQuant < 0 || availQuant < 0) {
                    throw new Exception("Input contract violation :: 0<= Quantity_of_ingredient <= LONG_MAX");
                }
                make.add(availQuant / reqQuant);
            }
        }
    }
}
