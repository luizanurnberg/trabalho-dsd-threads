package constants;

import java.util.Arrays;
import java.util.List;

public enum TerrainType {
    NOTHING(0, "grass_background.png", Arrays.asList("NONE")),
    ROAD_UP(1, "arrow_up.png", Arrays.asList("UP")),
    ROAD_RIGHT(2, "arrow_right.png", Arrays.asList("RIGHT")),
    ROAD_DOWN(3, "arrow_down.png", Arrays.asList("DOWN")),
    ROAD_LEFT(4, "arrow_left.png", Arrays.asList("LEFT")),
    INTERSECTION_UP(5, "double_arrow_up.png", Arrays.asList("UP", "UP")),
    INTERSECTION_RIGHT(6, "double_arrow_right.png", Arrays.asList("RIGHT", "RIGHT")),
    INTERSECTION_DOWN(7, "double_arrow_down.png", Arrays.asList("DOWN", "DOWN")),
    INTERSECTION_LEFT(8, "double_arrow_left.png", Arrays.asList("LEFT", "LEFT")),
    INTERSECTION_UP_RIGHT(9, "double_arrow_top_right.png", Arrays.asList("UP", "RIGHT")),
    INTERSECTION_UP_LEFT(10, "double_arrow_top_left.png", Arrays.asList("UP", "LEFT")),
    INTERSECTION_RIGHT_DOWN(11, "double_arrow_right_down.png", Arrays.asList("RIGHT", "DOWN")),
    INTERSECTION_DOWN_LEFT(12, "double_arrow_down_left.png", Arrays.asList("DOWN", "LEFT"));

    private final int value;
    private final String imagePath;
    private final List<String> directions;

    TerrainType(int value, String imagePath, List<String> directions) {
        this.value = value;
        this.imagePath = imagePath;
        this.directions = directions;
    }

    public int getValue() {
        return value;
    }

    public String getImagePath() {
        return imagePath;
    }

    public List<String> getDirections() {
        return directions;
    }

    public static TerrainType getByValue(int value) {
        for (TerrainType type : TerrainType.values()) {
            if (type.getValue() == value) {
                return type;
            }
        }
        return null;
    }

    public static String getImagePath(int value) {
        for (TerrainType type : TerrainType.values()) {
            if (type.getValue() == value) {
                return type.getImagePath();
            }
        }
        return null;
    }
}
