package org.vertxtest.perform;

/**
 * @author jamesdbloom
 */
public interface Matcher<E> {

    void match(E event);
}
