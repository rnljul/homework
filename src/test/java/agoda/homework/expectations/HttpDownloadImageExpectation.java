package agoda.homework.expectations;

import org.mockserver.mock.action.ExpectationCallback;
import org.mockserver.model.HttpRequest;
import org.mockserver.model.HttpResponse;

import java.io.*;

import static org.mockserver.model.HttpResponse.notFoundResponse;
import static org.mockserver.model.HttpResponse.response;

public class HttpDownloadImageExpectation implements ExpectationCallback {

    @Override
    public HttpResponse handle(HttpRequest httpRequest) {

        if (httpRequest.getPath().getValue().endsWith("/download/image.png")) {


            File file = new File("./src/test/resources/http/image.png");

            byte[] image = new byte[(int) file.length()];


            try (
                    FileInputStream fis = new FileInputStream(file)
            ) {

                fis.read(image);

            } catch (IOException e) {
                e.printStackTrace();
            }


            return response().withBody(image).withStatusCode(200);

        } else {
            return notFoundResponse();
        }

    }

}
