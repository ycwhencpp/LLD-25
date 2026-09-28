package AryanMittal.Chess;

import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

public class Chess {
    public static void main(String[] args) {
        
    }
}

interface Game{
    public void startGame();
}

class ChessGame implements  Game{

    private Deque<Player> players;
    private Board board;
    private List<Move> log;

    public ChessGame(Deque<Player> players, Board board) {
        this.players = players;
        this.board = board;
        this.log = new ArrayList<>();

        createAndAsssignPieces();
    }


    public void startGame(){
        this.board.setGameState(GameState.IN_PROGRESS);
        while(true){
            this.board.displayBoard();
            Player currentPlayer = getCurrentPlayer();

            Move move = currentPlayer.makeMove(this.board);

            boolean success = this.board.isValidMove(move);
            if(!success){
                System.out.println("Invalid Move");
                continue;
            }

            this.board.placeMove(move);
            this.log.add(move);

            if(this.board.getGameState() == GameState.COMPLETED){
                if(this.board.getWinner() != null){
                    System.out.println("player " + currentPlayer.getName() + " won");
                } else {
                    System.out.println("Match Tied");
                }

                break;
            }

            nextTurn();

        }
    }

    public Player getWinner(){
        return this.board.getWinner();
    }

    private void createAndAsssignPieces() {

        this.board.createAndAssignPeices();
        
        

        // other peices as well 
    }

    private Player getCurrentPlayer(){
        return this.players.peek();
    }

    private void nextTurn(){
        this.players.offerLast(this.players.pollFirst());
    }
}

class Board {
    private Cell[][] board;
    private Player winner;
    private GameState gameState;
    private PeiceFactory peiceFactory;


    public Board(int n, int m, PeiceFactory peiceFactory ){
        intializeBoard(n, m);
        this.winner = null;
        this.gameState = GameState.NOT_STARTED;
        this.peiceFactory = peiceFactory;
    }

    public void intializeBoard(int n, int m){
        this.board = new Cell[n][m];
        for(int i=0; i<n; i++){
            for(int j=0; j<m; j++){
                this.board[i][j] = new Cell(i,j, null);
            }
        }

        createAndAssignPeices();

    }

    public boolean isValidMove(Move move){
        /**
         * check starting cell 
         * lay out all the possible cell that peice can move from starting cell
         * if ending cell is one of those possiblites return true else false 
         * also check starting cell contains peicecolor of current player only 
         */
        Peice peice= move.getMovedPeice();
        return peice.isValidMove(move);

    }

    public void createAndAssignPeices(){
        for(int i=0; i<this.board[0].length; i++){
            Cell cell = this.board[0][i];
            cell.setPeice(this.peiceFactory.createPeicees("PAWN", PeiceColor.WHITE);
        }
    }

    public void placeMove(Move move){
        
        /**
         * renove cell.peice from startcell 
         * add cell.peice in end cell 
         * check if any peice is there in the end cell if yes 
         * update move.peicekilled = that peice
         * and check if that peice is king if yes 
         * 
         * and also track king state of both user and create set of possible movements of king
         * now if all the movements of king can be captured by either player then they loose
         * 
         * add winner and gamestate == COMPLETED 
         * 
         * at last checkDraw();
         * and if true return gameState == COMPLETED with winner as null 
         */
        checkKingProtected();
        checkDraw();
    }

    private boolean checkKingProtected(){
        return true;
    }

    private boolean checkDraw(){
        /**
         * layout all the positions of the cell from each peice in a set then check 
         * if the set contians any valid position or not if not return true 
         * if yes return false;
         */

        return false;
    }

    public Player getWinner(){
        return this.winner;
    }
    public GameState getGameState(){
        return this.gameState;
    }

    public void displayBoard(){
        for(int i=0; i<this.board.length; i++){
            for(int j=0; j<this.board[0].length; j++){
                
                Peice peice= this.board[i][j].getPeice();

                if(peice == null){
                    System.out.print("Empty");
                } else {
                    System.out.print(peice.getPeiceColor() + " " + peice.getName());
                }

                
            }
            System.out.println();
        }

    }
    public Cell getCell(int i, int j){
        return this.board[i][j];
    }

    public void setGameState(GameState gameState){
        this.gameState = gameState;
    }
}

abstract class Peice {
    private PeiceColor peiceColor;
    private String peiceName;

