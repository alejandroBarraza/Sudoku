package cl.ucn.disc.soduku;

import org.apache.commons.lang3.time.StopWatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.*;
import java.util.*;

/**
 * @author alejandro barraza
 */
public class Main {
    private static  final Logger log = LoggerFactory.getLogger(Main.class);
    public static void main(String[] args) throws InterruptedException, ExecutionException {

        int n=9;

        int[][] board={ { 3, 0, 6, 5, 0, 8, 4, 0, 0 },
                { 5, 2, 0, 0, 0, 0, 0, 0, 0 },
                { 0, 8, 7, 0, 0, 0, 0, 3, 1 },
                { 0, 0, 3, 0, 1, 0, 0, 8, 0 },
                { 9, 0, 0, 8, 6, 3, 0, 0, 5 },
                { 0, 5, 0, 0, 9, 0, 6, 0, 0 },
                { 1, 3, 0, 0, 0, 0, 2, 5, 0 },
                { 0, 0, 0, 0, 0, 0, 0, 7, 4 },
                { 0, 0, 5, 2, 0, 6, 3, 0, 0 }};

//        int[][] board={ { 3, 0, 6, 5, 0, 8, 4, 0, 0, 3, 0, 6, 5, 0, 8, 4},
//                { 5, 2, 0, 0, 0, 0, 0, 0, 0, 3, 0, 6, 5, 0, 8, 4},
//                { 0, 8, 7, 0, 0, 0, 0, 3, 1, 3, 0, 6, 5, 0, 8, 4},
//                { 0, 0, 3, 0, 1, 0, 0, 8, 0, 3, 0, 6, 5, 0, 8, 4},
//                { 9, 0, 0, 8, 6, 3, 0, 0, 5, 3, 0, 6, 5, 0, 8, 4},
//                { 0, 5, 0, 0, 9, 0, 6, 0, 0, 3, 0, 6, 5, 0, 8, 4},
//                { 1, 3, 0, 0, 0, 0, 2, 5, 0, 3, 0, 6, 5, 0, 8, 4},
//                { 0, 0, 0, 0, 0, 0, 0, 7, 4, 3, 0, 6, 5, 0, 8, 4},
//                { 0, 0, 5, 2, 0, 6, 3, 0, 0, 3, 0, 6, 5, 0, 8, 4},
//                { 3, 0, 6, 5, 0, 8, 4, 0, 0, 3, 0, 6, 5, 0, 8, 4},
//                { 5, 2, 0, 0, 0, 0, 0, 0, 0, 3, 0, 6, 5, 0, 8, 4},
//                { 0, 8, 7, 0, 0, 0, 0, 3, 1, 3, 0, 6, 5, 0, 8, 4},
//                { 0, 0, 3, 0, 1, 0, 0, 8, 0, 3, 0, 6, 5, 0, 8, 4},
//                { 9, 0, 0, 8, 6, 3, 0, 0, 5, 3, 0, 6, 5, 0, 8, 4},
//                { 0, 5, 0, 0, 9, 0, 6, 0, 0, 3, 0, 6, 5, 0, 8, 4},
//                { 1, 3, 0, 0, 0, 0, 2, 5, 0, 3, 0, 6, 5, 0, 8, 4}};



        final StopWatch time = StopWatch.createStarted();
        Sudoku sudoku = new Sudoku(board,n);
        if(sudoku.Solve()){
            sudoku.printSudoku();
        }
        else {
            log.debug("no se pudo resolver");
        }
        log.debug("el tiempo es de {} " , time);
    }



    /**
     *
     * Sudoku class contains all atributes from a classical sudoku.This class contains all necesary
     * methods for resolve a sudoku ,as Solve,IsColumnSafe,IsColumsafe and IsValid.
     * Is Valid is a method executed in parallel though Callable for check is a Number is valid to store in the board.
     *
     */

    private static class Sudoku{

        private final int[][] board;
        public final int n;

        /**
         * Constructor from sudoku Class.
         * @param sudoku : get the board from a classical sudoku and storage in the array.
         * @param n: long from a sudoku board. in a 9x9 sudoku , n should be set as 9.
         */

        public Sudoku(int sudoku[][],int n){
            this.board=sudoku;
            this.n=n;
        }

