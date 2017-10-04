package com.ileossa.project.exception;

/**
 * Created by ileossa on 01/08/2017.
 */
@SuppressWarnings("serial")
public class UserNotExist extends Throwable {
    public UserNotExist(final String s) {
        super(s);
    }

}
