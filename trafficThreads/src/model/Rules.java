package model;

public class Rules {
    private  int numberVehicles;
    private  int numberSimultaneousVehicles;
    private int rangeInsertion;
    private String fileNameGrid;
    private int exclusionType;

    public Rules(int numberVehicles, int numberSimultaneousVehicles, int rangeInsertion, String fileNameGrid, int exclusionTYpe) {
        this.numberVehicles = numberVehicles;
        this.numberSimultaneousVehicles = numberSimultaneousVehicles;
        this.rangeInsertion = rangeInsertion;
        this.fileNameGrid = fileNameGrid;
        this.exclusionType = exclusionTYpe;
    }

    public int getNumberVehicles() {
        return numberVehicles;
    }

    public int getNumberSimultaneousVehicles() {
        return numberSimultaneousVehicles;
    }

    public int getRangeInsertion() {
        return rangeInsertion;
    }

    public String getFileNameGrid() {
        return fileNameGrid;
    }

    public int getExclusionType() {
        return exclusionType;
    }

}
