package agoda.homework.downloader;

import agoda.homework.AppProperties;
import agoda.homework.Application;
import agoda.homework.exceptions.DownloadException;
import agoda.homework.streamers.InStreamer;
import agoda.homework.streamers.OutStreamer;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;

/**
 * Superclass for all downloaders
 *
 */

public abstract class Downloader {

    private static Logger logger = Logger.getLogger(Downloader.class);

    protected final InStreamer inStreamer;
    protected final OutStreamer outStreamer;
    protected final URI source;

    protected Downloader(InStreamer inStreamer, OutStreamer outStreamer, URI source) {
        this.inStreamer = inStreamer;
        this.outStreamer = outStreamer;
        this.source = source;
    }


    /**
     * Method for download data from inStreamer to outStreamer
     *
     * Read data from InputStream and write to OutputStream
     */
    public void download() throws DownloadException {

        logger.info(String.format("download from source[%s] started", source));

        final int BUFFER_SIZE = AppProperties.getInstance().getBufferSize();

        try (
                InputStream inputStream = inStreamer.openInStream();
                OutputStream outputStream = outStreamer.openOutStream()
        ) {

            int bytesRead;
            byte[] buffer = new byte[BUFFER_SIZE];
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }

        } catch (IOException e) {
            throw new DownloadException(String.format("download from source[%s] failed", source), e);
        }

        logger.info(String.format("download from source[%s] completed", source));

    }


    /**
     * Method should be implemented in child classes.
     *
     * When douwload filed, this method should be invoked, to delete partial data
     */
    public abstract void cleanup() throws IOException;

}
