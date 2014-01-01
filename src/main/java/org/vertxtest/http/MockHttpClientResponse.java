package org.vertxtest.http;

import io.netty.handler.codec.http.HttpResponseStatus;
import org.vertx.java.core.Handler;
import org.vertx.java.core.MultiMap;
import org.vertx.java.core.buffer.Buffer;
import org.vertx.java.core.http.CaseInsensitiveMultiMap;
import org.vertx.java.core.http.HttpClientResponse;
import org.vertx.java.core.net.NetSocket;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author jamesdbloom
 */
public class MockHttpClientResponse implements HttpClientResponse {
    private int statusCode = HttpResponseStatus.OK.code();
    private String statusMessage = HttpResponseStatus.OK.reasonPhrase();
    private MultiMap headers = new CaseInsensitiveMultiMap();
    private MultiMap trailers = new CaseInsensitiveMultiMap();
    private List<String> cookies = new ArrayList<String>();
    private Buffer body = new Buffer();
    private Handler<Buffer> bodyHandler;
    private NetSocket netSocket;
    private Handler<Void> endHandler;
    private Handler<Buffer> dataHandler;
    private boolean pause = false;
    private Handler<Throwable> exceptionHandler;

    @Override
    public int statusCode() {
        return statusCode;
    }

    public MockHttpClientResponse setStatusCode(int statusCode) {
        this.statusCode = statusCode;
        return this;
    }

    @Override
    public String statusMessage() {
        return statusMessage;
    }

    public MockHttpClientResponse setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
        return this;
    }

    @Override
    public MultiMap headers() {
        return this.headers;
    }

    public MockHttpClientResponse withHeaders(MultiMap headers) {
        this.headers = headers;
        return this;
    }

    public MockHttpClientResponse withHeader(String name, String value) {
        headers.add(name, value);
        return this;
    }

    public MockHttpClientResponse withHeader(String name, Iterable<String> values) {
        headers.add(name, values);
        return this;
    }

    public MockHttpClientResponse withHeader(String name, String... values) {
        withHeader(name, Arrays.asList(values));
        return this;
    }

    @Override
    public MultiMap trailers() {
        return this.trailers;
    }

    public MockHttpClientResponse withTrailers(MultiMap trailers) {
        this.trailers = trailers;
        return this;
    }

    public MockHttpClientResponse withTrailer(String name, String value) {
        trailers.add(name, value);
        return this;
    }

    public MockHttpClientResponse withTrailer(String name, Iterable<String> values) {
        trailers.add(name, values);
        return this;
    }

    public MockHttpClientResponse withTrailer(String name, String... values) {
        withTrailer(name, Arrays.asList(values));
        return this;
    }

    @Override
    public List<String> cookies() {
        return this.cookies;
    }

    public MockHttpClientResponse withCookies(List<String> cookies) {
        this.cookies = cookies;
        return this;
    }

    public MockHttpClientResponse withBody(byte[] body) {
        this.body.appendBytes(body);
        if (this.bodyHandler != null) {
            this.bodyHandler.handle(this.body);
        }
        if (this.dataHandler != null) {
            this.dataHandler.handle(this.body);
        }
        return this;
    }

    public byte[] body() {
        return body.getBytes();
    }

    @Override
    public MockHttpClientResponse bodyHandler(Handler<Buffer> bodyHandler) {
        this.bodyHandler = bodyHandler;
        if (this.body.length() > 0) {
            bodyHandler.handle(this.body);
        }
        return this;
    }

    public Handler<Buffer> bodyHandler() {
        return this.bodyHandler;
    }

    @Override
    public NetSocket netSocket() {
        return this.netSocket;
    }

    public MockHttpClientResponse withNetSocket(NetSocket netSocket) {
        this.netSocket = netSocket;
        return this;
    }

    @Override
    public MockHttpClientResponse endHandler(Handler<Void> endHandler) {
        this.endHandler = endHandler;
        this.endHandler.handle(null);
        return this;
    }

    public Handler<Void> endHandler() {
        return this.endHandler;
    }

    @Override
    public MockHttpClientResponse dataHandler(Handler<Buffer> handler) {
        this.dataHandler = handler;
        if (this.body.length() > 0) {
            this.dataHandler.handle(this.body);
        }
        return this;
    }

    public Handler<Buffer> dataHandler() {
        return this.dataHandler;
    }

    @Override
    public MockHttpClientResponse pause() {
        this.pause = true;
        return this;
    }

    @Override
    public MockHttpClientResponse resume() {
        this.pause = false;
        return this;
    }

    public boolean paused() {
        return this.pause;
    }

    @Override
    public MockHttpClientResponse exceptionHandler(Handler<Throwable> handler) {
        this.exceptionHandler = handler;
        return this;
    }

    public Handler<Throwable> exceptionHandler() {
        return this.exceptionHandler;
    }
}
