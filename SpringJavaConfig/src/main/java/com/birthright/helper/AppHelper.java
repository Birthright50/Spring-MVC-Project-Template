package com.birthright.helper;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by birthright on 25.02.17.
 */

public class AppHelper {

    public static String getAppUrl(HttpServletRequest request) {
        return request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
    }
}
