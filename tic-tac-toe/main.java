import java.util.*;
class main{
    public static void main(String[] args) {
        

        game game = new game();
        game.startGame();


    }
  
}


 class game {
    Queue<player> players;
    Board board;

    public game() {

        playingstartegy hps = new humanPlayingStrategy();
        player p1 = new player(1, piece.X, hps);
        player p2 = new player(2, piece.O, hps);

        Queue<player> players = new LinkedList<>();
        players.add(p1);
        players.add(p2);

        this.players = players;
        this.board = new Board(3);
    }

    public void startGame() {

        while(true){
            board.display();
            if(!board.isEmptyPlaceLeft()){
                break;
            }
            player p = players.peek();
            int[] move = p.play();
            if(!board.is_valid(move[0], move[1])){
                System.out.println("invalid move");
                continue;
            }
            board.add_peice(p.piece, move[0], move[1]);


            if(check_winner(p)){
                System.out.println("player " + p.id + " wins");
                return;
            }

            players.poll();
            players.offer(p);

        }
        System.out.println("Game Drawn");

    }


    public boolean check_winner(player p ){
        return false;
    }



}


class Board{
    int size;
    piece[][] matrix;

    public Board(int s){
        size = s;
        matrix = new piece[s][s];
    }

   public void display() {
        for(piece[] row : this.matrix){
            for(int i=0; i< row.length; i++){
                if (row[i] == null) System.out.print(".");
                else System.out.print(row[i]);
            }
             System.out.println();
        }
    }

    public boolean add_peice(piece p, int r, int c) {
        if(!is_valid(r,c)){
            return false;
        }
        matrix[r][c] = p;
        return true;
    }
    public boolean is_valid(int r , int c){
        return matrix[r][c] == null;
    }

    public boolean isEmptyPlaceLeft(){
        for(piece[] row : this.matrix){
            for(piece val : row){
                if(val == null) return true;
            }
        }
        return false;
    }



}




class player {
    int id;
    piece piece;
    playingstartegy playingstartegy;

    public player(int id, piece p, playingstartegy ps){
        this.id = id;
        this.piece = p;
        this.playingstartegy =ps;
    }
    public int[] play(){
        return this.playingstartegy.move();
    }

}


interface playingstartegy{
    public int[] move();
}

class humanPlayingStrategy implements playingstartegy{
     Scanner sc = new Scanner(System.in);
    public int[] move() {
        System.out.println("enter a move");

        int r = sc.nextInt();
        int c  = sc.nextInt();

        return new int[]{r,c};
    }
}

enum piece{
    X,
    O
}