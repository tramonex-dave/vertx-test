package org.vertxtest.uri;

import com.google.common.base.Strings;
import io.netty.handler.codec.http.QueryStringDecoder;
import org.vertx.java.core.MultiMap;
import org.vertx.java.core.http.CaseInsensitiveMultiMap;

import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author jamesdbloom
 */
public class URI {
    private static final String HTTP_PATTERN = "(http|https):";
    private static final String USER_INFO_PATTERN = "([^@/]*)";
    private static final String HOST_PATTERN = "([^/?#:]*)";
    private static final String PORT_PATTERN = "(\\d*)";
    private static final String PATH_PATTERN = "([^?#]*)";
    private static final String QUERY_STRING_PATTERN = "(.*)";
    private static final Pattern HTTP_URL_PATTERN = Pattern.compile("^" +
            HTTP_PATTERN + /* 1 */
            ("" +
                    "(//" +
                    "(" + USER_INFO_PATTERN /* 4 */ + "@)?" + /* 3 */
                    HOST_PATTERN + /* 5 */
                    "(:" + PORT_PATTERN /* 7 */ + ")?" + /* 6 */
                    ")?" /* 2 */
            ) +
            PATH_PATTERN + /* 8 */
            "(\\?" + QUERY_STRING_PATTERN /* 9 */ + ")?" /* 10 */
    );
    private final String uri;
    private final String scheme;
    private final String userInfo;
    private final String host;
    private final int port;
    private final String path;
    private final MultiMap params = new CaseInsensitiveMultiMap();

    public URI(String uri) {
        this.uri = uri;
        if (uri == null) throw new IllegalArgumentException("[" + uri + "] is not a valid HTTP URL");
        Matcher httpUrlMatcher = HTTP_URL_PATTERN.matcher(uri);
        if (httpUrlMatcher.matches()) {

            scheme = httpUrlMatcher.group(1);
            userInfo = httpUrlMatcher.group(4);
            host = httpUrlMatcher.group(5);
            if (!Strings.isNullOrEmpty(httpUrlMatcher.group(7))) {
                port = Integer.parseInt(httpUrlMatcher.group(7));
            } else {
                port = (scheme.equals("http") ? 80 : 443);
            }
            path = httpUrlMatcher.group(8);
            Map<String, List<String>> parameters = new QueryStringDecoder(uri).parameters();
            for (String name : parameters.keySet()) {
                params.add(name, parameters.get(name));
            }
        } else {
            throw new IllegalArgumentException("[" + uri + "] is not a valid HTTP URL");
        }
    }

    public String scheme() {
        return scheme;
    }

    public String userInfo() {
        return userInfo;
    }

    public String host() {
        return host;
    }

    public int port() {
        return port;
    }

    public String path() {
        return path;
    }

    public MultiMap params() {
        return params;
    }

    public String toString() {
        return uri;
    }
}
