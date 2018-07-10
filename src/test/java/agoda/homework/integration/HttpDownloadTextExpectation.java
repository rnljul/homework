package agoda.homework.integration;

import org.mockserver.mock.action.ExpectationCallback;
import org.mockserver.model.HttpRequest;
import org.mockserver.model.HttpResponse;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import static org.mockserver.model.HttpResponse.notFoundResponse;
import static org.mockserver.model.HttpResponse.response;

public class HttpDownloadTextExpectation implements ExpectationCallback {

    public HttpResponse handle(HttpRequest httpRequest) {
        if (httpRequest.getPath().getValue().endsWith("/download/text.txt")) {

            StringBuilder content = new StringBuilder();


            try (
                    BufferedReader reader = new BufferedReader(new FileReader("./src/test/resources/http/text.txt"))
            ) {

                String line;

                while ((line = reader.readLine()) != null) {
                    content.append(line);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }


            return response().withBody(content.toString()).withStatusCode(200);

        } else {
            return notFoundResponse();
        }
    }

}
