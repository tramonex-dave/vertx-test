package org.vertxtest.http;

import org.vertx.java.core.Handler;
import org.vertx.java.core.MultiMap;
import org.vertx.java.core.buffer.Buffer;
import org.vertx.java.core.http.HttpServerFileUpload;
import org.vertx.java.core.http.HttpServerRequest;
import org.vertx.java.core.http.HttpServerResponse;
import org.vertx.java.core.http.HttpVersion;
import org.vertx.java.core.net.NetSocket;

import javax.net.ssl.SSLPeerUnverifiedException;
import javax.security.cert.X509Certificate;
import java.net.InetSocketAddress;
import java.net.URI;

/**
 * @author jamesdbloom
 */
public class MockHttpServerRequest implements HttpServerRequest {
    @Override
    public HttpVersion version() {
        throw new UnsupportedOperationException("method not implemented yet");
    }

    @Override
    public String method() {
        throw new UnsupportedOperationException("method not implemented yet");
    }

    @Override
    public String uri() {
        throw new UnsupportedOperationException("method not implemented yet");
    }

    @Override
    public String path() {
        throw new UnsupportedOperationException("method not implemented yet");
    }

    @Override
    public String query() {
        throw new UnsupportedOperationException("method not implemented yet");
    }

    @Override
    public HttpServerResponse response() {
        throw new UnsupportedOperationException("method not implemented yet");
    }

    @Override
    public MultiMap headers() {
        throw new UnsupportedOperationException("method not implemented yet");
    }

    @Override
    public MultiMap params() {
        throw new UnsupportedOperationException("method not implemented yet");
    }

    @Override
    public InetSocketAddress remoteAddress() {
        throw new UnsupportedOperationException("method not implemented yet");
    }

    @Override
    public InetSocketAddress localAddress() {
        throw new UnsupportedOperationException("method not implemented yet");
    }

    @Override
    public X509Certificate[] peerCertificateChain() throws SSLPeerUnverifiedException {
        throw new UnsupportedOperationException("method not implemented yet");
    }

    @Override
    public URI absoluteURI() {
        throw new UnsupportedOperationException("method not implemented yet");
    }

    @Override
    public HttpServerRequest bodyHandler(Handler<Buffer> bodyHandler) {
        throw new UnsupportedOperationException("method not implemented yet");
    }

    @Override
    public NetSocket netSocket() {
        throw new UnsupportedOperationException("method not implemented yet");
    }

    @Override
    public HttpServerRequest expectMultiPart(boolean expect) {
        throw new UnsupportedOperationException("method not implemented yet");
    }

    @Override
    public HttpServerRequest uploadHandler(Handler<HttpServerFileUpload> uploadHandler) {
        throw new UnsupportedOperationException("method not implemented yet");
    }

    @Override
    public MultiMap formAttributes() {
        throw new UnsupportedOperationException("method not implemented yet");
    }

    @Override
    public HttpServerRequest endHandler(Handler<Void> endHandler) {
        throw new UnsupportedOperationException("method not implemented yet");
    }

    @Override
    public HttpServerRequest dataHandler(Handler<Buffer> handler) {
        throw new UnsupportedOperationException("method not implemented yet");
    }

    @Override
    public HttpServerRequest pause() {
        throw new UnsupportedOperationException("method not implemented yet");
    }

    @Override
    public HttpServerRequest resume() {
        throw new UnsupportedOperationException("method not implemented yet");
    }

    @Override
    public HttpServerRequest exceptionHandler(Handler<Throwable> handler) {
        throw new UnsupportedOperationException("method not implemented yet");
    }
}
