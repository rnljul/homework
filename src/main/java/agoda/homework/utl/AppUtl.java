package agoda.homework.utl;

import agoda.homework.AppProperties;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URI;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class AppUtl {


    public static String getOutFilePath(URI source) {

        String scheme = source.getScheme();
        String path;

        if(scheme.equals("file")) {
            path = source.toString().replace("file:", "");
        } else {
            path = source.getPath();
        }

        return AppProperties.getInstance().getDestination() + File.separator + scheme + "_" +
                path.replace(scheme + "://", "").
                        replace("/", "_");

    }

    public static String md5File(String filePath) throws NoSuchAlgorithmException, IOException {

        File file = new File(filePath);

        MessageDigest md = MessageDigest.getInstance("MD5");
        try (
                FileInputStream fs = new FileInputStream(file);
                BufferedInputStream bs = new BufferedInputStream(fs)
        ) {

            byte[] buffer = new byte[1024];
            int bytesRead;

            while ((bytesRead = bs.read(buffer, 0, buffer.length)) != -1) {
                md.update(buffer, 0, bytesRead);
            }
            byte[] digest = md.digest();

            StringBuilder sb = new StringBuilder();
            for (byte bite : digest) {
                sb.append(String.format("%02x", bite & 0xff));
            }
            return sb.toString();
        }
    }

}
