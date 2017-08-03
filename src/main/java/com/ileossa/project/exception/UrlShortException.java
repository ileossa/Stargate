package com.ileossa.project.exception;

import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Created by ileossa on 04/08/2017.
 */
public class UrlShortException extends Throwable {
    public UrlShortException(String s){
        super(s);
    }
}
