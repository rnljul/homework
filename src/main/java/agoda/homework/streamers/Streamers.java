package agoda.homework.streamers;

import agoda.homework.exceptions.UnsupportedProtocolException;
import agoda.homework.streamers.impl.in.FileInStreamer;
import agoda.homework.streamers.impl.in.FtpFileInStreamer;
import agoda.homework.streamers.impl.in.HttpFileInStreamer;
import agoda.homework.streamers.impl.in.SftpFileInStreamer;
import agoda.homework.streamers.impl.out.FileOutStreamer;
import agoda.homework.utl.AppUtl;

import java.net.MalformedURLException;
import java.net.URI;

/**
 * Class for creation concrete Streamer instancies
 */
public class Streamers {


    /**
     * Create concrete instance of OutStreamer
     *
     * param sourceUri URI file source location
     * @return Concrete OutStreamer
     * @throws MalformedURLException
     */
    public static OutStreamer createFileOutStreamer(URI source) {

            return new FileOutStreamer(AppUtl.getOutFilePath(source));

    }


    /**
     * Create concrete instance of InStreamer
     *
     * @param sourceUri URI file source location
     * @return Concrete InStreamer
     * @throws UnsupportedProtocolException
     * @throws MalformedURLException
     */
    public static InStreamer createInStreamerByUri(URI sourceUri) throws UnsupportedProtocolException, MalformedURLException {

            String protocol = sourceUri.getScheme();

            if(protocol == null)
                throw new UnsupportedProtocolException("Protocol not defined");

            switch (protocol.toUpperCase()) {

                case "HTTP" :

                case "HTTPS":
                    return new HttpFileInStreamer(sourceUri.toURL());

                case "FTP":
                    return new FtpFileInStreamer(sourceUri.toURL());

                case "SFTP":
                    return new SftpFileInStreamer(sourceUri);

                case "FILE":
                    return new FileInStreamer(sourceUri.toURL());

                 default: throw new UnsupportedProtocolException(String.format("Unsupported protocol [%s]", protocol));

            }

    }

}
