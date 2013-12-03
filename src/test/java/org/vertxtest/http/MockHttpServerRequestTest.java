package org.vertxtest.http;

import org.junit.Test;
import org.vertx.java.core.Handler;
import org.vertx.java.core.buffer.Buffer;
import org.vertx.java.core.http.CaseInsensitiveMultiMap;
import org.vertx.java.core.http.HttpServerFileUpload;
import org.vertx.java.core.http.HttpVersion;
import org.vertx.java.core.net.NetSocket;

import javax.net.ssl.SSLPeerUnverifiedException;
import javax.security.cert.X509Certificate;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.URISyntaxException;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * @author jamesdbloom
 */
@SuppressWarnings("unchecked")
public class MockHttpServerRequestTest {

    @Test
    public void shouldReturnCorrectVersion() {
        // given
        MockHttpServerRequest mockHttpServerRequest = new MockHttpServerRequest();
        HttpVersion version = HttpVersion.HTTP_1_1;

        // then
        // - default is 1.1
        assertEquals(mockHttpServerRequest.version(), HttpVersion.HTTP_1_1);
        // - updated version is returned
        assertEquals(mockHttpServerRequest.withVersion(HttpVersion.HTTP_1_0).version(), HttpVersion.HTTP_1_0);
    }

    @Test
    public void shouldReturnCorrectMethod() {
        // given
        MockHttpServerRequest mockHttpServerRequest = new MockHttpServerRequest();

        // then
        // - default is GET
        assertEquals(mockHttpServerRequest.method(), "GET");
        // - updated method is returned
        assertEquals(mockHttpServerRequest.withMethod("some_method").method(), "some_method");
    }

    @Test
    public void shouldReturnCorrectUri() {
        // given
        MockHttpServerRequest mockHttpServerRequest = new MockHttpServerRequest();

        // then
        // - default is empty
        assertEquals(mockHttpServerRequest.uri(), "");
        // - updated uri is returned
        assertEquals(mockHttpServerRequest.withUri("some_uri").uri(), "some_uri");
    }

    @Test
    public void shouldReturnCorrectPath() {
        // given
        MockHttpServerRequest mockHttpServerRequest = new MockHttpServerRequest();

        // then
        // - default is empty
        assertEquals(mockHttpServerRequest.path(), "");
        // - updated path is returned
        assertEquals(mockHttpServerRequest.withPath("some_path").path(), "some_path");
    }

    @Test
    public void shouldReturnCorrectQuery() {
        // given
        MockHttpServerRequest mockHttpServerRequest = new MockHttpServerRequest();

        // then
        // - default is empty
        assertEquals(mockHttpServerRequest.query(), "");
        // - updated query is returned
        assertEquals(mockHttpServerRequest.withQuery("some_query").query(), "some_query");
    }

    @Test
    public void shouldReturnCorrectResponse() {
        // given
        MockHttpServerRequest mockHttpServerRequest = new MockHttpServerRequest();
        MockHttpServerResponse mockHttpServerResponse = new MockHttpServerResponse();

        // then
        // - default is instance of MockHttpServerResponse
        assertEquals(mockHttpServerRequest.response().getClass(), MockHttpServerResponse.class);
        // - updated response is returned
        assertSame(mockHttpServerRequest.withResponse(mockHttpServerResponse).response(), mockHttpServerResponse);
    }

    @Test
    public void shouldReturnCorrectHeaders() {
        // given
        MockHttpServerRequest mockHttpServerRequest = new MockHttpServerRequest();
        CaseInsensitiveMultiMap caseInsensitiveMultiMap = new CaseInsensitiveMultiMap();

        // then
        // - default is empty CaseInsensitiveMultiMap
        assertEquals(mockHttpServerRequest.headers().getClass(), CaseInsensitiveMultiMap.class);
        assertEquals(mockHttpServerRequest.headers().size(), 0);
        // - updated headers is returned
        assertSame(mockHttpServerRequest.withHeaders(caseInsensitiveMultiMap).headers(), caseInsensitiveMultiMap);
    }

