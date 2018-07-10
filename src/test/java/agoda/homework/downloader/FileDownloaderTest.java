package agoda.homework.downloader;

import agoda.homework.DownloadTask;
import agoda.homework.downloader.impl.FileDownloader;
import agoda.homework.exceptions.DownloadException;
import agoda.homework.exceptions.UnsupportedProtocolException;
import agoda.homework.streamers.InStreamer;
import agoda.homework.streamers.OutStreamer;
import agoda.homework.streamers.Streamers;
import agoda.homework.streamers.impl.in.FileInStreamer;
import agoda.homework.streamers.impl.out.FileOutStreamer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.mockito.Mockito.*;

class FileDownloaderTest {

    @Test
    public void wnenStreamersOk_ThenFileContentDownloaded() throws DownloadException {

        final String CONTENT = "it works!!!";

        InStreamer inStreamerMock = mock(FileInStreamer.class);
        OutStreamer outStreamerMock = mock(FileOutStreamer.class);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        when(inStreamerMock.openInStream()).thenReturn(new ByteArrayInputStream(CONTENT.getBytes()));
        when(outStreamerMock.openOutStream()).thenReturn(baos);

        Downloader downloader = new FileDownloader(inStreamerMock, outStreamerMock, null);

        downloader.download();

        Assertions.assertEquals(CONTENT, baos.toString());

    }


    @Test
    public void wnenInStreamerIOException_ThenDownloadException() throws DownloadException {

        InStreamer inStreamerMock = mock(FileInStreamer.class);

        doThrow(IOException.class).when(inStreamerMock).openInStream();

        Downloader downloader = new FileDownloader(inStreamerMock, null, null);

        Assertions.assertThrows(DownloadException.class, downloader::download);

    }

    @Test
    public void whenDownloadException_ThenCleanupCalled() throws DownloadException, IOException {

        FileDownloader downloaderMock = mock(FileDownloader.class);

        doThrow(DownloadException.class).when(downloaderMock).download();

        DownloadTask downloadTask = new DownloadTask(downloaderMock);

        downloadTask.run();

        verify(downloaderMock, times(1)).cleanup();

    }


    @Test
    public void whenDownloadException_ThenCleanup() throws IOException, URISyntaxException, UnsupportedProtocolException {

        String source = "file:./src/test/resources/file/file.txt";

        InStreamer in = Streamers.createInStreamerByUri(new URI(source));
        OutStreamer out = Streamers.createFileOutStreamer(new URI(source));

        FileDownloader downloader = new TestDownloaderForFail(in, out, source);

        DownloadTask downloadTask = new DownloadTask(downloader);

        downloadTask.run();

        Assertions.assertFalse(Files.exists(Paths.get("/file.txt")));

    }



}