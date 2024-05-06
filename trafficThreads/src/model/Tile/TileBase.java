package model.Tile;

import model.Vehicle;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.Random;

public class TileBase {
    protected List<String> directions;
    protected String imagePath;
    protected Vehicle currentVehicle;
    protected int posX;
    protected int posY;
    protected JLabel tileLabel;
    protected Vehicle reservedFor;


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

    private void paintPath() {
        if (this.reservedFor != null) {
            this.tileLabel.setFont(new Font("Serif", Font.PLAIN, 8));
            this.tileLabel.setForeground(Color.GREEN);
            this.tileLabel.setText(formatThreadString(this.reservedFor.getName()));
        }

        if (this.currentVehicle != null) {
            Color color = getRandomColor();
            for (TileBase tile : this.currentVehicle.getPath()) {
                JLabel label = tile.getTileLabel();
//                if (label.getBackground() == null) {
                label.setBackground(color);
//                } else {
//                    label.setBackground(null);
//                }
            }
        }
    }

    private static Color getRandomColor() {
        Random random = new Random();
        int red = random.nextInt(256);
        int green = random.nextInt(256);
        int blue = random.nextInt(256);
        return new Color(red, green, blue);
    }

    public boolean reserveTile(Vehicle vehicle) {
        if (this.reservedFor != null && this.reservedFor != vehicle) {
            return false;
        }

        if (this.currentVehicle != null && this.currentVehicle != vehicle) {
            return false;
        }

        this.setReserved(vehicle);
        return true;
    }

    public void removeReservedVehicle(Vehicle vehicle) {
        if (reservedFor != null && reservedFor == vehicle) {
            this.setReserved(null);
        }

        this.setTileCurrentImage();
    }

    public void setReserved(Vehicle reserveFor) {
        this.reservedFor = reserveFor;
    }

    public boolean isCrossing() {
        return this.directions.size() > 1;
    }

    public void removeVehicleFromTile(Vehicle vehicle) {
        if (this.currentVehicle == vehicle) {
            setCurrentVehicle(null);
        }

        this.setTileCurrentImage();
    }

    public void setTileCurrentImage() {
        if (this.currentVehicle == null) {
            String imagePath = this.getImagePath();
            String relativePath = "icons/" + imagePath;

            ImageIcon icon = new ImageIcon(getResource(relativePath));

            this.tileLabel.setFont(new Font("Serif", Font.BOLD, 8));
            this.tileLabel.setForeground(Color.RED);

            if (this.reservedFor != null) {
                this.tileLabel.setText(formatThreadString(this.reservedFor.getName()));
            } else {
                this.tileLabel.setText(null);
            }

            this.tileLabel.setIcon(icon);
        }

        if (this.currentVehicle != null) {
            String imagePath = this.currentVehicle.getImagePath();
            String relativePath = "icons/" + imagePath;

            ImageIcon icon = new ImageIcon(getResource(relativePath));

            this.tileLabel.setFont(new Font("Serif", Font.PLAIN, 8));

            if (this.currentVehicle != null) {
                this.tileLabel.setText(formatThreadString(this.currentVehicle.getName()));
            } else {
                this.tileLabel.setText(null);
            }

            this.tileLabel.setIcon(icon);
        }
    }

    //    REMOVE LATER
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

    public boolean moveVehicleToTile(Vehicle vehicle) {
        return false;
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

    protected static java.net.URL getResource(String path) {
        return TileBase.class.getClassLoader().getResource(path);
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
}
