package agoda.homework.streamers;

import agoda.homework.exceptions.UnsupportedProtocolException;
import agoda.homework.streamers.impl.in.FileInStreamer;
import agoda.homework.streamers.impl.in.FtpFileInStreamer;
import agoda.homework.streamers.impl.in.HttpFileInStreamer;
import agoda.homework.streamers.impl.in.SftpFileInStreamer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;

class StreamersTest {

    @Test
    void whenUnsupportedProtocol_ThenError() {

        Assertions.assertThrows(
                UnsupportedProtocolException.class,
                () -> Streamers.createInStreamerByUri(new URI("htt://a/f.txt"))
        );

    }


    @Test
    void whenHttp_ThenHttpInStreamer() throws MalformedURLException, UnsupportedProtocolException, URISyntaxException {

        Assertions.assertTrue(Streamers.createInStreamerByUri(new URI("http://a/f.txt")).getClass().equals(HttpFileInStreamer.class));

    }


    @Test
    void whenFtp_ThenFtpInStreamer() throws MalformedURLException, UnsupportedProtocolException, URISyntaxException {

        Assertions.assertTrue(Streamers.createInStreamerByUri(new URI("ftp://a/f.txt")).getClass().equals(FtpFileInStreamer.class));

    }



    @Test
    void whenSftp_ThenSftpInStreamer() throws MalformedURLException, UnsupportedProtocolException, URISyntaxException {

        Assertions.assertTrue(Streamers.createInStreamerByUri(new URI("sftp://foo:pass@localhost:2222/upload/1519564219868.jpg")).getClass().equals(SftpFileInStreamer.class));

    }



    @Test
    void whenFile_ThenFileInStreamer() throws MalformedURLException, UnsupportedProtocolException, URISyntaxException {

        Assertions.assertTrue(Streamers.createInStreamerByUri(new URI("file:///a/f.txt")).getClass().equals(FileInStreamer.class));

    }


}