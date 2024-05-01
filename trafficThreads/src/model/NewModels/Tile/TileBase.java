package model.NewModels.Tile;

import controller.SimulationController;
import model.NewModels.Vehicle;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class TileBase {
    protected List<String> directions;
    protected String imagePath;
    protected Vehicle currentVehicle;
    protected int posX;
    protected int posY;
    protected JLabel tileLabel;

    public int getPosX() {
        return posX;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public int getPosY() {
        return posY;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }

    public JLabel getTileLabel() {
        return tileLabel;
    }

    public void setTileLabel(JLabel tileLabel) {
        this.tileLabel = tileLabel;
    }

    public boolean isAvaliable() {
        return this.currentVehicle == null;
    }

    public List<String> getDirections() {
        return directions;
    }

    public void setDirections(List<String> directions) {
        this.directions = directions;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public Vehicle getCurrentVehicle() {
        return currentVehicle;
    }

    public void setCurrentVehicle(Vehicle currentVehicle) {
        this.currentVehicle = currentVehicle;
    }

    public Boolean isEntryTile(TileBase tile) throws IOException {
        String pathFile = "grids/mesh2.txt";

        URL relativeFilePath = SimulationController.class.getClassLoader().getResource(pathFile);
        BufferedReader reader = new BufferedReader(new InputStreamReader(relativeFilePath.openStream()));

        int rows = Integer.parseInt(reader.readLine());
        int cols = Integer.parseInt(reader.readLine());

        int[][] grid = new int[rows][cols];
        this.posX = 25;
        this.posY = 2;
        List<String> directions = new ArrayList<>();
        directions.add("DOWN");
        this.setDirections(directions);
        directions = this.getDirections();

        if (posX == 0) {
            if (directions.get(0).equals("RIGHT")) {
                return true;
            }
        }
        if (posX == grid.length) {
            if (directions.get(0).equals("LEFT")) {
                return true;
            }
        }
        if (posY == 0) {
            if (directions.get(0).equals("DOWN")) {
                return true;
            }
        }
        if (posY == grid[0].length) {
            if (directions.get(0).equals("UP")) {
                return true;
            }
        }
        return false;
    }

    public Boolean isExitTile() throws IOException {
        String pathFile = "grids/mesh2.txt";

        URL relativeFilePath = SimulationController.class.getClassLoader().getResource(pathFile);
        BufferedReader reader = new BufferedReader(new InputStreamReader(relativeFilePath.openStream()));

        int rows = Integer.parseInt(reader.readLine());
        int cols = Integer.parseInt(reader.readLine());

        int[][] grid = new int[rows][cols];
        this.posX = 25;
        this.posY = 2;
        List<String> directions = new ArrayList<>();
        directions.add("DOWN");
        this.setDirections(directions);
        directions = this.getDirections();

        if (posX == 0) {
            if (directions.get(0).equals("LEFT")) {
                return true;
            }
        }
        if (posX == grid.length) {
            if (directions.get(0).equals("RIGHT")) {
                return true;
            }
        }
        if (posY == 0) {
            if (directions.get(0).equals("UP")) {
                return true;
            }
        }
        if (posY == grid[0].length) {
            if (directions.get(0).equals("DOWN")) {
                return true;
            }
        }
        return false;
    }
}
