package org.vertxtest.perform;

import org.vertx.java.core.AsyncResult;
import org.vertx.java.core.Handler;
import org.vertx.java.core.http.HttpServer;
import org.vertx.java.core.http.HttpServerRequest;
import org.vertx.java.core.http.ServerWebSocket;

/**
 * @author jamesdbloom
 */
public class MockHttpServer implements HttpServer {
    private Handler<HttpServerRequest> serverRequestHandler;

    @Override
    public HttpServer requestHandler(Handler<HttpServerRequest> requestHandler) {
        this.serverRequestHandler = requestHandler;
        return this;
    }

     public Handler<HttpServerRequest> serverRequestHandler(){
         return this.serverRequestHandler;
     }

    @Override
    public Handler<HttpServerRequest> requestHandler() {
        throw new UnsupportedOperationException("method not implemented yet");
    }

    @Override
    public HttpServer websocketHandler(Handler<ServerWebSocket> wsHandler) {
        throw new UnsupportedOperationException("method not implemented yet");
    }

    @Override
    public Handler<ServerWebSocket> websocketHandler() {
        throw new UnsupportedOperationException("method not implemented yet");
    }

    @Override
    public HttpServer listen(int port) {
        throw new UnsupportedOperationException("method not implemented yet");
    }

    @Override
    public HttpServer listen(int port, Handler<AsyncResult<HttpServer>> listenHandler) {
        throw new UnsupportedOperationException("method not implemented yet");
    }

    @Override
    public HttpServer listen(int port, String host) {
        // THIS IS A NO-OP DELIBERATELY
        return this;
    }

    @Override
    public HttpServer listen(int port, String host, Handler<AsyncResult<HttpServer>> listenHandler) {
        throw new UnsupportedOperationException("method not implemented yet");
    }

    @Override
    public void close() {
        throw new UnsupportedOperationException("method not implemented yet");
    }

    @Override
    public void close(Handler<AsyncResult<Void>> doneHandler) {
        throw new UnsupportedOperationException("method not implemented yet");
    }

    @Override
    public HttpServer setCompressionSupported(boolean compressionSupported) {
        throw new UnsupportedOperationException("method not implemented yet");
    }

    @Override
    public boolean isCompressionSupported() {
        throw new UnsupportedOperationException("method not implemented yet");
    }

    @Override
    public HttpServer setMaxWebSocketFrameSize(int maxSize) {
        throw new UnsupportedOperationException("method not implemented yet");
    }

    @Override
    public int getMaxWebSocketFrameSize() {
        throw new UnsupportedOperationException("method not implemented yet");
    }

    @Override
    public HttpServer setAcceptBacklog(int backlog) {
        throw new UnsupportedOperationException("method not implemented yet");
    }

    @Override
    public int getAcceptBacklog() {
        throw new UnsupportedOperationException("method not implemented yet");
    }

    @Override
    public HttpServer setTCPNoDelay(boolean tcpNoDelay) {
        throw new UnsupportedOperationException("method not implemented yet");
    }

    @Override
    public HttpServer setTCPKeepAlive(boolean keepAlive) {
        throw new UnsupportedOperationException("method not implemented yet");
    }

    @Override
    public HttpServer setSoLinger(int linger) {
        throw new UnsupportedOperationException("method not implemented yet");
    }

    @Override
    public HttpServer setUsePooledBuffers(boolean pooledBuffers) {
        throw new UnsupportedOperationException("method not implemented yet");
    }

    @Override
    public boolean isTCPNoDelay() {
        throw new UnsupportedOperationException("method not implemented yet");
    }

    @Override
    public boolean isTCPKeepAlive() {
        throw new UnsupportedOperationException("method not implemented yet");
    }

    @Override
    public int getSoLinger() {
        throw new UnsupportedOperationException("method not implemented yet");
    }

    @Override
    public boolean isUsePooledBuffers() {
        throw new UnsupportedOperationException("method not implemented yet");
    }

    @Override
    public HttpServer setSendBufferSize(int size) {
        throw new UnsupportedOperationException("method not implemented yet");
    }

    @Override
    public HttpServer setReceiveBufferSize(int size) {
        throw new UnsupportedOperationException("method not implemented yet");
    }

    @Override
    public HttpServer setReuseAddress(boolean reuse) {
        throw new UnsupportedOperationException("method not implemented yet");
    }

    @Override
    public HttpServer setTrafficClass(int trafficClass) {
        throw new UnsupportedOperationException("method not implemented yet");
    }

    @Override
    public int getSendBufferSize() {
        throw new UnsupportedOperationException("method not implemented yet");
    }

    @Override
    public int getReceiveBufferSize() {
        throw new UnsupportedOperationException("method not implemented yet");
    }

    @Override
    public boolean isReuseAddress() {
        throw new UnsupportedOperationException("method not implemented yet");
    }

    @Override
    public int getTrafficClass() {
        throw new UnsupportedOperationException("method not implemented yet");
    }

    @Override
    public HttpServer setClientAuthRequired(boolean required) {
        throw new UnsupportedOperationException("method not implemented yet");
    }

    @Override
    public boolean isClientAuthRequired() {
        throw new UnsupportedOperationException("method not implemented yet");
    }

    @Override
    public HttpServer setSSL(boolean ssl) {
        throw new UnsupportedOperationException("method not implemented yet");
    }

    @Override
    public boolean isSSL() {
        throw new UnsupportedOperationException("method not implemented yet");
    }

    @Override
    public HttpServer setKeyStorePath(String path) {
        throw new UnsupportedOperationException("method not implemented yet");
    }

    @Override
    public String getKeyStorePath() {
        throw new UnsupportedOperationException("method not implemented yet");
    }

    @Override
    public HttpServer setKeyStorePassword(String pwd) {
        throw new UnsupportedOperationException("method not implemented yet");
    }

    @Override
    public String getKeyStorePassword() {
        throw new UnsupportedOperationException("method not implemented yet");
    }

    @Override
    public HttpServer setTrustStorePath(String path) {
        throw new UnsupportedOperationException("method not implemented yet");
    }

    @Override
    public String getTrustStorePath() {
        throw new UnsupportedOperationException("method not implemented yet");
    }

    @Override
    public HttpServer setTrustStorePassword(String pwd) {
        throw new UnsupportedOperationException("method not implemented yet");
    }

    @Override
    public String getTrustStorePassword() {
        throw new UnsupportedOperationException("method not implemented yet");
    }
}
