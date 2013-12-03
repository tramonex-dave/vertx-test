package org.vertxtest.perform;

import org.vertx.java.core.Context;
import org.vertx.java.core.Handler;
import org.vertx.java.core.Vertx;
import org.vertx.java.core.datagram.DatagramSocket;
import org.vertx.java.core.datagram.InternetProtocolFamily;
import org.vertx.java.core.dns.DnsClient;
import org.vertx.java.core.eventbus.EventBus;
import org.vertx.java.core.file.FileSystem;
import org.vertx.java.core.http.HttpClient;
import org.vertx.java.core.http.HttpServer;
import org.vertx.java.core.net.NetClient;
import org.vertx.java.core.net.NetServer;
import org.vertx.java.core.shareddata.SharedData;
import org.vertx.java.core.sockjs.SockJSServer;

import java.net.InetSocketAddress;

import static org.mockito.Mockito.mock;

/**
 * @author jamesdbloom
 */
public class MockVertx implements Vertx {

    public static final MockHttpServer MOCK_HTTP_SERVER = new MockHttpServer();

    @Override
    public NetServer createNetServer() {
        throw new UnsupportedOperationException("method not implemented yet");
    }

    @Override
    public NetClient createNetClient() {
        throw new UnsupportedOperationException("method not implemented yet");
    }

    @Override
    public HttpServer createHttpServer() {
        return MOCK_HTTP_SERVER;
    }

    public MockHttpServer mockHttpServer() {
        return MOCK_HTTP_SERVER;
    }

    @Override
    public HttpClient createHttpClient() {
        throw new UnsupportedOperationException("method not implemented yet");
    }

    @Override
    public DatagramSocket createDatagramSocket(InternetProtocolFamily family) {
        throw new UnsupportedOperationException("method not implemented yet");
    }

    @Override
    public SockJSServer createSockJSServer(HttpServer httpServer) {
        throw new UnsupportedOperationException("method not implemented yet");
    }

    @Override
    public FileSystem fileSystem() {
        throw new UnsupportedOperationException("method not implemented yet");
    }

    @Override
    public EventBus eventBus() {
        throw new UnsupportedOperationException("method not implemented yet");
    }

    @Override
    public DnsClient createDnsClient(InetSocketAddress... dnsServers) {
        throw new UnsupportedOperationException("method not implemented yet");
    }

    @Override
    public SharedData sharedData() {
        throw new UnsupportedOperationException("method not implemented yet");
    }

    @Override
    public long setTimer(long delay, Handler<Long> handler) {
        throw new UnsupportedOperationException("method not implemented yet");
    }

    @Override
    public long setPeriodic(long delay, Handler<Long> handler) {
        throw new UnsupportedOperationException("method not implemented yet");
    }

    @Override
    public boolean cancelTimer(long id) {
        throw new UnsupportedOperationException("method not implemented yet");
    }

    @Override
    public Context currentContext() {
        throw new UnsupportedOperationException("method not implemented yet");
    }

    @Override
    public void runOnContext(Handler<Void> action) {
        throw new UnsupportedOperationException("method not implemented yet");
    }

    @Override
    public boolean isEventLoop() {
        throw new UnsupportedOperationException("method not implemented yet");
    }

    @Override
    public boolean isWorker() {
        throw new UnsupportedOperationException("method not implemented yet");
    }

    @Override
    public void stop() {
        throw new UnsupportedOperationException("method not implemented yet");
    }
}
