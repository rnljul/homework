package agoda.homework.dowloader;

import agoda.homework.dowloader.exceptions.AppException;

public interface Downloader {

    void download(String source, String destination) throws AppException;

}
