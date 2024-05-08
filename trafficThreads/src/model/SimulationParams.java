package model;

import constants.ExclusionType;
import constants.GridType;

public class SimulationParams {
    private final GridType selectedGrid;
    private final ExclusionType exclusionType;
    private final int numberOfVehicles;
    private final int numberOfSimultaneousVehicles;
    private final int rangeInsertion;

    public SimulationParams(
            GridType selectedGrid,
            ExclusionType exclusionType,
            int numberOfVehicles,
            int numberOfSimultaneousVehicles,
            int rangeInsertion
    ) {
        this.selectedGrid = selectedGrid;
        this.exclusionType = exclusionType;
        this.numberOfVehicles = numberOfVehicles;
        this.numberOfSimultaneousVehicles = numberOfSimultaneousVehicles;
        this.rangeInsertion = rangeInsertion;
    }

    public GridType getSelectedGrid() {
        return selectedGrid;
    }

    public ExclusionType getExclusionType() {
        return exclusionType;
    }

    public int getNumberOfVehicles() {
        return numberOfVehicles;
    }

    public int getNumberOfSimultaneousVehicles() {
        return numberOfSimultaneousVehicles;
    }

    public int getRangeInsertion() {
        return rangeInsertion;
    }
}
