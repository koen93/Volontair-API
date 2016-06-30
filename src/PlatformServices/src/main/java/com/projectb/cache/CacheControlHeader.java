package com.projectb.cache;

import org.apache.commons.lang.StringUtils;

public class CacheControlHeader {
    public enum CacheType {
        PUBLIC, PRIVATE;
    }

    /**
     * public | private
     */
    CacheType cacheType = null;
    /**
     * max-age = delta-seconds (0 ==> no-cache | no-store )。
     */
    boolean noCache;
    boolean noStore;

    int maxAge = -1;

    /**
     * s-maxage = delta-seconds.
     */
    int sMaxAge = -1;

    /**
     * must-revalidate = true | false
     */
    boolean mustRevalidate = false;

    /**
     * proxy-revalidate = true | false
     */
    boolean proxyRevalidate = false;

    /**
     * no-transform = true | false
     */
    boolean noTransform = false;

    private CacheControlHeader() {
    }

    public static Builder newBuilder() {
        return Builder.create();
    }

    public CacheType getCacheType() {
        return cacheType;
    }

    public boolean isNoCache() {
        return noCache;
    }

    public boolean isNoStore() {
        return noStore;
    }

    public int getMaxAge() {
        return maxAge;
    }

    public int getsMaxAge() {
        return sMaxAge;
    }

    public boolean isMustRevalidate() {
        return mustRevalidate;
    }

    public boolean isProxyRevalidate() {
        return proxyRevalidate;
    }

    public boolean isNoTransform() {
        return noTransform;
    }

    public String stringValue() {
        StringBuilder sb = new StringBuilder();

        if (cacheType == CacheType.PUBLIC) {
            append(sb, "public");
        } else if (cacheType == CacheType.PRIVATE) {
            append(sb, "private");
        }

        if (mustRevalidate) {
            append(sb, "must-revalidate");
        }

        if (noTransform) {
            append(sb, "no-transform");
        }

        if (proxyRevalidate) {
            append(sb, "proxy-revalidate");
        }

        if (maxAge == 0) {
            append(sb, "max-age=0");
            noCache = noStore = true;
        } else if (maxAge > 0) {
            append(sb, "max-age=" + maxAge);
        }

        if (sMaxAge == 0) {
            append(sb, "s-maxage=0");
            noCache = noStore = true;
        } else if (sMaxAge > 0) {
            append(sb, "s-maxage=" + sMaxAge);
        }

        if (noCache || noStore) {
            append(sb, "no-cache,no-store");
        }

        return sb.toString();
    }

    private StringBuilder append(StringBuilder sb, String str) {
        if (sb == null) {
            sb = new StringBuilder();
            sb.append(str);
        } else {
            String s = StringUtils.trimToEmpty(sb.toString());
            if (StringUtils.isEmpty(s) || s.charAt(s.length() - 1) == ',') {
                sb.append(str);
            } else {
                sb.append(',').append(str);
            }
        }

        return sb;
    }


    /**
     * <pre>
     * Builder for CacheControlHeader.
     *
     * 这里没有处理字段是否有被显示设置问题。可以考虑每个字段增加一个标志字段，表面是否被显示设值。
     * </pre>
     *
     * @author zhengzhibin
     *
     */
    public static class Builder {
        private CacheControlHeader cacheControlHeader;

        public static Builder create() {
            return new Builder();
        }

        private Builder() {
            this.cacheControlHeader = new CacheControlHeader();
        }

        public Builder setCacheType(CacheType cacheType) {
            cacheControlHeader.cacheType = cacheType;
            return this;
        }

        public Builder setNoCache(boolean noCache) {
            cacheControlHeader.noCache = noCache;
            return this;
        }

        public Builder setNoStore(boolean noStore) {
            cacheControlHeader.noStore = noStore;
            return this;
        }

        public Builder setMaxAge(int maxAge) {
            cacheControlHeader.maxAge = maxAge;
            return this;
        }

        public Builder setsMaxAge(int sMaxAge) {
            cacheControlHeader.sMaxAge = sMaxAge;
            return this;
        }

        public Builder setMustRevalidate(boolean mustRevalidate) {
            cacheControlHeader.mustRevalidate = mustRevalidate;
            return this;
        }

        public Builder setProxyRevalidate(boolean proxyRevalidate) {
            cacheControlHeader.proxyRevalidate = proxyRevalidate;
            return this;
        }

        public Builder setNoTransform(boolean noTransform) {
            cacheControlHeader.noTransform = noTransform;
            return this;
        }

        public CacheControlHeader build() {
            return cacheControlHeader;
        }
    }
}
