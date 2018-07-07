package agoda.homework.delme;

import java.io.*;
import java.net.*;
import java.util.Properties;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;



import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;

public class HttpDownloadUtility {

//
//    private static final int BUFFER_SIZE = 4096;
//
//
//
//    private class SftpConfig {
//
//        private SftpConfig(String sftpUrl) throws URISyntaxException {
//
//            URI sourceUri = new URI(sftpUrl);
//
//            HOST = sourceUri.getHost();
//            PORT = sourceUri.getPort();
//
//            String path = sourceUri.getPath();
//
//            FILE_NAME = path.substring(path.lastIndexOf('/') + 1);
//
//            WORK_DIR = path.substring(0, path.lastIndexOf('/'));
//
//
//
//            String userFragment = sourceUri.getUserInfo();
//            if(userFragment != null) {
//                String[] q = userFragment.split(":");
//                USER = q[0];
//                PASS = 2 == q.length ? q[1] : "";
//            } else {
//                USER = "";
//                PASS = "";
//            }
//
//
//        }
//
//        final int PORT;
//        final String HOST;
//        final String USER;
//        final String PASS;
//        final String WORK_DIR;
//        final String FILE_NAME;
//
//    }
//
//
//    private ChannelSftp buildChannelSftp(SftpConfig sftpConfig) throws JSchException, SftpException {
//
//        JSch jsch = new JSch();
//
//        Session session = jsch.getSession(sftpConfig.USER, sftpConfig.HOST, sftpConfig.PORT);
//        session.setPassword(sftpConfig.PASS);
//        java.util.Properties config = new java.util.Properties();
//        config.put("StrictHostKeyChecking", "no");
//        session.setConfig(config);
//        session.connect();
//        Channel channel = session.openChannel("sftp");
//        channel.connect();
//        ChannelSftp channelSftp = (ChannelSftp) channel;
//        channelSftp.cd(sftpConfig.WORK_DIR);
//        return channelSftp;
//
//    }

//
//    public void downloadSFTP(String sourceLocation, String distLocation) throws JSchException, SftpException, IOException, URISyntaxException {
//
//        SftpConfig sftpConfig = new SftpConfig(sourceLocation);
//
//        ChannelSftp channelSftp = buildChannelSftp(sftpConfig);
//
//        String distFilePath = String.format("%s%s%s", distLocation, File.separator, sftpConfig.FILE_NAME);
//
//        try (
//                InputStream inputStream = new BufferedInputStream(channelSftp.get(sftpConfig.FILE_NAME));
//                FileOutputStream outputStream = new FileOutputStream(distFilePath)
//        ) {
//
//            int bytesRead = -1;
//            byte[] buffer = new byte[BUFFER_SIZE];
//            while ((bytesRead = inputStream.read(buffer)) != -1) {
//                outputStream.write(buffer, 0, bytesRead);
//            }
//
//        }
//
//    }
//

//    public void downloadHTTP(String sourceLocation, String distLocation) throws NoDownLoadFile, IOException {
//
//        URL sourceUrl = new URL(sourceLocation);
//
//        HttpURLConnection httpConn = (HttpURLConnection) sourceUrl.openConnection();
//
//        int responseCode = httpConn.getResponseCode();
//
//        if (responseCode != HttpURLConnection.HTTP_OK)
//            //TODO "No file to download. Server replied HTTP code: " + responseCode
//            throw new NoDownLoadFile();
//
//        String distFilePath =
//                distLocation +
//                File.separator +
//                sourceLocation.substring(sourceLocation.lastIndexOf("/") + 1);
//
//
//        try (
//                InputStream inputStream = httpConn.getInputStream();
//                FileOutputStream outputStream = new FileOutputStream(distFilePath)
//        ) {
//
//            int bytesRead = -1;
//            byte[] buffer = new byte[BUFFER_SIZE];
//            while ((bytesRead = inputStream.read(buffer)) != -1) {
//                outputStream.write(buffer, 0, bytesRead);
//            }
//
//        }
//
//
//    }


//    public void downloadFtp(String sourceLocation, String distLocation) throws NoDownLoadFile, IOException {
//
//        URL sourceUrl = new URL(sourceLocation);
//
//        URLConnection httpConn = sourceUrl.openConnection();
//
//
//        String distFilePath =
//                distLocation +
//                        File.separator +
//                        sourceLocation.substring(sourceLocation.lastIndexOf("/") + 1);
//
//
//        try (
//                InputStream inputStream = httpConn.getInputStream();
//                FileOutputStream outputStream = new FileOutputStream(distFilePath)
//        ) {
//
//            int bytesRead = -1;
//            byte[] buffer = new byte[BUFFER_SIZE];
//            while ((bytesRead = inputStream.read(buffer)) != -1) {
//                outputStream.write(buffer, 0, bytesRead);
//            }
//
//        }
//
//
//    }



//    public void downloadFileFtp() {
//        String server = "localhost";
//        int port = 21;
//        String user = "myuser";
//        String pass = "mypass";
//
//        FTPClient ftpClient = new FTPClient();
//        try {
//
//            ftpClient.connect(server, port);
//            ftpClient.login(user, pass);
//            ftpClient.enterLocalPassiveMode();
//            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
//
//
//            String remoteFile2 = "/tests.sql";
//            File downloadFile2 = new File("./tests.sql");
//            InputStream inputStream;
//            try (OutputStream outputStream2 = new BufferedOutputStream(new FileOutputStream(downloadFile2))) {
//                inputStream = ftpClient.retrieveFileStream(remoteFile2);
//                byte[] bytesArray = new byte[4096];
//                int bytesRead = -1;
//                while ((bytesRead = inputStream.read(bytesArray)) != -1) {
//                    outputStream2.write(bytesArray, 0, bytesRead);
//                }
//
//                boolean success = ftpClient.completePendingCommand();
//                if (success) {
//                    System.out.println("File #2 has been downloaded successfully.");
//                }
//            }
//            inputStream.close();
//
//        } catch (IOException ex) {
//            System.out.println("Error: " + ex.getMessage());
//            ex.printStackTrace();
//        } finally {
//            try {
//                if (ftpClient.isConnected()) {
//                    ftpClient.logout();
//                    ftpClient.disconnect();
//                }
//            } catch (IOException ex) {
//                ex.printStackTrace();
//            }
//        }
//    }
}
