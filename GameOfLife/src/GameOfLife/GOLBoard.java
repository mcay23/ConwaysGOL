package GameOfLife;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JPanel;

public class GOLBoard extends JPanel implements MouseListener {
    private static final long serialVersionUID = 1L;

    private int rows;
    private int cols;
    private Grid grid;
    private List<BoardListener> listeners;

    public GOLBoard(int rows, int cols) {
        setLayout(new BorderLayout());
        grid = new Grid(rows, cols);

        this.add(grid, BorderLayout.CENTER);
        listeners = new ArrayList<BoardListener>();
        addMouseListener(this);

        this.rows = rows;
        this.cols = cols;
    }

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }

    public void addListener(BoardListener l) {
        listeners.add(l);
    }

    public void removeListener(BoardListener l) {
        listeners.remove(l);
    }
    
    public void setPlayback(int speed) {
        grid.setPlayback(speed);
    }
    
    public void setSpot(int x, int y) {
        grid.setSpot(x, y);
    }

    public void unsetSpot(int x, int y) {
        grid.unsetSpot(x, y);
    }

    public void toggleSpot(int x, int y) {
        grid.toggleSpot(x, y);
    }

    public void emptyBoard() {
        grid.emptyBoard();
    }

    public boolean isAlive(int x, int y) {
        return grid.isEmpty(x, y);
    }

    public boolean[][] getBoardState() {
        return grid.getBoardState();
    }

    public void setBoardState(boolean[][] state) {
        grid.setBoardState(state);
    }

    public Grid getGridComponent() {
        return grid;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        double width = getSize().width;
        double height = getSize().height;
        double squareWidth = width / cols;
        double squareHeight = height / rows;
        int x = (int) (e.getX() / squareWidth);
        int y = (int) (e.getY() / squareHeight);
        for (BoardListener b : listeners) {
            b.boardPressed(x, y);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }
}

class Grid extends JComponent implements Runnable, Observable {
    private static final long serialVersionUID = 1L;

    private int rows;
    private int cols;
    private int playback_speed;
    private boolean[][] grid_array;
    private List<Observer> observers;
    private volatile boolean running = true;
    private volatile boolean paused = false;
    private final Object pauseLock = new Object();

    public Grid(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        observers = new ArrayList<Observer>();

        grid_array = new boolean[rows][cols];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                grid_array[i][j] = false;
            }
        }
    }

    @Override
    public void run() {
        while (running) {
            synchronized (pauseLock) {
                if (!running) {
                    break;
                }
                if (paused) {
                    try {
                        synchronized (pauseLock) {
                            pauseLock.wait();
                        }
                    } catch (InterruptedException ex) {
                        break;
                    }
                    if (!running) {
                        break;
                    }
                }
            }
            notifyObservers("step");
            repaint();
            try {
                Thread.sleep(playback_speed);
            } catch (Exception e) {
            }
        } 
    }
    
    public boolean isRunning() {
        return running;
    }
    
    public boolean isPaused() {
        return paused;
    }
    
    public void stop() {
        running = false;
        resume();
    }

    public void pause() {
        paused = true;
    }

    public void resume() {
        synchronized (pauseLock) {
            paused = false;
            pauseLock.notifyAll(); 
        }
    }
    
    public void setPlayback(int speed) {
        playback_speed = speed;
    }

    public void paint(Graphics g) {
        double width = getSize().width;
        double height = getSize().height;
        double squareWidth = width / cols;
        double squareHeight = height / rows;

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (grid_array[i][j]) {
                    g.setColor(Color.RED);
                } else {
                    g.setColor(Color.WHITE);
                }
                g.fillRect((int) (j * squareWidth), (int) (i * squareHeight),
                        (int) squareWidth + 1, (int) squareHeight + 1);
            }
        }

        g.setColor(Color.BLACK);

        for (int i = 0; i < rows; i++) {
            g.drawLine(0, (int) (i * squareHeight), (int) width,
                    (int) (i * squareHeight));
        }

        for (int i = 0; i < cols; i++) {
            g.drawLine((int) (i * squareWidth), 0, (int) (i * squareWidth),
                    (int) height);
        }

    }

    public void setSpot(int x, int y) {
        grid_array[y][x] = true;
        repaint();
    }

    public void unsetSpot(int x, int y) {
        grid_array[y][x] = false;
        repaint();
    }

    public void toggleSpot(int x, int y) {
        if (grid_array[y][x]) {
            unsetSpot(x, y);
        } else {
            setSpot(x, y);
        }
    }

    public void emptyBoard() {
        grid_array = new boolean[rows][cols];
        repaint();
    }

    public boolean isEmpty(int x, int y) {
        return grid_array[x][y];
    }

    public boolean[][] getBoardState() {
        return grid_array;
    }

    public void setBoardState(boolean[][] state) {
        this.grid_array = state;
        repaint();
    }

    @Override
    public void notifyObservers(String event) {
        for (Observer o : observers) {
            o.update(event);
        }
    }

    @Override
    public void addObserver(Observer o) {
        observers.add(o);
    }

    @Override
    public void removeObserver(Observer o) {
        observers.remove(o);
    }
}
