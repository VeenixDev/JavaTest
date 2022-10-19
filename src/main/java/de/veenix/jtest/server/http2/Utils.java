package de.veenix.jtest.server.http2;

import lombok.extern.log4j.Log4j2;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

@Log4j2
public class Utils {

    private static final String BASE_PATH = "/home/paul/workspace/plainWeb";

    public static String readFile(String relativePath) {
        if(relativePath.contains("..")) {
            log.warn("Blocked file read because it used '..' which is not allowed");
            return null;
        }
        StringBuilder fileContentBuilder = new StringBuilder();
        File file = new File(BASE_PATH + (relativePath.startsWith("/") ? "" : "/") + relativePath);

        if(!file.exists()) {
            return null;
        }

        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            while((line = reader.readLine()) != null) {
                fileContentBuilder.append(line).append("\n");
            }

            reader.close();
            return fileContentBuilder.toString();
        } catch (IOException exception) {
            log.error("Failed while reading the file: " + exception.getMessage());
            return null;
        }
    }

    public static String getMimeType(String fileName) {
        String[] extensionSplit = fileName.split("\\.", Integer.MAX_VALUE);
        return switch(extensionSplit[extensionSplit.length - 1]) {
            case "css" -> "text/css";
            case "ico" -> "image/x-icon";
            case "html", "htm" -> "text/html";
            case "js" -> "text/javascript";
            default -> "text/plain";
        };
    }
}
