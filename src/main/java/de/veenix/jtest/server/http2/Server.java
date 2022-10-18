package de.veenix.jtest.server.http2;

import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Optional;

public class Server {

    private static final Logger logger = Logger.getLogger(Server.class);

    private final int port;
    private final ServerSocket serverSocket;

    public Server(int port) throws IOException {
        this.port = port;
        serverSocket = new ServerSocket(port);
        logger.info("Started Server on port " + port);
    }

    public Optional<Socket> accept() {
        try {
            return Optional.of(serverSocket.accept());
        } catch (IOException exception) {
            logger.error(exception.getMessage());
            return Optional.empty();
        }
    }

    public void close() {
        try {
            if (!serverSocket.isClosed()) {
                serverSocket.close();
                logger.info("Server closed");
            } else {
                logger.warn("Server is already closed!");
            }
        } catch (IOException exception) {
            logger.error(exception.getMessage());
        }
    }

    public ServerSocket getServerSocket() {
        return serverSocket;
    }

    public int getPort() {
        return port;
    }
}
