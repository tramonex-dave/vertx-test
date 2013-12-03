package org.vertxtest.junit;

import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.InitializationError;

/**
 * @author jamesdbloom
 */
public class VertxJUnit4ClassRunner extends BlockJUnit4ClassRunner {

    public VertxJUnit4ClassRunner(Class<?> klass) throws InitializationError {
        super(klass);
    }
}
