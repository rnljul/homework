package agoda.homework;

import agoda.homework.exceptions.DownloadException;
import agoda.homework.streamers.InStreamer;
import agoda.homework.streamers.OutStreamer;
import org.apache.log4j.Logger;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * Superclass for all downloaders
 *
 */

public class Downloader {

    private static Logger logger = Logger.getLogger(Downloader.class);

    private final InStreamer inStreamer;
    private final OutStreamer outStreamer;

    protected Downloader(InStreamer inStreamer, OutStreamer outStreamer) {
        this.inStreamer = inStreamer;
        this.outStreamer = outStreamer;
    }


    /**
     * Method for download data from inStreamer to outStreamer
     *
     * Read data from InputStream and write to OutputStream
     */
    public void download() throws DownloadException {

        try {


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

            } finally {

                inStreamer.release();
                outStreamer.release();

            }

        }  catch (DownloadException e) {

            outStreamer.cleanup();
            throw e;

        } catch (Exception e) {

            outStreamer.cleanup();
            throw new DownloadException(e);
        }

    }


}
