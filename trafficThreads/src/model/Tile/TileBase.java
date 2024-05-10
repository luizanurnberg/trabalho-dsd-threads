package model.Tile;

import model.Vehicle;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.List;
import java.util.Random;

public abstract class TileBase {
    protected List<String> directions;
    protected String imagePath;
    protected Vehicle vehicle;
    protected String currentImagePath;
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
        this.tileLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                paintPath();
            }
        });
    }

    public boolean isCrossing() {
        return this.directions.size() > 1;
    }

    public boolean isEmpty() {
        return this.vehicle == null;
    }

    public void removeVehicle() {
        this.vehicle = null;
        this.setTileCurrentImage();
    }

    protected int generateRandomCooldown(int minMs, int maxMs) {
        Random random = new Random();
        int minTime = minMs;
        int maxTime = maxMs;

        int randomTime = random.nextInt((maxTime - minTime) + 1) + minTime;

        return randomTime;
    }

    public void setTileCurrentImage(String imagePath) {
        this.currentImagePath = imagePath;
    }

    public void setTileCurrentImage() {
        if (this.vehicle == null) {
            this.currentImagePath = this.getImagePath();
        }

        if (this.vehicle != null) {
            this.currentImagePath = this.vehicle.getImagePath();
        }
    }

    public abstract boolean tryAcquire();

    public abstract void release() throws IOException;

    public void addVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
        this.setTileCurrentImage();
    }

    public Boolean isEntryTile(TileBase[][] tileMap) {
        if (this.posX == 0) {
            if (directions.get(0).equals("RIGHT")) {
                return true;
            }
        }
        if (this.posX == tileMap[0].length - 1) {
            if (directions.get(0).equals("LEFT")) {
                return true;
            }
        }
        if (this.posY == 0) {
            if (directions.get(0).equals("DOWN")) {
                return true;
            }
        }
        if (this.posY == tileMap.length - 1) {
            if (directions.get(0).equals("UP")) {
                return true;
            }
        }
        return false;
    }

    public Boolean isExitTile(TileBase[][] tileMap) {
        if (this.posX == 0) {
            if (directions.get(0).equals("LEFT")) {
                return true;
            }
        }
        if (this.posX == tileMap[0].length - 1) {
            if (directions.get(0).equals("RIGHT")) {
                return true;
            }
        }
        if (this.posY == 0) {
            if (directions.get(0).equals("UP")) {
                return true;
            }
        }
        if (this.posY == tileMap.length - 1) {
            if (directions.get(0).equals("DOWN")) {
                return true;
            }
        }
        return false;
    }

    // REMOVE LATER
    private void paintPath() {
        if (this.vehicle != null) {
            Color color = getRandomColor();
            for (TileBase tile : this.vehicle.getPath()) {
                JLabel label = tile.getTileLabel();
                label.setBackground(color);
            }
        }
    }

    // REMOVE LATER
    private static Color getRandomColor() {
        Random random = new Random();
        int red = random.nextInt(256);
        int green = random.nextInt(256);
        int blue = random.nextInt(256);
        return new Color(red, green, blue);
    }

    // REMOVE LATER
    public static String formatThreadString(String input) {
        String regex = "Thread-(\\d+)";

        java.util.regex.Pattern pattern = java.util.regex.Pattern.compile(regex);
        java.util.regex.Matcher matcher = pattern.matcher(input);

        if (matcher.find()) {
            String dynamicNumber = matcher.group(1);
            return dynamicNumber;
        } else {
            return input;
        }
    }


    public boolean isAvaliable() {
        return this.vehicle == null;
    }

    public List<String> getDirections() {
        return directions;
    }

    public void setDirections(List<String> directions) {
        this.directions = directions;
    }

    public String getCurrentImagePath() {
        return this.currentImagePath;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
}
