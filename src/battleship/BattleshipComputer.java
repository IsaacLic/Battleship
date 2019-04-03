//Isaac Lichter
package battleship;

import java.awt.Point;
import java.util.Scanner;

public class BattleshipComputer {

    private final BattleshipBoard computerBoard;
    private final BattleshipBoard playerBoard;
    private final BattleshipBoard playerGuesses;
    private final BattleshipBoard computerGuesses;
    private int hitsAgainstComputer;
    private int hitsAgainstPlayer;
    private boolean isInHuntMode;
    private Point startingPoint;

    public BattleshipComputer() {
        computerBoard = new BattleshipBoard();
        computerBoard.setShipPlacements();
        playerBoard = new BattleshipBoard();
        playerGuesses = new BattleshipBoard();
        computerGuesses = new BattleshipBoard();
    }

    public boolean isWinner() {
        if (hitsAgainstComputer == 17 || hitsAgainstPlayer == 17) {
            return true;
        }
        return false;
    }

    public boolean isPlayerWinner() {
        if (hitsAgainstComputer == 17) {
            return true;
        }
        return false;
    }

    public void guess() {
        Point move = selectMove();
        System.out.println((char) (move.x + 'A') + (move.y + ""));

        Scanner kb = new Scanner(System.in);
        System.out.println("Type 'H' for hit or 'M' for miss");
        String s = kb.nextLine();
        while (s.isEmpty() || (s.toUpperCase().charAt(0) != 'H' && s.toUpperCase().charAt(0) != 'M')) {
            System.out.println("That was not a valid response. Try again.");
            s = kb.nextLine();
        }
        if (s.toLowerCase().charAt(0) == 'h') {
            playerBoard.setValue(move.x, move.y, true);
            startingPoint = move;
            isInHuntMode = true;
        }
        computerGuesses.setValue(move.x, move.y, true);
    }

    private Point selectMove() {
        int xPosition;
        int yPosition;
        if (isInHuntMode) {
            xPosition = startingPoint.x;
            yPosition = startingPoint.y;

            //check to the left
            while (xPosition > 0 && computerGuesses.getValue(xPosition, yPosition) && playerBoard.getValue(xPosition, yPosition)) {
                xPosition--;
            }
            if (!computerGuesses.getValue(xPosition, yPosition)) {
                return new Point(xPosition, yPosition);
            }
            if (!playerBoard.getValue(xPosition, yPosition)) {
                xPosition++;
            }

            //check to the right
            while (xPosition < 9 && computerGuesses.getValue(xPosition, yPosition) && playerBoard.getValue(xPosition, yPosition)) {
                xPosition++;
            }
            if (!computerGuesses.getValue(xPosition, yPosition)) {
                return new Point(xPosition, yPosition);
            }
            if (!playerBoard.getValue(xPosition, yPosition)) {
                xPosition--;
            }

            //check up and down for each element in the row
            while (xPosition > 0 && computerGuesses.getValue(xPosition, yPosition) && playerBoard.getValue(xPosition, yPosition)) {

                //check up
                while (yPosition > 0 && computerGuesses.getValue(xPosition, yPosition) && playerBoard.getValue(xPosition, yPosition)) {
                    yPosition--;
                }
                if (!computerGuesses.getValue(xPosition, yPosition)) {
                    return new Point(xPosition, yPosition);
                }
                if (!playerBoard.getValue(xPosition, yPosition)) {
                    yPosition++;
                }

                //check down
                while (yPosition < 9 && computerGuesses.getValue(xPosition, yPosition) && playerBoard.getValue(xPosition, yPosition)) {
                    yPosition++;
                }
                if (!computerGuesses.getValue(xPosition, yPosition)) {
                    return new Point(xPosition, yPosition);
                }
                if (!playerBoard.getValue(xPosition, yPosition)) {
                    yPosition--;
                }

                xPosition--;
            }
            isInHuntMode = false;
        }
        do {
            xPosition = (int) (Math.random() * 5) * 2;
            yPosition = (int) (Math.random() * 5) * 2;
            if ((int) (Math.random() * 2) == 1) {
                xPosition++;
            } else {
                yPosition++;
            }
        } while (computerGuesses.getValue(xPosition, yPosition));
        
        return new Point(xPosition, yPosition);
    }

    public void processUserMove(int row, int col) {

        if (computerBoard.getValue(row, col)) {
            System.out.println("Hit!");
            if (!playerGuesses.getValue(row, col)) {
                hitsAgainstComputer++;
            }
        } else {
            System.out.println("Miss.");
        }
        playerGuesses.setValue(row, col, true);
    }

    private class BattleshipBoard {

        private boolean[][] board;

        public BattleshipBoard() {
            board = new boolean[10][10];
        }

        public boolean getValue(int x, int y) {
            return board[x][y];
        }

        public void setValue(int x, int y, boolean value) {
            board[x][y] = value;
        }

        private void setShipPlacements() {

            placeShip(5);
            placeShip(4);
            placeShip(3);
            placeShip(3);
            placeShip(2);
        }

        private void placeShip(int size) {
            int x;
            int y;
            boolean isVertical;
            boolean isValid = false;
            do {
                x = (int) (Math.random() * 10);
                y = (int) (Math.random() * 10);
                isVertical = (int) (Math.random() * 2) == 1;

                isValid = true;

                if (isVertical) {
                    if (y + size < 10) {
                        for (int index = y; index < y + size; index++) {
                            if (board[x][index]) {
                                isValid = false;
                            }
                        }
                    } else {
                        isValid = false;
                    }

                } else {
                    if (x + size < 10) {
                        for (int index = x; index < x + size; index++) {
                            if (board[index][y]) {
                                isValid = false;
                            }
                        }
                    } else {
                        isValid = false;
                    }
                }
            } while (!isValid);

            if (isVertical) {
                for (int index = y; index < y + size; index++) {
                    board[x][index] = true;
                }
            } else {
                for (int index = x; index < x + size; index++) {
                    board[index][y] = true;
                }
            }

        }
    }

}
