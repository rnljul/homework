package agoda.homework;


import agoda.homework.dowloader.Downloader;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.mockserver.integration.ClientAndServer;

import java.io.IOException;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockserver.integration.ClientAndServer.startClientAndServer;
import static org.mockserver.model.HttpCallback.callback;
import static org.mockserver.model.HttpRequest.request;

//@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class DownloaderTest {

    private ClientAndServer mockServer;

    @BeforeAll
    public void startServer() {
        mockServer = startClientAndServer(1080);
    }

    @AfterAll
    public void stopServer() {
        mockServer.stop();
    }

//    @Test
//    void printContetnt() {
//    }


//    @Test
    public void whenCallbackRequest_ThenCallbackMethodCalled(){
        createExpectationForCallBack();


//        Downloader downloader = new Downloader();
//
//        downloader.printContetnt("http://127.0.0.1:1080/callback");


//        org.apache.http.HttpResponse response = hitTheServerWithGetRequest("/callback");
//        assertEquals(200,response.getStatusLine().getStatusCode());
    }

    private org.apache.http.HttpResponse hitTheServerWithGetRequest(String page) {
        String url = "http://127.0.0.1:1080/"+page;
        HttpClient client = HttpClientBuilder.create().build();
        org.apache.http.HttpResponse response=null;
        HttpGet get = new HttpGet(url);
        try {
            response=client.execute(get);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return response;
    }

    private void createExpectationForCallBack(){
        mockServer.when(
                        request()
                                .withPath("/callback")
                ).callback(
                        callback().withCallbackClass("agoda.homework.TestExpectationCallback")
        );

//                .respond(
//          HttpResponse.response().withStatusCode(200)
//        );
//                .callback(
//                        callback()
//                                .withCallbackClass("agoda.homework.ExpectationCallbackHandler")
//                );
    }

}