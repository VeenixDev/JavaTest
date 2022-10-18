package de.veenix.jtest.server.http2;

import lombok.extern.log4j.Log4j2;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Optional;

@Log4j2
public class Server {
    private final int port;
    private final ServerSocket serverSocket;

    public Server(int port) throws IOException {
        this.port = port;
        serverSocket = new ServerSocket(port);
        log.info("Started Server on port " + port);
    }

    public Optional<Socket> accept() {
        try {
            return Optional.of(serverSocket.accept());
        } catch (IOException exception) {
            log.error(exception.getMessage());
            return Optional.empty();
        }
    }

    public void close() {
        try {
            if (!serverSocket.isClosed()) {
                serverSocket.close();
                log.info("Server closed");
            } else {
                log.warn("Server is already closed!");
            }
        } catch (IOException exception) {
            log.error(exception.getMessage());
        }
    }

    public ServerSocket getServerSocket() {
        return serverSocket;
    }

    public int getPort() {
        return port;
    }
}
