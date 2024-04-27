package view.table;

import constants.RoadTranslationConst;
import model.road.RoadItem;

import java.awt.Component;
import java.util.Objects;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;

public class TableCellRenderer extends DefaultTableCellRenderer{
    public Component getTableCellRendererComponent(JTable table, Object road, boolean isSelected, boolean hasFocus, int row, int column) {
        if(road != null) {
            RoadItem roadActual = (RoadItem) road;

            super.setIcon(roadActual.getIcon());

            switch(roadActual.getType()) {
                case 1: {
                    ImageIcon image = new ImageIcon(Objects.requireNonNull(this.getClass().getResource(RoadTranslationConst.icon1.getPath())));
                    super.setIcon(image);
                    break;
                }
                case 2: {
                    ImageIcon image = new ImageIcon(Objects.requireNonNull(this.getClass().getResource(RoadTranslationConst.icon2.getPath())));
                    super.setIcon(image);
                    break;
                }
                case 3: {
                    ImageIcon image = new ImageIcon(Objects.requireNonNull(this.getClass().getResource(RoadTranslationConst.icon3.getPath())));
                    super.setIcon(image);
                    break;
                }
                case 4: {
                    ImageIcon image = new ImageIcon(Objects.requireNonNull(this.getClass().getResource(RoadTranslationConst.icon4.getPath())));
                    super.setIcon(image);
                    break;
                }
            }

            if(roadActual.getVehicle()!= null) {
                super.setIcon(roadActual.getVehicle().getIcon());
            }
            else if(roadActual.isBusy()) {
                ImageIcon image = new ImageIcon(Objects.requireNonNull(this.getClass().getResource(RoadTranslationConst.icon12.getPath())));
                super.setIcon(image);
            }


        }

        super.setHorizontalAlignment(CENTER);
        super.setVerticalAlignment(CENTER);
        super.repaint();

        return this;
    }
}