    @Test
    public void shouldReturnCorrectParams() {
        // given
        MockHttpServerRequest mockHttpServerRequest = new MockHttpServerRequest();
        CaseInsensitiveMultiMap caseInsensitiveMultiMap = new CaseInsensitiveMultiMap();

        // then
        // - default is empty CaseInsensitiveMultiMap
        assertEquals(mockHttpServerRequest.params().getClass(), CaseInsensitiveMultiMap.class);
        assertEquals(mockHttpServerRequest.params().size(), 0);
        // - updated parameters is returned
        assertSame(mockHttpServerRequest.withParams(caseInsensitiveMultiMap).params(), caseInsensitiveMultiMap);
    }

    @Test
    public void shouldReturnCorrectRemoteAddress() {
        // given
        MockHttpServerRequest mockHttpServerRequest = new MockHttpServerRequest();
        InetSocketAddress inetSocketAddress = new InetSocketAddress(0);

        // then
        // - default is null
        assertNull(mockHttpServerRequest.remoteAddress());
        // - updated remote address is returned
        assertSame(mockHttpServerRequest.withRemoteAddress(inetSocketAddress).remoteAddress(), inetSocketAddress);
    }

    @Test
    public void shouldReturnCorrectLocalAddress() {
        // given
        MockHttpServerRequest mockHttpServerRequest = new MockHttpServerRequest();
        InetSocketAddress inetSocketAddress = new InetSocketAddress(0);

        // then
        // - default is null
        assertNull(mockHttpServerRequest.localAddress());
        // - updated local address is returned
        assertSame(mockHttpServerRequest.withLocalAddress(inetSocketAddress).localAddress(), inetSocketAddress);
    }

    @Test
    public void shouldReturnCorrectPeerCertificateChain() throws SSLPeerUnverifiedException {
        // given
        MockHttpServerRequest mockHttpServerRequest = new MockHttpServerRequest();
        X509Certificate[] x509Certificates = new X509Certificate[0];

        // then
        // - default is null
        assertNull(mockHttpServerRequest.peerCertificateChain());
        // - updated peer certificate chain is returned
        assertSame(mockHttpServerRequest.withPeerCertificateChain(x509Certificates).peerCertificateChain(), x509Certificates);
    }

    @Test
    public void shouldReturnCorrectAbsoluteURI() throws URISyntaxException {
        // given
        MockHttpServerRequest mockHttpServerRequest = new MockHttpServerRequest();
        URI uri = new URI("http://blog.jamesdbloom.com/");

        // then
        // - default is null
        assertNull(mockHttpServerRequest.absoluteURI());
        // - updated absolute uri is returned
        assertSame(mockHttpServerRequest.withAbsoluteURI(uri).absoluteURI(), uri);
    }

    @Test
    public void shouldReturnCorrectBody() throws URISyntaxException {
        // given
        MockHttpServerRequest mockHttpServerRequest = new MockHttpServerRequest();
        byte[] body = "somebody".getBytes();

        // then
        // - default is empty byte[]
        assertEquals(mockHttpServerRequest.body().getClass(), byte[].class);
        assertEquals(mockHttpServerRequest.body().length, 0);
        // - updated body is returned
        assertEquals(new String(mockHttpServerRequest.withBody(body).body()), new String(body));
    }

    @Test
    public void shouldCallBodyHandlerAndDataHandlerWhenBodyUpdated() {
        // given
        MockHttpServerRequest mockHttpServerRequest =
                new MockHttpServerRequest()
                        .bodyHandler(mock(Handler.class))
                        .dataHandler(mock(Handler.class));
        byte[] body = "somebody".getBytes();

        // when
        mockHttpServerRequest.withBody(body);

        // then
        // - body handler is called
        verify(mockHttpServerRequest.bodyHandler()).handle(new Buffer().appendBytes(body));
        // - data handler is called
        verify(mockHttpServerRequest.dataHandler()).handle(new Buffer().appendBytes(body));
    }

    @Test
    public void shouldReturnCorrectBodyHandler() throws URISyntaxException {
        // given
        MockHttpServerRequest mockHttpServerRequest = new MockHttpServerRequest();
        Handler<Buffer> bodyHandler = mock(Handler.class);

        // then
        // - default is null
        assertNull(mockHttpServerRequest.bodyHandler());
        // - updated body handler is returned
        assertEquals(mockHttpServerRequest.bodyHandler(bodyHandler).bodyHandler(), bodyHandler);
    }

