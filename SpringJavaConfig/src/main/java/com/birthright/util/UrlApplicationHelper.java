package com.birthright.util;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by birthright on 25.02.17.
 */

public class UrlApplicationHelper {

    public static String getAppUrl(HttpServletRequest request) {
        return request.getScheme() + "://" + request.getServerName()
                + ":" + request.getServerPort() + request.getContextPath();
    }

}
