package model.road;

import java.util.List;

public class RoadWayBase implements RoadWay{
    @Override
    public List<RoadItem> getRoadWay(RoadItem currentRoad, RoadItem lastRoad) {
        return List.of();
    }

    @Override
    public RoadItem getNextRoadWay(RoadItem currentRoad, RoadItem lastRoad) {
        return null;
    }
}
