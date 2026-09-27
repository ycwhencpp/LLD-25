package TicTacToe;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Scanner;

class TicTacToe {
    public static void main (String[] args ){
        GameBoard gameBoard = new GameBoard(3,3, 3);

        PlayerStartegy startegy = new HumanPlayerStartegy();
        Player player1 = new Player(Symbol.X, startegy);
        Player player2 = new Player(Symbol.O, startegy);
        Deque players = new LinkedList<>();
        players.add(player1);
        players.add(player2);


        TicTacToeGame game = TicTacToeGame.getInstance(gameBoard, players);

        game.startGame();


    }
}

interface Game {
    public void startGame();
}

class  TicTacToeGame implements Game{
    private GameBoard gameBoard;
    private Deque<Player> players;
    private GameState gameState;
    private static TicTacToeGame instance;

    public static TicTacToeGame getInstance(GameBoard gameBoard, Deque<Player> players){
        if(instance == null){
            instance = new TicTacToeGame(gameBoard, players);
        }
        return instance;
    }



    private TicTacToeGame(GameBoard gameBoard, Deque<Player> players){
        this.gameBoard = gameBoard;
        this.players = players;
        this.gameState = GameState.NOT_STARTED;
    }

    public GameState showGameState(){
        return this.gameState;
    }

    public void startGame(){
        this.gameState = GameState.IN_PROGRESS;
        gameBoard.printBoard();
        while(this.gameState != GameState.COMPLETED){
            Player player = getCurrentPlayer();
            Cell cell = player.makeMove();
            Symbol playerSymbol = player.getSymbol();

            boolean success = gameBoard.placeMove(cell, playerSymbol);

            if(!success) continue;

            GameState currentGameState = gameBoard.checkGameState(cell, playerSymbol);
            gameBoard.printBoard();
            if(currentGameState == GameState.COMPLETED){
                Symbol winner = this.gameBoard.getWinner();
                this.gameState = currentGameState;
                // notify here 
                if(winner == null){
                    System.out.println("GAME DRAWN !!!");
                } else {
                    System.out.println("Player " + winner + " Won");
                }
                break;
            }

            changePlayer();
        }
    }

    private Player getCurrentPlayer(){
        return this.players.peek();
    }

    private void changePlayer(){
        this.players.offerLast(this.players.pollFirst());
    }
}

class GameBoard {
    Symbol[][] grid;
    int winsAt;
    Symbol winner;

    public GameBoard(int row, int col, int winsAt){
        this.grid = new Symbol[row][col];
        this.winsAt = winsAt;
    }

    public boolean placeMove(Cell cell, Symbol symbol){
        boolean isInvalidMove = checkInvalidMove(cell, symbol);
        if(isInvalidMove){
            return false;
        }
        this.grid[cell.getRow()][cell.getCol()] = symbol;
        return true;
    }

    private boolean checkInvalidMove(Cell cell, Symbol symbol){
        int row = cell.getRow();
        int col = cell.getCol();
        return row<0 || col <0 || row >= grid.length || col >= grid[0].length || grid[row][col] != null;
    }

    

    public GameState checkGameState(Cell cell, Symbol symbol){
        
        GameState gameState = checkWinState(cell, symbol);
        if(gameState == GameState.COMPLETED){
            return gameState;
        }

        gameState = checkDrawState(cell, symbol);

        return gameState;
        

    }

    private GameState checkDrawState(Cell cell, Symbol symbol){
        int row = cell.getRow();
        int col = cell.getCol();
        for(int i=0; i<this.grid.length; i++){
            for(int j=0; j<this.grid[0].length; j++){
                if(this.grid[i][j] == null) return GameState.IN_PROGRESS;
            }
        }

        return GameState.COMPLETED;
    }

    private GameState checkWinState (Cell cell, Symbol symbol){
        int row = cell.getRow();
        int col = cell.getCol();


        int rowCount =0;
        for(int i=0; i<grid[0].length; i++){
            if(grid[row][i] == symbol) rowCount++;
        }

        if(rowCount == this.winsAt){
           return announceWinner(symbol);
        }

        int colCount =0;
        for(int i=0; i<grid.length; i++){
            if(grid[i][col] == symbol) colCount++;
        }

        if(colCount == this.winsAt){
            return announceWinner(symbol);
        }

        int mainDiagonalCount = 0;
        for(int k = 0; k < grid.length; k++){
            if(grid[k][k] == symbol) mainDiagonalCount++;
        }
        if(mainDiagonalCount == this.winsAt) return announceWinner(symbol);

        int antiDiagonalCount = 0;
        for(int k = 0; k < grid.length; k++){
            if(grid[k][grid.length - 1 - k] == symbol) antiDiagonalCount++;
        }
        if(antiDiagonalCount == this.winsAt) return announceWinner(symbol);

        return GameState.IN_PROGRESS; 

    }
    public void printBoard(){
        for(int i=0; i<grid.length; i++){
            for(int j=0; j<grid[0].length; j++){
                if(grid[i][j] == null){
                    System.out.print( "_ ");
                } else {
                    System.out.print(grid[i][j] + " ");
                }
            }
            System.out.println();
        }
    }
    private GameState announceWinner(Symbol symbol){
        this.winner = symbol;
        return GameState.COMPLETED;
    }

    public Symbol getWinner(){
        return this.winner;
    }
}

class Player {
    private Symbol symbol;
    private PlayerStartegy playerStartegy;

    public Player(Symbol symbol, PlayerStartegy playerStartegy){
        this.symbol = symbol;
        this.playerStartegy = playerStartegy;
    }

    public Cell makeMove(){
        return this.playerStartegy.makeMove();
    }

    public Symbol getSymbol(){
        return this.symbol;
    }


}

enum GameState{
    IN_PROGRESS,
    COMPLETED, 
    NOT_STARTED
}

class Cell {
    private int row;
    private int col;
    public Cell(int row, int col){
        this.row = row;
        this.col = col;
    }

    public int getRow(){
        return this.row;
    }
    public int getCol(){
        return this.col;
    }
}

interface PlayerStartegy{
    public Cell makeMove();
}

class HumanPlayerStartegy implements PlayerStartegy{
    private Scanner scanner;

    public HumanPlayerStartegy() {
        this.scanner = new Scanner(System.in);
    }

    public Cell makeMove() {
        System.out.println("Enter row and col (e.g. 0 1): ");
        int row = scanner.nextInt();
        int col = scanner.nextInt();
        return new Cell(row, col);
    }
}

enum Symbol{
    X,
    O
}