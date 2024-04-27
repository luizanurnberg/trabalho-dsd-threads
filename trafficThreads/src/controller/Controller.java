package controller;

import model.road.RoadItem;
import model.vehicle.Vehicle;
import view.ViewObserver;

import java.io.*;
import java.util.ArrayList;

public class Controller implements ControllerObserver {

    private boolean start = true;
    private boolean running = true;

    private File file;
    private int lines;
    private int columns;

    private static Controller instance = null;

    private ArrayList<Vehicle> vehicles;
    private final ArrayList<ControllerObserver> observers;

    public Controller() {
        this.observers = new ArrayList<>();
        this.vehicles = new ArrayList<>();
    }

    public static Controller getInstance() {
        if (instance == null) {
            instance = new Controller();
        }
        return instance;
    }

    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    public void setStart(boolean start) {
        this.start = start;
    }

    public void addVehicle(Vehicle car) {
        this.vehicles.add(car);
    }

    public void removeAllVehicles() {
        this.vehicles = new ArrayList<Vehicle>();
    }

    public void removeVeiculo(Vehicle car) {
        this.vehicles.remove(car);
    }

    public void addObserver(ViewObserver observer) {
        this.observers.add((ControllerObserver) observer);
    }

    public void removeObserver(ViewObserver observer) {
        this.observers.remove(observer);
    }

    public void loadSimulation(File file) throws IOException {
        this.file = file;
        this.createGrid(false);
    }

    public void createGrid(boolean usaMonitor) throws FileNotFoundException, IOException {
        BufferedReader in = new BufferedReader(new FileReader(file));
        this.lines = Integer.parseInt(in.readLine());
        this.columns = Integer.parseInt(in.readLine());

        this.factoryGrid(false);

        for (int lineActual = 0; lineActual < lines; lineActual++) {

        }
    }

    public void factoryGrid(boolean monitor) {

    }

    public synchronized void notifyTableModelChanged() {

    }

    @Override
    public RoadItem[][] getTraffic() {
        return new RoadItem[0][];
    }
}
