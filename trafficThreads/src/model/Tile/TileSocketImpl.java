package model.Tile;

import model.Vehicle;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class TileSocketImpl extends TileBase {
    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(8089);
            System.out.println("Server is running...");

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected: " + clientSocket.getInetAddress());

                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    System.out.println("Received from client: " + inputLine);

                    out.println("Server received: " + inputLine);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*private static void handleClientConnection(Socket clientSocket) {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                System.out.println("Received from client: " + inputLine);

                if (isMoveVehicleToTileRequest(inputLine)) {
                    Vehicle vehicle = parseVehicleFromRequest(inputLine);
                    setCurrentVehicle(vehicle);
                    setTileCurrentImage();

                    System.out.println("Vehicle moved to tile: " + getCurrentVehicle().getCurrentTile());

                    out.println("Vehicle moved to tile: " + getCurrentVehicle().getCurrentTile());

                    getCurrentVehicle().getCurrentTile().removeVehicleFromTile();
                    getCurrentVehicle().setCurrentTile(getCurrentVehicle().getCurrentTile());
                } else {
                    out.println("Invalid request.");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                clientSocket.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }*/
}
