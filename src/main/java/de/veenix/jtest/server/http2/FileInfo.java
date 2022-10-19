package de.veenix.jtest.server.http2;

/**
 * Contains information such as the mime type and if it's a binary or not
 *
 * Note:    you can get the info from {@link Utils#getFileInfo(String)}
 *          If the file is binary is determined by the file extension, the extension is checked against an array of binary extensions
 */
public class FileInfo {

    private final String mimeType;
    private final String fileName;
    private final String fileExtension;
    private final boolean isBinary;

    public FileInfo(String mimeType, boolean isBinary, String fileName, String fileExtension) {
        this.mimeType = mimeType;
        this.isBinary = isBinary;
        this.fileName = fileName;
        this.fileExtension = fileExtension;
    }

    public String getMimeType() {
        return mimeType;
    }

    public boolean isBinary() {
        return isBinary;
    }

    public String getFileName() {
        return fileName;
    }

    public String getFileExtension() {
        return fileExtension;
    }
}
