package com.projectb.cache;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.lang.time.FastDateFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.env.Environment;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * http://blog.arganzheng.me/posts/builder-pattern-in-action.html
 */
public class CacheControlInterceptor extends HandlerInterceptorAdapter {
    public static final String EXPIRES_PATTERN = "yyyy-MM-dd HH:mm:ss";
    public static final String PATTERN_RFC1123 = "EEE, dd MMM yyyy HH:mm:ss zzz";

    @Autowired
    private Environment environment;

    /**
     * 根据@CacheControl注解设置对应的HTTP Response Header
     */
    public void postHandle(HttpServletRequest request, HttpServletResponse response,
                           Object handler, ModelAndView modelAndView) throws Exception {
        if (handler instanceof HandlerMethod) {
            HandlerMethod method = (HandlerMethod) handler;
            CacheControl cacheControlAnnotation = method.getMethodAnnotation(CacheControl.class);
            if (cacheControlAnnotation == null
                    || StringUtils.isNotEmpty(response.getHeader("Cache-Control"))) {
                return;
            }

            CacheControlHeader.Builder cc = CacheControlHeader.newBuilder();

            if (cacheControlAnnotation.isPublic()) {
                cc.setCacheType(CacheControlHeader.CacheType.PUBLIC);
            } else {
                cc.setCacheType(CacheControlHeader.CacheType.PRIVATE);
            }

            Date expiresDate = null;
            if (cacheControlAnnotation.mustRevalidate()) {
                cc.setMustRevalidate(true);
            }

            if (cacheControlAnnotation.noTransform()) {
                cc.setNoTransform(true);
            }

            if (cacheControlAnnotation.proxyRevalidate()) {
                cc.setProxyRevalidate(true);
            }

            if (cacheControlAnnotation.maxAge() >= 0) {
                cc.setMaxAge(cacheControlAnnotation.maxAge());
            }
            if (cacheControlAnnotation.sMaxAge() >= 0) {
                cc.setsMaxAge(cacheControlAnnotation.sMaxAge());
            }

            if (StringUtils.isNotEmpty(cacheControlAnnotation.expires())) {
                try {
                    expiresDate = DateUtils.parseDate(cacheControlAnnotation.expires(),
                            new String[] { EXPIRES_PATTERN, PATTERN_RFC1123 });

                    Date now = new Date();

                    long age = expiresDate.getTime() - now.getTime();
                    long maxAge = age / 1000;

                    if (cacheControlAnnotation.maxAge() < 0) { // maxAge not set
                        cc.setMaxAge(Long.valueOf(maxAge).intValue());
                    }
                    if (cacheControlAnnotation.sMaxAge() < 0) { // sMaxAge not
                        // set
                        cc.setsMaxAge(Long.valueOf(maxAge).intValue());
                    }
                } catch (Exception e) {

                }
            }

            String value = cc.build().stringValue();
            if (StringUtils.isNotBlank(value)) {
                response.addHeader("Cache-Control", value);
            }

            if (expiresDate != null) {
                response.addHeader("Expires", FastDateFormat.getInstance(PATTERN_RFC1123).format(expiresDate));
            }
        }
    }
}
