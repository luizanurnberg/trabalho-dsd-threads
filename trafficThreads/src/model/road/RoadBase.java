package model.road;

import java.util.ArrayList;
import java.util.List;

public class RoadBase implements RoadWay{
    @Override
    public List<RoadItem> getRoadWay(RoadItem currentRoad, RoadItem lastRoad) {
        List<RoadItem> way = new ArrayList<>();
        RoadItem nextRoad = null;

        do {
            nextRoad = (RoadItem) this.getNextRoadWay(currentRoad, lastRoad);
        } while (nextRoad.isBusy() || !nextRoad.isAvailable());

        nextRoad.setBusy(true);
        way.add(nextRoad);
        if(nextRoad.isCrossing()) {
            way.addAll(this.getRoadWay(nextRoad, currentRoad));
        }

        return way;
    }

    //TODO
    @Override
    public List<RoadItem> getNextRoadWay(RoadItem currentRoad, RoadItem lastRoad) {
        return List.of();
    }
}
