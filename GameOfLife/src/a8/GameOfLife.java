package a8;

import GameOfLife.GameOfLifeController;
import GameOfLife.GameOfLifeModel;

public class GameOfLife {

    public static void main(String[] args) {
        GameOfLifeModel model = new GameOfLifeModel();
        GameOfLifeController controller = new GameOfLifeController(model);
        
    }

}
