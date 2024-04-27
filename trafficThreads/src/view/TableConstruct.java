package view;

import model.Road;
import model.Rules;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Paths;
import java.util.Scanner;

public class TableConstruct extends AbstractTableModel {

    private int lines;
    private int columns;
    private Road road[][];
    private static final String FILES_DIRECTORY =  Paths.get("").toAbsolutePath() +"/src/grids/";
    private Rules rules;

    public int getLines() {
        return lines;
    }

    public void setLines(int lines) {
        this.lines = lines;
    }

    public int getColumns() {
        return columns;
    }

    public void setColumns(int columns) {
        this.columns = columns;
    }

    public Road[][] getRoads() {
        return road;
    }

    @Override
    public int getRowCount() {
        return 0;
    }

    @Override
    public int getColumnCount() {
        return 0;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return new ImageIcon(this.road[columnIndex][rowIndex].getIconDirectory());
    }

    public void newMatrix() {
        Scanner scanner = null;
        try {
            File meshFile = new File(FILES_DIRECTORY + this.rules.getFileNameGrid());
            scanner = new Scanner(meshFile);
            while (scanner.hasNextInt()) {
                this.setLines(scanner.nextInt());
                this.setColumns(scanner.nextInt());
                this.road = new Road[this.columns][this.lines];
                for (int line = 0; line < this.lines; line++) {
                    for (int column = 0; column < this.columns; column++) {
                        int direction = scanner.nextInt();
                        Road road = new Road(column, line, direction, rules.getExclusionType());
                        if (road.isRoad()) {
                            road. setEntryOrExit(this);
                        }
                        this.road[column][line] = road;
                    }
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            scanner.close();
        }
    }
}
