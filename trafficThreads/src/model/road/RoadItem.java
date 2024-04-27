package model.road;

import model.vehicle.Vehicle;

import javax.swing.*;
import java.awt.*;

public class RoadItem {
    private RoadItem itemUp;
    private RoadItem itemDown;
    private RoadItem itemRight;
    private RoadItem itemLeft;

    private RoadItem itemUpRight;
    private RoadItem itemUpLeft;
    private RoadItem itemDownRight;
    private RoadItem itemDownLeft;

    private RoadPosition roadPosition;

    private boolean isBusy = false;
    private boolean isEntry;
    private boolean isExit;
    private int type;
    private Color color;
    private ImageIcon icon;

    private Vehicle vehicle;

    public RoadItem(int type, RoadPosition roadPosition) {
        this.type = type;
        this.isEntry = false;
        this.isExit = false;
        this.roadPosition = roadPosition;
    }

    public int getType() {
        return type;
    }

    public RoadItem getItemDown() {
        return itemDown;
    }

    public RoadItem getItemDownLeft() {
        return itemDownLeft;
    }

    public RoadItem getItemDownRight() {
        return itemDownRight;
    }

    public RoadItem getItemLeft() {
        return itemLeft;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public RoadPosition getItemPosition() {
        return roadPosition;
    }

    public RoadItem getItemRight() {
        return itemRight;
    }

    public RoadItem getItemUp() {
        return itemUp;
    }

    public RoadItem getItemUpLeft() {
        return itemUpLeft;
    }

    public RoadItem getItemUpRight() {
        return itemUpRight;
    }

    public Color getColor() {
        return color;
    }

    public ImageIcon getIcon() {
        return icon;
    }

    public void setExit(boolean exit) {
        isExit = exit;
    }

    public void setEntry(boolean entry) {
        isEntry = entry;
    }

    public void setBusy(boolean busy) {
        this.isBusy = busy;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void setIcon(ImageIcon icon) {
        this.icon = icon;
    }

    public void setItemDown(RoadItem itemDown) {
        this.itemDown = itemDown;
    }

    public void setItemDownLeft(RoadItem itemDownLeft) {
        this.itemDownLeft = itemDownLeft;
    }

    public void setItemDownRight(RoadItem itemDownRight) {
        this.itemDownRight = itemDownRight;
    }

    public void setItemLeft(RoadItem itemLeft) {
        this.itemLeft = itemLeft;
    }

    public RoadPosition getRoadPosition() {
        return roadPosition;
    }

    public void setItemRight(RoadItem itemRight) {
        this.itemRight = itemRight;
    }

    public void setItemUp(RoadItem itemUp) {
        this.itemUp = itemUp;
    }

    public void setItemUpLeft(RoadItem itemUpLeft) {
        this.itemUpLeft = itemUpLeft;
    }

    public void setItemUpRight(RoadItem itemUpRight) {
        this.itemUpRight = itemUpRight;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public boolean isBusy() {
        return this.isBusy;
    }

    public boolean isEntry() {
        return this.isEntry;
    }

    public boolean isExit() {
        return this.isExit;
    }

    public boolean isCrossing() {
        return this.getType() > 4; //Cruzamentos possuem o Tipo entre 5 e 12 (Limite)
    }

    public boolean isAvailable(){
        return this.getVehicle() != null;
    }
}
