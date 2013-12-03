package org.vertxtest.http;

import org.jsoup.nodes.Document;
import org.junit.Test;
import org.vertx.java.core.AsyncResult;
import org.vertx.java.core.Handler;
import org.vertx.java.core.buffer.Buffer;
import org.vertx.java.core.http.CaseInsensitiveMultiMap;

import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.util.Arrays;

import static org.junit.Assert.*;
import static org.mockito.Matchers.same;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * @author jamesdbloom
 */
@SuppressWarnings("unchecked")
public class MockHttpServerResponseTest {

    public static final String UTF_8 = "UTF-8";

    @Test
    public void shouldReturnCorrectStatusCode() {
        // given
        MockHttpServerResponse mockHttpServerResponse = new MockHttpServerResponse();

        // then
        // - default is 200
        assertEquals(mockHttpServerResponse.getStatusCode(), 200);
        // - updated status code is returned
        assertEquals(mockHttpServerResponse.setStatusCode(404).getStatusCode(), 404);
    }

    @Test
    public void shouldReturnCorrectStatusMessage() {
        // given
        MockHttpServerResponse mockHttpServerResponse = new MockHttpServerResponse();

        // then
        // - default is OK
        assertEquals(mockHttpServerResponse.getStatusMessage(), "OK");
        // - updated status message is returned
        assertEquals(mockHttpServerResponse.setStatusMessage("Not Found").getStatusMessage(), "Not Found");
    }

    @Test
    public void shouldReturnCorrectChunked() {
        // given
        MockHttpServerResponse mockHttpServerResponse = new MockHttpServerResponse();

        // then
        // - default is false
        assertFalse(mockHttpServerResponse.isChunked());
        // - updated paused is returned
        assertTrue(mockHttpServerResponse.setChunked(true).isChunked());
    }

    @Test
    public void shouldReturnCorrectHeaders() {
        // given
        MockHttpServerResponse mockHttpServerResponse = new MockHttpServerResponse();
        CaseInsensitiveMultiMap caseInsensitiveMultiMap = new CaseInsensitiveMultiMap();

        // then
        // - default is empty CaseInsensitiveMultiMap
        assertEquals(mockHttpServerResponse.headers().getClass(), CaseInsensitiveMultiMap.class);
        assertEquals(mockHttpServerResponse.headers().size(), 0);
        // - updated headers is returned
        assertSame(mockHttpServerResponse.withHeaders(caseInsensitiveMultiMap).headers(), caseInsensitiveMultiMap);
    }

    @Test
    public void shouldUpdateIndividualHeaders() {
        // given
        MockHttpServerResponse mockHttpServerResponse = new MockHttpServerResponse();

        // when
        mockHttpServerResponse.putHeader("header1", "value1");
        mockHttpServerResponse.putHeader("header2", Arrays.asList("value2_1", "value2_2"));
        mockHttpServerResponse.putHeader("header3", "value3_1", "value3_2");

        // then
        // - updated headers is returned
        assertEquals(mockHttpServerResponse.headers().get("header1"), "value1");
        assertEquals(mockHttpServerResponse.headers().getAll("header2"), Arrays.asList("value2_1", "value2_2"));
        assertEquals(mockHttpServerResponse.headers().getAll("header3"), Arrays.asList("value3_1", "value3_2"));
    }

    @Test
    public void shouldReturnCorrectTrailers() {
        // given
        MockHttpServerResponse mockHttpServerResponse = new MockHttpServerResponse();
        CaseInsensitiveMultiMap caseInsensitiveMultiMap = new CaseInsensitiveMultiMap();

        // then
        // - default is empty CaseInsensitiveMultiMap
        assertEquals(mockHttpServerResponse.trailers().getClass(), CaseInsensitiveMultiMap.class);
        assertEquals(mockHttpServerResponse.trailers().size(), 0);
        // - updated trailers is returned
        assertSame(mockHttpServerResponse.withTrailers(caseInsensitiveMultiMap).trailers(), caseInsensitiveMultiMap);
    }

    @Test
    public void shouldUpdateIndividualTrailers() {
        // given
        MockHttpServerResponse mockHttpServerResponse = new MockHttpServerResponse();

        // when
        mockHttpServerResponse.putTrailer("trailer1", "value1");
        mockHttpServerResponse.putTrailer("trailer2", Arrays.asList("value2_1", "value2_2"));
        mockHttpServerResponse.putTrailer("trailer3", "value3_1", "value3_2");

        // then
        // - updated trailers is returned
        assertEquals(mockHttpServerResponse.trailers().get("trailer1"), "value1");
        assertEquals(mockHttpServerResponse.trailers().getAll("trailer2"), Arrays.asList("value2_1", "value2_2"));
        assertEquals(mockHttpServerResponse.trailers().getAll("trailer3"), Arrays.asList("value3_1", "value3_2"));
    }

