package de.veenix.jtest.server.http2;

import lombok.extern.log4j.Log4j2;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

@Log4j2
public class Utils {

    private static final String BASE_PATH = "/home/paul/workspace/plainWeb";

    public static byte[] readFile(String relativePath, boolean isBinary) {
        if(relativePath.contains("..")) {
            log.warn("Blocked file read because it used '..' which is not allowed");
            return null;
        }

        if(relativePath.startsWith("./")) {
            relativePath = relativePath.substring(2);
        }

        String path = BASE_PATH + (relativePath.startsWith("/") ? "" : "/") + relativePath;
        File file = new File(path);

        if(!file.exists()) {
            log.warn("File doesn't exit at path: " + file.getPath());
            return null;
        }

        if(file.isDirectory()) {
            if(!isBinary) {
                byte[] indexBytes = readFile(relativePath + (relativePath.endsWith("/") ? "" : "/") + "index.html", false);
                if(indexBytes != null) {
                    return indexBytes;
                } else {
                    log.warn("Couldn't find a fallback file for the directory!");
                    return null;
                }
            }
            log.warn("Can't read directory: " + relativePath);
            return null;
        }

        try {
            if(isBinary) {
                return Files.readAllBytes(Path.of(path));
            } else {
                FileReader fReader = new FileReader(file);
                BufferedReader reader = new BufferedReader(fReader);

                StringBuilder contentBuilder = new StringBuilder();
                String line;

                while((line = reader.readLine()) != null) {
                    contentBuilder.append(line).append("\n");
                }

                reader.close();
                return contentBuilder.toString().getBytes();
            }
        } catch (IOException exception) {
            log.error("Failed while reading the file: " + exception.getMessage());
            return null;
        }
    }

    public static String getMimeType(String fileName) {
        try {
            return Files.probeContentType(Path.of(fileName));
        } catch (IOException exception) {
            log.error("Failed at retrieving mime type: " + exception.getMessage());
            return null;
        }
    }

    private static final String[] binaryExtensions = {"ico", "bin", "exe", "jar"};

    public static boolean isFileBinary(String fileName) {
        String[] extensionSplit = fileName.split("\\.", Integer.MAX_VALUE);
        return Arrays.stream(binaryExtensions).anyMatch(ext -> ext.equals(extensionSplit[extensionSplit.length - 1]));
    }

    public static FileInfo getFileInfo(String path) {
        String[] fileNameSplit = (BASE_PATH + path).split("/", Integer.MAX_VALUE);
        String fileName = fileNameSplit[fileNameSplit.length - 1];

        String mimeType = getMimeType(path);
        boolean isBinary = isFileBinary(fileName);
        String[] extensionSplit = fileName.split("\\.", Integer.MAX_VALUE);
        String fileExtension = extensionSplit[extensionSplit.length - 1];

        return new FileInfo(mimeType, isBinary, fileName, fileExtension);
    }

    public static boolean checkMethod(HTTPRequest request, HTTPType type) {
        return request.getType() == type;
    }

    public static boolean checkRelativePath(HTTPRequest request, String relativePath) {
        return request.getRequestPath().equals(relativePath);
    }

    public static boolean checkMethodAndPath(HTTPRequest request, HTTPType type, String relativePath) {
        return checkMethod(request, type) && checkRelativePath(request, relativePath);
    }
}
