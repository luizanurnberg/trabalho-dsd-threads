package model.road;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
    public RoadItem getNextRoadWay(RoadItem currentRoad, RoadItem lastRoad) {
        RoadItem road = null;

        switch (currentRoad.getType()) {
            case 1: {
                if(currentRoad.getItemRight().getType() == currentRoad.getType()){
                    Random random = new Random();
                    int option = random.nextInt(5);

                    if (option == 1) {
                        road = currentRoad.getItemUpRight();
                    } else {
                        road = currentRoad.getItemUp();
                    }
                }
                else if(currentRoad.getItemLeft().getType() == currentRoad.getType()) {
                    Random random = new Random();
                    int option = random.nextInt(5);

                    if (option == 1) {
                        road = currentRoad.getItemUpLeft();
                    } else {
                        road = currentRoad.getItemUp();
                    }
                }
                else {
                    road = currentRoad.getItemUp();
                }
                break;
            }
            case 2: {
                if(currentRoad.getItemUp().getType() == currentRoad.getType()){
                    Random random = new Random();
                    int option = random.nextInt(5);

                    if (option == 1) {
                        road = currentRoad.getItemUpRight();
                    } else {
                        road = currentRoad.getItemRight();
                    }
                }
                else if(currentRoad.getItemDown().getType() == currentRoad.getType()) {
                    Random random = new Random();
                    int option = random.nextInt(5);

                    if (option == 1) {
                        road = currentRoad.getItemDownRight();
                    } else {
                        road = currentRoad.getItemRight();
                    }
                }
                else {
                    road = currentRoad.getItemRight();
                }

                break;

            }
            case 3: {
                if(currentRoad.getItemLeft().getType() == currentRoad.getType()){
                    Random random = new Random();
                    int option = random.nextInt(5);

                    if (option == 1) {
                        road = currentRoad.getItemDownLeft();
                    } else {
                        road = currentRoad.getItemDown();
                    }
                }
                else if(currentRoad.getItemRight().getType() == currentRoad.getType()) {
                    Random random = new Random();
                    int option = random.nextInt(5);

                    if (option == 1) {
                        road = currentRoad.getItemDownRight();
                    } else {
                        road = currentRoad.getItemDown();
                    }
                }
                else {
                    road = currentRoad.getItemDown();
                }
                break;

            }
            case 4: {
                if(currentRoad.getItemUp().getType() == currentRoad.getType()){
                    Random random = new Random();
                    int option = random.nextInt(5);

                    if (option == 1) {
                        road = currentRoad.getItemUpLeft();
                    } else {
                        road = currentRoad.getItemLeft();
                    }
                }
                else if(currentRoad.getItemDown().getType() == currentRoad.getType()) {
                    Random random = new Random();
                    int option = random.nextInt(5);

                    if (option == 1) {
                        road = currentRoad.getItemDownLeft();
                    } else {
                        road = currentRoad.getItemLeft();
                    }
                }
                else {
                    road = currentRoad.getItemLeft();
                }
                break;
            }
            case 5: {
                road = currentRoad.getItemUp();
                break;
            }
            case 6: {
                road = currentRoad.getItemRight();
                break;
            }
            case 7: {
                road = currentRoad.getItemDown();
                break;
            }
            case 8: {
                road = currentRoad.getItemLeft();
                break;
            }
            case 9: {
                if (lastRoad.getType() == 11 || lastRoad.getType() == 9) {
                    road = currentRoad.getItemRight();
                }
                else {
                    Random random = new Random();
                    int option = random.nextInt(2);

                    if (option == 1) {
                        road = currentRoad.getItemUp();
                    } else {
                        road = currentRoad.getItemRight();
                    }
                }
                break;
            }
            case 10: {
                if (lastRoad.getType() == 9 || lastRoad.getType() == 10) {
                    road = currentRoad.getItemUp();
                } else {
                    Random random = new Random();
                    int option = random.nextInt(2);

                    if (option == 1) {
                        road = currentRoad.getItemUp();
                    } else {
                        road = currentRoad.getItemLeft();
                    }
                }
                break;
            }
            case 11: {
                if (lastRoad.getType() == 12 || lastRoad.getType() == 11) {
                    road = currentRoad.getItemDown();
                } else {
                    Random random = new Random();
                    int option = random.nextInt(2);

                    if (option == 1) {
                        road = currentRoad.getItemDown();
                    } else {
                        road = currentRoad.getItemRight();
                    }
                }
                break;
            }
            case 12: {
                if (lastRoad.getType() == 10 || lastRoad.getType() == 12) {
                    road = currentRoad.getItemLeft();
                } else {
                    Random random = new Random();
                    int option = random.nextInt(2);

                    if (option == 1) {
                        road = currentRoad.getItemDown();
                    } else {
                        road = currentRoad.getItemLeft();
                    }
                }
                break;
            }
        }
        return road;
    }
}
