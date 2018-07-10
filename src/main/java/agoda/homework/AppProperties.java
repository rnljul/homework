package agoda.homework;

import agoda.homework.exceptions.RuntimeAppException;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class AppProperties {

    private int bufferSize;

    private int threadPoolSize;

    private String destination;

    private static AppProperties ourInstance = new AppProperties();

    public static AppProperties getInstance() {
        return ourInstance;
    }

    private AppProperties() {

        try(InputStream input = new FileInputStream("config/app.properties")) {

            Properties prop = new Properties();

            prop.load(input);

            bufferSize = Integer.parseInt(prop.getProperty("buffer_size"));
            threadPoolSize = Integer.parseInt(prop.getProperty("thread_pool_size"));
            destination = prop.getProperty("download_location");

        } catch (FileNotFoundException e) {
            throw new RuntimeAppException("config/app.properties not found", e);
        } catch (IOException e) {
            throw new RuntimeAppException("unable to read from config/app.properties", e);
        }

    }

    public int getBufferSize() {
        return bufferSize;
    }

    public int getThreadPoolSize() {
        return threadPoolSize;
    }

    public String getDestination() {
        return destination;
    }
}
