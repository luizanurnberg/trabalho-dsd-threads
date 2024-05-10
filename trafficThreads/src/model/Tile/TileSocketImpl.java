package model.Tile;

import model.Vehicle;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class TileSocketImpl extends TileBase {
    private int serverPort;
    private Socket socket;

    public TileSocketImpl(int serverPort) {
        this.serverPort = serverPort;
        this.startServer(this.serverPort);
    }

    public void startServer(int port) {
        this.serverPort = port + 8095;
        try {
            ServerSocket serverSocket = new ServerSocket(serverPort);
            new Thread(() -> {
                while (!serverSocket.isClosed()) {
                    try {
                        Socket clientSocket = serverSocket.accept();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean tryAcquire() {
        try {
            this.socket = new Socket("localhost", serverPort);
            return socket.isConnected();
        } catch (IOException e) {
            e.printStackTrace();
            return false; // Retorna false se ocorrer algum erro na conexão
        }
    }

    @Override
    public void release() {
        try {
            this.socket.close();
        } catch (Exception error) {
        }
    }

    @Override
    public void addVehicle(Vehicle vehicle) {
    }
}
