package lab5;

//Пользователь вводит некоторое число. Записать его цифры в стек.
//Вывести число, у которого цифры идут в обратном порядке. Предусмотреть возможность введения произвольного количества чисел.

import javax.swing.text.html.parser.Parser;
import java.util.ArrayDeque;
import java.util.Scanner;

public class Main {
    public static int mathRandom(int min, int max) {
        return (int)(Math.random() * (max - min) + min);
    }

    public static boolean initDeque(String str, ArrayDeque<ArrayDeque<Character>> deque) {
        String temp = "";
        for (int i = 0; i < str.length(); ++i) {
            if (str.charAt(i) < 48 && str.charAt(i) != ' ' || str.charAt(i) > 57) {             //if isn't number or space
                deque.clear();
                return false;
            } else if (str.charAt(i) >= 48 && str.charAt(i) <= 57) {                            //if number
                temp += str.charAt(i);
            }
            if ((str.charAt(i) == ' ' || i + 1 == str.length()) && temp.compareTo("") != 0) {   //add new number
                ArrayDeque<Character> newNumber = new ArrayDeque<>();
                for (int j = 0; j < temp.length(); ++j) //push digits
                    newNumber.push(temp.charAt(j));
                deque.push(newNumber);                  //push number
                temp = "";
            }
        }
        return true;
    }

    public static void main(String[] args) {
        try {
            ArrayDeque<ArrayDeque<Character>> deque = new ArrayDeque<>();
            Scanner scanner = new Scanner(System.in);
            //System.out.println("Input number: ");
            String s = "";

            //--------new lines---------
//            for (int i = 0; i < mathRandom(3, 15); ++i)
//                s += Integer.toString(mathRandom(100, 999)) + " ";
//            System.out.println(s);
//            initDeque(s, deque);
            //--------------------------

            //------new lines 2---------
            s = scanner.nextLine();
            System.out.println("Input number: ");
            System.out.println(s);
            if (!initDeque(s, deque)) {
                System.out.println("The numbers isn't correct! Use decimal integers and spaces only!");
                return;
            }
            //--------------------------

            //do {
                //System.out.println((s == "") ? "" : "The numbers isn't correct! Use decimal integers and spaces only!");
            //s = scanner.nextLine();
            //} while (!initDeque(s, deque));


            //output and clear deque of deque
            while (!deque.isEmpty()) {
                while (!deque.getFirst().isEmpty())
                    System.out.print(deque.getFirst().pop());
                System.out.print(" ");
                deque.pop();
            }
            scanner.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}