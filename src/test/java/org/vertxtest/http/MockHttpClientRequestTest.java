package org.vertxtest.http;

import org.junit.Test;
import org.vertx.java.core.Handler;
import org.vertx.java.core.buffer.Buffer;
import org.vertx.java.core.http.CaseInsensitiveMultiMap;

import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.util.Arrays;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

/**
 * @author jamesdbloom
 */
public class MockHttpClientRequestTest {

    public static final String UTF_8 = "UTF-8";

    @Test
    public void shouldReturnCorrectChunked() {
        // given
        MockHttpClientRequest mockHttpClientRequest = new MockHttpClientRequest();

        // then
        // - default is false
        assertFalse(mockHttpClientRequest.isChunked());
        // - updated paused is returned
        assertTrue(mockHttpClientRequest.setChunked(true).isChunked());
    }

    @Test
    public void shouldReturnCorrectHeaders() {
        // given
        MockHttpClientRequest mockHttpClientRequest = new MockHttpClientRequest();
        CaseInsensitiveMultiMap caseInsensitiveMultiMap = new CaseInsensitiveMultiMap();

        // then
        // - default is empty CaseInsensitiveMultiMap
        assertEquals(mockHttpClientRequest.headers().getClass(), CaseInsensitiveMultiMap.class);
        assertEquals(mockHttpClientRequest.headers().size(), 0);
        // - updated headers is returned
        assertSame(mockHttpClientRequest.withHeaders(caseInsensitiveMultiMap).headers(), caseInsensitiveMultiMap);
    }

    @Test
    public void shouldUpdateIndividualHeaders() {
        // given
        MockHttpClientRequest mockHttpClientRequest = new MockHttpClientRequest();

        // when
        mockHttpClientRequest.putHeader("header1", "value1");
        mockHttpClientRequest.putHeader("header2", Arrays.asList("value2_1", "value2_2"));
        mockHttpClientRequest.putHeader("header3", "value3_1", "value3_2");

        // then
        // - updated headers is returned
        assertEquals(mockHttpClientRequest.headers().get("header1"), "value1");
        assertEquals(mockHttpClientRequest.headers().getAll("header2"), Arrays.asList("value2_1", "value2_2"));
        assertEquals(mockHttpClientRequest.headers().getAll("header3"), Arrays.asList("value3_1", "value3_2"));
    }


    @Test
    public void shouldWriteToBody() throws UnsupportedEncodingException {
        // given
        MockHttpClientRequest mockHttpClientRequest = new MockHttpClientRequest();

        // then - default body is empty
        assertEquals(new String(mockHttpClientRequest.body(), UTF_8), "");

        // when
        mockHttpClientRequest.write("some_string");
        // then - body appended
        assertEquals(new String(mockHttpClientRequest.body(), UTF_8), "some_string");

        // when
        mockHttpClientRequest.write("_and_another_string", UTF_8);
        // then - body appended
        assertEquals(new String(mockHttpClientRequest.body(), UTF_8), "some_string_and_another_string");

        // when
        mockHttpClientRequest.write(new Buffer(0).appendString("_and_yet_another_string"));
        // then - body appended
        assertEquals(new String(mockHttpClientRequest.body(), UTF_8), "some_string_and_another_string_and_yet_another_string");
    }

    @Test
    public void shouldEndWritingToBody() throws UnsupportedEncodingException {
        // given
        MockHttpClientRequest mockHttpClientRequest = new MockHttpClientRequest();
        // when
        mockHttpClientRequest.end("some_string");
        // then
        // -- body updated
        assertEquals(new String(mockHttpClientRequest.body(), UTF_8), "some_string");
        // -- Content-Length header updated
        assertEquals(mockHttpClientRequest.headers().get("Content-Length"), String.valueOf("some_string".length()));
        // -- body marked as written
        assertEquals(mockHttpClientRequest.written(), true);
    }

    @Test
    public void shouldEndWritingToBodyWithSpecificCharacterSet() throws UnsupportedEncodingException {
        // given
        MockHttpClientRequest mockHttpClientRequest = new MockHttpClientRequest();
        // when
        mockHttpClientRequest.end("some_string", UTF_8);
        // then
        // -- body updated
        assertEquals(new String(mockHttpClientRequest.body(), UTF_8), "some_string");
        // -- Content-Length header updated
        assertEquals(mockHttpClientRequest.headers().get("Content-Length"), String.valueOf("some_string".length()));
        // -- body marked as written
        assertEquals(mockHttpClientRequest.written(), true);
    }

