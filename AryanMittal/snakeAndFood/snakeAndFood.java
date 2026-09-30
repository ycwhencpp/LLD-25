package AryanMittal.snakeAndFood;

import java.util.Deque;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.HashSet;

public class snakeAndFood {
    
}

interface Game  {
    public void startGame();
}

class SnakeAndFood implements  Game {

    private Board board;
    private User player;
    private int score;
    private int time;
    private GameState gameState;
    private int maxPoint;
    private Snake snake;

    public SnakeAndFood(Board board, User user, int maxPoint, Snake snake){
        this.board = board;
        this.player = user;
        this.score =0;
        this.time=0;
        this.gameState = GameState.NOT_STARTED;
        this.maxPoint = maxPoint;
        this.snake = snake;
    }

    public int getScore(){
        return this.score;

    }

    public GameState getGameState(){
        return this.gameState;
    }




    public void startGame(){
        while(true){
            Direction userDirection = this.player.chooseDirection();
            boolean isValidMove = this.board.isValidMove(this.snake.getHead(), userDirection);

            if(!isValidMove){
                System.out.println("Not a valid Direction");
                continue;
            }
            Pair position = this.board.compute(this.snake.getHead(), userDirection);
            boolean isSnakeDead = this.snake.isColliding(position);
            if(isSnakeDead){
                this.gameState = GameState.SNAKE_DEAD;
                System.out.println("Snake is dead you scored " + this.score);
                break;
            }

            GameContext gameContext = new GameContext(time, time, score, maxPoint);

            int point = this.board.moveSnake(this.snake, userDirection, gameContext);
            score+=point;

            if(point == maxPoint){
                this.gameState = GameState.COMPLETED;
                System.out.println("You won!!!, you scored " + this.score);
                break;
            }
            this.time++;
        }

        
    }
}

class Board{
    private Cell[][] board;
    private DirectionCalculator directionCalulator;
    private FoodSpawnStartegy foodSpawnStartegy;


    public Board(int n, int m){
        this.board = new Cell[n][m];
    }

    public Pair compute(Pair currentHead, Direction userDirection){
        Pair Newcordintaes = this.directionCalulator.compute(currentHead, userDirection);
        return Newcordintaes;
    }


    public boolean isValidMove(Pair cordinates, Direction userDirection){
        Pair Newcordintaes = compute(cordinates, userDirection);
        if(Newcordintaes.getR()<0 || Newcordintaes.getC()<0 || Newcordintaes.getR()>= this.board.length || Newcordintaes.getC()>=board[0].length){
            return false;
        }
        return true;
    }

    public int moveSnake(Snake snake,  Direction userDirection, GameContext gameContext){
        Pair Newcordintaes = compute(snake.getHead(), userDirection);
        Cell currentCell = this.board[Newcordintaes.getR()][Newcordintaes.getC()];


        int point = snake.moveSnake(currentCell, gameContext);

        if(currentCell.getFood() != null){
            Cell newFoodCell = this.foodSpawnStartegy.getNextFood();
            this.board[newFoodCell.getCordinates().getR()][newFoodCell.getCordinates().getC()] = newFoodCell;
            currentCell.setFood(null);
        }

        return point;
    }
}

class Snake{
    private Deque<Pair> snake;
    private HashSet<Pair> snakeSet;


    public Snake(){
        this.snake = new LinkedList<>();
        this.snakeSet = new HashSet<>();
    }

    public Deque<Pair> getSnake(){
        return this.snake;
    }

    public HashSet<Pair> getBody(){
        return this.snakeSet;
    }

    public Pair getHead(){
        return this.snake.peekFirst();
    }

    public int moveSnake(Cell cell, GameContext gameContext){
        if(cell.getFood() == null){
            drfitSnake(cell.getCordinates());
            return 0;
        }

        return consumeFood(cell, gameContext);
    }

    private int consumeFood(Cell cell, GameContext gameContext){
        Food food = cell.getFood();
        Pair cordinates = cell.getCordinates();

        boolean isOkayToConsume = food.isOkayToConsume(gameContext);

        if(!isOkayToConsume){
            drfitSnake(cordinates);
            return 0;
        }

        int point = food.getPoint();

        moveHead(cordinates);

        return point;
    }

