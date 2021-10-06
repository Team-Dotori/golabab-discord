package com.dotori.golababdiscord.domain.logger.annotation;;

import java.lang.annotation.*;

@Inherited
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ApiEntry {
    String apiName() default " rest_api";
    String actionName() default "get_api_data";
    Class<?> response() default Object.class;
    Class<?> request() default Object.class;
}
