package org.vertxtest.perform;

import org.vertx.java.core.Handler;
import org.vertxtest.http.MockHttpServerResponse;

/**
 * @author jamesdbloom
 */
public interface Expectation {

    public Expectation expect(Handler<MockHttpServerResponse> handler);

    public Expectation handle(Handler<MockHttpServerResponse> handler);

    public MockHttpServerResponse andReturn();
}
