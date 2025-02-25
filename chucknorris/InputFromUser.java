package chucknorris;

import java.util.Scanner;

public class InputFromUser {

    private static final Scanner scanner = new Scanner(System.in);


    // get input string until it's valid
    static String getInputString() {
        System.out.println("Input string: ");
        String s;
        while (true) {
            s = scanner.nextLine();
            if (isValid(s)) {
                return s;
            } else {
                System.out.println("Error! Enter only alphanumeric, punctuation marks or spaces!");
            }
        }
    }

    public static boolean isValid(String input) {
        // Loop through each character of the string
        for (int i = 0; i < input.length(); i++) {
            char ch = input.charAt(i);
            // Check if the character is alphabetic or punctuation
            if (!Character.isLetterOrDigit(ch) && !isPunctuation(ch) && (ch != ' ')) {
                return false; // Return false if the character is not valid
            }
        }
        return true; // Return true if all characters are valid
    }

    // Helper method to check if a character is a punctuation mark
    public static boolean isPunctuation(char ch) {
        return (ch >= 33 && ch <= 47) || (ch >= 58 && ch <= 64) ||
                (ch >= 91 && ch <= 96) || (ch >= 123 && ch <= 126);
    }

    // return the input with space between characters
    static String showPhrase(String s) {
        if (s.isEmpty()) {
            return "";
        }
        char[] characters = s.toCharArray();
        StringBuilder sb = new StringBuilder();
        for (char c : characters) {
            sb.append(c).append(" ");
        }
        return String.valueOf(sb);
    }

    // return each letter in the input into its binary corespondent
    static String StringIntoBinary(String input) {
        //System.out.println("The result:");
        StringBuilder binaryString = new StringBuilder();
        for (char c : input.toCharArray()) {
            String binary = Integer.toBinaryString(c);
            // Ensure each character is represented by 7 bits
            while (binary.length() < 7) {
                binary = "0" + binary;
            }
            binaryString.append(binary);
        }
        return String.valueOf(binaryString);
    }

    static String StringIntoUnary(String input) {
        StringBuilder encodedMessage = new StringBuilder();
        String binaryString = StringIntoBinary(input);
        int i = 0;
        while (i < binaryString.length()) {
            char currentBit = binaryString.charAt(i);
            int count = 1;

            // Count the number of consecutive same bits
            while (i + count < binaryString.length() && binaryString.charAt(i + count) == currentBit) {
                count++;
            }

            // Append the encoded block
            encodedMessage.append(currentBit == '1' ? "0 " : "00 ");
            encodedMessage.append("0".repeat(count)).append(" ");

            // Move to the next bit sequence
            i += count;
        }

        // Step 3: Print the result (remove the trailing space)
        return String.valueOf(encodedMessage);
    }
}


