package GameOfLife;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

public class GameOfLifeController implements Observer {

    private int width;
    private int height;
    private double random_factor;
    private int playback_speed;
    private int[] thresholds;
    private boolean torus_mode;
    
    private List<boolean[][]> history;
    private JFrame main_frame;
    private GameOfLifeView view;
    private GameOfLifeModel model;
    private SettingsFrame settings_view;
    private boolean animationStarted;

    GameOfLifeController(GameOfLifeModel model) {
        history = new ArrayList<boolean[][]>();
        this.model = model;
        promptSettings();
    }

    public void promptSettings() {
        verifyPaused();
        if (settings_view != null) {
            double[] settings = settings_view.exportSettings();
            settings_view.dispose();
            settings_view = new SettingsFrame();
            settings_view.setDefaultSettings(settings);
        } else {
            settings_view = new SettingsFrame();
        }
        settings_view.setVisible(true);
        settings_view.addObserver(this);
    }

    @Override
    public void update(String event) {
        if (event.equals("settings_apply")) {
            this.width = settings_view.getWidthInput();
            this.height = settings_view.getHeightInput();
            this.random_factor = settings_view.getRandomInput();
            this.playback_speed = settings_view.getSpeedMillis();
            this.thresholds = settings_view.getThresholds();
            this.torus_mode = settings_view.getTorusMode();
            settings_view.dispose();
            createMainframe();
        } else if (event.equals("reset")) {
            promptSettings();
        } else if (event.equals("step")) {
            stepNext();
        } else if (event.equals("toggleplay")) {
            togglePlay();
        } else if (event.equals("clear")) {
            view.emptyBoard();
            clearHistory();
        }
    }

    public void createMainframe() {
        clearHistory();
        animationStarted = false;
        if (main_frame != null) {
            view.resetBoard(width, height);
        } else {
            this.main_frame = new JFrame();
            main_frame.setTitle("Conway's Game Of Life");
            main_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            main_frame.setBounds(30, 30, 1000, 1000);
            GameOfLifeView GOLview = new GameOfLifeView(width, height);
            GOLview.addObserver(this);
            this.view = GOLview;
        }
        
        if (random_factor != -1 && random_factor != 0) {
            this.view.setBoardState(GameOfLifeModel.generateRandomBoard(width,
                    height, random_factor));
        }
        
        view.setPlayback(playback_speed);
        model.setThresholds(thresholds);
        model.setTorusMode(torus_mode);
        
        main_frame.setContentPane(view);
        main_frame.setVisible(true);
    }

    public void stepNext() {
        model.setCurrentGeneration(view.getBoardState());
        // add current state to history
        history.add(view.getBoardState());
        view.setMessage("Generation " + getNumGenerations());
        view.setBoardState(model.getNextGeneration());
    }
    
    public void togglePlay() {
        Grid animation = view.getGridComponent();
        if (animation.isRunning() && animationStarted) {
            if (animation.isPaused()) {
                animation.resume();
            } else {
                animation.pause();
            }
        } else {
            view.getGridComponent().addObserver(this);
            Thread animation_thread = new Thread(view.getGridComponent());
            animation_thread.start();
            animationStarted = true;
        }
    }
    
    public int getNumGenerations() {
        return history.size();
    }
    
    public void clearHistory() {
        history.clear();
    }
    
    public void verifyPaused() {
        if (view != null) {
            if (!view.getGridComponent().isPaused()) {
                view.getGridComponent().pause();
            }
        }
    }
}
