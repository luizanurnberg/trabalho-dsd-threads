package model.vehicle;

import controller.Controller;
import model.road.RoadItem;
import model.road.RoadWay;
import model.road.RoadWayBase;

import javax.swing.*;
import java.util.Iterator;
import java.util.List;

public class Vehicle extends Thread{

    private final int speed;
    private RoadItem roadActual;
    private RoadItem roadAfter;
    private ImageIcon icon;
    private Iterator<RoadItem> route;
    private RoadWay roadWay;


    public Vehicle(int speed) {
        this.speed = speed;
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

    public synchronized void start() {
        while (Controller.getInstance().isRunning()) {
            try {
                if (this.roadActual.isExit()) {
                    this.roadActual.setBusy(false);
                    this.roadActual.setVehicle(null);

                    removeVehicle();

                    return;
                }

                if (this.route == null || !this.route.hasNext()) {
                    this.route = this.getRoute().iterator();
                }

                RoadItem proximaPista = this.route.next();

                this.roadAfter = this.roadActual;
                this.roadAfter.setVehicle(null);
                this.roadAfter.setBusy(false);

                this.roadActual = proximaPista;

                //malhaViaria.adicionarVeiculo(roadActual.getPosicaoPista(), this);

                Controller.getInstance().notifyTableModelChanged();

                Thread.sleep(this.getSpeed());
            } catch (InterruptedException e) {
                System.out.println("Erro");
            }
        }
    }

    private void removeVehicle() {

    }

    private synchronized List<RoadItem> getRoute() {
        return this.roadWay.getRoadWay(this.roadActual, this.roadAfter);
    }

}
