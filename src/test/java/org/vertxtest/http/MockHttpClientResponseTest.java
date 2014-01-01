package org.vertxtest.http;

import org.junit.Test;
import org.vertx.java.core.Handler;
import org.vertx.java.core.buffer.Buffer;
import org.vertx.java.core.http.CaseInsensitiveMultiMap;
import org.vertx.java.core.net.NetSocket;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * @author jamesdbloom
 */
public class MockHttpClientResponseTest {
    @Test
    public void shouldReturnCorrectStatusCode() {
        // given
        MockHttpClientResponse mockHttpClientResponse = new MockHttpClientResponse();

        // then
        // - default is 200
        assertEquals(mockHttpClientResponse.statusCode(), 200);
        // - updated status code is returned
        assertEquals(mockHttpClientResponse.setStatusCode(404).statusCode(), 404);
    }

    @Test
    public void shouldReturnCorrectStatusMessage() {
        // given
        MockHttpClientResponse mockHttpClientResponse = new MockHttpClientResponse();

        // then
        // - default is OK
        assertEquals(mockHttpClientResponse.statusMessage(), "OK");
        // - updated status message is returned
        assertEquals(mockHttpClientResponse.setStatusMessage("Not Found").statusMessage(), "Not Found");
    }

    @Test
    public void shouldReturnCorrectHeaders() {
        // given
        MockHttpClientResponse mockHttpClientResponse = new MockHttpClientResponse();
        CaseInsensitiveMultiMap caseInsensitiveMultiMap = new CaseInsensitiveMultiMap();

        // then
        // - default is empty CaseInsensitiveMultiMap
        assertEquals(mockHttpClientResponse.headers().getClass(), CaseInsensitiveMultiMap.class);
        assertEquals(mockHttpClientResponse.headers().size(), 0);
        // - updated headers is returned
        assertSame(mockHttpClientResponse.withHeaders(caseInsensitiveMultiMap).headers(), caseInsensitiveMultiMap);
    }

    @Test
    public void shouldUpdateIndividualHeaders() {
        // given
        MockHttpClientResponse mockHttpClientResponse = new MockHttpClientResponse();

        // when
        mockHttpClientResponse.withHeader("header1", "value1");
        mockHttpClientResponse.withHeader("header2", Arrays.asList("value2_1", "value2_2"));
        mockHttpClientResponse.withHeader("header3", "value3_1", "value3_2");

        // then
        // - updated headers is returned
        assertEquals(mockHttpClientResponse.headers().get("header1"), "value1");
        assertEquals(mockHttpClientResponse.headers().getAll("header2"), Arrays.asList("value2_1", "value2_2"));
        assertEquals(mockHttpClientResponse.headers().getAll("header3"), Arrays.asList("value3_1", "value3_2"));
    }

    @Test
    public void shouldReturnCorrectTrailers() {
        // given
        MockHttpClientResponse mockHttpClientResponse = new MockHttpClientResponse();
        CaseInsensitiveMultiMap caseInsensitiveMultiMap = new CaseInsensitiveMultiMap();

        // then
        // - default is empty CaseInsensitiveMultiMap
        assertEquals(mockHttpClientResponse.trailers().getClass(), CaseInsensitiveMultiMap.class);
        assertEquals(mockHttpClientResponse.trailers().size(), 0);
        // - updated trailers is returned
        assertSame(mockHttpClientResponse.withTrailers(caseInsensitiveMultiMap).trailers(), caseInsensitiveMultiMap);
    }

    @Test
    public void shouldUpdateIndividualTrailers() {
        // given
        MockHttpClientResponse mockHttpClientResponse = new MockHttpClientResponse();

        // when
        mockHttpClientResponse.withTrailer("trailer1", "value1");
        mockHttpClientResponse.withTrailer("trailer2", Arrays.asList("value2_1", "value2_2"));
        mockHttpClientResponse.withTrailer("trailer3", "value3_1", "value3_2");

        // then
        // - updated trailers is returned
        assertEquals(mockHttpClientResponse.trailers().get("trailer1"), "value1");
        assertEquals(mockHttpClientResponse.trailers().getAll("trailer2"), Arrays.asList("value2_1", "value2_2"));
        assertEquals(mockHttpClientResponse.trailers().getAll("trailer3"), Arrays.asList("value3_1", "value3_2"));
    }


