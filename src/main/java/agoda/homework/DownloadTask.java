package agoda.homework;

import agoda.homework.exceptions.DownloadException;
import org.apache.log4j.Logger;

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

    public DownloadTask(CountDownLatch latch, Downloader downloader) {
        this(downloader);
        this.latch = latch;
    }

    @Override
    public void run() {

        try {
            downloader.download();
        } catch (DownloadException e) {
            logger.warn(e);
        } finally {
            if(latch != null) {
                latch.countDown();
                logger.debug(String.format("current latch count = [%d]", latch.getCount()));
            }
        }

    }

}
