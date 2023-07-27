package com.liuche.common.util;


import java.io.Serializable;

public class RequestContext implements Serializable {

    private static final long serialVersionUID = 2228882330407487143L;
    private static final ThreadLocal<Long> remoteAddr = new ThreadLocal<>();

    public static long getUserId() {
        return remoteAddr.get();
    }

    public static void setUserId(long id) {
        RequestContext.remoteAddr.set(id);
    }

}
