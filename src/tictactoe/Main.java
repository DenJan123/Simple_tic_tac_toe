package tictactoe;

import java.util.Objects;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // because of the previous tasks games works through "_________" string.
        // It's going to be a good practice one day to remake this code )
        Scanner sc = new Scanner(System.in);
        String grid = "_________";
        Printer pr = new Printer();
        pr.printGame(grid);
        Analyzer an = new Analyzer();
        Game game = new Game();
        boolean validInput;
        String inpCoord = "";
        String gameSt = "";
        boolean finishedGame = false;
        while (!finishedGame) {
            do {
                String player = "X";
                System.out.print("Enter the coordinates: ");
                inpCoord = sc.nextLine();
                validInput = an.checkUserGridInputFlow(inpCoord, grid);
                if (!validInput) {
                    continue;
                }
                grid = game.changeGrid(grid, inpCoord, player);
                pr.printGame(grid);
                gameSt = an.gameState(grid);
                if (Objects.equals(gameSt, "X wins") ||
                        Objects.equals(gameSt, "O wins") ||
                        Objects.equals(gameSt, "Draw")) {
                    finishedGame = true;
                    break;
                }
            } while (!validInput);
            if (finishedGame) {
                break;
            }

            do {
                String player = "O";
                System.out.print("Enter the coordinates: ");
                inpCoord = sc.nextLine();
                validInput = an.checkUserGridInputFlow(inpCoord, grid);
                if (!validInput) {
                    continue;
                }
                grid = game.changeGrid(grid, inpCoord, player);
                pr.printGame(grid);
                gameSt = an.gameState(grid);
                if (Objects.equals(gameSt, "X wins") ||
                        Objects.equals(gameSt, "O wins") ||
                        Objects.equals(gameSt, "Draw")) {
                    finishedGame = true;
                    break;
                }
            } while (!validInput);
        }
        System.out.println(gameSt);
    }


    static class Game {
        public String changeGrid(String grid, String coordinates, String player) {
            int x, y, pos;
            x = Integer.parseInt(coordinates.substring(0, 1)) - 1;
            y = coordinates.charAt(2) - '0' - 1;
            pos = x * 3 + y;
            grid = grid.substring(0, pos) + player + grid.substring(pos + 1);
            return grid;
        }


    }

    static class Printer {
        public void printGame(String pat) {
            System.out.println("---------");
            System.out.print("| ");
            for (int i = 0; i < pat.length(); i++) {
                if ((i) % 3 == 0 && i != 0) {
                    System.out.print("|");
                    System.out.println();
                    System.out.print("| ");
                }
                System.out.print(pat.charAt(i) + " ");
            }
            System.out.print("|");
            System.out.println();
            System.out.println("---------");
        }
    }

    static class Analyzer {
        public boolean checkUserGridInputFlow(String userInput, String grid) {
            boolean inpN = validInputMoveCoordinates(userInput);
            if (!inpN) {
                return false;
            }
            boolean checkPosition = cellOccupied(userInput, grid);
            if (checkPosition) {
                return false;
            }
            return true;
        }

        public String gameState(String pat) {
            boolean hasEmptyCell = pat.contains("_");
            boolean hasWinCombX = checkWinComb("X", pat);
            boolean hasWinCombO = checkWinComb("O", pat);
            boolean tooManyChars = Math.abs(countCharOccurrences('X', pat) - countCharOccurrences('O', pat)) >
                    1 ? true : false;
            String res;
            if (tooManyChars || (hasWinCombO && hasWinCombX)) {
                res = "Impossible";
            } else if (hasWinCombO) {
                res = "O wins";
            } else if (hasWinCombX) {
                res = "X wins";
            } else if (hasEmptyCell) {
                res = "Game not finished";
            } else if (!hasEmptyCell && (!hasWinCombO || !hasWinCombX)) {
                res = "Draw";
            } else {
                res = "Some mistake here";
            }

            return res;
        }

        public boolean checkWinComb(String ch, String pat) {
            boolean winComb = false;
            String r1 = String.format("%s%s%s......", ch, ch, ch);
            String r2 = String.format("...%s%s%s...", ch, ch, ch);
            String r3 = String.format("......%s%s%s", ch, ch, ch);
            String r4 = String.format("%s..%s..%s..", ch, ch, ch);
            String r5 = String.format(".%s..%s..%s.", ch, ch, ch);
            String r6 = String.format("..%s..%s..%s", ch, ch, ch);
            String r7 = String.format("%s...%s...%s", ch, ch, ch);
            String r8 = String.format("..%s.%s.%s..", ch, ch, ch);
            String[] winningPatterns = {r1, r2, r3, r4, r5, r6, r7, r8};
            for (String regPat :
                    winningPatterns) {
                if (pat.matches(regPat)) {
                    winComb = true;
                    break;
                }
            }
            return winComb;
        }

        public int countCharOccurrences(char ch, String pat) {
            int count = 0;
            for (int i = 0; i < pat.length(); i++) {
                if (pat.charAt(i) == ch) {
                    count++;
                }
            }
            return count;
        }

        public boolean validInputMoveCoordinates(String userInput) {
            boolean result = userInput.matches("\\d \\d");
            if (!result) {
                System.out.println("You should enter numbers !");
                return result;
            }
            result = userInput.matches("[123] [123]");
            if (!result) {
                System.out.println("Coordinates should be from 1 to 3 !");
            }
            return result;
        }

        public boolean cellOccupied(String userInput, String grid) {
            boolean result = false;
            int x, y;
            x = Integer.parseInt(userInput.substring(0, 1)) - 1;
            y = userInput.charAt(2) - '0' - 1; // this one is interesting '0' == 48
            if (!(grid.charAt(x * 3 + y) == '_')) {
                System.out.println("This cell is occupied! Choose another one!");
                result = true;
            } else {
                result = false;
            }
            return result;
        }
    }
}
