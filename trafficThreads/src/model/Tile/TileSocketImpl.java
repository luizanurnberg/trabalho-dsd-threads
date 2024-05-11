package model.Tile;

import model.Vehicle;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.InetSocketAddress;
import java.net.Socket;

public class TileSocketImpl extends TileBase {
    private int serverPort;
    private Socket client;

    public TileSocketImpl(int port) {
        this.startServer(port);
    }

    public void startServer(int port) {
        this.setServerPort(8000 + port);
        int portNumber = this.getServerPort();
        try {
            ServerSocket serverSocket = new ServerSocket(portNumber);
            new Thread(() -> {
                while (true) {
                    try {
                        serverSocket.accept();
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
            this.client = new Socket();
            int portNumber = this.getServerPort();
            this.client.connect(new InetSocketAddress("localhost", portNumber));
            if (this.client.isConnected()) {
                return true;
            }
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public void release() throws IOException {
        this.client.close();
    }

    @Override
    public void addVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
        this.setTileCurrentImage();
    }

    public void setServerPort(int serverPort) {
        this.serverPort = serverPort;
    }

    public int getServerPort() {
        return this.serverPort;
    }
}
