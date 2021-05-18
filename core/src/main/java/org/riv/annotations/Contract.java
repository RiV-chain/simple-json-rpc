package org.riv.annotations;

import java.lang.annotation.*;

/**
 * Date: 8/2/14
 * Time: 6:10 PM
 * Annotation for marking a service as a JSON-RPC service
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Inherited
public @interface Contract {
}
