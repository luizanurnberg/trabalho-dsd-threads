package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class Simulation extends JFrame{
    private JButton btnFInish;
    private JPanel jpPainel;

    public Simulation(){
        super.setSize(new Dimension(900, 900));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(this.jpPainel);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        super.setVisible(true);
    }
}
