package org.vertxtest.http;

import org.vertx.java.core.Handler;
import org.vertx.java.core.MultiMap;
import org.vertx.java.core.buffer.Buffer;
import org.vertx.java.core.http.*;
import org.vertx.java.core.net.NetSocket;

import javax.net.ssl.SSLPeerUnverifiedException;
import javax.security.cert.X509Certificate;
import java.net.InetSocketAddress;
import java.net.URI;
import java.util.Arrays;

/**
 * @author jamesdbloom
 */
public class MockHttpServerRequest implements HttpServerRequest {
    private HttpVersion version = HttpVersion.HTTP_1_1;
    private String method = "GET";
    private String uri = "";
    private String path = "";
    private String query = "";
    private HttpServerResponse response = new MockHttpServerResponse();
    private MultiMap headers = new CaseInsensitiveMultiMap();
    private MultiMap params = new CaseInsensitiveMultiMap();
    private InetSocketAddress remoteAddress;
    private InetSocketAddress localAddress;
    private X509Certificate[] peerCertificateChain;
    private URI absoluteURI;
    private Buffer body = new Buffer();
    private Handler<Buffer> bodyHandler;
    private NetSocket netSocket;
    private boolean expectMultiPart;
    private Handler<HttpServerFileUpload> uploadHandler;
    private MultiMap formAttributes = new CaseInsensitiveMultiMap();
    private Handler<Void> endHandler;
    private Handler<Buffer> dataHandler;
    private boolean pause;
    private Handler<Throwable> exceptionHandler;

    @Override
    public HttpVersion version() {
        return version;
    }

    public MockHttpServerRequest withVersion(HttpVersion version) {
        this.version = version;
        return this;
    }

    @Override
    public String method() {
        return this.method;
    }

    public MockHttpServerRequest withMethod(String method) {
        this.method = method;
        return this;
    }

    @Override
    public String uri() {
        return this.uri;
    }

    public MockHttpServerRequest withUri(String uri) {
        this.uri = uri;
        return this;
    }

    @Override
    public String path() {
        return this.path;
    }

    public MockHttpServerRequest withPath(String path) {
        this.path = path;
        return this;
    }

    @Override
    public String query() {
        return this.query;
    }

    public MockHttpServerRequest withQuery(String query) {
        this.query = query;
        return this;
    }

    @Override
    public HttpServerResponse response() {
        return this.response;
    }

    public MockHttpServerRequest withResponse(HttpServerResponse response) {
        this.response = response;
        return this;
    }

    @Override
    public MultiMap headers() {
        return this.headers;
    }

    public MockHttpServerRequest withHeaders(MultiMap headers) {
        this.headers = headers;
        return this;
    }

    @Override
    public MultiMap params() {
        return this.params;
    }

    public MockHttpServerRequest withParams(MultiMap params) {
        this.params = params;
        return this;
    }

    @Override
    public InetSocketAddress remoteAddress() {
        return this.remoteAddress;
    }

    public MockHttpServerRequest withRemoteAddress(InetSocketAddress remoteAddress) {
        this.remoteAddress = remoteAddress;
        return this;
    }

    @Override
    public InetSocketAddress localAddress() {
        return this.localAddress;
    }

    public MockHttpServerRequest withLocalAddress(InetSocketAddress localAddress) {
        this.localAddress = localAddress;
        return this;
    }

    @Override
    public X509Certificate[] peerCertificateChain() throws SSLPeerUnverifiedException {
        return this.peerCertificateChain;
    }

    public MockHttpServerRequest withPeerCertificateChain(X509Certificate[] peerCertificateChain) {
        this.peerCertificateChain = peerCertificateChain;
        return this;
    }

    @Override
    public URI absoluteURI() {
        return this.absoluteURI;
    }

    public MockHttpServerRequest withAbsoluteURI(URI absoluteURI) {
        this.absoluteURI = absoluteURI;
        return this;
    }

    public MockHttpServerRequest withBody(byte[] body) {
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
    public MockHttpServerRequest bodyHandler(Handler<Buffer> bodyHandler) {
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

    public MockHttpServerRequest withNetSocket(NetSocket netSocket) {
        this.netSocket = netSocket;
        return this;
    }

    @Override
    public MockHttpServerRequest expectMultiPart(boolean expect) {
        this.expectMultiPart = expect;
        return this;
    }

    public boolean isExpectMultiPart() {
        return this.expectMultiPart;
    }

    @Override
    public MockHttpServerRequest uploadHandler(Handler<HttpServerFileUpload> uploadHandler) {
        this.uploadHandler = uploadHandler;
        return this;
    }

    public Handler<HttpServerFileUpload> uploadHandler() {
        return this.uploadHandler;
    }

    @Override
    public MultiMap formAttributes() {
        return this.formAttributes;
    }

    public MockHttpServerRequest withFormAttributes(MultiMap formAttributes) {
        this.formAttributes = formAttributes;
        return this;
    }

    @Override
    public MockHttpServerRequest endHandler(Handler<Void> endHandler) {
        this.endHandler = endHandler;
        this.endHandler.handle(null);
        return this;
    }

    public Handler<Void> endHandler() {
        return this.endHandler;
    }

    @Override
    public MockHttpServerRequest dataHandler(Handler<Buffer> handler) {
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
    public MockHttpServerRequest pause() {
        this.pause = true;
        return this;
    }

    @Override
    public MockHttpServerRequest resume() {
        this.pause = false;
        return this;
    }

    public boolean paused() {
        return this.pause;
    }

    @Override
    public MockHttpServerRequest exceptionHandler(Handler<Throwable> handler) {
        this.exceptionHandler = handler;
        return this;
    }

    public Handler<Throwable> exceptionHandler() {
        return this.exceptionHandler;
    }
}
