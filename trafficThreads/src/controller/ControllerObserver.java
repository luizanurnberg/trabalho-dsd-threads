package controller;

import model.road.RoadItem;
import view.ViewObserver;

public interface ControllerObserver {
    public RoadItem[][] getTraffic();

    public void addObserver(ViewObserver observer);

    public void removeObserver(ViewObserver observer);
}
