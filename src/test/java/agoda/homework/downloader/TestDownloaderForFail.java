package agoda.homework.downloader;

import agoda.homework.downloader.impl.FileDownloader;
import agoda.homework.exceptions.DownloadException;
import agoda.homework.streamers.InStreamer;
import agoda.homework.streamers.OutStreamer;

import java.net.URI;
import java.net.URISyntaxException;

public class TestDownloaderForFail extends FileDownloader {

    public TestDownloaderForFail(InStreamer inStreamer, OutStreamer outStreamer, String source) throws URISyntaxException {
        super(inStreamer, outStreamer, new URI(source));
    }

    @Override
    public void download() throws DownloadException {

        super.download();
        throw new DownloadException();

    }


}
