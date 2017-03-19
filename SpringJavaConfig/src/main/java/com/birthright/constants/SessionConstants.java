package com.birthright.constants;

import javax.annotation.concurrent.NotThreadSafe;

/**
 * Created by birthright on 23.02.17.
 */
public class SessionConstants {
    //existing token, it used to resend a new token to register
    public static final String EXISTING_TOKEN = "existingToken";

    public static final String LAST_RESEND = "lastResend";

    private SessionConstants() throws Throwable {
        throw new Throwable();
    }

}