    @Test
    public void shouldReturnCorrectNetSocket() {
        // given
        MockHttpServerRequest mockHttpServerRequest = new MockHttpServerRequest();
        NetSocket netSocket = mock(NetSocket.class);

        // then
        // - default is null
        assertNull(mockHttpServerRequest.netSocket());
        // - updated local address is returned
        assertSame(mockHttpServerRequest.withNetSocket(netSocket).netSocket(), netSocket);
    }

    @Test
    public void shouldReturnCorrectExpectMultiPart() {
        // given
        MockHttpServerRequest mockHttpServerRequest = new MockHttpServerRequest();

        // then
        // - default is false
        assertFalse(mockHttpServerRequest.isExpectMultiPart());
        // - updated expect multi-part is returned
        assertEquals(mockHttpServerRequest.expectMultiPart(true).isExpectMultiPart(), true);
    }

    @Test
    public void shouldReturnCorrectUploadHandler() throws URISyntaxException {
        // given
        MockHttpServerRequest mockHttpServerRequest = new MockHttpServerRequest();
        Handler<HttpServerFileUpload> uploadHandler = mock(Handler.class);

        // then
        // - default is null
        assertNull(mockHttpServerRequest.uploadHandler());
        // - updated body handler is returned
        assertEquals(mockHttpServerRequest.uploadHandler(uploadHandler).uploadHandler(), uploadHandler);
    }

    @Test
    public void shouldReturnCorrectFormAttributes() {
        // given
        MockHttpServerRequest mockHttpServerRequest = new MockHttpServerRequest();
        CaseInsensitiveMultiMap caseInsensitiveMultiMap = new CaseInsensitiveMultiMap();

        // then
        // - default is empty CaseInsensitiveMultiMap
        assertEquals(mockHttpServerRequest.formAttributes().getClass(), CaseInsensitiveMultiMap.class);
        assertEquals(mockHttpServerRequest.formAttributes().size(), 0);
        // - updated form attributes is returned
        assertSame(mockHttpServerRequest.withFormAttributes(caseInsensitiveMultiMap).formAttributes(), caseInsensitiveMultiMap);
    }

    @Test
    public void shouldReturnCorrectEndHandler() throws URISyntaxException {
        // given
        MockHttpServerRequest mockHttpServerRequest = new MockHttpServerRequest();
        Handler<Void> endHandler = mock(Handler.class);

        // then
        // - default is null
        assertNull(mockHttpServerRequest.endHandler());
        // - updated end handler is returned
        assertEquals(mockHttpServerRequest.endHandler(endHandler).endHandler(), endHandler);
    }

    @Test
    public void shouldReturnCorrectDataHandler() throws URISyntaxException {
        // given
        MockHttpServerRequest mockHttpServerRequest = new MockHttpServerRequest();
        Handler<Buffer> dataHandler = mock(Handler.class);

        // then
        // - default is null
        assertNull(mockHttpServerRequest.dataHandler());
        // - updated data handler is returned
        assertEquals(mockHttpServerRequest.dataHandler(dataHandler).dataHandler(), dataHandler);
    }

    @Test
    public void shouldReturnCorrectPausedState() {
        // given
        MockHttpServerRequest mockHttpServerRequest = new MockHttpServerRequest();

        // then
        // - default is false
        assertFalse(mockHttpServerRequest.paused());
        // - updated paused is returned
        assertEquals(mockHttpServerRequest.pause().paused(), true);
        assertEquals(mockHttpServerRequest.resume().paused(), false);
    }

    @Test
    public void shouldReturnCorrectExceptionHandler() throws URISyntaxException {
        // given
        MockHttpServerRequest mockHttpServerRequest = new MockHttpServerRequest();
        Handler<Throwable> exceptionHandler = mock(Handler.class);

        // then
        // - default is null
        assertNull(mockHttpServerRequest.exceptionHandler());
        // - updated exception handler is returned
        assertEquals(mockHttpServerRequest.exceptionHandler(exceptionHandler).exceptionHandler(), exceptionHandler);
    }
}