        /**
         * Solve is a Method that can resolve a sudoku.
         * Basically iterate in column and row up to the lenght of current board.(9x9) in this case with n=9.
         * after iterate over column and row check for an " 0 ",if find it, means u can storage a number.
         *after check u can storage a number,enter in a loop from 0 until n for decide what number u should store.
         * depending if is a valid number.if is valid,storage the number in teh board and backtracking Solve.
         * is not the case. insert a 0 in the board.
         //* @param n : argument receive from the long of the board.
         * @return true in case is resolved.return false in case that soduku has no solution.
         * @throws ExecutionException
         * @throws InterruptedException
         */

        private boolean Solve() throws ExecutionException, InterruptedException {
            for (int row = 0; row <this.n ; row++) {
                for (int column = 0; column <this.n ; column++) {
                    if (board[row][column] == 0){
                        for (int number = 1; number <=this.n ; number++) {
                            if (isValid(row,column,number)){
                                board[row][column] = number;
                                if(Solve()) {
                                    return true;
                                }
                                else{
                                    board[row][column]= 0;

                                }


                            }

                        }
                        return false;
                    }
                }
            }
            return true;

        }

        /**
         * check if a row is safe in actual number passed in as num
         * @param row current row from board
         * @param num current num from board
         //* @param n length of the board.
         * @return true is case a row is safe(the number not include in the row), in the another way return false;
         */

        private  boolean isRowSafe(int row,int num){
            for (int i = 0; i <this.n ; i++) {
                if(board[row][i]==num){
                    return false;

                }
            }
            return true;
        }

        /**
         * check if column is save in actual row passed in as num.
         * @param column current column from board
         * @param num current num
         //* @param n length of board.
         * @return is number is included in current column return false,another case return true;
         */

        private  boolean isColumnSafe(int column,int num){
            for (int i = 0; i <this.n ; i++) {
                if(board[i][column]==num){
                    return false;
                }
            }
            return true;
        }

        /**
         * check if a grid is safe in current number
         * @param row cuurent row from board.
         * @param colum current column from board.
         * @param num current num.
         * @return  if current number is content it in current row is not safe return false, another case return true;
         */

        private boolean isGradeSafe(int row, int colum , int num){


            for (int i = 0; i <Math.sqrt(board.length) ; i++) {
                for (int j = 0; j < Math.sqrt(board.length); j++) {
                    if (board[i+row][j+colum] == num){
                        return false;
                    }
                }
            }
            return true;
        }

        /**
         * isValid is a parallel method that check if current number is valid in(row,coljmn,gird)
         * using all the another previous
         * methods(row,column and grid).Use Callabe and not runnable because, i'd like to get a boolean(true,false)
         * in case all method that has been executed in a thread(isColumnSafe,isRowSafe,isGradeSafe) are valid
         * to storage.So Create a Arraylist for each thread and store this threadPool in a field,
         * so doesn't create it everytime.at the end we use InvokeAll ,the purpose is waiting for the three check
         * (column,row and grade.)
         * @param row cuurent row from board.
         * @param column current column from board.
         * @param num current num
         //* @param n length from board.
         * @return false in case
         * @throws InterruptedException
         * @throws ExecutionException
         */

        private boolean isValid(int row, int column, int num) throws InterruptedException, ExecutionException {
            List<Callable<Boolean>> tests = new ArrayList<>();
            tests.add(() -> isColumnSafe(column, num));
            tests.add(() -> isRowSafe(row, num));
            tests.add(() -> isGradeSafe((int) (row - row % Math.sqrt(board.length)),
                                        (int) (column - column % Math.sqrt(board.length)), num));

            /* store this threadPool in a field so doesn't create it everytime*/
            ExecutorService executorService = Executors.newFixedThreadPool(1);
            List<Future<Boolean>> results = executorService.invokeAll(tests);
            executorService.shutdown();
            for (Future<Boolean> future : results) {
                if(!Boolean.TRUE.equals(future.get())){
                    return false;
                }
            }
            //System.out.println(executorService);
            return true;
        }

        /**
         * print the solution
         */
        public void printSudoku(){
            for (int row = 0; row < board.length; row++) {
                if (row % Math.sqrt(board.length) == 0) {
                    System.out.println();
                }
                for (int col = 0; col < board.length; col++) {
                    if (col % Math.sqrt(board.length) == 0) {
                        System.out.print(" ");
                    }
                    System.out.print(board[row][col] + " ");

                }
                System.out.println();
            }
        }

    }
}


