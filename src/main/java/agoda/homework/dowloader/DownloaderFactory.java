package agoda.homework.dowloader;

public interface DownloaderFactory {

    Downloader createDownloader(String source);

}
