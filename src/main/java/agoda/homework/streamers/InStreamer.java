package agoda.homework.streamers;

import agoda.homework.exceptions.DownloadException;

import java.io.InputStream;

/**
 * Interface InStreamer, it should be implemented to open InputStream for specific protocol
 */
public interface InStreamer {

    /**
     * Method should open connection with source and create InputStream to data can be read
     *
     * */
    InputStream openInStream() throws DownloadException;


    /**
     * Method invoked after data was read and should close all opened connection resources
     */
    void release();


}
