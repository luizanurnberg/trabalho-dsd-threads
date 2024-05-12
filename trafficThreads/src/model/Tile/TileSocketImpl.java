package model.Tile;

import model.Vehicle;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.InetSocketAddress;
import java.net.Socket;

public class TileSocketImpl extends TileBase {
    private int serverPort;
    private Socket client;
    private ServerSocket serverSocket;
    private Thread socketThread;

    public TileSocketImpl(int port) {
        this.startServer(port);
    }

    public void startServer(int port) {
        this.setServerPort(8000 + port);
        int portNumber = this.getServerPort();
        try {
            this.serverSocket = new ServerSocket(portNumber);
            this.socketThread = new Thread(() -> {
                while (!this.serverSocket.isClosed()) {
                    try {
                        serverSocket.accept();
                    } catch (IOException e) {
                    }
                }
            });
            this.socketThread.start();
        } catch (Exception e) {
        }
    }

    @Override
    public boolean tryAcquire() {
        try {
            if (!this.socketThread.isAlive() || (this.serverSocket == null && this.serverSocket.isClosed())) {
                return false;
            }
            this.client = new Socket();
            int portNumber = this.getServerPort();
            this.client.connect(new InetSocketAddress("localhost", portNumber));
            if (this.client.isConnected()) {
                System.out.println("Vehicle moved to tile");
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

    public void closeSocketServer() throws IOException {
        this.socketThread.stop();
        this.socketThread.interrupt();

        if (this.client != null) {
            this.client.close();
        }
        if (!this.serverSocket.isClosed()) {
            this.serverSocket.close();
        }
    }

    public void setServerPort(int serverPort) {
        this.serverPort = serverPort;
    }

    public int getServerPort() {
        return this.serverPort;
    }
}
