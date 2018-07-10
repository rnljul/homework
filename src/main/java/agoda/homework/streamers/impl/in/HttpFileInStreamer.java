package agoda.homework.streamers.impl.in;

import agoda.homework.exceptions.DownloadException;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpFileInStreamer extends FileInStreamer {

    private static Logger logger = Logger.getLogger(HttpFileInStreamer.class);

    public HttpFileInStreamer(URL sourceUrl) {
        super(sourceUrl);
    }

    @Override
    public InputStream openInStream() throws DownloadException {

        logger.debug(String.format("Download from [%s] started", sourceUrl));

        try {

            HttpURLConnection httpConn = (HttpURLConnection) sourceUrl.openConnection();

            int responseCode = httpConn.getResponseCode();

            if (responseCode != HttpURLConnection.HTTP_OK)
                throw new DownloadException("No file to download. Server replied HTTP code: " + responseCode);

            return httpConn.getInputStream();

        } catch (IOException e) {

            throw new DownloadException(
                    String.format("Unable to open stream from source location [%s]", sourceUrl),
                    e
            );

        }

    }


}
