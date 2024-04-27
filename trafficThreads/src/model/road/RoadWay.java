package model.road;

import java.util.List;

public interface RoadWay {
    public List<RoadItem> getRoadWay(RoadItem currentRoad, RoadItem lastRoad);
    public List<RoadItem> getNextRoadWay(RoadItem currentRoad, RoadItem lastRoad);

}
