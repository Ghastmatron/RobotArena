import java.sql.Array;
import java.util.ArrayList;
import java.util.Random;

public class RobotArena {
    private int xSize, ySize;
    private char[][] board;
    private ArrayList<Robot> robots;
    private Random randomNum;

    //constructor
    public RobotArena(int xSize, int ySize){
        this.xSize = xSize + 2;//add two to compensate for the borders
        this.ySize = ySize + 2;
        randomNum = new Random();
        robots = new ArrayList<Robot>();
    }

    public void showRobots(ConsoleCanvas c){
        for (Robot r : robots){
            r.displayRobot(c);//need to add one to equate into the canvas size, boarders are all #
        }
    }

    public void addRobot(String ... inputs) {//can take in multiple strings, or none, dynamical use of function
        if (inputs.length > 0) {
            for (String input : inputs) {
                String[] robotInfo = input.split(",");
                    try {
                        String xStr = robotInfo[0].trim();
                        String yStr = robotInfo[1].trim();
                        String directionStr = robotInfo[2].trim().toUpperCase();

                        // Validate if xStr and yStr are numeric
                        if (!xStr.matches("\\d+") || !yStr.matches("\\d+")) {//regex to check if it is a number
                            throw new NumberFormatException("Invalid number format");
                        }

                        int x = Integer.parseInt(xStr);//converts string to int
                        int y = Integer.parseInt(yStr);
                        Direction d = Direction.valueOf(directionStr);//converts string to enum

                        robots.add(new Robot(x, y, d));
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid number format: " + input);
                    } catch (IllegalArgumentException e) {
                        System.out.println("Invalid direction format: " + input);
                    }
            }
        } else {//if no inputs, add a random robot
            int xVal, yVal;
            do {
                xVal = randomNum.nextInt(1, xSize - 1);
                yVal = randomNum.nextInt(1, ySize - 1);
            } while (getRobot(xVal, yVal) != null);
            robots.add(new Robot(xVal, yVal, Direction.values()[randomNum.nextInt(Direction.values().length)]));
        }
    }

    public String toString(){
        return "RobotArena of size " + xSize + " by " + ySize + " with a robots at positions " + getRobotPositions();
    }
    public String getRobotPositions(){
        String positions = "";
        for (Robot r : robots){
            positions += r.toString() + "\n";//big long string of all robot positions
        }
        return positions;
    }
    public Robot getRobot(int x, int y){
        for (Robot r : robots){
            if (r.getX() == x && r.getY() == y){
                return r;
            }
        }
        return null;
    }

    public ArrayList<Robot> getRobot(){
        return robots;
    }

    public char getBoard(int x, int y){
        return board[x][y];
    }

    public int getxSize(){
        return xSize;
    }
    public int getySize(){
        return ySize;
    }

    public boolean canMoveHere(Robot r) {//delegate to robot
        int x = r.getX();
        int y = r.getY();
        switch (r.getDirection()) {
            case NORTH:
                y--;
                break;
            case SOUTH:
                y++;
                break;
            case EAST:
                x++;
                break;
            case WEST:
                x--;
                break;
        }
        if (x < 1 || x >= xSize - 1 || y < 1 || y >= ySize - 1) { // Check if robot is at borders
            return false;
        }
        if (getRobot(x, y) != null) { // Check if robot is on top of another robot
            return false;
        }
        return true;
    }
    public void moveAllRobots() {
        for (Robot r : robots) {
            r.tryToMove(r);
            if (canMoveHere(r)) {
                r.Forward();
            } else {
                r.tryToMove(r);
                if (canMoveHere(r)) {
                    r.Forward();
                }
            }
        }
    }

    public static void main(String[] args) {
        RobotArena a = new RobotArena(15, 15);
        a.addRobot("10, 10, NORTH");

    }
}
