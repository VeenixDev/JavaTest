package de.veenix.jtest.server.http;

import de.veenix.jtest.server.http.components.MainComponent;
import de.veenix.jtest.server.http.components.StyleComponent;

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
            OutputStream writer = client.getOutputStream();
            final HttpInfo httpInfo = new HttpInfo();

            int counter = 0;
            String s;
            while((s = reader.readLine()) != null) {
                System.out.println(s + " | " + counter);

                if (counter == 0) {
                    String[] split = s.split(" ");
                    httpInfo.setMethod(HttpInfo.getMethodFromString(split[0]));
                    httpInfo.setPath(split[1]);
                    httpInfo.setVersion(split[2]);
                }

                counter++;
                if(s.isEmpty()) {
                    break;
                }
            }

            if (httpInfo.getMethod() == HttpInfo.RequestMethod.GET && httpInfo.getPath().equals("/favicon.ico")) {
                InputStream fileReader = Main.class.getClassLoader().getResourceAsStream("server/http/among_us_netflix_icon_156927.ico");

                if(fileReader == null) {
                    writer.close();
                    reader.close();
                    client.close();
                    continue;
                }

                int count;
                byte[] buffer = new byte[8192];
                while((count = fileReader.read(buffer)) > 0) {
                    writer.write(buffer, 0, count);
                }

                writer.flush();
                writer.close();
                reader.close();
                fileReader.close();
                client.close();
                continue;
            }

            String html =
                    "<html><head><style>{{css}}</style><title>{{title}}</title></head><body>{{body}}</body></html>";

            StyleComponent style = new StyleComponent();
            style.addRule("*", "margin", "0");
            style.addRule("*", "padding", "0");
            style.addRule(".text", "background-color", "red");

            html = html.replace("{{css}}", style.toString());
            html = html.replace("{{body}}", new MainComponent().generateHtml());
            html = html.replace("{{title}}", "SERVER RESPONSE");

            writer.write("HTTP/1.1 200 OK\r\n".getBytes());
            writer.write("Server: VHTTP/0.1\r\n".getBytes());
            writer.write("Content-Type: text/html, text, plain\r\n".getBytes());
            writer.write(("Content-Length: " + (html.length() - 1) + "\r\n").getBytes());
            writer.write("\r\n".getBytes());
            writer.write(html.getBytes());

            System.out.println("Terminating client connection");
            writer.flush();
            writer.close();
            reader.close();
            client.close();
        }
    }

}
