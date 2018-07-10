package agoda.homework.streamers.impl.out;

import agoda.homework.exceptions.DownloadException;
import agoda.homework.streamers.OutStreamer;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;

public class FileOutStreamer implements OutStreamer {

    private final String filePath;

    public FileOutStreamer(String filePath) {
        this.filePath = filePath;
    }

    public OutputStream openOutStream() throws DownloadException {

        try {
            return new FileOutputStream(filePath);
        } catch (FileNotFoundException e) {
            throw new DownloadException(String.format("Unable create file [%s]", filePath));
        }

    }

}
