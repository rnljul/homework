package agoda.homework.streamers.impl.in;

import agoda.homework.exceptions.DownloadException;
import com.jcraft.jsch.*;
import org.apache.log4j.Logger;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.URI;

public class SftpFileInStreamer extends FileInStreamer {

    private static org.apache.log4j.Logger logger = Logger.getLogger(SftpFileInStreamer.class);

    private final int port;
    private final String host;
    private final String user;
    private final String pass;
    private final String path;
    private final String fileName;
    private final URI sourceUri;

    private ChannelSftp channelSftp;
    private Session session;


    public SftpFileInStreamer(URI sourceUri) {

        super(null);

        this.sourceUri = sourceUri;

        host = sourceUri.getHost();
        port = sourceUri.getPort();

        String fullPath = sourceUri.getPath();

        fileName = fullPath.substring(fullPath.lastIndexOf('/') + 1);

        path = fullPath.substring(0, fullPath.lastIndexOf('/'));

        String userFragment = sourceUri.getUserInfo();
        if(userFragment != null) {
            String[] userPass = userFragment.split(":");
            user = userPass[0];
            pass = 2 == userPass.length ? userPass[1] : "";
        } else {
            user = "";
            pass = "";
        }

    }

    private void buildChannelSftp() throws JSchException, SftpException {

        JSch jsch = new JSch();

        session = jsch.getSession(user, host, port);
        session.setPassword(pass);
        java.util.Properties config = new java.util.Properties();
        config.put("StrictHostKeyChecking", "no");
        session.setConfig(config);
        session.connect();
        Channel channel = session.openChannel("sftp");
        channel.connect();
        channelSftp = (ChannelSftp) channel;
        channelSftp.cd(path);

    }


    @Override
    public InputStream openInStream() throws DownloadException {

        logger.debug(String.format("Download from [%s] started", sourceUri));

        try {

            buildChannelSftp();

            return new BufferedInputStream(channelSftp.get(fileName));

        } catch (JSchException | SftpException e) {

            throw new DownloadException(
                    String.format("Unable to open stream from source location [%s]", sourceUri),
                    e
            );

        }

    }


    @Override
    public void release() {

        channelSftp.exit();
        session.disconnect();

        logger.debug(String.format("Download from [%s] finished", sourceUri));

    }


}
