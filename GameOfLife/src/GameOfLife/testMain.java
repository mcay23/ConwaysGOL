package GameOfLife;

public class testMain {
    public static void main(String args[]) throws InterruptedException {
        /*
        JFrame main_frame = new JFrame();
        main_frame.setTitle("Example");
        main_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       
        
        main_frame.setBounds(30, 30, 1000, 1000);
        main_frame.setContentPane(new GameOfLifeView());
        main_frame.setVisible(true);

        System.out.println("tst");
        SettingsFrame settings_view = new SettingsFrame();

        settings_view.setVisible(true);
        while(!settings_view.isAvailable()) {
            Thread.sleep(200);
        }
        */
        
        GameOfLifeController cont = new GameOfLifeController(new GameOfLifeModel());
       
    }
}
