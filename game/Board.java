package game;

/**
 *
 * @author cristopher
 */
public class Board {
    private final int boardSize;
    private final int [][] board;
    protected int score;

    public Board(int boardSize) {
        if (boardSize < 0)
            throw new IllegalArgumentException("Board size cannot be negative");
        
        this.boardSize = boardSize;
        this.board = new int[boardSize][boardSize];
    }

    public int getScore() {
        return score;
    }
    
    public String getStringScore() {
        return score + " pts";
    }

    public int getBoardSize() {
        return boardSize;
    }
    
    public int getData(int i, int j) {
        return board[i][j];
    }
    
    protected int getNewBoardNumber() {
//        int [] powers = {2, 4, 8, 16, 32, 64, 128, 256, 512, 1024, 2048, 4096, 8192, 16384, 32768, 65536, 131072, 262144, 524288, 1048576};
//        return powers[(int) (Math.random() * powers.length)];
        
        if (score == 0)
            return 2;
        
        int maxValue = (int) (Math.log(score) / Math.log(Math.abs(boardSize - 6)));
        
        maxValue--;
        maxValue |= maxValue >> 1;
        maxValue |= maxValue >> 2;
        maxValue |= maxValue >> 4;
        maxValue |= maxValue >> 8;
        maxValue |= maxValue >> 16;
        maxValue++;
        
        return maxValue;
    }
    
    public boolean isFull() {
        for (int i = 0; i < boardSize; i++)
            for (int j = 0; j < boardSize; j++)
                if (board[i][j] == 0)
                    return false;
        
        return true;
    }
    
    public void generateRandomTiles() {
        boolean zero = score == 0;
        
        if (!zero)
            if ((int) (boardSize * Math.random()) % 2 == 0)
                return;
        
        int tilesToGenerate = zero ? 2 : (int) ((boardSize - 2) * Math.random());
        
        int i, j;
        int count = 0;
//        System.out.println("tilesToGenerate = " + tilesToGenerate);
        while (count < tilesToGenerate) {
            i = (int) (boardSize * Math.random());
            j = (int) (boardSize * Math.random());
            if (isFull())
                break;
            
            if (board[i][j] != 0)
                continue;
            
            board[i][j] = getNewBoardNumber();
            count++;
        }
        
//        System.out.println("a");
//        for (j = 0; j < boardSize; j++) {
//            for (i = 0; i < boardSize; i++)
//                System.out.print(board[i][j]);
//            System.out.println();
//        }
    }
}
