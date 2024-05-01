package model.NewModels.Tile;

import model.NewModels.Vehicle;

import java.net.ServerSocket;
import java.net.Socket;

public class TileSocketImpl extends TileBase {
    @Override
    public boolean moveVehicleToTile(Vehicle vehicle) {
        try {
            ServerSocket serverSocket = new ServerSocket(12345);
            Socket socket = serverSocket.accept();

            if (this.isAvaliable()) {
                this.currentVehicle = vehicle;
                this.setTileCurrentImage();
                System.out.println("Vehicle moved to tile: " + this);
            } else {
                System.out.println("Tile is occupied. Vehicle cannot move to tile: " + this);
            }

            socket.close();
            serverSocket.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
