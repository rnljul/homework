package agoda.homework;

import agoda.homework.downloader.Downloader;
import agoda.homework.downloader.impl.FileDownloader;
import agoda.homework.exceptions.DownloadException;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;


/**
 * Task for ThreadPoolExecutor
 * Download executed in separate thread
 */
public class DownloadTask implements Runnable {

    private static Logger logger = Logger.getLogger(DownloadTask.class);

    private final Downloader downloader;
    private CountDownLatch latch;

    public DownloadTask(Downloader downloader) {
        this.downloader = downloader;
    }

    public DownloadTask(CountDownLatch latch, FileDownloader downloader) {
        this(downloader);
        this.latch = latch;
    }

    @Override
    public void run() {

        try {
            downloader.download();
        } catch (DownloadException e) {
            try {

                logger.warn(e.getMessage());

                downloader.cleanup();
            } catch (IOException e1) {
                logger.error(e1.getMessage());
            }
        } finally {
            if(latch != null)
                latch.countDown();
        }

    }

}
