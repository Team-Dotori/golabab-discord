package com.dotori.golababdiscord.domain.logger.annotation;

import java.lang.annotation.*;

@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface CommandRunner {
    boolean requireLog() default true;
}
