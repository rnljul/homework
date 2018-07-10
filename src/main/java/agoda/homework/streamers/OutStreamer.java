package agoda.homework.streamers;

import agoda.homework.exceptions.DownloadException;

import java.io.OutputStream;

/**
 * Interface OutStreamer, it should be implemented to open OutputStream for writing data to specific place
 */
public interface OutStreamer {

    OutputStream openOutStream() throws DownloadException;

}
