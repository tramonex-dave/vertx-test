package org.vertxtest.perform;

import io.netty.handler.codec.http.HttpResponseStatus;
import org.vertx.java.core.Handler;
import org.vertx.java.core.http.HttpServerRequest;
import org.vertx.java.platform.Verticle;

/**
 * @author jamesdbloom
 */
public class SimpleTestVertical extends Verticle {
    public void start() {
        System.out.println(getClass().getName() + " has started");
        vertx.createHttpServer().requestHandler(new Handler<HttpServerRequest>() {
            @Override
            public void handle(HttpServerRequest httpServerRequest) {
                httpServerRequest.response()
                        .setStatusCode(HttpResponseStatus.OK.code())
                        .setStatusMessage(HttpResponseStatus.OK.reasonPhrase())
                        .write(getClass().getName() + " has responded to a request");
                System.out.println(getClass().getName() + " has handle a request");
            }
        }).listen(9090, "localhost");
    }
}
