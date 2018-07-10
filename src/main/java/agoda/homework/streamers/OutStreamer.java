package agoda.homework.streamers;

import agoda.homework.exceptions.DownloadException;

import java.io.OutputStream;

/**
 * Interface OutStreamer, it should be implemented to open OutputStream for writing data to specific place
 */
public interface OutStreamer {

    /**
     * Method should open OutputStream to write data
     * */
    OutputStream openOutStream() throws DownloadException;

    /**
     * Method should close all opened resources
     * */
    void release();

    /**
     * If write data fails, then this method should be invoked to delete partial data
     */
    void cleanup();



}
