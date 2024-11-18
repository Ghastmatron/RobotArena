public class ConsoleCanvas {
    private int xSize, ySize;
    private char[][] board;
    private String StudentNum = "32025125";

    public ConsoleCanvas(int xSize, int ySize) {
        this.xSize = xSize;
        this.ySize = ySize;
        board = new char[this.xSize][this.ySize];
        for (int i = 0; i < xSize; i++) {
            for (int j = 0; j < ySize; j++) {
                if (i == 0 || i == xSize - 1) {
                    board[i][j] = '#';
                } else if (j == 0 || j == ySize - 1) {
                    board[i][j] = '#';
                } else {
                    board[i][j] = ' ';
                }
            }
        }
        placeStudentNumber();
    }
    public void placeStudentNumber(){
        int studentNumLength = StudentNum.length();
        //throw error checking if student number is too long
        if(studentNumLength > xSize){
            throw new IllegalArgumentException("Student number is too long");
        }
        //place student number in the middle of the canvas on the top line
        int start = (xSize - studentNumLength) / 2;
        for(int i = 0; i < studentNumLength; i++){
            board[start + i][0] = StudentNum.charAt(i);
        }
    }
    public String toString() {
        String s = "";
        for (int i = 0; i < ySize; i++) {
            for (int j = 0; j < xSize; j++) {
                s += board[j][i];
            }
            s += "\n";
        }
        return s;
    }

    public void showIt(int x, int y, char c) {
        board[x][y] = c;
    }


    public static void main(String[] args) {
        ConsoleCanvas c = new ConsoleCanvas(20 + 2, 6 + 2);
        System.out.println(c.toString());
    }
}