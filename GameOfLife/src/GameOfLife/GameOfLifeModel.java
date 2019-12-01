package GameOfLife;

public class GameOfLifeModel {

    private boolean[][] curr_gen;
    private int rows;
    private int cols;
    private int[] thresholds;
    private boolean torus_mode;

    public GameOfLifeModel() {
    }

    public boolean[][] getNextGeneration() {
        if (curr_gen == null) {
            throw new RuntimeException();
        }

        rows = curr_gen.length;
        cols = curr_gen[0].length;
        boolean[][] nextGeneration = new boolean[rows][cols];

        int numAdjacent = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                numAdjacent = getNumAdjacent(i, j);
                if (curr_gen[i][j]) {
                    // alive
                    if (thresholds[0] <= numAdjacent
                            && numAdjacent <= thresholds[1]) {
                        nextGeneration[i][j] = true;
                    } else {
                        nextGeneration[i][j] = false;
                    }
                } else {
                    // dead
                    if (thresholds[2] <= numAdjacent
                            && numAdjacent <= thresholds[3]) {
                        nextGeneration[i][j] = true;
                    } else {
                        nextGeneration[i][j] = false;
                    }
                }
            }
        }
        this.curr_gen = nextGeneration;

        return nextGeneration;
    }

    public void setCurrentGeneration(boolean[][] curr_gen) {
        this.curr_gen = curr_gen;
    }

    private int getNumAdjacent(int x, int y) {
        int counter = 0;
        
        // top left to top right
        for (int i = 0; i < 3; i++) {
            if (x - 1 + i < 0 || x - 1 + i >= rows || y - 1 < 0) {
                if (torus_mode) {
                    int new_x = x - 1 + i;
                    int new_y = y - 1;
                    if (x - 1 + i < 0) {
                        new_x = rows - 1;
                    }
                    if (x - 1 + i >= rows) {
                        new_x = 0;
                    }
                    if (y - 1 < 0) {
                        new_y = cols - 1;
                    }
                    if (curr_gen[new_x][new_y]) {
                        counter++;
                    }
                } else {
                    continue;
                }
            } else {
                if (curr_gen[x - 1 + i][y - 1]) {
                    counter++;
                }
            }
        }
        
        // bottom left to bottom right
        for (int i = 0; i < 3; i++) {
            int new_x = x - 1 + i;
            int new_y = y + 1;
            if (x - 1 + i < 0 || x - 1 + i >= rows || y + 1 >= cols) {
                if (torus_mode) {
                    if (x - 1 + i < 0) {
                        new_x = rows - 1;
                    }
                    if (x - 1 + i >= rows) {
                        new_x = 0;
                    }
                    if (y + 1 >= cols) {
                        new_y = 0;
                    }
                    if (curr_gen[new_x][new_y]) {
                        counter++;
                    }
                } else {
                    continue;
                }
            } else {
                if (curr_gen[x - 1 + i][y + 1]) {
                    counter++;
                }
            }
        }
        
        // left of spot
        if (x - 1 >= 0 && curr_gen[x - 1][y]) {
            counter++;
        } else if (x - 1 < 0 && torus_mode) {
            if (curr_gen[rows - 1][y]) {
                counter++;
            }
        }
        
        // right of spot
        if (x + 1 < rows && curr_gen[x + 1][y]) {
            counter++;
        } else if (x + 1 >= rows && torus_mode) {
            if (curr_gen[0][y]) {
                counter++;
            }
        }
        return counter;
    }

    public static boolean[][] generateRandomBoard(int x, int y, double chance) {
        boolean[][] random_board = new boolean[x][y];
        double threshold;
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                threshold = Math.random();
                if (threshold > chance) {
                    random_board[i][j] = false;
                } else {
                    random_board[i][j] = true;
                }
            }
        }
        return random_board;
    }

    public void setThresholds(int[] thresholds) {
        this.thresholds = thresholds;
    }
    
    public void setTorusMode(boolean mode) {
        this.torus_mode = mode;
    }
}
