package org.vertxtest.perform;

import org.vertx.java.core.Handler;
import org.vertxtest.http.MockHttpServerRequest;
import org.vertxtest.http.MockHttpServerResponse;

/**
 * @author jamesdbloom
 */
public class HttpServerScenario {

    public Expectation handle(final MockHttpServerRequest mockHttpServerRequest) {
        if (mockHttpServerRequest.response() instanceof MockHttpServerResponse) throw new IllegalArgumentException("Response must be an instance of org.vertxtest.http.MockHttpServerResponse");

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
