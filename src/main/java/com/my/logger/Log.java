package com.my.logger;

import java.lang.annotation.*;

/**
 * Created by marcin on 06.01.16.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Documented
public @interface Log {
}
