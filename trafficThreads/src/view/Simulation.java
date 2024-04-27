package view;

import javax.swing.*;
import java.awt.*;

public class Simulation extends JFrame{
    private JButton btnFinish;
    private JPanel jpPainel;
    private JTable tbGrids;

    public Simulation(){
        super.setSize(new Dimension(900, 900));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(this.jpPainel);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        super.setVisible(true);
    }
}
