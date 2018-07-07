package agoda.homework.dowloader;

import agoda.homework.dowloader.exceptions.AppException;

import java.io.InputStream;
import java.io.OutputStream;

public interface StreamProvider {

    InputStream openInStream(String source) throws AppException;

    OutputStream openOutStream(String destination) throws AppException;

}
