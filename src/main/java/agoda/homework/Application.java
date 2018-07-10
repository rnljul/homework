package agoda.homework;

import agoda.homework.downloader.impl.FileDownloader;
import agoda.homework.streamers.InStreamer;
import agoda.homework.streamers.OutStreamer;
import agoda.homework.streamers.Streamers;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;


/**
 * Application fo download data from URL list
 *
 */

public class Application {

    private static Logger logger = Logger.getLogger(Application.class);


    /**
     * Entry point.
     *
     * @param  args array of url
     *
     */
    public static void main(String[] args) {

        logger.debug("Application started");

        try {

            new Application().startup(args);

        } catch (Exception e) {

            logger.error(e.getMessage());

        }

        logger.debug("Application stopped");
    }


    /**
     * Startup download process, data will be downloaded in parallel mode
     *
     * @param  args array of url
     *
     */
    public void startup(String[] args) throws IOException, InterruptedException {


        AppProperties appProperties = AppProperties.getInstance();

        if (!Files.exists(Paths.get(appProperties.getDestination())))
            Files.createDirectory(Paths.get(appProperties.getDestination()));

        // Remove duplicated sources
        Set<String> sources = new HashSet<>();
        Collections.addAll(sources, args);

        // Pool for parallel downloading
        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(appProperties.getThreadPoolSize());

        // It needs to wait for all sources download completion
        CountDownLatch latch = new CountDownLatch(sources.size());


        // download all sources
        for (String source : sources) {

            try {

                URI sourceUri = new URI(source);

                InStreamer inStreamer = Streamers.createInStreamerByUri(sourceUri);

                OutStreamer outStreamer = Streamers.createFileOutStreamer(sourceUri);

                DownloadTask task = new DownloadTask(latch, new FileDownloader(inStreamer, outStreamer, sourceUri));

                executor.execute(task);

            } catch (Exception e) {
                // it needs to awoid block main thread, when error occurred
                latch.countDown();

                logger.error(e.getMessage());
            }

        }

        executor.shutdown();

        // wait for all sources download completion
        latch.await();

    }

}
