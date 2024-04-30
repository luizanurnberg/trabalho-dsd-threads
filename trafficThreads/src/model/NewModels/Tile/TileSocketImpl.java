package model.NewModels.Tile;

import model.NewModels.Vehicle;

import java.net.ServerSocket;
import java.net.Socket;

public class TileSocketImpl extends TileBase implements ITile {
    @Override
    public void moveVehicleToTile(Vehicle vehicle) {
        try {
            ServerSocket serverSocket = new ServerSocket(12345);
            Socket socket = serverSocket.accept();

            if (this.isAvaliable()) {
                this.currentVehicle = vehicle;
                System.out.println("Vehicle moved to tile: " + this);
            } else {
                System.out.println("Tile is occupied. Vehicle cannot move to tile: " + this);
            }

            socket.close();
            serverSocket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
