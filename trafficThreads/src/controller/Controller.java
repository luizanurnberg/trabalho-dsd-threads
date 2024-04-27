package controller;

import mesh.Mesh;
import mesh.factory.AbstractMeshFactory;
import mesh.factory.MeshByMonitorFactory;
import mesh.factory.MeshByTrafficLightFactory;
import model.road.RoadItem;
import model.road.RoadPosition;
import model.vehicle.Vehicle;
import view.Simulation;
import view.ViewObserver;
import view.table.Table;

import java.io.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Controller implements ControllerObserver {

    private boolean start = true;
    private boolean running = true;

    private File file;
    private int lines;
    private int columns;

    private static Controller instance = null;

    private Mesh mesh;

    private ArrayList<Vehicle> vehicles;
    private final ArrayList<ViewObserver> observers;

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

    public void removeVehicle(Vehicle car) {
        this.vehicles.remove(car);
    }

    public void addObserver(ViewObserver observer) {
        this.observers.add((ViewObserver) observer);
    }

    public void removeObserver(ViewObserver observer) {
        this.observers.remove(observer);
    }

    public void loadSimulation(File file) throws IOException {
        this.file = file;
        this.createGrid(false);
        this.startMesh();
        notifyTableModel(new Table(this));
    }

    private void notifyTableModel(Table tableModelMalha) {
        for (ViewObserver observer : this.observers) {
            observer.updateTableModel(tableModelMalha);
        }
    }

    public synchronized void notifyTableModelChanged() {
        for (ViewObserver observer : this.observers) {
            observer.updateTable();
        }
    }

    public void notifyButtonChanged(boolean iniciar) {
        for (ViewObserver observer : this.observers) {
            observer.updateButton(iniciar);
        }
    }

    public void createGrid(boolean usaMonitor) throws FileNotFoundException, IOException {
        BufferedReader in = new BufferedReader(new FileReader(file));
        this.lines = Integer.parseInt(in.readLine());
        this.columns = Integer.parseInt(in.readLine());

        this.factoryGrid(false);

        for (int lineActual = 0; lineActual < lines; lineActual++) {
            String[] listTypes = in.readLine().split("\t");
            for (int columnsActual = 0; columnsActual < columns; columnsActual++) {
                int typeRoad = Integer.parseInt(listTypes[columnsActual]);
                RoadPosition roadPosition = new RoadPosition(lineActual, columnsActual);
                mesh.addItemRoad(roadPosition, new RoadItem(typeRoad, roadPosition));
            }
        }
    }

    public void factoryGrid(boolean monitor) {
        if(monitor) {
            AbstractMeshFactory factory = new MeshByMonitorFactory();
            mesh = factory.addMesh(lines, columns);
        } else {
            AbstractMeshFactory factory = new MeshByTrafficLightFactory();
            mesh = factory.addMesh(lines, columns);
        }
    }

    public void startMesh(){
        mesh.initMesh();
    }

    public void startSimulation(int numberVehicles, int numberSimultaneousVehicles, int rangeInsertion, String selectedGrid, int exclusionType) throws IOException {
        if (exclusionType == 1) {
            this.mesh = null;
            this.createGrid(true);

            mesh.initMesh();
            notifyTableModel(new Table(this));
        } else if (exclusionType == 2) {
            mesh.initMesh();
        } else if (exclusionType == 3) {
            mesh.initMesh();
        }

        new Thread(() -> {
            while (start) {
                if (this.vehicles.size() < numberVehicles) {
                    startNewVehicle(mesh.ramdomEntry());
                    notifyTableModelChanged();
                }
                sleepThread();
            }
        }).start();
    }

    public void startNewVehicle(RoadItem roadItemActual) {
        Vehicle vehicle = new Vehicle(mesh);
        this.addVehicle(vehicle);
        vehicle.setRoadActual(roadItemActual);
        new Thread(vehicle::start).start();
    }

    public void sleepThread() {
        try {
            Thread.sleep(400);
        } catch (InterruptedException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public RoadItem[][] getTraffic() {
        return this.mesh.getMesh();
    }
}
