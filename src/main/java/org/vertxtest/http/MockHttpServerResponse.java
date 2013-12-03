package org.vertxtest.http;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ReadOnlyByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpResponseStatus;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.vertx.java.core.AsyncResult;
import org.vertx.java.core.Handler;
import org.vertx.java.core.MultiMap;
import org.vertx.java.core.buffer.Buffer;
import org.vertx.java.core.http.CaseInsensitiveMultiMap;
import org.vertx.java.core.http.HttpServerResponse;

import java.util.Arrays;

/**
 * @author jamesdbloom
 */
public class MockHttpServerResponse implements HttpServerResponse {
    private int statusCode = HttpResponseStatus.OK.code();
    private String statusMessage = HttpResponseStatus.OK.reasonPhrase();
    private boolean chunked;
    private MultiMap headers = new CaseInsensitiveMultiMap();
    private MultiMap trailers = new CaseInsensitiveMultiMap();
    private Handler<Void> closeHandler;
    private ByteBuf byteBuf = Unpooled.buffer();
    private boolean written = false;
    private String sendFileFilename;
    private String sendFileNotFoundFile;
    private Handler<AsyncResult<Void>> resultHandler;
    private AsyncResult<Void> sendFileResult;
    private boolean closed = false;
    private int writeQueueMaxSize = Integer.MAX_VALUE;
    private Handler<Void> drainHandler;
    private Handler<Throwable> exceptionHandler;

    @Override
    public int getStatusCode() {
        return statusCode;
    }

    @Override
    public MockHttpServerResponse setStatusCode(int statusCode) {
        this.statusCode = statusCode;
        return this;
    }

    @Override
    public String getStatusMessage() {
        return statusMessage;
    }

    @Override
    public MockHttpServerResponse setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
        return this;
    }

    @Override
    public boolean isChunked() {
        return chunked;
    }

    @Override
    public MockHttpServerResponse setChunked(boolean chunked) {
        this.chunked = chunked;
        return this;
    }

    @Override
    public MultiMap headers() {
        return headers;
    }

    public MockHttpServerResponse withHeaders(MultiMap headers) {
        this.headers = headers;
        return this;
    }

    @Override
    public MockHttpServerResponse putHeader(String name, String value) {
        headers.add(name, value);
        return this;
    }

    @Override
    public MockHttpServerResponse putHeader(String name, Iterable<String> values) {
        headers.add(name, values);
        return this;
    }

    public MockHttpServerResponse putHeader(String name, String... values) {
        putHeader(name, Arrays.asList(values));
        return this;
    }

    @Override
    public MultiMap trailers() {
        return trailers;
    }

    public MockHttpServerResponse withTrailers(MultiMap trailers) {
        this.trailers = trailers;
        return this;
    }

    @Override
    public MockHttpServerResponse putTrailer(String name, String value) {
        trailers.add(name, value);
        return this;
    }

    @Override
    public MockHttpServerResponse putTrailer(String name, Iterable<String> values) {
        trailers.add(name, values);
        return this;
    }

    public MockHttpServerResponse putTrailer(String name, String... values) {
        putTrailer(name, Arrays.asList(values));
        return this;
    }

    @Override
    public MockHttpServerResponse closeHandler(Handler<Void> handler) {
        this.closeHandler = handler;
        return this;
    }

    public Handler<Void> closeHandler() {
        return this.closeHandler;
    }

    @Override
    public MockHttpServerResponse write(String chunk, String enc) {
        write(new Buffer(chunk, enc));
        return this;
    }

    @Override
    public MockHttpServerResponse write(String chunk) {
        write(new Buffer(chunk));
        return this;
    }

    @Override
    public MockHttpServerResponse write(Buffer chunk) {
        byteBuf.writeBytes(chunk.getByteBuf());
        return this;
    }

    @Override
    public void end(String chunk) {
        end(new Buffer(chunk));
    }

    @Override
    public void end(String chunk, String enc) {
        end(new Buffer(chunk, enc));
    }

    @Override
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

    public Document html() {
        return Jsoup.parse(new String(body()));
    }

    @Override
    public void end() {
        written = true;
    }

    public boolean written() {
        return written;
    }

    @Override
    public MockHttpServerResponse sendFile(String filename) {
        return sendFile(filename, (String) null);
    }

    @Override
    public MockHttpServerResponse sendFile(String filename, String notFoundResource) {
        return sendFile(filename, notFoundResource, null);
    }

    @Override
    public MockHttpServerResponse sendFile(String filename, Handler<AsyncResult<Void>> resultHandler) {
        return sendFile(filename, null, resultHandler);
    }

    @Override
    public MockHttpServerResponse sendFile(String filename, String notFoundFile, Handler<AsyncResult<Void>> resultHandler) {
        this.sendFileFilename = filename;
        this.sendFileNotFoundFile = notFoundFile;
        this.resultHandler = resultHandler;
        if (this.resultHandler != null) {
            this.resultHandler.handle(this.sendFileResult);
        }
        return this;
    }

    public MockHttpServerResponse withSendFileResult(AsyncResult<Void> sendFileResult) {
        this.sendFileResult = sendFileResult;
        return this;
    }

    public Handler<AsyncResult<Void>> resultHandler() {
        return resultHandler;
    }

    public String sendFileFilename() {
        return sendFileFilename;
    }

    public String sendFileNotFoundFile() {
        return sendFileNotFoundFile;
    }

    @Override
    public void close() {
        closed = true;
        if (closeHandler != null) {
            closeHandler.handle(null);
        }
    }

    public boolean closed() {
        return closed;
    }

    public int writeQueueMaxSize() {
        return this.writeQueueMaxSize;
    }

    @Override
    public MockHttpServerResponse setWriteQueueMaxSize(int maxSize) {
        this.writeQueueMaxSize = maxSize;
        return this;
    }

    @Override
    public boolean writeQueueFull() {
        return byteBuf.writerIndex() >= this.writeQueueMaxSize;
    }

    @Override
    public MockHttpServerResponse drainHandler(Handler<Void> handler) {
        this.drainHandler = handler;
        return this;
    }

    public Handler<Void> drainHandler() {
        return this.drainHandler;
    }

    @Override
    public MockHttpServerResponse exceptionHandler(Handler<Throwable> handler) {
        this.exceptionHandler = handler;
        return this;
    }

    public Handler<Throwable> exceptionHandler() {
        return this.exceptionHandler;
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
}