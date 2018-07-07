package agoda.homework.dowloader;

import agoda.homework.dowloader.exceptions.AppException;

import java.io.*;

public abstract class FileDownloader implements Downloader, StreamProvider {

    private final int DEFAULT_BUFFER_SIZE = 4096;

    protected int getBufferSize() {
        return DEFAULT_BUFFER_SIZE;
    }


    protected String getDestinationFilePath(String sourceLocation, String destinationLocation) {

        return destinationLocation + File.separator + sourceLocation.substring(sourceLocation.lastIndexOf("/") + 1);
    }


    public void download(String sourceLocation, String destination) throws AppException {

        final int BUFFER_SIZE = getBufferSize();

        String destinationFilePath = getDestinationFilePath(sourceLocation, destination);

        try (
                InputStream inputStream = openInStream(sourceLocation);
                OutputStream outputStream = openOutStream(destinationFilePath)
        ) {

            int bytesRead;
            byte[] buffer = new byte[BUFFER_SIZE];
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }

        } catch (Exception e) {
            throw new AppException(e);
        }

    }


    @Override
    public OutputStream openOutStream(String destination) throws AppException {

        try {
            return new FileOutputStream(destination);
        } catch (FileNotFoundException e) {
            throw new AppException(String.format("Unable create file [%s]", destination));
        }

    }

}