    public Peice(PeiceColor peiceColor,  String peiceName) {
        this.peiceColor = peiceColor;
        this.peiceName = peiceName;
    }

    public abstract boolean isValidMove(Move move); 

    public PeiceColor getPeiceColor(){
        return this.peiceColor;
    }

    public String getName(){
        return this.peiceName;
    }
}

class Pawn extends Peice{
    public Pawn(PeiceColor peiceColor,  String name){
        super(peiceColor, name);
    }

    public boolean isValidMove(Move move){
        /**
         * write loop for each peice according to how they can move 
         * add it in set or list 
         * 
         * then check start cell and end cell if its valid or not 
         * and also reachabled by that peice or not 
         * and also that color is choosen by player or not 
         * 
         * 
         */
        return true;
    }
}

class Cell {
    private int row;
    private int col;
    private Peice peice;

    public Cell(int r, int c, Peice peice){
        this.row = r;
        this.col = c;
        this.peice =peice;
    }

    public String getPeiceName(){
        return this.peice.getName();
    }

    public void setPeice(Peice peice){
        this.peice = peice;
    }
    public Peice getPeice(){
        return this.peice;
    }

}


class Move {
    private Cell startCell;
    private Cell endCell;
    private Player player;
    private Peice peiceKilled;
    private Peice peiceMoved;

    public Move(Cell startCell, Cell endCell, Player player, Peice peiceKilled, Peice peiceMoved){
        this.startCell = startCell;
        this.endCell = endCell;
        this.player = player;
        this.peiceKilled = peiceKilled;
        this.peiceMoved = peiceMoved;

    }

    public Move(){

    }

    public void setPeiceKilled(Peice peiceKilled){
        this.peiceKilled = peiceKilled;
    }
    public Cell getStartCell(){
        return this.startCell;
    }
    public Cell getEndCell(){
        return this.endCell;
    }

    public Peice getMovedPeice(){
        return this.peiceMoved;
    }
}

class Player{
    private String name;
    private PeiceColor peiceColor;
    private PlayingStrategy PlayingStrategy;


    public Player(String name, PeiceColor peiceColor, PlayingStrategy playingStrategy){
        this.peiceColor = peiceColor;
        this.PlayingStrategy = playingStrategy;
        this.name = name;
    }

    public String getName(){
        return this.name;

    }

    public PeiceColor getGameColor(){
        return this.peiceColor;
    }

    public Move makeMove(Board board){
        /**
         * list all the peices of the current player color left and then 
         * add it in list and prompt to user and ask which peice to move 
         * then take that peice and rrun makeMove() function of the peice 
         * or
         * just wait for user input of cell of which peice type 
         * he wants move take that peice type do validation of 
         * valid cell and valid peice selection 
         * then call makeMove of that peice function
         * 
         */
        return this.PlayingStrategy.makeMove(board);

       

    }
}

enum PeiceColor{
    BLACK,
    WHITE
}



enum GameState{
    NOT_STARTED, 
    IN_PROGRESS, 
    COMPLETED
}

interface PlayingStrategy{
    public Move makeMove(Board board );
}

class HumanPlayerStartegy implements PlayingStrategy{
    public Move makeMove(Board board){
        return new Move();
    }
}

class PeiceFactory{
    public Peice createPeicees(String name, PeiceColor color) throws Exception{
        switch(name){
            case "PAWN":
                return new Pawn(color, name);
            default:
                throw new Exception("unspoorted peice error");
                // System.out.println("error");
                // break;
        }
    }
}