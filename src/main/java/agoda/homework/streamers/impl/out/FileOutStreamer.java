package agoda.homework.streamers.impl.out;

import agoda.homework.exceptions.DownloadException;
import agoda.homework.streamers.OutStreamer;
import org.apache.log4j.Logger;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileOutStreamer implements OutStreamer {

    private static Logger logger = Logger.getLogger(FileOutStreamer.class);

    private final String filePath;

    public FileOutStreamer(String filePath) {
        this.filePath = filePath;
    }

    public OutputStream openOutStream() throws DownloadException {

        logger.debug(String.format("file [%s] created", filePath));

        try {
            return new FileOutputStream(filePath);
        } catch (FileNotFoundException e) {
            throw new DownloadException(String.format("Unable create file [%s]", filePath));
        }

    }

    @Override
    public void release() {
        logger.debug(String.format("file [%s] downloaded", filePath));
    }

    @Override
    public void cleanup() {

        if(Files.exists(Paths.get(filePath))) {
            try {
                Files.delete(Paths.get(filePath));
            } catch (IOException e) {
                logger.warn(String.format("Unable to delete file [%s]", filePath), e);
            }
        }

        logger.debug(String.format("file [%s] deleted", filePath));

    }

}
