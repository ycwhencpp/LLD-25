import java.util.*;
class main{
    public static void main(String[] args) {
        
        game game = new game(8);
        game.startGame();


    }
}

    class game {
        board board;
        Queue<player> players = new LinkedList<>();
        gameStatus gameStatus;
        public game(int gamesize) {
            this.board = new board(gamesize);
            players.offer(new player(1, color.BLACK));
            players.offer(new player(2, color.WHITE));
            this.gameStatus = gameStatus.ONGOING;
        }

        public void startGame(){
            System.out.println("starting game");
        }
    }

    class board {
        cell[][] matrix;
        public board (int size) {
            this.matrix = new cell[size][size];
            init();
        }

        public void init() {
            for(int i=0; i<matrix.length; i++){
                pawn p = new pawn(color.BLACK);
                this.matrix[1][i] = new cell(1, i, p);
            }
            for(int i=0; i<matrix.length; i++){
                pawn p = new pawn(color.WHITE);
                this.matrix[6][i] = new cell(6, i, p);
            }
        }

        public boolean move(cell start, cell end){
            return true;
        }
    }


    class cell {
        int x;
        int y;
        peice peice;

        public cell(int x, int y, peice p){
            this.x =x;
            this.y =y;
            this.peice = p;
        }
    }

    abstract class peice{
        color color;
        public abstract boolean is_valid(cell start, cell end);
    }

    class pawn extends peice{
        public pawn(color color){
            super.color = color;
        }
        @Override
        public boolean is_valid(cell start, cell end){
            return false;
        }
    }

    class player{
        int id;
        color color;
        //strategy
        public player(int id, color color){
            this.id = id;
            this.color =color;
        }
    }

    enum color {
        WHITE, 
        BLACK
    }

    enum gameStatus {
        ONGOING, 
        DRAW, 
        CHECKMATE, 
        STALEMATE,
        CHECK
    }