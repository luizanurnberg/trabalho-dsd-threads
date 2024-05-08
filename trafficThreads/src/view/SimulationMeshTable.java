package view;

import model.Tile.TileBase;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.net.URL;

public class SimulationMeshTable extends AbstractTableModel {

    private int lines;
    private int columns;
    private TileBase[][] tileMap;

    public SimulationMeshTable(TileBase[][] tileMap) {
        this.tileMap = tileMap;

        this.lines = this.tileMap.length;
        this.columns = this.tileMap[0].length;
    }

    public int getLines() {
        return lines;
    }

    public int getColumns() {
        return columns;
    }

    @Override
    public int getRowCount() {
        return this.getLines();
    }

    @Override
    public int getColumnCount() {
        return this.getColumns();
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        TileBase tile = this.tileMap[rowIndex][columnIndex];

        String imagePath = tile.getCurrentImagePath();
        String relativePath = "icons/" + imagePath;

        URL url = getResource(relativePath);
        return new ImageIcon(url);
    }

    protected static java.net.URL getResource(String path) {
        return SimulationMeshTable.class.getClassLoader().getResource(path);
    }
}