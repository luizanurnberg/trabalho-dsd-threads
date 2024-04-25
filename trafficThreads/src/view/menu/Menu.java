package view.menu;

import javax.swing.*;
import java.awt.*;

public class Menu  extends JFrame{
    private JPanel jpPainel;
    private JLabel lbMenu;
    private JLabel lbNumberVehicles;
    private JTextField tfNumberVehicles;
    private JLabel lbNumberSimultaneousVehicles;
    private JTextField tfNumberSimultaneousVehicles;
    private JLabel lbRangeInsertion;
    private JTextField tfRangeInsertion;
    private JLabel lbGrid;
    private JRadioButton rbGrid1;
    private JRadioButton rbGrid2;
    private JRadioButton rbGrid3;
    private JLabel lbMutualExclusion;
    private JRadioButton rbMonitor;
    private JRadioButton rbSemaphore;
    private JRadioButton rbMessageExchange;
    private JButton btnStart;

    public Menu(){
        super.setSize(new Dimension(800, 600));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(this.jpPainel);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        super.setVisible(true);
    }
}
