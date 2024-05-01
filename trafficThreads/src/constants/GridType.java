package constants;

import java.util.Arrays;
import java.util.List;

public enum GridType {
    MESH_ONE(0, "grids/mesh1.txt"),
    MESH_TWO(1, "grids/mesh2.txt"),
    MESH_THREE(3, "grids/mesh3.txt");

    private final int value;
    private final String imagePath;
    GridType(int value, String imagePath) {
        this.value = value;
        this.imagePath = imagePath;
    }

    public int getValue() {
        return value;
    }

    public String getImagePath() {
        return imagePath;
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
