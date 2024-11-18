import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class RobotInterface {
    private Scanner s;
    private RobotArena arena;
    private SaveSystem saveSystem;

    public RobotInterface(){
        s = new Scanner(System.in); //set up scanner for user input
        arena = new RobotArena(20, 6); //create an area of dedicated size
        saveSystem = new SaveSystem();

        char ch = ' ';
        do{
            System.out.println("Enter 'a' to add a robot, Enter 'i' for information, Enter 'd' to Display, Enter 'm' to move all robots, Enter 'n' to move all robots 10 times, Enter 'g' for a new arena, Enter 'S' to save your arena, Enter 'L' to load an arena, Enter 'X' to quit");
            ch = s.next().charAt(0);
            s.nextLine(); //clear the buffer
            switch(ch) {
                case 'a':
                case 'A':
                    arena.addRobot(); //add a new robot to the arena
                    break;
                case 'i':
                case 'I':
                    System.out.println(arena.toString()); //prints the arena and robot positions
                    break;
                case 'd':
                case 'D':
                    doDisplay();
                    //display the robots
                    break;
                case 'm':
                case 'M':
                    arena.moveAllRobots();
                    doDisplay();
                    break;
                case 'n':
                case 'N':
                    for(int i = 0; i < 10; i++){
                        arena.moveAllRobots();
                        doDisplay();
                        System.out.println(arena.toString());
                        try{
                            TimeUnit.MILLISECONDS.sleep(200);
                        }catch(InterruptedException ex){
                            ex.printStackTrace();//throws error into basic error stream
                        }
                    }
                    doDisplay();
                    break;
                case 'g':
                case 'G':
                    System.out.println("Enter x size of the arena");
                    int x = s.nextInt();
                    System.out.println("Enter y size of the arena");
                    int y = s.nextInt();
                    arena = new RobotArena(x, y);
                    break;
                case 'S':
                case 's':
                    saveSystem.saveArena(arena);
                    break;
                case 'L':
                case 'l':
                    System.out.println("Enter the key of the arena to load");
                    String key = s.next();
                    RobotArena newArena = saveSystem.CreateArena(key);
                    saveSystem.CreateRobots(key, newArena);
                    if (newArena != null) {
                        arena = newArena;
                        System.out.println("Arena loaded successfully.");
                    } else {
                        System.out.println("Failed to load arena.");
                    }
                    break;


            }
        }while(ch != 'X' && ch != 'x');
        s.close();
    }

    void doDisplay(){
        ConsoleCanvas c = new ConsoleCanvas(arena.getxSize(), arena.getySize());
        arena.showRobots(c);
        System.out.println(c.toString());
    }

    public static void main(String[] args) {
        RobotInterface r = new RobotInterface();
    }
}

