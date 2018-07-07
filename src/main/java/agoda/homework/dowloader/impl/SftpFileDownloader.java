package agoda.homework.dowloader.impl;

import agoda.homework.dowloader.FileDownloader;
import agoda.homework.dowloader.exceptions.AppException;
import com.jcraft.jsch.*;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;

public class SftpFileDownloader extends FileDownloader {


    private class SftpConfig {

        private SftpConfig(String sftpUrl) throws URISyntaxException {

            URI sourceUri = new URI(sftpUrl);

            HOST = sourceUri.getHost();
            PORT = sourceUri.getPort();

            String path = sourceUri.getPath();

            FILE_NAME = path.substring(path.lastIndexOf('/') + 1);

            WORK_DIR = path.substring(0, path.lastIndexOf('/'));



            String userFragment = sourceUri.getUserInfo();
            if(userFragment != null) {
                String[] q = userFragment.split(":");
                USER = q[0];
                PASS = 2 == q.length ? q[1] : "";
            } else {
                USER = "";
                PASS = "";
            }


        }

        final int PORT;
        final String HOST;
        final String USER;
        final String PASS;
        final String WORK_DIR;
        final String FILE_NAME;

    }


    private ChannelSftp buildChannelSftp(SftpConfig sftpConfig) throws JSchException, SftpException {

        JSch jsch = new JSch();

        Session session = jsch.getSession(sftpConfig.USER, sftpConfig.HOST, sftpConfig.PORT);
        session.setPassword(sftpConfig.PASS);
        java.util.Properties config = new java.util.Properties();
        config.put("StrictHostKeyChecking", "no");
        session.setConfig(config);
        session.connect();
        Channel channel = session.openChannel("sftp");
        channel.connect();
        ChannelSftp channelSftp = (ChannelSftp) channel;
        channelSftp.cd(sftpConfig.WORK_DIR);
        return channelSftp;

    }


    @Override
    public InputStream openInStream(String source) throws AppException {

        try {
            SftpConfig sftpConfig = new SftpConfig(source);

            ChannelSftp channelSftp = buildChannelSftp(sftpConfig);

            return new BufferedInputStream(channelSftp.get(sftpConfig.FILE_NAME));
        } catch (JSchException | SftpException e) {

            throw new AppException(
                    String.format("Unable to open stream from source location [%s]", source),
                    e
            );

        } catch (URISyntaxException e) {

            throw new AppException(
                    String.format("Invalid source location [%s]", source),
                    e
            );

        }

    }


}
