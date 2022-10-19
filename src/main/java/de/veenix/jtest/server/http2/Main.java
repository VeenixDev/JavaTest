package de.veenix.jtest.server.http2;

import de.veenix.jtest.server.http2.functions.ErrorResponseFunction;
import de.veenix.jtest.server.http2.functions.ResponseFunction;
import lombok.extern.log4j.Log4j2;

import java.io.IOException;
import java.net.Socket;
import java.util.Optional;

@Log4j2
public class Main {
    private static boolean running = true;

    public static void main(String[] args) {
        Server server;
        try {
            server = new Server(80);
        } catch (IOException exception) {
            log.error("Failed to start the server: " + exception.getMessage());
            return;
        }

        while(running) {
            Optional<Socket> acceptedSocket = server.accept();

            if(acceptedSocket.isPresent()) {
                Socket socket = acceptedSocket.get();
                log.info("Accepted incoming request");
                HTTPParser parser = new HTTPParser(new ResponseFunction(), new ErrorResponseFunction());
                HTTPResponse response = parser.parseFromSocket(socket);
                try {
                    response.write(socket.getOutputStream());

                    parser.getReader().close();
                    socket.close();
                    log.info("Successfully closed socket");
                } catch (IOException exception) {
                    log.error("Failed to write to socket: " + exception.getMessage());
                }
            }
        }
    }

    public static void setRunning(boolean running) {
        Main.running = running;
    }

    public static boolean isRunning() {
        return running;
    }
}
