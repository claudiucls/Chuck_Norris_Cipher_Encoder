package chucknorris;

import java.util.ArrayList;
import java.util.Scanner;

public class InputFromUser {

    private static final Scanner scanner = new Scanner(System.in);


    static void showMenu() {
        String option;
        while (true) {
            System.out.println("Please input operation (encode/decode/exit):");
            option = scanner.nextLine();
            switch (option) {
                case "encode" -> System.out.printf("Encoded string:%n%s%n", StringIntoUnary(getInputString(false)));
                case "decode" -> {
                    String encodeMessage = unaryToBinary(getInputString(true));
                    if (encodeMessage.isEmpty()){
                        System.out.println("Encoded string is not valid.");
                        continue;
                    }
                    System.out.printf("Decoded string:%n%s%n", encodeMessage);
                }
                case "exit" -> {
                    scanner.close();
                    System.out.println("Bye");
                    System.exit(0);
                }
                default -> System.out.printf("There is no '%s' operation%n", option);
            }
        }


    }

    // get input string until it's valid
    static String getInputString(boolean encode) {
        String s;
        while (true) {
            System.out.println(encode ? "Input encoded string: " : "Input string: ");
            s = scanner.nextLine();
            if (isValid(s)) {
                return s;
            } else {
                System.out.println("Error! Input not valid! Enter only alphanumeric, punctuation marks or spaces!");
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

    //This method converts encoded Unary into a String
    static String unaryToBinary(String encodedMessage) {
        if (!isValidUnaryFormat(encodedMessage)) {
            return "";
        }
        StringBuilder binaryString = new StringBuilder();
        String[] blocks = encodedMessage.split(" ");

        for (int i = 0; i < blocks.length; i += 2) {
            String typeBlock = blocks[i]; // "0" or "00"
            String countBlock = blocks[i + 1]; // Sequence of "0"s

            char bit = (typeBlock.equals("0")) ? '1' : '0'; // Determine the bit
            int count = countBlock.length(); // Number of bits

            // Append the bits to the binary string
            binaryString.append(String.valueOf(bit).repeat(count));
        }
        if(binaryString.length()%7==0) {
            return binaryToString(binaryString.toString());
        } else
            return "";
    }

    // Converts a binary string into the original ASCII string
    static String binaryToString(String binaryString) {
        ArrayList<String> list = new ArrayList<>();
        StringBuilder returnString = new StringBuilder();

        int start = 0;
        int end = 7;

        while (end <= binaryString.length()) {
            list.add(binaryString.substring(start, end));
            start = end;
            end = start + 7;
        }


        for (String s : list) {

            int charCode = Integer.parseInt(s, 2);
            char asciiToChar = (char) charCode;
            returnString.append(asciiToChar);
        }
        return returnString.toString();
    }

    // check if encodedMessage consist of blocks of one 0 or more 0's
    static boolean isValidUnaryFormat(String encodedMessage) {
        String[] blocks = encodedMessage.split(" ");

        // The number of blocks must be even (pairs of type and count blocks)
        if (blocks.length % 2 != 0) {
            return false;
        }

        // Check each pair of blocks
        for (int i = 0; i < blocks.length; i += 2) {
            String typeBlock = blocks[i]; // Should be "0" or "00"
            String countBlock = blocks[i + 1]; // Should consist of one or more "0"s

            // Validate the type block
            if (!typeBlock.equals("0") && !typeBlock.equals("00")) {
                return false;
            }

            // Validate the count block
            if (!countBlock.matches("0+")) { // Check if it contains only "0"s
                return false;
            }
        }

        return true;
    }


}


