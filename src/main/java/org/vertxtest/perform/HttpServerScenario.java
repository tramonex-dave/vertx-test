package org.vertxtest.perform;

import org.vertx.java.core.AsyncResult;
import org.vertx.java.core.Handler;
import org.vertx.java.core.http.HttpServerRequest;
import org.vertx.java.platform.PlatformLocator;
import org.vertx.java.platform.PlatformManager;
import org.vertx.java.platform.Verticle;
import org.vertxtest.http.MockHttpServerRequest;
import org.vertxtest.http.MockHttpServerResponse;

import java.net.URL;

/**
 * @author jamesdbloom
 */
public class HttpServerScenario {

    private final Verticle verticle;

    public HttpServerScenario(Verticle verticle) {
        this.verticle = verticle;
    }

    public static void main(String[] args) {
        new HttpServerScenario(new SimpleTestVertical()).handle(new MockHttpServerRequest()).handle(new Handler<MockHttpServerResponse>() {
            @Override
            public void handle(MockHttpServerResponse event) {
                System.out.println("Finished test");
            }
        });
    }

    public Expectation handle(final MockHttpServerRequest mockHttpServerRequest) {
        if (!(mockHttpServerRequest.response() instanceof MockHttpServerResponse)) throw new IllegalArgumentException("Response must be an instance of org.vertxtest.http.MockHttpServerResponse");

        MockVertx vertx = new MockVertx();
        verticle.setVertx(vertx);
        verticle.start();

        Handler<HttpServerRequest> httpServerRequestHandler = vertx.mockHttpServer().serverRequestHandler();
        httpServerRequestHandler.handle(mockHttpServerRequest);

        final MockHttpServerResponse response = (MockHttpServerResponse) mockHttpServerRequest.response();
        return new Expectation() {

            public Expectation expect(Handler<MockHttpServerResponse> handler) {
                handler.handle(response);
                return this;
            }

            public Expectation handle(Handler<MockHttpServerResponse> handler) {
                handler.handle(response);
                return this;
            }

            public MockHttpServerResponse andReturn() {
                return response;
            }
        };
    }
}
