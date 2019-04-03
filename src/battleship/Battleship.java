//Isaac Lichter
package battleship;

import java.util.Scanner;

public class Battleship {

    public static void main(String[] args) {

        System.out.println("Ready to play battleship? Build a paper board. When you're ready to play, press enter.");
        Scanner kb = new Scanner(System.in);

        BattleshipComputer computer = new BattleshipComputer();

        boolean realPlayerMove;

        kb.nextLine();

        realPlayerMove = (int) (Math.random() * 2) == 1;

        System.out.println(String.format("It's %s turn first!", realPlayerMove ? "your" : "my"));

        int col = -1, row = -1;

        do {
            if (realPlayerMove) {
                System.out.println("Your turn!");
                System.out.println("Enter your Move (A0 - J9).");
                String userMove = kb.next();
                if (userMove.length() >= 2) {
                    col = userMove.toUpperCase().charAt(0) - 'A';
                    row = userMove.charAt(1) - '0';
                }
                while (userMove.length() < 2 || row >= 10 || row < 0 || col >= 10 || col < 0) {
                    System.out.println("That was not a legal move. Try again. ");
                    userMove = kb.next();
                    if (userMove.length() >= 2) {
                        col = userMove.toUpperCase().charAt(0) - 'A';
                        row = userMove.charAt(1) - '0';
                    }
                }

                computer.processUserMove(row, col);
                realPlayerMove = !realPlayerMove;
            } else {
                System.out.println("My turn!");
                computer.guess();
                realPlayerMove = !realPlayerMove;
            }
        } while (!computer.isWinner());
        if (computer.isPlayerWinner()) {
            System.out.println("Congratulations! You won!");
        } else {
            System.out.println("I won! You can't beat silicon!");
        }
    }

}
