package de.veenix.jtest.server.http;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {

    public static void main(String[] args) throws IOException {
        ServerSocket socket = new ServerSocket(80);
        System.out.println("Server now listens to port: " + socket.getLocalPort());

        while(true) {
            Socket client = socket.accept();
            System.out.println("Client connected to port: " + client.getLocalPort());

            BufferedReader reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));

            String s;
            while((s = reader.readLine()) != null) {
                System.out.println(s);
                if(s.isEmpty()) {
                    break;
                }
            }

            String html = "<html><header><title>HTTP SERVER RESPONSE</title><style>{{css}}</style></header><body>{{body}}</body></html>";

            StyleComponent style = new StyleComponent();
            style.addRule(".text", "background-color", "red");

            html = html.replace("{{body}}", new MainComponent().toString());
            html = html.replace("{{css}}", style.toString());

            writer.write("HTTP/1.1 200 OK\r\n");
            writer.write("Server: VHTTP/0.1\r\n");
            writer.write("Content-Type: text/html, text, plain\r\n");
            writer.write("Content-Length: " + (html.length() - 1) + "\r\n");
            writer.write("\r\n");
            writer.write(html);

            System.out.println("Terminating client connection");
            writer.flush();
            writer.close();
            reader.close();
            client.close();
        }
    }

}
