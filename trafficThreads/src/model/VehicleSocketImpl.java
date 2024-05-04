package model;

import model.Tile.TileBase;

import java.io.PrintWriter;
import java.net.Socket;

public class VehicleSocketImpl extends TileBase {
    public boolean moveVehicleToTile(Vehicle vehicle) {
        final String serverAddress = "localhost";
        final int port = 8089;

        try {
            Socket socket = new Socket(serverAddress, port);

            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            if (this.isAvaliable()) {
                out.println("MOVE_VEHICLE|" + vehicle.getId() + "|" + this.getPosX() + "|" + this.getPosY());

                this.currentVehicle = vehicle;
                this.setTileCurrentImage();
                System.out.println("Veículo movido para o bloco: " + this);
            } else {
                System.out.println("Bloco ocupado. Veículo não pode ser movido para o bloco: " + this);
            }

            socket.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


}