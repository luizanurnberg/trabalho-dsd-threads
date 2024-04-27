package mesh;

import controller.Controller;
import model.road.RoadItem;
import model.road.RoadPosition;
import model.vehicle.Vehicle;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public abstract class Mesh {
    protected final RoadItem[][] mesh;
    private final int lines;
    private final int columns;
    private final List<RoadItem> entry;

    public Mesh(int lines, int columns) {
        this.lines = lines;
        this.columns = columns;
        this.entry = new ArrayList<>();
        this.mesh = new RoadItem[lines][columns];
    }

    public abstract void addVehicle(RoadPosition roadPosition, Vehicle vehicle);
    public abstract void removeVehicle(RoadPosition roadPosition, Vehicle vehicle);

    public void initMesh() {
        cleanRoad();
        setEntryAndExit();
    }

    public void addItemRoad(RoadPosition roadPosition, RoadItem roadItem) {
        int line = roadPosition.getLine();
        int column = roadPosition.getColumn();
        mesh[line][column] = roadItem;
        adjustRoadItemType(roadPosition, roadItem.getType());
    }

    public RoadItem ramdomEntry() {
        Random random = new Random();
        return entry.get(random.nextInt(entry.size()));
    }

    public RoadItem[][] getMesh() {
        return mesh;
    }

    protected void notificateController(Vehicle vehicle) {
        Controller.getInstance().removeVehicle(vehicle);
        Controller.getInstance().notifyTableModelChanged();
    }

    //TODO ajustar road type
    private void adjustRoadItemType(RoadPosition roadPosition, int roadType) {
        if (roadType == 1) {
            roadItemGrass(roadPosition);
        } else {
            roadItemPavement(roadPosition);
        }
    }

    //TODO: Ajustar caminhos dos arquivos com as imagens que temos
    private void roadItemGrass(RoadPosition roadPosition) {
        int line = roadPosition.getLine();
        int column = roadPosition.getColumn();
        Random random = new Random();
        int option = random.nextInt(16);

        switch(option) {
            case 5: case 6: {
                mesh[line][column].setIcon(getResourceFileByPath("CAMINHO_ARQUIVO_FLOR"));
                break;
            }
            case 8: {
                mesh[line][column].setIcon(getResourceFileByPath("CAMINHO_ARQUIVO_FLORZINHA"));
                break;
            }
            case 9: {
                mesh[line][column].setIcon(getResourceFileByPath("CAMINHO_ARQUIVO_MATINHO"));
                break;
            }
            default: {
                mesh[line][column].setIcon(getResourceFileByPath("CAMINHO_ARQUIVO_GRAMA"));
                break;
            }

        }
    }

    //TODO: Ajustar caminhos dos arquivos com as imagens que temos
    private void roadItemPavement(RoadPosition roadPosition) {
        int line = roadPosition.getLine();
        int column = roadPosition.getColumn();
        mesh[line][column].setIcon(getResourceFileByPath("CAMINHO_ARQUIVO_ASFALTO"));
    }

    private ImageIcon getResourceFileByPath(String filePath) {
        return new ImageIcon(Objects.requireNonNull(this.getClass().getResource(filePath)));
    }

    // TODO ajustar direnção das ruas
    private void setEntryAndExit() {
        for (int currentLine = 0; currentLine < lines; currentLine++) {
            for (int currentColumn = 0; currentColumn < columns; currentColumn++) {
                RoadItem currentRoadItem = mesh[currentLine][currentColumn];

                addAdjacentRoadItem(currentRoadItem, new RoadPosition(currentLine, currentColumn));

                if (firstLine(currentLine)) {
                    defineEntry(currentRoadItem, 2);
                    defineExit(currentRoadItem, 2);
                }

                if (lastLine(currentLine)) {
                    defineEntry(currentRoadItem, 1);
                    defineExit(currentRoadItem, 1);
                }

                if (firstColumn(currentColumn)) {
                    defineEntry(currentRoadItem, 2);
                    defineExit(currentRoadItem, 2);
                }

                if (lastColumn(currentColumn)) {
                    defineEntry(currentRoadItem, 2);
                    defineExit(currentRoadItem, 2);
                }
            }
        }
    }

    public void cleanRoad() {
        for (int currentLine = 0; currentLine < lines; currentLine++) {
            for (int currentColumn = 0; currentColumn < columns; currentColumn++) {
                RoadItem currentRoadItem = mesh[currentLine][currentColumn];

                currentRoadItem.setVehicle(null);
                currentRoadItem.setBusy(false);
            }
        }
    }

    private void defineEntry(RoadItem currentRoadItem, int currentRoadDirection) {
        if (currentRoadItem.getType() == currentRoadDirection) {
            currentRoadItem.setEntry(true);
            addNewEntry(currentRoadItem);
        }
    }

    private void defineExit(RoadItem currentRoadItem, int currentRoadDirection) {
        if (currentRoadItem.getType() == currentRoadDirection) {
            currentRoadItem.setExit(true);
        }
    }

    public int getLines() {
        return lines;
    }

    public int getColumns() {
        return columns;
    }

    public List<RoadItem> getEntry() {
        return entry;
    }

    private Boolean firstLine(int currentLine) {
        return currentLine == 0;
    }

    private Boolean lastLine(int currentLine) {
        return currentLine == (lines - 1);
    }

    private Boolean firstColumn(int currentColumn) {
        return currentColumn == 0;
    }

    private Boolean lastColumn(int currentColumn) {
        return currentColumn == (columns - 1);
    }

    private void addNewEntry(RoadItem roadItem) {
        this.entry.add(roadItem);
    }

    private void addAdjacentRoadItem(RoadItem roadItem, RoadPosition roadPosition) {
        int line = roadPosition.getLine();
        int column = roadPosition.getColumn();

        if (checkExistingArray(line, column - 1)) {
            roadItem.setItemLeft(mesh[line][column - 1]);
        }

        if (checkExistingArray(line, column + 1)) {
            roadItem.setItemRight(mesh[line][column + 1]);
        }

        if (checkExistingArray(line + 1, column)) {
            roadItem.setItemDown(mesh[line + 1][column]);
        }

        if (checkExistingArray(line - 1, column)) {
            roadItem.setItemUp(mesh[line - 1][column]);
        }

        if (checkExistingArray(line - 1, column + 1)) {
            roadItem.setItemUpRight(mesh[line - 1][column + 1]);
        }

        if (checkExistingArray(line - 1, column - 1)) {
            roadItem.setItemUpLeft(mesh[line - 1][column - 1]);
        }

        if (checkExistingArray(line + 1, column + 1)) {
            roadItem.setItemDownRight(mesh[line + 1][column + 1]);
        }

        if (checkExistingArray(line + 1, column - 1)) {
            roadItem.setItemDownLeft(mesh[line + 1][column - 1]);
        }
    }


    private boolean checkExistingArray(int x, int y) {
        return (x >= 0 && x < this.mesh.length) && (y >= 0 && y < this.mesh[0].length);
    }
}
