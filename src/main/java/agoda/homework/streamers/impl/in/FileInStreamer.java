package agoda.homework.streamers.impl.in;

import agoda.homework.exceptions.DownloadException;
import agoda.homework.streamers.InStreamer;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

public class FileInStreamer implements InStreamer {


    protected final URL sourceUrl;

    public FileInStreamer(URL sourceUrl) {
        this.sourceUrl = sourceUrl;
    }


    @Override
    public InputStream openInStream() throws DownloadException {

        try {

            URLConnection connection = sourceUrl.openConnection();

            return connection.getInputStream();

        } catch (IOException e) {

            throw new DownloadException(
                    String.format("Unable to open stream from source location [%s]", sourceUrl.toString()),
                    e
            );

        }

    }

}
