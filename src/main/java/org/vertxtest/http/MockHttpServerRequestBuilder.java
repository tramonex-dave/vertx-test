package org.vertxtest.http;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.google.common.net.MediaType;
import io.netty.handler.codec.http.HttpMethod;
import org.vertxtest.uri.URI;

import java.io.UnsupportedEncodingException;
import java.net.HttpCookie;
import java.util.Arrays;

/**
 * @author jamesdbloom
 */
public class MockHttpServerRequestBuilder {

    private MockHttpServerRequest mockHttpServerRequest;

    private MockHttpServerRequestBuilder(HttpMethod method, String uri) {
        if (uri == null) throw new IllegalArgumentException("uri can not be null");
        mockHttpServerRequest = new MockHttpServerRequest().withMethod(method.name());
        applyUri(new URI(uri));
    }

    public static MockHttpServerRequestBuilder get(String uri) {
        if (uri == null) throw new IllegalArgumentException("uri can not be null");
        return new MockHttpServerRequestBuilder(HttpMethod.GET, uri);
    }

    public static MockHttpServerRequestBuilder post(String uri) {
        if (uri == null) throw new IllegalArgumentException("uri can not be null");
        return new MockHttpServerRequestBuilder(HttpMethod.POST, uri);
    }

    public static MockHttpServerRequestBuilder put(String uri) {
        if (uri == null) throw new IllegalArgumentException("uri can not be null");
        return new MockHttpServerRequestBuilder(HttpMethod.PUT, uri);
    }

    public static MockHttpServerRequestBuilder delete(String uri) {
        if (uri == null) throw new IllegalArgumentException("uri can not be null");
        return new MockHttpServerRequestBuilder(HttpMethod.DELETE, uri);
    }

    public static MockHttpServerRequestBuilder options(String uri) {
        if (uri == null) throw new IllegalArgumentException("uri can not be null");
        return new MockHttpServerRequestBuilder(HttpMethod.OPTIONS, uri);
    }

    private void applyUri(URI uri) {
        mockHttpServerRequest.withPath(uri.path());
        mockHttpServerRequest.withParams(uri.params());
        mockHttpServerRequest.withUri(uri.toString());
        // TODO more to do in here with other address objects
    }

    public MockHttpServerRequestBuilder param(String name, Object... values) {
        if (name == null) throw new IllegalArgumentException("name can not be null");
        for (Object value : values) {
            if (value == null) throw new IllegalArgumentException("value can not be null for " + name);
            mockHttpServerRequest.params().add(name, value.toString());
        }
        return this;
    }

    public MockHttpServerRequestBuilder header(String name, Object... values) {
        if (name == null) throw new IllegalArgumentException("name can not be null");
        for (Object value : values) {
            if (value == null) throw new IllegalArgumentException("value can not be null for " + name);
            mockHttpServerRequest.headers().add(name, value.toString());
        }
        return this;
    }

    public MockHttpServerRequestBuilder contentType(MediaType mediaType) {
        if (mediaType == null) throw new IllegalArgumentException("mediaType can not be null");
        mockHttpServerRequest.headers().add("Content-Type", mediaType.toString());
        return this;
    }

    public MockHttpServerRequestBuilder contentType(String mediaType) {
        return contentType(MediaType.parse(mediaType));
    }

    /**
     * Set the 'Accept' header to the given media type(s).
     */
    public MockHttpServerRequestBuilder accept(MediaType... mediaTypes) {
        return accept(Lists.transform(Arrays.asList(mediaTypes), new Function<MediaType, String>() {
            public String apply(MediaType mediaType) {
                return mediaType.toString();
            }
        }).toArray(new String[mediaTypes.length]));
    }

    public MockHttpServerRequestBuilder accept(String... mediaTypes) {
        mockHttpServerRequest.headers().set("Accept", Arrays.asList(mediaTypes));
        return this;
    }

    public MockHttpServerRequestBuilder content(byte[] content) {
        mockHttpServerRequest.withBody(content);
        return this;
    }

    public MockHttpServerRequestBuilder content(String content) {
        try {
            mockHttpServerRequest.withBody(content.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException uee) {
            throw new IllegalArgumentException("Error reading content string", uee);
        }
        return this;
    }

    public MockHttpServerRequestBuilder cookie(String name, Object... values) {
        if (name == null) throw new IllegalArgumentException("name can not be null");
        for (Object value : values) {
            if (value == null) throw new IllegalArgumentException("value can not be null for " + name);
            mockHttpServerRequest.headers().add("Cookie", new HttpCookie(name, value.toString()).toString());
        }
        return this;
    }
}
