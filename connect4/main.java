import java.util.*;

public class main {

    class Game{
        Board board;
        GameStatus gameStatus;
        Deque<Player> playersQueue;
        Player winner;

        public Game(Board board, Deque<Player> queue){
            this.board = board;
            this.playersQueue = queue;
        }



        public void startGame() throws Exception{
            while(true){
                Player currentPlayer = getCurrentPlayer();
                if(currentPlayer == null){
                    throw new Exception("player is not present");
                }
                boolean isValid = this.makeMove(currentPlayer, 2);
                if(!isValid){
                    System.out.println("Invalid Move");
                    continue;
                }
                if(board.checkWin(currentPlayer.disc, 2, 2)){
                    return ;
                } else if (board.checkTie()){
                    return ;
                }
                switchPlayer();
            }
        }



        public Player getCurrentPlayer(){
            if(!isPlayerAvailiable()) return null;
            return playersQueue.peek();
        }

        public void switchPlayer(){
            if(!isPlayerAvailiable()) return;
            playersQueue.offer(playersQueue.poll());
        }

        public boolean isPlayerAvailiable(){
            return !playersQueue.isEmpty();
        }



        public boolean makeMove(Player player, int col){
            return board.placeDisc(player.disc, col);
        }

        public boolean isGameCompleted(){
            return this.gameStatus == GameStatus.COMPLETED;
        }

        public Player getWinner(){
            return this.winner;
        }
    }

    enum GameStatus{
        COMPLETED, 
        DRAW, 
        IN_PROGRESS
    }

    class Board{
        int row;
        int column;
        Cell[][] grid;

        public Board(int row, int col){
            this.row = row;
            this.column = col;
            grid = new Cell[row][col];
        }
        /**
            - define the core logic 
            - consider edge cases 
         */
        public boolean placeDisc(Disc disc, int col){
            boolean isValidColumn = isValidColumn(col);
            if(!isValidColumn){
                return false;
            }

            int row = getValidRow(col);
            if(row == -1) {
                return false;
            }

            grid[row][col] = new Cell();

            return true;


        }

        public boolean checkTie(){
            return false;
        }

        public boolean checkWin(Disc disc, int col, int row ){
            return false;

        }

        public int getValidRow(int col){
            for(int i=0; i<this.column; i++){
                if(grid[i][col] == null) return i;
            }
            return -1;
        }

        public boolean isValidColumn(int col){
            return col>=0 && col<this.column;
        }

        public boolean isValidMove(int col){
            return false;
        }






    }
    class Player{
        Disc disc;
        int id;
    }
    class Cell{
        Disc disc;
        int id;
    }

    class Disc{
        color color;
    }
    enum color{
        RED,
        GREEN
    }
}

