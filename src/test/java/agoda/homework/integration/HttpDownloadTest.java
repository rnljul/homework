package agoda.homework.integration;


import agoda.homework.AppProperties;
import agoda.homework.Application;
import agoda.homework.downloader.Downloader;
import agoda.homework.downloader.impl.FileDownloader;
import agoda.homework.exceptions.DownloadException;
import agoda.homework.exceptions.UnsupportedProtocolException;
import agoda.homework.streamers.InStreamer;
import agoda.homework.streamers.Streamers;
import agoda.homework.streamers.impl.out.FileOutStreamer;
import agoda.homework.utl.AppUtl;
import org.junit.jupiter.api.*;
import org.mockserver.integration.ClientAndServer;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.NoSuchAlgorithmException;

import static org.mockserver.integration.ClientAndServer.startClientAndServer;
import static org.mockserver.model.HttpCallback.callback;
import static org.mockserver.model.HttpRequest.request;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class HttpDownloadTest {

    public static final String DIR_SOURCE = "file:./src/test/resources/file";
    public static final String DIR_SOURCE_MD5 = "b2feacc75e3cf80067f532be56aadb95";

    public static final String BIG_FILE_SOURCE = "file:./src/test/resources/file/1gb.txt";
    public static final String BIG_FILE_MD5 = "cd573cfaace07e7949bc0c46028904ff";

    public static final String TEXT_TXT_SOURCE = "http://localhost:1080/download/text.txt";
    public static final String TEXT_TXT_MD5 = "8bd254c98a14a6b305be6117fa04b694";

    public static final String IMAGE_PNG_SOURCE = "http://localhost:1080/download/image.png";
    public static final String IMAGE_PNG_MD5 = "5b0e78488230dcb87101e120f92347b2";


    public static final String NOT_FOUND_SOURCE = "http://localhost:1080/download/notfound/text.txt";
    public static final String TXT_FILE_PATH = "./test/test.txt";

    private ClientAndServer mockServer;

    @BeforeAll
    public void setup() {
        mockServer = startClientAndServer(1080);
    }

    @AfterAll
    public void tearDown() {
        mockServer.stop();

        File testDir = new File(AppProperties.getInstance().getDestination());

        File[] files = testDir.listFiles();
        if (files != null) {
            for (File file : files) {
                file.delete();
            }
        }
        testDir.delete();


    }


    void download(String source, String sourceMd5) throws IOException, NoSuchAlgorithmException, InterruptedException, URISyntaxException {

        Application app = new Application();

        app.startup(new String[]{source});

        String md5 = AppUtl.md5File(AppUtl.getOutFilePath(new URI(source)));

        Assertions.assertEquals(sourceMd5, md5);

    }


    @Test
    public void whenHttpOk_ThenTextFileDownloaded() throws IOException, NoSuchAlgorithmException, InterruptedException, URISyntaxException {

        createExpectationForTextDownload();

        download(TEXT_TXT_SOURCE, TEXT_TXT_MD5);

    }


    @Test
    public void whenHttpOk_ThenImageDownloaded() throws IOException, InterruptedException, NoSuchAlgorithmException, URISyntaxException {

        createExpectationForImageDownload();

        download(IMAGE_PNG_SOURCE, IMAGE_PNG_MD5);

    }

    @Test
    public void whenDirectory_ThenContentListDownloaded() throws IOException, InterruptedException, NoSuchAlgorithmException, URISyntaxException {

        download(DIR_SOURCE, DIR_SOURCE_MD5);

    }

    @Test
    public void whenFileBiggerThanMemory_ThenDownloaded() throws IOException, InterruptedException, NoSuchAlgorithmException, URISyntaxException {

        download(BIG_FILE_SOURCE, BIG_FILE_MD5);

    }


    @Test
    public void whenHttpNotFound_ThenDownloadException() throws IOException, UnsupportedProtocolException, URISyntaxException {
        createExpectationForTextDownload();

        InStreamer inStreamer = Streamers.createInStreamerByUri(new URI(NOT_FOUND_SOURCE));

        FileOutStreamer outStreamer = new FileOutStreamer(AppUtl.getOutFilePath(new URI(NOT_FOUND_SOURCE)));

        Downloader downloader = new FileDownloader(inStreamer, outStreamer, new URI(NOT_FOUND_SOURCE));

        Assertions.assertThrows(DownloadException.class, downloader::download);

    }




    private void createExpectationForTextDownload() {
        mockServer.when(
                        request()
                                .withPath("/download/text.txt")
                ).callback(
                        callback().withCallbackClass(HttpDownloadTextExpectation.class.getName())
        );
    }

    private void createExpectationForImageDownload() {

        mockServer.when(
                request()
                        .withPath("/download/image.png")
        ).callback(
                callback().withCallbackClass(HttpDownloadImageExpectation.class.getName())
        );
    }

}