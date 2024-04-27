package mesh;

import model.road.RoadItem;
import model.road.RoadPosition;
import model.vehicle.Vehicle;

import java.util.ArrayList;
import java.util.List;

public abstract class Mesh {
    protected final RoadItem[][] roadItems;
    private final int lines;
    private final int columns;
    private final List<RoadItem> entry;

    public Mesh(int lines, int columns) {
        this.lines = lines;
        this.columns = columns;
        this.entry = new ArrayList<>();
        this.roadItems = new RoadItem[lines][columns];
    }

    public abstract void addVehicle(RoadPosition roadPosition, Vehicle vehicle);
    public abstract void removeVehicle(RoadPosition roadPosition, Vehicle vehicle);
}
