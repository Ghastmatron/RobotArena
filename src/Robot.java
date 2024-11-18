import java.util.Random;

public class Robot{
    private int x, y;
    private Direction d;

    public Robot(int x, int y, Direction d){ //constructor
        this.x = x;
        this.y = y;
        this.d = d;
    }
    public int getX(){
        return x;
    }
    public int getY(){
        return y;
    }

    public void displayRobot(ConsoleCanvas c){
        c.showIt(this.x, this.y, 'R');
    }

    public void tryToMove(Robot r){
        //choose a random decision for the robot to move
        Random random = new Random();
        int decision = random.nextInt(3); //0, 1, 2
        switch(decision){
            case 0:
                r.turnLeft();
                break;
            case 1:
                r.turnRight();
                break;
            case 2:
                break;
        }
    }

    public void turnLeft() {
        d = d.turnLeft();
    }

    public void turnRight() {
        d = d.turnRight();
    }

    public void Forward(){
        switch(d){
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
    }

    public Direction getDirection(){
        return d;
    }

    public Robot getRobot(){
        return this;
    }

    public String toString(){
        return "Robot at (" + x + ", " + y + ") and is facing " + d.toString();
    }
}