    @Test
    public void shouldReturnCorrectCookies() {
        // given
        MockHttpClientResponse mockHttpClientResponse = new MockHttpClientResponse();

        // then
        // - default is empty list
        assertEquals(mockHttpClientResponse.cookies().getClass(), ArrayList.class);
        assertEquals(mockHttpClientResponse.cookies().size(), 0);
        // - updated cookies is returned
        assertEquals(mockHttpClientResponse.withCookies(Arrays.asList("1", "2", "3")).cookies(), Arrays.asList("1", "2", "3"));
    }


    @Test
    public void shouldCallBodyHandlerAndDataHandlerWhenBodyUpdated() {
        // given
        MockHttpClientResponse mockHttpClientResponse =
                new MockHttpClientResponse()
                        .bodyHandler(mock(Handler.class))
                        .dataHandler(mock(Handler.class));
        byte[] body = "somebody".getBytes();

        // when
        mockHttpClientResponse.withBody(body);

        // then
        // - body handler is called
        verify(mockHttpClientResponse.bodyHandler()).handle(new Buffer().appendBytes(body));
        // - data handler is called
        verify(mockHttpClientResponse.dataHandler()).handle(new Buffer().appendBytes(body));
    }

    @Test
    public void shouldReturnCorrectBodyHandler() throws URISyntaxException {
        // given
        MockHttpClientResponse mockHttpClientResponse = new MockHttpClientResponse();
        Handler<Buffer> bodyHandler = mock(Handler.class);

        // then
        // - default is null
        assertNull(mockHttpClientResponse.bodyHandler());
        // - updated body handler is returned
        assertEquals(mockHttpClientResponse.bodyHandler(bodyHandler).bodyHandler(), bodyHandler);
    }

    @Test
    public void shouldReturnCorrectNetSocket() {
        // given
        MockHttpClientResponse mockHttpClientResponse = new MockHttpClientResponse();
        NetSocket netSocket = mock(NetSocket.class);

        // then
        // - default is null
        assertNull(mockHttpClientResponse.netSocket());
        // - updated local address is returned
        assertSame(mockHttpClientResponse.withNetSocket(netSocket).netSocket(), netSocket);
    }


    @Test
    public void shouldReturnCorrectEndHandler() throws URISyntaxException {
        // given
        MockHttpClientResponse mockHttpClientResponse = new MockHttpClientResponse();
        Handler<Void> endHandler = mock(Handler.class);

        // then
        // - default is null
        assertNull(mockHttpClientResponse.endHandler());
        // - updated end handler is returned
        assertEquals(mockHttpClientResponse.endHandler(endHandler).endHandler(), endHandler);
    }

    @Test
    public void shouldReturnCorrectDataHandler() throws URISyntaxException {
        // given
        MockHttpClientResponse mockHttpClientResponse = new MockHttpClientResponse();
        Handler<Buffer> dataHandler = mock(Handler.class);

        // then
        // - default is null
        assertNull(mockHttpClientResponse.dataHandler());
        // - updated data handler is returned
        assertEquals(mockHttpClientResponse.dataHandler(dataHandler).dataHandler(), dataHandler);
    }

    @Test
    public void shouldReturnCorrectPausedState() {
        // given
        MockHttpClientResponse mockHttpClientResponse = new MockHttpClientResponse();

        // then
        // - default is false
        assertFalse(mockHttpClientResponse.paused());
        // - updated paused is returned
        assertTrue(mockHttpClientResponse.pause().paused());
        assertFalse(mockHttpClientResponse.resume().paused());
    }

    @Test
    public void shouldReturnCorrectExceptionHandler() throws URISyntaxException {
        // given
        MockHttpClientResponse mockHttpClientResponse = new MockHttpClientResponse();
        Handler<Throwable> exceptionHandler = mock(Handler.class);

        // then
        // - default is null
        assertNull(mockHttpClientResponse.exceptionHandler());
        // - updated exception handler is returned
        assertEquals(mockHttpClientResponse.exceptionHandler(exceptionHandler).exceptionHandler(), exceptionHandler);
    }
}