    @Test
    public void shouldReturnCorrectCloseHandler() throws URISyntaxException {
        // given
        MockHttpServerResponse mockHttpServerResponse = new MockHttpServerResponse();
        Handler<Void> closeHandler = mock(Handler.class);

        // then
        // - default is null
        assertNull(mockHttpServerResponse.closeHandler());
        // - updated close handler is returned
        assertEquals(mockHttpServerResponse.closeHandler(closeHandler).closeHandler(), closeHandler);
    }

    @Test
    public void shouldCallCloseHandler() {
        // given
        Handler<Void> closeHandler = mock(Handler.class);
        MockHttpServerResponse mockHttpServerResponse = new MockHttpServerResponse().closeHandler(closeHandler);

        // then - not closed at beginning
        assertFalse(mockHttpServerResponse.closed());

        // when
        mockHttpServerResponse.close();
        // then
        // - now closed
        assertTrue(mockHttpServerResponse.closed());
        // - closed handler called
        verify(closeHandler).handle(null);
    }

    @Test
    public void shouldWriteToBody() throws UnsupportedEncodingException {
        // given
        MockHttpServerResponse mockHttpServerResponse = new MockHttpServerResponse();

        // then - default body is empty
        assertEquals(new String(mockHttpServerResponse.body(), UTF_8), "");

        // when
        mockHttpServerResponse.write("some_string");
        // then - body appended
        assertEquals(new String(mockHttpServerResponse.body(), UTF_8), "some_string");

        // when
        mockHttpServerResponse.write("_and_another_string", UTF_8);
        // then - body appended
        assertEquals(new String(mockHttpServerResponse.body(), UTF_8), "some_string_and_another_string");

        // when
        mockHttpServerResponse.write(new Buffer(0).appendString("_and_yet_another_string"));
        // then - body appended
        assertEquals(new String(mockHttpServerResponse.body(), UTF_8), "some_string_and_another_string_and_yet_another_string");
    }

    @Test
    public void shouldEndWritingToBody() throws UnsupportedEncodingException {
        // given
        MockHttpServerResponse mockHttpServerResponse = new MockHttpServerResponse();
        // when
        mockHttpServerResponse.end("some_string");
        // then
        // -- body updated
        assertEquals(new String(mockHttpServerResponse.body(), UTF_8), "some_string");
        // -- Content-Length header updated
        assertEquals(mockHttpServerResponse.headers().get("Content-Length"), String.valueOf("some_string".length()));
        // -- body marked as written
        assertEquals(mockHttpServerResponse.written(), true);
    }

    @Test
    public void shouldEndWritingToBodyWithSpecificCharacterSet() throws UnsupportedEncodingException {
        // given
        MockHttpServerResponse mockHttpServerResponse = new MockHttpServerResponse();
        // when
        mockHttpServerResponse.end("some_string", UTF_8);
        // then
        // -- body updated
        assertEquals(new String(mockHttpServerResponse.body(), UTF_8), "some_string");
        // -- Content-Length header updated
        assertEquals(mockHttpServerResponse.headers().get("Content-Length"), String.valueOf("some_string".length()));
        // -- body marked as written
        assertEquals(mockHttpServerResponse.written(), true);
    }

    @Test
    public void shouldEndWritingToBodyWithBuffer() throws UnsupportedEncodingException {
        // given
        MockHttpServerResponse mockHttpServerResponse = new MockHttpServerResponse();
        // when
        mockHttpServerResponse.end(new Buffer(0).appendString("some_string"));
        // then
        // -- body updated
        assertEquals(new String(mockHttpServerResponse.body(), UTF_8), "some_string");
        // -- Content-Length header updated
        assertEquals(mockHttpServerResponse.headers().get("Content-Length"), String.valueOf("some_string".length()));
        // -- body marked as written
        assertEquals(mockHttpServerResponse.written(), true);
    }

    @Test(expected = IllegalStateException.class)
    public void shouldThrowExceptionIfEndCalledMoreThenOnce() throws UnsupportedEncodingException {
        // given
        MockHttpServerResponse mockHttpServerResponse = new MockHttpServerResponse();
        // when
        mockHttpServerResponse.end("some_string");
        // then - throw exception
        mockHttpServerResponse.end("some_string");
    }

    @Test
    public void shouldReturnDocumentIfBodyValidHTML() {
        // given
        MockHttpServerResponse mockHttpServerResponse = new MockHttpServerResponse();
        // when
        mockHttpServerResponse.end("<html><head></head><body><div id='test_id'>test_value</div></body></html>");
        // then
        // - returns Document object
        assertEquals(mockHttpServerResponse.html().getClass(), Document.class);
        // - returned Document can be query
        assertEquals(mockHttpServerResponse.html().select("#test_id").text(), "test_value");
    }

    @Test
    public void shouldMarkASWrittenIfEndCalled() throws UnsupportedEncodingException {
        // given
        MockHttpServerResponse mockHttpServerResponse = new MockHttpServerResponse();
        // when
        mockHttpServerResponse.end();
        // then - body marked as written
        assertEquals(mockHttpServerResponse.written(), true);
    }

