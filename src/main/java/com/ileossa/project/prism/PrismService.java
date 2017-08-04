package com.ileossa.project.prism;

import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by ileossa on 04/08/2017.
 */
public interface PrismService {

    void logAccess(HttpServletRequest request);
}
