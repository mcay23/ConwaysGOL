package GameOfLife;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class GameOfLifeView extends JPanel implements ActionListener, BoardListener, Observable {

    private static final long serialVersionUID = 1L;
    private GOLBoard board;
    private JLabel message;
    
    private List<Observer> observers;
    
    public GameOfLifeView(int rows, int cols) {
        setLayout(new BorderLayout());
        resetBoard(rows, cols);
        
        JPanel message_panel = new JPanel();
        message_panel.setLayout(new BorderLayout());
        
        JPanel button_panel = new JPanel();
        button_panel.setLayout(new FlowLayout());
        message_panel.add(button_panel, BorderLayout.EAST);
        
        JButton reset_button = new JButton("Reset");
        reset_button.addActionListener(this);
        JButton next_button = new JButton("Step");
        next_button.addActionListener(this);
        JButton play_button = new JButton("Play/Pause");
        play_button.addActionListener(this);
        JButton clear_button = new JButton("Clear");
        clear_button.addActionListener(this);
        
        button_panel.add(next_button);
        button_panel.add(play_button);
        button_panel.add(clear_button);
        button_panel.add(reset_button);
        
        message = new JLabel("Conway's Game of Life");
        message_panel.add(message, BorderLayout.CENTER);
        
        button_panel.setBackground(Color.lightGray);
        message_panel.setBackground(Color.lightGray);

        add(message_panel, BorderLayout.SOUTH);
        
        observers = new ArrayList<Observer>();
    }
    
    public void resetBoard(int width, int height) {
        // remove old board from  layout
        if (board != null) {
            BorderLayout layout = (BorderLayout)this.getLayout();
            this.remove(layout.getLayoutComponent(BorderLayout.CENTER));
        }
        board = new GOLBoard(width, height);
        add(board, BorderLayout.CENTER);
        setMessage("Conway's Game of Life");
        board.addListener(this);
    }

    @Override
    public void boardPressed(int x, int y) {
        if (!getGridComponent().isPaused()) {
            getGridComponent().pause();
        }
        board.toggleSpot(x, y);
    }
    
    public boolean[][] getBoardState() {
        return board.getBoardState();
    }
    
    public void emptyBoard() {
        setMessage("Conway's Game of Life");
        board.emptyBoard();
    }
    
    public void setBoardState(boolean[][] state) {
        board.setBoardState(state);
    }
    
    public void setPlayback(int speed) {
        board.setPlayback(speed);
    }
    
    public Grid getGridComponent() {
        return board.getGridComponent();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String event = e.getActionCommand();
        if ("Reset".equals(event)) {
            notifyObservers("reset");
        } else if ("Step".equals(event)) {
            notifyObservers("step");
        } else if ("Play/Pause".equals(event)) {
            notifyObservers("toggleplay");
        } else if ("Clear".equals(event)) {
            notifyObservers("clear");
        }
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
    
    public void setMessage(String s) {
        if (message == null) {
            message = new JLabel();
        }
        message.setText(s);
    }
}
