package constants;

public enum TerrainType {
    NOTHING(0, "grass_background.png"),
    ROAD_UP(1, "arrow_up.png"),
    ROAD_RIGHT(2, "arrow_right.png"),
    ROAD_DOWN(3, "arrow_down.png"),
    ROAD_LEFT(4, "arrow_left.png"),
    INTERSECTION_UP(5, "double_arrow_up.png"),
    INTERSECTION_RIGHT(6, "double_arrow_right.png"),
    INTERSECTION_DOWN(7, "double_arrow_down.png"),
    INTERSECTION_LEFT(8, "double_arrow_left.png"),
    INTERSECTION_UP_RIGHT(9, "double_arrow_top_right.png"),
    INTERSECTION_UP_LEFT(10, "double_arrow_top_left.png"),
    INTERSECTION_RIGHT_DOWN(11, "double_arrow_right_down.png"),
    INTERSECTION_DOWN_LEFT(12, "double_arrow_down_left.png");

    private final int value;
    private final String imagePath;

    TerrainType(int value, String imagePath) {
        this.value = value;
        this.imagePath = imagePath;
    }

    public int getValue() {
        return value;
    }

    public String getImagePath() {
        return imagePath;
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
