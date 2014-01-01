package org.vertxtest.http;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ReadOnlyByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.handler.codec.http.HttpHeaders;
import org.vertx.java.core.Handler;
import org.vertx.java.core.MultiMap;
import org.vertx.java.core.buffer.Buffer;
import org.vertx.java.core.http.CaseInsensitiveMultiMap;
import org.vertx.java.core.http.HttpClientRequest;

import java.util.Arrays;

/**
 * @author jamesdbloom
 */
public class MockHttpClientRequest implements HttpClientRequest {
    private boolean chunked = false;
    private MultiMap headers = new CaseInsensitiveMultiMap();
    private ByteBuf byteBuf = Unpooled.buffer();
    private boolean written = false;
    private Handler<Void> continueHandler;
    private long timeoutMs = -1;
    private int writeQueueMaxSize = Integer.MAX_VALUE;
    private Handler<Void> drainHandler;
    private Handler<Throwable> exceptionHandler;

    public MockHttpClientRequest setChunked(boolean chunked) {
        this.chunked = chunked;
        return this;
    }

    public boolean isChunked() {
        return chunked;
    }

    public MultiMap headers() {
        return this.headers;
    }

    public MockHttpClientRequest putHeader(String name, String value) {
        headers.add(name, value);
        return this;
    }

    public MockHttpClientRequest putHeader(String name, Iterable<String> values) {
        headers.add(name, values);
        return this;
    }

    public MockHttpClientRequest putHeader(String name, String... values) {
        putHeader(name, Arrays.asList(values));
        return this;
    }

    public MockHttpClientRequest withHeaders(MultiMap headers) {
        this.headers = headers;
        return this;
    }

    public MockHttpClientRequest write(Buffer chunk) {
        byteBuf.writeBytes(chunk.getByteBuf());
        return this;
    }

    public MockHttpClientRequest write(String chunk) {
        write(new Buffer(chunk));
        return this;
    }

    public MockHttpClientRequest write(String chunk, String enc) {
        write(new Buffer(chunk, enc));
        return this;
    }

    public MockHttpClientRequest continueHandler(Handler<Void> handler) {
        this.continueHandler = handler;
        return this;
    }

    public Handler<Void> continueHandler() {
        return this.continueHandler;
    }

    public HttpClientRequest sendHead() {
        return this;
    }

    public void end(String chunk) {
        end(new Buffer(chunk));
    }

    public void end(String chunk, String enc) {
        end(new Buffer(chunk, enc));
    }

    public void end(Buffer chunk) {
        checkWritten();
        if (!chunked && !contentLengthSet()) {
            headers().set("Content-Length", String.valueOf(chunk.length()));
        }
        byteBuf.writeBytes(chunk.getByteBuf());
        written = true;
    }

    public byte[] body() {
        byte[] body = new byte[byteBuf.writerIndex()];
        new ReadOnlyByteBuf(byteBuf).readBytes(body);
        return body;
    }

    public void end() {
        written = true;
    }

    public boolean written() {
        return written;
    }

    public MockHttpClientRequest setTimeout(long timeoutMs) {
        this.timeoutMs = timeoutMs;
        return this;
    }

    public long timeout() {
        return this.timeoutMs;
    }

    private void checkWritten() {
        if (written) {
            throw new IllegalStateException("Response has already been written");
        }
    }

    private boolean contentLengthSet() {
        if (headers == null) {
            return false;
        }
        return headers.contains(HttpHeaders.Names.CONTENT_LENGTH);
    }

    public int writeQueueMaxSize() {
        return this.writeQueueMaxSize;
    }

    public MockHttpClientRequest setWriteQueueMaxSize(int maxSize) {
        this.writeQueueMaxSize = maxSize;
        return this;
    }

    public boolean writeQueueFull() {
        return byteBuf.writerIndex() >= this.writeQueueMaxSize;
    }

    public MockHttpClientRequest drainHandler(Handler<Void> handler) {
        this.drainHandler = handler;
        return this;
    }

    public Handler<Void> drainHandler() {
        return this.drainHandler;
    }

    public MockHttpClientRequest exceptionHandler(Handler<Throwable> handler) {
        this.exceptionHandler = handler;
        return this;
    }

    public Handler<Throwable> exceptionHandler() {
        return this.exceptionHandler;
    }
}
