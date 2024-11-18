import java.io.*;
import java.util.*;

public class SaveSystem {

    private String ArenaKey;
    private String RobotKey;

    public SaveSystem() {
        //direct file paths for simplicity
        ArenaKey = generateKey("arena.txt");
        RobotKey = generateKey("robot.txt");
    }

    public void saveArena(RobotArena a) {
        //direct file paths for simplicity
        String arenaFilePath = "arena.txt";
        String robotFilePath = "robot.txt";
        StringBuilder robotData = new StringBuilder();

        try (PrintWriter fileToWrite = new PrintWriter(new FileWriter(arenaFilePath, true))) {//true for append mode
            fileToWrite.println(ArenaKey +"," + a.getxSize() + "," + a.getySize() + "," + RobotKey);
            try (PrintWriter fileToWrite2 = new PrintWriter(new FileWriter(robotFilePath, true))) {//true for append mode
                robotData.append(RobotKey).append(",").append(a.getRobot().size()).append(",");
                for (Robot r : a.getRobot()) {
                    robotData.append(r.getX()).append(",").append(r.getY()).append(",").append(r.getDirection()).append(",");//concatenates robot data
                }
                fileToWrite2.println(robotData);//writes robot data to file, in one long line
                System.out.println("Files saved, your key to access the arena is: " + ArenaKey);
            } catch (IOException e) {
                System.out.println("Error writing to file: " + robotFilePath);
            }
        } catch (IOException e) {
            System.out.println("Error writing to file: " + arenaFilePath);
        }
    }

    public String generateKey(String file) {
        if (file.equals("robot.txt")) {
            return generateRobotKey();
        } else if (file.equals("arena.txt")) {
            return generateArenaKey();
        } else {
            return fileNotFound(file);
        }
    }

    public String generateRobotKey() {
        Random random = new Random();
        String key = "R" + random.nextInt(1000);
        String robotFilePath = "robot.txt";

        try (BufferedReader fileToRead = new BufferedReader(new FileReader(robotFilePath))) {
            String line;
            while ((line = fileToRead.readLine()) != null) {
                if (key.equals(line.substring(0, key.length()))) {
                    return generateRobotKey();//if key already exists, generate a new one #recursion
                }
            }
        } catch (IOException e) {
            return fileNotFound(robotFilePath);
        }
        return key;
    }

    public String generateArenaKey() {
        Random random = new Random();
        String key = "A" + random.nextInt(1000);
        String arenaFilePath = "arena.txt";

        try (BufferedReader fileToRead = new BufferedReader(new FileReader(arenaFilePath))) {//read text from character based input steam
            String line;
            while ((line = fileToRead.readLine()) != null) {
                if (key.equals(line.substring(0, key.length()))) {
                    return generateArenaKey();//if key already exists, generate a new one #recursion
                }
            }
        } catch (IOException e) {
            return fileNotFound(arenaFilePath);
        }
        return key;
    }

    public String getToPosition(String key, int position) {//gets the data at a certain position in a line
        String file = determineFile(key);
        try (BufferedReader fileToRead = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = fileToRead.readLine()) != null) {
                if (key.equals(line.substring(0, key.length()))) {
                    String[] tokens = line.split(",");
                    if (position < tokens.length) {
                        return tokens[position];
                    }
                }
            }
        } catch (IOException e) {
            return fileNotFound(file);
        }
        return lineNotFound(file);
    }

    public String fileNotFound(String file) {//error message
        System.out.println("File not found: " + file);
        return "File not found";
    }

    public String endOfFile(String file) {//error message
        System.out.println("End of file reached: " + file);
        return "End of file reached";
    }

    public String lineNotFound(String file) {//error message
        System.out.println("Line not found in file: " + file);
        return "Line not found in file";
    }

    public String determineFile(String key) {//determines which file to read from
        String fileName = key.substring(0, 1);
        if (fileName.equals("R")) {
            return "robot.txt";
        } else if (fileName.equals("A")) {
            return "arena.txt";
        } else {
            return fileNotFound(fileName);
        }
    }

    public String getRobotKey(String key) {
        return getToPosition(key, 3);
    }

    public RobotArena CreateArena(String key) {
        String file = determineFile(key);
        try (BufferedReader fileToRead = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = fileToRead.readLine()) != null) {
                if (key.equals(line.substring(0, key.length()))) {
                    String[] tokens = line.split(",");//splits the line into tokens
                    if (tokens.length > 1) {
                        int x = Integer.parseInt(tokens[1]);//converts string to int
                        int y = Integer.parseInt(tokens[2]);//converts string to int
                        RobotArena arena = new RobotArena(x, y);//creates a new arena
                        return arena;
                    }
                }
            }
        } catch (IOException e) {
            fileNotFound(file);
        }
        return null;
    }

    public void CreateRobots(String key, RobotArena a) {
        String robotKey = getRobotKey(key);
        String file = determineFile(robotKey);
        try (BufferedReader fileToRead = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = fileToRead.readLine()) != null) {
                if (robotKey.equals(line.substring(0, robotKey.length()))) {
                    String[] tokens = line.split(",");
                    int numberOfRobots = Integer.parseInt(tokens[1]);//gets number of robots on that line
                    int tokenIndex = 2;//start of robot data

                    for (int i = 0; i < numberOfRobots; i++) {//loops through the robots
                        int x = Integer.parseInt(tokens[tokenIndex++]);//appends the token index to keep track of the data
                        int y = Integer.parseInt(tokens[tokenIndex++]);
                        Direction d = Direction.valueOf(tokens[tokenIndex++]);
                        String concatenated = x + "," + y + "," + d;
                        a.addRobot(concatenated);//adds the robot to the arena
                    }
                }
            }
        } catch (IOException e) {
            fileNotFound(file);
        }
    }
}