    private void  drfitSnake(Pair cordinates){
        moveHead(cordinates);
        moveTail();
    }

    private void moveHead(Pair cordinates){
        this.snake.addFirst(cordinates);
        this.snakeSet.add(cordinates);
    }

    private void moveTail(){
        Pair tail = this.snake.removeLast();
        this.snakeSet.remove(tail);
    }

    public boolean isColliding(Pair position){
        return this.snakeSet.contains(position);
    }
}

class Pair{
    private int r;
    private int c;

    public Pair(int r, int c){
        this.r=r;
        this.c=c;
    }

    public int getR() {
        return r;
    }

    public int getC() {
        return c;
    }

    
}

class Cell {
    private Pair cordinates;
    private Food food;

    public Cell(Pair cordinates, Food food){
        this.cordinates= cordinates;
        this.food = food;
    }

    public Pair getCordinates(){
        return this.cordinates;
    }
    public Food getFood(){
        return this.food;
    }

    public void setFood(Food food){
        this.food = food;
    }
}

abstract class Food{
    private int points;

    public Food(int points){
        this.points = points;
    }


    public int getPoint(){
        return this.points;
    }

    public abstract boolean isOkayToConsume(GameContext gameContext);


}

class NormalFood extends Food{
    public NormalFood(int points){
        super(points);
    }


    public boolean isOkayToConsume(GameContext gameContext){
        return true;
    }
}


class GameContext{


    private int time;
    private int steps;
    private int score;
    private int length;

    
    public GameContext(int time, int steps, int score, int length) {
        this.time = time;
        this.steps = steps;
        this.score = score;
        this.length = length;
    }

    public int getTime() {
        return time;
    }
    public int getSteps() {
        return steps;
    }
    public int getScore() {
        return score;
    }
    public int getLength() {
        return length;
    }

    
    
}


interface FoodSpawnStartegy{
    public Cell getNextFood();
}

class RandomFoodSpawnOfSameType implements  FoodSpawnStartegy{
    private int m;
    private int n;
    private int point;

    public RandomFoodSpawnOfSameType(int n, int m , int point ){
        this.n = n;
        this.m = m;
        this.point = point;
    }

    public Cell getNextFood(){
        int newN = (int)(Math.random() * 10) % n;
        int newM = (int)(Math.random() * 10) % m;
        Pair cordinates = new Pair(newM, newN);
        Food food = new NormalFood(this.point);
        return new Cell(cordinates, food);
    }
}

class DirectionCalculator{

    private int increment_n;
    private int increment_m;

    public DirectionCalculator(int increment_n, int increment_m) {
        this.increment_n = increment_n;
        this.increment_m = increment_m;
    }

    public Pair compute(Pair head, Direction direction){
        int new_r = head.getR();
        int new_c = head.getC();
        switch(direction ){
            case Direction.UP:
                new_r-=increment_n;
                break;
            case Direction.DOWN:
                new_r+=increment_n;
                break;
            case Direction.LEFT:
                new_c-=increment_m;
                break;
            case Direction.RIGHT:
                new_c+=increment_m;
                break;
            default:
                break;
            
        }
        return new Pair(new_r, new_c);
    }
    
}

enum Direction{

    UP,
    DOWN,
    RIGHT,
    LEFT
}

enum GameState{
    COMPLETED, 
    IN_PROGRESS, 
    SNAKE_DEAD,
    NOT_STARTED
}

class User{
    private String name;
    Scanner scanner;
    // we can have startgey has well for input 

    public User(String name){
        this.name = name;
        this.scanner = new Scanner(System.in);
    }

    public Direction chooseDirection(){
        System.out.println("CHOOSE W, A, S, D to move snake");
        String dir = scanner.next();

        if(dir == "W"){
            return Direction.UP;
        } else if (dir == "A"){
            return Direction.LEFT;
        } else if(dir == "S"){
            return Direction.DOWN;
        } else if(dir == "D"){
            return Direction.RIGHT;
        } else {
            System.out.println("invalid input");
            return chooseDirection();
        }
    }


    public String getName(){
        return this.name;
    }

}