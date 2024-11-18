import java.util.Random;

public enum Direction {
    NORTH, SOUTH, EAST, WEST;

    public Direction Forward() {
        return this;
    }

    public Direction turnLeft() {
        switch (this) {
            case NORTH:
                return WEST;
            case SOUTH:
                return EAST;
            case EAST:
                return NORTH;
            case WEST:
                return SOUTH;
            default:
                return null;
        }
    }

    public Direction turnRight() {
        switch (this) {
            case NORTH:
                return EAST;
            case SOUTH:
                return WEST;
            case EAST:
                return SOUTH;
            case WEST:
                return NORTH;
            default:
                return null;
        }
    }

    public Direction turnAround() {
        switch (this) {
            case NORTH:
                return SOUTH;
            case SOUTH:
                return NORTH;
            case EAST:
                return WEST;
            case WEST:
                return EAST;
            default:
                return null;
        }
    }

    //to string method
    public String toString() {
        return switch (this) {
            case NORTH -> "NORTH";
            case SOUTH -> "SOUTH";
            case EAST -> "EAST";
            case WEST -> "WEST";
            default -> null;
        };
    }

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            Direction d = Direction.values()[new Random().nextInt(Direction.values().length)];//gets a random direction, and runs the for loop 10 times
            System.out.println(d + " turns left to " + d.turnLeft());
            System.out.println(d + " turns right to " + d.turnRight());
            System.out.println(d + " turns around to " + d.turnAround());
        }
    }
}
