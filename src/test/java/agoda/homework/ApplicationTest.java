package agoda.homework;

import agoda.homework.exceptions.DownloadException;
import agoda.homework.exceptions.UnsupportedProtocolException;
import agoda.homework.expectations.HttpDownloadImageExpectation;
import agoda.homework.expectations.HttpDownloadTextExpectation;
import agoda.homework.streamers.InStreamer;
import agoda.homework.streamers.Streamers;
import agoda.homework.streamers.impl.in.FileInStreamer;
import agoda.homework.streamers.impl.out.FileOutStreamer;
import agoda.homework.utl.AppUtl;
import org.junit.jupiter.api.*;
import org.mockserver.integration.ClientAndServer;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.NoSuchAlgorithmException;

import static org.mockito.Mockito.*;
import static org.mockserver.integration.ClientAndServer.startClientAndServer;
import static org.mockserver.model.HttpCallback.callback;
import static org.mockserver.model.HttpRequest.request;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ApplicationTest {

    public static final String SFTP_SOURCE = "sftp://demo-user:demo-user@demo.wftpserver.com:2222/download/manual_en.pdf";
    public static final String SFTP_MD5 = "d4d239768794b728c4267c9fffa0438f";

    public static final String DIR_SOURCE = "file:./src/test/resources/file";
    public static final String DIR_SOURCE_MD5 = "e22d8dd40a886dd786ccce0a35a6e4a9";

    public static final String BIG_FILE_SOURCE = "file:./test/bigfile.txt";
    public static final String BIG_FILE_MD5 = "f5f24b666fea7cc6825ceae8f9750362";

    public static final String TEXT_TXT_SOURCE = "http://localhost:1080/download/text.txt";
    public static final String TEXT_TXT_MD5 = "8bd254c98a14a6b305be6117fa04b694";

    public static final String IMAGE_PNG_SOURCE = "http://localhost:1080/download/image.png";
    public static final String IMAGE_PNG_MD5 = "5b0e78488230dcb87101e120f92347b2";

    public static final String NOT_FOUND_SOURCE = "http://localhost:1080/download/notfound/text.txt";

    private ClientAndServer mockServer;


    private void createBigFile() throws IOException {

        if (!Files.exists(Paths.get("./test")))
            Files.createDirectory(Paths.get("./test"));

        byte[] buf = new byte[8192];
        long n = 1024 * 30000;
        FileOutputStream fos = new FileOutputStream( "./test/bigfile.txt");
        long m = n / buf.length;
        for (long i = 0; i < m; i++) {
            fos.write(buf, 0, buf.length);
        }
        fos.write(buf, 0, (int) (n % buf.length));
        fos.close();

    }

    private void removeBigFile() throws IOException {
        Files.delete(Paths.get("./test/bigfile.txt"));
        Files.delete(Paths.get("./test"));
    }

    @BeforeAll
    public void setup() throws IOException {

        createBigFile();

        mockServer = startClientAndServer(1080);
    }

    @AfterAll
    public void tearDown() throws IOException {
        mockServer.stop();

        File testDir = new File(AppProperties.getInstance().getDestination());

        File[] files = testDir.listFiles();
        if (files != null) {
            for (File file : files) {
                file.delete();
            }
        }
        testDir.delete();

        removeBigFile();
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
    public void whenSftpUrl_ThenDownloaded() throws IOException, InterruptedException, NoSuchAlgorithmException, URISyntaxException {

        download(SFTP_SOURCE, SFTP_MD5);

    }


    @Test
    public void whenHttpNotFound_ThenDownloadException() throws IOException, UnsupportedProtocolException, URISyntaxException {
        createExpectationForTextDownload();

        InStreamer inStreamer = Streamers.createInStreamerByUri(new URI(NOT_FOUND_SOURCE));

        FileOutStreamer outStreamer = new FileOutStreamer(AppUtl.getOutFilePath(new URI(NOT_FOUND_SOURCE)));

        Downloader downloader = new Downloader(inStreamer, outStreamer);

        Assertions.assertThrows(DownloadException.class, downloader::download);

    }


    @Test
    public void whenDownloadFailed_ThenCleanup() throws DownloadException {

        FileInStreamer fis = mock(FileInStreamer.class);
        FileOutStreamer fos = mock(FileOutStreamer.class);

        when(fis.openInStream()).thenReturn(new ByteArrayInputStream("data".getBytes()));
        when(fos.openOutStream()).thenReturn(new ByteArrayOutputStream());
        doThrow(DownloadException.class).when(fos).release();

        Downloader downloader = new Downloader(fis, fos);


        try {
            downloader.download();
        } catch (DownloadException e) {

        }


        verify(fos, times(1)).cleanup();


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