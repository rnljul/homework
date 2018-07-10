package agoda.homework.downloader.impl;

import agoda.homework.downloader.Downloader;
import agoda.homework.streamers.InStreamer;
import agoda.homework.streamers.OutStreamer;
import agoda.homework.utl.AppUtl;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Concrete downloader for download to file
 *
 */
public class FileDownloader extends Downloader {


    public FileDownloader(InStreamer inStreamer, OutStreamer outStreamer, URI source) {
        super(inStreamer, outStreamer, source);
    }


    /**
     * Delete downloaded file, should be invoked when download failed
     *
     * @throws IOException
     */
    @Override
    public void cleanup() throws IOException {

        if(Files.exists(Paths.get(AppUtl.getOutFilePath(source))))
            Files.delete(Paths.get(AppUtl.getOutFilePath(source)));

    }

}

