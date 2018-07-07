package agoda.homework;

import agoda.homework.delme.HttpDownloadUtility;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.SftpException;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;

class HttpDownloadUtilityTest {


    private static final int BUFFER_SIZE = 4096;

    @Test
    void downloadFile() {


//        String fileURL = "https://static.ngs.ru/news/99/preview/99c85a6841105678f5cd8078d080d8da022fdc44_900.jpg";

//        String fileURL = "ftp://myuser:mypass@localhost/tests.sql";

        String fileURL = "sftp://foo:pass@localhost:2222/upload/1519564219868.jpg";

        //String fileURL = "file:///home/roman/Загрузки/JavaNetworkProgramming.pdf";

        String saveDir = "./";

        HttpDownloadUtility utl = new HttpDownloadUtility();

//        try {
//            utl.downloadFileFtp(fileURL, saveDir);
//        } catch (IOException ex) {
//            ex.printStackTrace();
//        }
//
//        try {
//            utl.downloadSFTP(fileURL, saveDir);
//        } catch (JSchException e) {
//            e.printStackTrace();
//        } catch (SftpException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (URISyntaxException e) {
//            e.printStackTrace();
//        }

    }

}