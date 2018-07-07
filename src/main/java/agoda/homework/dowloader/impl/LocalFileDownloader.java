package agoda.homework.dowloader.impl;

import agoda.homework.dowloader.FileDownloader;
import agoda.homework.dowloader.exceptions.AppException;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class LocalFileDownloader extends FileDownloader {

    @Override
    public InputStream openInStream(String source) throws AppException {

        try {

            URL sourceUrl = new URL(source);

            URLConnection connection = sourceUrl.openConnection();

            return connection.getInputStream();

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
