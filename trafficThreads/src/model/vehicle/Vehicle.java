package model.vehicle;

import model.road.RoadItem;
import model.road.RoadWay;
import model.road.RoadWayBase;

import javax.swing.*;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class Vehicle extends Thread{

    private final int speed;
    private RoadItem roadActual;
    private RoadItem roadAfter;
    private ImageIcon icon;
    private Iterator<RoadItem> route;
    private RoadWay roadWay;


    public Vehicle() {
        this.speed = speedRandom();
        this.roadWay = new RoadWayBase();
    }

    public ImageIcon getIcon() {
        return icon;
    }

    public void setRoadActual(RoadItem roadActual) {
        this.roadActual = roadActual;
    }

    public int getSpeed() {
        return speed;
    }

    public RoadWay getRoadWay() {
        return roadWay;
    }

    public void setRoadWay(RoadWay roadWay) {
        this.roadWay = roadWay;
    }

    private int speedRandom() {
        Random random = new Random();
        int add = random.nextInt(250);
        return 250 + add;
    }

    private void removeVehicle() {

    }

    private synchronized List<RoadItem> getRoute() {
        return this.roadWay.getRoadWay(this.roadActual, this.roadAfter);
    }

}