    @Test
    public void shouldEndWritingToBodyWithBuffer() throws UnsupportedEncodingException {
        // given
        MockHttpClientRequest mockHttpClientRequest = new MockHttpClientRequest();
        // when
        mockHttpClientRequest.end(new Buffer(0).appendString("some_string"));
        // then
        // -- body updated
        assertEquals(new String(mockHttpClientRequest.body(), UTF_8), "some_string");
        // -- Content-Length header updated
        assertEquals(mockHttpClientRequest.headers().get("Content-Length"), String.valueOf("some_string".length()));
        // -- body marked as written
        assertEquals(mockHttpClientRequest.written(), true);
    }

    @Test
    public void shouldMarkASWrittenIfEndCalled() throws UnsupportedEncodingException {
        // given
        MockHttpClientRequest mockHttpClientRequest = new MockHttpClientRequest();
        // when
        mockHttpClientRequest.end();
        // then - body marked as written
        assertEquals(mockHttpClientRequest.written(), true);
    }

    @Test(expected = IllegalStateException.class)
    public void shouldThrowExceptionIfEndCalledMoreThenOnce() throws UnsupportedEncodingException {
        // given
        MockHttpClientRequest mockHttpClientRequest = new MockHttpClientRequest();
        // when
        mockHttpClientRequest.end("some_string");
        // then - throw exception
        mockHttpClientRequest.end("some_string");
    }

    @Test
    public void shouldReturnCorrectContinueHandler() throws URISyntaxException {
        // given
        MockHttpClientRequest mockHttpClientRequest = new MockHttpClientRequest();
        Handler<Void> continueHandler = mock(Handler.class);

        // then
        // - default is null
        assertNull(mockHttpClientRequest.continueHandler());
        // - updated drain handler is returned
        assertEquals(mockHttpClientRequest.continueHandler(continueHandler).continueHandler(), continueHandler);
    }

    @Test
    public void shouldSupportSendHeadMethod() {
        // given
        MockHttpClientRequest mockHttpClientRequest = new MockHttpClientRequest();

        // then
        assertSame(mockHttpClientRequest.sendHead(), mockHttpClientRequest);
    }

    @Test
    public void shouldReturnCorrectTimeout() {
        // given
        MockHttpClientRequest mockHttpClientRequest = new MockHttpClientRequest();

        // then
        // - default is -1
        assertEquals(mockHttpClientRequest.timeout(), -1);
        // - updated timeout is returned
        assertEquals(mockHttpClientRequest.setTimeout(10).timeout(), 10);
    }

    @Test
    public void shouldReturnCorrectWriteQueueMaxSize() {
        // given
        MockHttpClientRequest mockHttpClientRequest = new MockHttpClientRequest();

        // then
        // - default is Integer.MAX_VALUE
        assertEquals(mockHttpClientRequest.writeQueueMaxSize(), Integer.MAX_VALUE);
        // - updated write queue max size is returned
        assertEquals(mockHttpClientRequest.setWriteQueueMaxSize(10).writeQueueMaxSize(), 10);
    }

    @Test
    public void shouldHaveFullWriteQueue() {
        // then
        // - a write queue should not be full
        assertFalse(new MockHttpClientRequest()
                .setWriteQueueMaxSize(5)
                .write("1234")
                .writeQueueFull());
        // - a write queue should be full
        assertTrue(new MockHttpClientRequest()
                .setWriteQueueMaxSize(5)
                .write("12345")
                .writeQueueFull());
        assertTrue(new MockHttpClientRequest()
                .setWriteQueueMaxSize(5)
                .write("123456")
                .writeQueueFull());
    }

    @Test
    public void shouldReturnCorrectDrainHandler() throws URISyntaxException {
        // given
        MockHttpClientRequest mockHttpClientRequest = new MockHttpClientRequest();
        Handler<Void> drainHandler = mock(Handler.class);

        // then
        // - default is null
        assertNull(mockHttpClientRequest.drainHandler());
        // - updated drain handler is returned
        assertEquals(mockHttpClientRequest.drainHandler(drainHandler).drainHandler(), drainHandler);
    }

    @Test
    public void shouldReturnCorrectExceptionHandler() throws URISyntaxException {
        // given
        MockHttpClientRequest mockHttpClientRequest = new MockHttpClientRequest();
        Handler<Throwable> exceptionHandler = mock(Handler.class);

        // then
        // - default is null
        assertNull(mockHttpClientRequest.exceptionHandler());
        // - updated exception handler is returned
        assertEquals(mockHttpClientRequest.exceptionHandler(exceptionHandler).exceptionHandler(), exceptionHandler);
    }
}
