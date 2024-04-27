package model.road;

public class RoadPosition {
    private final int line;
    private final int column;

    public RoadPosition(int line, int column) {
        this.line = line;
        this.column = column;
    }

    public int getLine() {
        return line;
    }

    public int getColumn() {
        return column;
    }
}
