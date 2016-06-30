package com.projectb.cache;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface CacheControl {

    /**
     * public和private都是关键字。。
     *
     */
    boolean isPublic() default true;

    /**
     * max-age = delta-seconds (0 ==> no-cache | no-store )。
     *
     */
    int maxAge() default -1;

    /**
     * s-maxage = delta-seconds.
     */
    int sMaxAge() default -1;

    /**
     * must-revalidate = true | false
     */
    boolean mustRevalidate() default false;

    /**
     * proxy-revalidate = true | false
     */
    boolean proxyRevalidate() default false;

    /**
     * no-transform = true | false
     */
    boolean noTransform() default true;

    /**
     * <pre>
     * Expires = 日期字符串，并且自动推算匹配的max-age。
     * HTTP标准是RFC 1123 date format, e.g., Thu, 01 Dec 1994 16:00:00 GMT.
     * public static final String PATTERN_RFC1123 = "EEE, dd MMM yyyy HH:mm:ss zzz";
     * 但是这个格式对于我们中国人非常不友好，还是改成这个格式方便些：yyyy-MM-dd HH:mm:ss。
     *
     * </pre>
     */
    String expires() default "";
}