    @Test
    public void shouldSendFile() {
        // given
        MockHttpServerResponse mockHttpServerResponse = new MockHttpServerResponse();
        // then
        // - default is null
        assertNull(mockHttpServerResponse.sendFileFilename());
        // then
        // - filename is set
        assertEquals(mockHttpServerResponse.sendFile("filename").sendFileFilename(), "filename");
    }

    @Test
    public void shouldSendFileWithResultHandler() {
        // given
        MockHttpServerResponse mockHttpServerResponse = new MockHttpServerResponse();
        Handler<AsyncResult<Void>> asyncResultHandler = mock(Handler.class);
        AsyncResult<Void> sendFileResult = mock(AsyncResult.class);

        // then
        // - default is null
        assertNull(mockHttpServerResponse.sendFileFilename());

        // when
        mockHttpServerResponse.withSendFileResult(sendFileResult).sendFile("filename", asyncResultHandler);
        // then
        // - filename is set
        assertEquals(mockHttpServerResponse.sendFileFilename(), "filename");
        // - handle is set
        assertSame(mockHttpServerResponse.resultHandler(), asyncResultHandler);
        // - handle is called with result
        verify(asyncResultHandler).handle(same(sendFileResult));
    }

    @Test
    public void shouldSendFileWithFallback() {
        // given
        MockHttpServerResponse mockHttpServerResponse = new MockHttpServerResponse();
        // then
        // - default is null
        assertNull(mockHttpServerResponse.sendFileFilename());

        // when
        mockHttpServerResponse.sendFile("filename", "notFoundFile");
        // then
        // - filename is set
        assertEquals(mockHttpServerResponse.sendFileFilename(), "filename");
        // - not found file is set
        assertEquals(mockHttpServerResponse.sendFileNotFoundFile(), "notFoundFile");
    }

    @Test
    public void shouldSendFileWithFallbackAndResultHandler() {
        // given
        MockHttpServerResponse mockHttpServerResponse = new MockHttpServerResponse();
        Handler<AsyncResult<Void>> asyncResultHandler = mock(Handler.class);
        AsyncResult<Void> sendFileResult = mock(AsyncResult.class);

        // then
        // - default is null
        assertNull(mockHttpServerResponse.sendFileFilename());

        // when
        mockHttpServerResponse.withSendFileResult(sendFileResult).sendFile("filename", "notFoundFile", asyncResultHandler);
        // then
        // - filename is set
        assertEquals(mockHttpServerResponse.sendFileFilename(), "filename");
        // - not found file is set
        assertEquals(mockHttpServerResponse.sendFileNotFoundFile(), "notFoundFile");
        // - handle is set
        assertSame(mockHttpServerResponse.resultHandler(), asyncResultHandler);
        // - handle is called with result
        verify(asyncResultHandler).handle(same(sendFileResult));
    }

    @Test
    public void shouldReturnCorrectWriteQueueMaxSize() {
        // given
        MockHttpServerResponse mockHttpServerResponse = new MockHttpServerResponse();

        // then
        // - default is Integer.MAX_VALUE
        assertEquals(mockHttpServerResponse.writeQueueMaxSize(), Integer.MAX_VALUE);
        // - updated write queue max size is returned
        assertEquals(mockHttpServerResponse.setWriteQueueMaxSize(10).writeQueueMaxSize(), 10);
    }

    @Test
    public void shouldHaveFullWriteQueue() {
        // then
        // - a write queue should not be full
        assertFalse(new MockHttpServerResponse()
                .setWriteQueueMaxSize(5)
                .write("1234")
                .writeQueueFull());
        // - a write queue should be full
        assertTrue(new MockHttpServerResponse()
                .setWriteQueueMaxSize(5)
                .write("12345")
                .writeQueueFull());
        assertTrue(new MockHttpServerResponse()
                .setWriteQueueMaxSize(5)
                .write("123456")
                .writeQueueFull());
    }

    @Test
    public void shouldReturnCorrectDrainHandler() throws URISyntaxException {
        // given
        MockHttpServerResponse mockHttpServerResponse = new MockHttpServerResponse();
        Handler<Void> drainHandler = mock(Handler.class);

        // then
        // - default is null
        assertNull(mockHttpServerResponse.drainHandler());
        // - updated drain handler is returned
        assertEquals(mockHttpServerResponse.drainHandler(drainHandler).drainHandler(), drainHandler);
    }

    @Test
    public void shouldReturnCorrectExceptionHandler() throws URISyntaxException {
        // given
        MockHttpServerResponse mockHttpServerResponse = new MockHttpServerResponse();
        Handler<Throwable> exceptionHandler = mock(Handler.class);

        // then
        // - default is null
        assertNull(mockHttpServerResponse.exceptionHandler());
        // - updated exception handler is returned
        assertEquals(mockHttpServerResponse.exceptionHandler(exceptionHandler).exceptionHandler(), exceptionHandler);
    }
}