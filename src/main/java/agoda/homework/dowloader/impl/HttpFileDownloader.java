package agoda.homework.dowloader.impl;

import agoda.homework.dowloader.FileDownloader;
import agoda.homework.dowloader.exceptions.AppException;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class HttpFileDownloader extends FileDownloader {


    @Override
    public InputStream openInStream(String source) throws AppException {

        try {

            URL sourceUrl = new URL(source);

            HttpURLConnection httpConn = (HttpURLConnection) sourceUrl.openConnection();

            int responseCode = httpConn.getResponseCode();

            if (responseCode != HttpURLConnection.HTTP_OK)
                throw new AppException("No file to download. Server replied HTTP code: " + responseCode);

            return httpConn.getInputStream();

        } catch (MalformedURLException e) {

            throw new AppException(
                    String.format("Invalid source location [%s]", source),
                    e
            );

        } catch (IOException e) {

            throw new AppException(
                    String.format("Unable to open stream from source location [%s]", source),
                    e
            );

        }

    }


}
