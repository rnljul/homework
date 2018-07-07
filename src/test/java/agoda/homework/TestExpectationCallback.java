package agoda.homework;

import org.mockserver.mock.action.ExpectationCallback;
import org.mockserver.model.HttpRequest;
import org.mockserver.model.HttpResponse;

import static org.mockserver.model.HttpResponse.notFoundResponse;
import static org.mockserver.model.HttpResponse.response;

public class TestExpectationCallback implements ExpectationCallback {

    public HttpResponse handle(HttpRequest httpRequest) {
        if (httpRequest.getPath().getValue().endsWith("/callback")) {


            HttpResponse httpResponse = response().withBody("12345").withStatusCode(200);

            return httpResponse;

        } else {
            return notFoundResponse();
        }
    }

    public static HttpResponse httpResponse = response()
            .withStatusCode(200);
}
