package chucknorris;

import java.util.Scanner;

public class Main {


    public static void main(String[] args) {
        String input  =InputFromUser.getInputString();

       // System.out.println(InputFromUser.StringIntoBinary(input));
        System.out.println("The Result:");
        System.out.println(InputFromUser.StringIntoUnary(input));
    }




}