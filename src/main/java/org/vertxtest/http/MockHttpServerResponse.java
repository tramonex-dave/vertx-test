package org.vertxtest.http;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpResponse;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;
import org.vertx.java.core.AsyncResult;
import org.vertx.java.core.Handler;
import org.vertx.java.core.MultiMap;
import org.vertx.java.core.buffer.Buffer;
import org.vertx.java.core.http.HttpServerResponse;

/**
 * @author jamesdbloom
 */
public class MockHttpServerResponse implements HttpServerResponse {
    private HttpResponse response;
    private HttpVersion version;
    private boolean keepAlive;
    private int statusCode = HttpResponseStatus.OK.code();
    private String statusMessage = HttpResponseStatus.OK.reasonPhrase();
    private boolean chunked;
    private MultiMap headers;
    private MultiMap trailers;
    private Handler<Void> closeHandler;
    private boolean closed = false;
    private Handler<Void> drainHandler;
    private Handler<Throwable> exceptionHandler;
    private int writeQueueMaxSize;
    private boolean writeQueueFull;

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

    @Override
    public MultiMap trailers() {
        return trailers;
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

    @Override
    public MockHttpServerResponse closeHandler(Handler<Void> handler) {
        this.closeHandler = handler;
        return this;
    }

    public Handler<Void> getCloseHandler() {
        return this.closeHandler;
    }

    @Override
    public MockHttpServerResponse write(Buffer chunk) {
        ByteBuf buf = chunk.getByteBuf();
        return write(buf, null);
    }

    @Override
    public MockHttpServerResponse write(String chunk, String enc) {
        return write(new Buffer(chunk, enc).getByteBuf(), null);
    }

    @Override
    public MockHttpServerResponse write(String chunk) {
        return write(new Buffer(chunk).getByteBuf(), null);
    }

    private MockHttpServerResponse write(ByteBuf chunk, final Handler<AsyncResult<Void>> doneHandler) {
//        checkWritten();
//        if (version != HttpVersion.HTTP_1_0 && !chunked && !contentLengthSet()) {
//            throw new IllegalStateException("You must set the Content-Length header to be the total size of the message "
//                    + "body BEFORE sending any data if you are not using HTTP chunked encoding.");
//        }
//
//        if (!headWritten) {
//            prepareHeaders();
//            channelFuture = conn.write(new AssembledHttpResponse(response, chunk));
//            headWritten = true;
//        }  else {
//            channelFuture = conn.write(new DefaultHttpContent(chunk));
//        }
//
//        conn.addFuture(doneHandler, channelFuture);
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
        if (!chunked && !contentLengthSet()) {
            headers().set("Content-Length", String.valueOf(chunk.length()));
        }
        ByteBuf buf = chunk.getByteBuf();
        end0(buf);
    }

    @Override
    public void end() {
        end0(Unpooled.EMPTY_BUFFER);
    }

    private void end0(ByteBuf data) {
//        checkWritten();
//
//        if (!headWritten) {
//            // if the head was not written yet we can write out everything in on go
//            // which is more cheap.
//            prepareHeaders();
//            FullHttpResponse resp;
//            if (trailing != null) {
//                resp = new AssembledFullHttpResponse(response, data, trailing.trailingHeaders());
//            }  else {
//                resp = new AssembledFullHttpResponse(response, data);
//            }
//            channelFuture = conn.write(resp);
//            headWritten = true;
//        } else {
//            if (!data.isReadable()) {
//                if (trailing == null) {
//                    channelFuture = conn.write(LastHttpContent.EMPTY_LAST_CONTENT);
//                } else {
//                    channelFuture = conn.write(trailing);
//                }
//            } else {
//                LastHttpContent content;
//                if (trailing != null) {
//                    content = new AssembledLastHttpContent(data, trailing.trailingHeaders());
//                } else {
//                    content = new DefaultLastHttpContent(data, false);
//                }
//                channelFuture = conn.write(content);
//            }
//        }
//
//        if (!keepAlive) {
//            closeConnAfterWrite();
//        }
//        written = true;
//        conn.responseComplete();
    }

    @Override
    public MockHttpServerResponse sendFile(String filename) {
        return sendFile(filename, (String) null);
    }

    @Override
    public MockHttpServerResponse sendFile(String filename, String notFoundResource) {
        doSendFile(filename, notFoundResource, null);
        return this;
    }

    @Override
    public HttpServerResponse sendFile(String filename, Handler<AsyncResult<Void>> resultHandler) {
        return sendFile(filename, null, resultHandler);
    }

    @Override
    public HttpServerResponse sendFile(String filename, String notFoundFile, Handler<AsyncResult<Void>> resultHandler) {
        doSendFile(filename, notFoundFile, resultHandler);
        return this;
    }

    private void doSendFile(String filename, String notFoundResource, final Handler<AsyncResult<Void>> resultHandler) {
//        if (headWritten) {
//            throw new IllegalStateException("Head already written");
//        }
//        checkWritten();
//        File file = new File(PathAdjuster.adjust(vertx, filename));
//        if (!file.exists()) {
//            if (notFoundResource != null) {
//                statusCode = HttpResponseStatus.NOT_FOUND.code();
//                sendFile(notFoundResource, (String)null, resultHandler);
//            } else {
//                sendNotFound();
//            }
//        } else {
//            if (!contentLengthSet()) {
//                putHeader("Content-Length", String.valueOf(file.length()));
//            }
//            if (!contentTypeSet()) {
//                int li = filename.lastIndexOf('.');
//                if (li != -1 && li != filename.length() - 1) {
//                    String ext = filename.substring(li + 1, filename.length());
//                    String contentType = MimeMapping.getMimeTypeForExtension(ext);
//                    if (contentType != null) {
//                        putHeader("Content-Type", contentType);
//                    }
//                }
//            }
//            prepareHeaders();
//            conn.queueForWrite(response);
//            conn.sendFile(file);
//
//            // write an empty last content to let the http encoder know the response is complete
//            channelFuture = conn.write(LastHttpContent.EMPTY_LAST_CONTENT);
//            headWritten = written = true;
//
//            if (resultHandler != null) {
//                channelFuture.addListener(new ChannelFutureListener() {
//                    public void operationComplete(ChannelFuture future) throws Exception {
//                        final AsyncResult<Void> res;
//                        if (future.isSuccess()) {
//                            res = new DefaultFutureResult<>((Void)null);
//                        } else {
//                            res = new DefaultFutureResult<>(future.cause());
//                        }
//                        vertx.runOnContext(new Handler<Void>() {
//                            @Override
//                            public void handle(Void v) {
//                                resultHandler.handle(res);
//                            }
//                        });
//                    }
//                });
//            }
//
//            if (!keepAlive) {
//                closeConnAfterWrite();
//            }
//            conn.responseComplete();
//        }
    }

    @Override
    public void close() {
        closed = true;
        if (closeHandler != null) {
            closeHandler.handle(null);
        }
    }

    public int getWriteQueueMaxSize() {
        return this.writeQueueMaxSize;
    }

    @Override
    public MockHttpServerResponse setWriteQueueMaxSize(int maxSize) {
        this.writeQueueMaxSize = maxSize;
        return this;
    }

    @Override
    public boolean writeQueueFull() {
        return this.writeQueueFull;
    }

    public MockHttpServerResponse withWriteQueueFull(boolean writeQueueFull) {
        this.writeQueueFull = writeQueueFull;
        return this;
    }

    @Override
    public MockHttpServerResponse drainHandler(Handler<Void> handler) {
        this.drainHandler = handler;
        return this;
    }

    public Handler<Void> getDrainHandler() {
        return this.drainHandler;
    }

    @Override
    public MockHttpServerResponse exceptionHandler(Handler<Throwable> handler) {
        this.exceptionHandler = handler;
        return this;
    }

    public Handler<Throwable> getExceptionHandler() {
        return this.exceptionHandler;
    }

    private boolean contentLengthSet() {
        if (headers == null) {
            return false;
        }
        return response.headers().contains(HttpHeaders.newEntity(HttpHeaders.Names.CONTENT_LENGTH));
    }

    private boolean contentTypeSet() {
        if (headers == null) {
            return false;
        }
        return response.headers().contains(HttpHeaders.newEntity(HttpHeaders.Names.CONTENT_TYPE));
    }
}