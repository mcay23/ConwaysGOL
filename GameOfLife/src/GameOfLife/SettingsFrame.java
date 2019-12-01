package GameOfLife;

import java.awt.Color;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.JSlider;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import javax.swing.SwingConstants;
import javax.swing.JCheckBox;

public class SettingsFrame extends JFrame
        implements ActionListener, Observable {
    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField width_textfield;
    private JLabel lblX;
    private JTextField height_textfield;
    private JLabel random_label;
    private JTextField random_textfield;
    private JSlider playback_slider;
    private JLabel lblMs;
    JCheckBox check_torus;

    private int width;
    private int height;
    private int speed;
    private int[] thresholds;
    private double random_val;
    private boolean torus_mode;

    private List<Observer> observers;
    private JTextField ST_text1;
    private JTextField ST_text2;
    private JTextField BT_text1;
    private JTextField BT_text2;

    public SettingsFrame() {
        observers = new ArrayList<Observer>();
        thresholds = new int[4];

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);
        setBounds(100, 100, 450, 600);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel settings_label = new JLabel("Settings");
        settings_label.setFont(new Font("Tahoma", Font.PLAIN, 30));
        settings_label.setBounds(22, 13, 169, 37);
        contentPane.add(settings_label);

        width_textfield = new JTextField();
        width_textfield.setBounds(126, 70, 42, 22);
        contentPane.add(width_textfield);
        width_textfield.setColumns(10);

        JLabel size_label = new JLabel("Size :");
        size_label.setBounds(22, 73, 56, 16);
        contentPane.add(size_label);

        lblX = new JLabel("x");
        lblX.setBounds(180, 73, 19, 16);
        contentPane.add(lblX);

        height_textfield = new JTextField();
        height_textfield.setColumns(10);
        height_textfield.setBounds(198, 70, 42, 22);
        contentPane.add(height_textfield);

        random_label = new JLabel("Random Factor :");
        random_label.setBounds(22, 126, 124, 16);
        contentPane.add(random_label);

        random_textfield = new JTextField();
        random_textfield.setForeground(Color.BLACK);
        random_textfield.setBounds(126, 123, 42, 22);
        contentPane.add(random_textfield);
        random_textfield.setColumns(10);

        JTextPane hint1 = new JTextPane();
        hint1.setEditable(false);
        hint1.setBackground(UIManager.getColor("Button.background"));
        hint1.setForeground(UIManager.getColor("Button.darkShadow"));
        hint1.setText("Enter a size between 10 x 10 and 500 by 500.");
        hint1.setBounds(281, 42, 139, 60);
        contentPane.add(hint1);

        JTextPane hint2 = new JTextPane();
        hint2.setEditable(false);
        hint2.setText(
                "Enter a factor between 0.0 and 1.0 or leave blank to set initial conditions yourself.");
        hint2.setForeground(UIManager.getColor("Button.darkShadow"));
        hint2.setBackground(SystemColor.menu);
        hint2.setBounds(281, 115, 141, 79);
        contentPane.add(hint2);

        JButton btnApply = new JButton("Apply");
        btnApply.addActionListener(this);
        btnApply.setBounds(214, 519, 97, 25);
        contentPane.add(btnApply);

        playback_slider = new JSlider(JSlider.HORIZONTAL, 10, 1000, 500);
        playback_slider.setBounds(40, 194, 200, 26);

        playback_slider.setMajorTickSpacing(100);
        playback_slider.setPaintTicks(true);
        contentPane.add(playback_slider);

        JLabel lblPlaybackSpeed = new JLabel("Playback Interval :");
        lblPlaybackSpeed.setBounds(22, 165, 124, 16);
        contentPane.add(lblPlaybackSpeed);

        lblMs = new JLabel("500 ms");
        lblMs.setBounds(194, 219, 56, 16);
        contentPane.add(lblMs);

        JLabel lblSurviveThreshold = new JLabel("Survive Threshold :");
        lblSurviveThreshold.setBounds(22, 256, 113, 16);
        contentPane.add(lblSurviveThreshold);

        ST_text1 = new JTextField();
        ST_text1.setHorizontalAlignment(SwingConstants.CENTER);
        ST_text1.setText("2");
        ST_text1.setColumns(10);
        ST_text1.setBounds(52, 298, 42, 22);
        contentPane.add(ST_text1);

        ST_text2 = new JTextField();
        ST_text2.setHorizontalAlignment(SwingConstants.CENTER);
        ST_text2.setText("3");
        ST_text2.setColumns(10);
        ST_text2.setBounds(203, 298, 42, 22);
        contentPane.add(ST_text2);

        JLabel ltort = new JLabel("<=   x   <=");
        ltort.setBounds(116, 301, 75, 16);
        contentPane.add(ltort);

        JLabel lblBirthThreshold = new JLabel("Birth Threshold :");
        lblBirthThreshold.setBounds(22, 343, 113, 16);
        contentPane.add(lblBirthThreshold);

        BT_text1 = new JTextField();
        BT_text1.setText("3");
        BT_text1.setHorizontalAlignment(SwingConstants.CENTER);
        BT_text1.setColumns(10);
        BT_text1.setBounds(52, 385, 42, 22);
        contentPane.add(BT_text1);

        JLabel ltort_2 = new JLabel("<=   y   <=");
        ltort_2.setBounds(116, 388, 75, 16);
        contentPane.add(ltort_2);

        BT_text2 = new JTextField();
        BT_text2.setText("3");
        BT_text2.setHorizontalAlignment(SwingConstants.CENTER);
        BT_text2.setColumns(10);
        BT_text2.setBounds(203, 385, 42, 22);
        contentPane.add(BT_text2);
        
        JButton btnCancel = new JButton("Cancel");
        btnCancel.setBounds(323, 519, 97, 25);
        btnCancel.addActionListener(this);
        contentPane.add(btnCancel);
        
        check_torus = new JCheckBox("Torus Mode");
        check_torus.setBounds(22, 444, 113, 25);
        contentPane.add(check_torus);

        playback_slider.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                int val = playback_slider.getValue();
                lblMs.setText(val + " ms");
            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Cancel")) {
            this.dispose();
            
        } else {
            String width_text = width_textfield.getText();
            String height_text = height_textfield.getText();
            String random_factor = random_textfield.getText();

            try {
                width = Integer.parseInt(width_text);
                height = Integer.parseInt(height_text);
                boolean setRandom = true;
                if (random_factor.equals("")) {
                    setRandom = false;
                } else {
                    random_val = Double.parseDouble(random_factor);
                }
                if (width <= 0 || width > 500 || height <= 0 || height > 500
                        || ((random_val < 0 || random_val > 1.0) && setRandom)) {
                    throw new IllegalArgumentException();
                }
                thresholds[0] = Integer.parseInt(ST_text1.getText());
                thresholds[1] = Integer.parseInt(ST_text2.getText());
                thresholds[2] = Integer.parseInt(BT_text1.getText());
                thresholds[3] = Integer.parseInt(BT_text2.getText());
                for (int num : thresholds) {
                    if (num < 0 || num > 8) {
                        throw new IllegalArgumentException();
                    }
                }
                if (thresholds[0] > thresholds[1]
                        || thresholds[2] > thresholds[3]) {
                    throw new IllegalArgumentException();
                }
            } catch (Exception ex) {
                JFrame frame = new JFrame("Error");
                JOptionPane.showMessageDialog(frame,
                        "Invalid input! Variables will be set to default");
                random_val = -1;
                width = 30;
                height = 30;
                thresholds[0] = 2;
                thresholds[1] = 3;
                thresholds[2] = 3;
                thresholds[3] = 3;
            }

            speed = playback_slider.getValue();
            torus_mode = check_torus.isSelected();

            setVisible(false);

            notifyObservers("settings_apply");
        }
    }

    public int getWidthInput() {
        return width;
    }

    public int getHeightInput() {
        return height;
    }

    public double getRandomInput() {
        return random_val;
    }

    public int[] getThresholds() {
        return thresholds;
    }

    public int getSpeedMillis() {
        return speed;
    }
    
    public boolean getTorusMode() {
        return torus_mode;
    }
    
    public double[] exportSettings() {
        double[] settings = new double[9];
        settings[0] = width;
        settings[1] = height;
        settings[2] = random_val;
        settings[3] = speed;
        settings[4] = thresholds[0];
        settings[5] = thresholds[1];
        settings[6] = thresholds[2];
        settings[7] = thresholds[3];
        settings[8] = torus_mode ? 1 : -1;
        return settings;
    }
    
    public void setDefaultSettings(double[] settings) {
        width_textfield.setText(Integer.toString((int) settings[0]));
        height_textfield.setText(Integer.toString((int) settings[1]));
        double random_val = settings[2];
        if (random_val <= 0 || random_val > 1.0) {
            random_textfield.setText("");
        } else {
            random_textfield.setText(Double.toString(random_val));
        }        
        lblMs.setText(Double.toString(settings[3]));
        ST_text1.setText(Integer.toString((int) settings[4]));
        ST_text2.setText(Integer.toString((int) settings[5]));
        BT_text1.setText(Integer.toString((int) settings[6]));
        BT_text2.setText(Integer.toString((int) settings[7]));
        
        
        if ((new Double(settings[8])).equals(1.0)) {
            check_torus.setSelected(true);
        } else {
            check_torus.setSelected(false);
        }
        
        playback_slider.setValue((int) settings[3]);
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
