package agoda.homework.streamers;

import agoda.homework.exceptions.DownloadException;

import java.io.InputStream;

/**
 * Interface InStreamer, it should be implemented to open InputStream for specific protocol
 */
public interface InStreamer {

    InputStream openInStream() throws DownloadException;

}
