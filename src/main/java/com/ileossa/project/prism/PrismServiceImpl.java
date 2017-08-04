package com.ileossa.project.prism;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by ileossa on 04/08/2017.
 */
@Component
public class PrismServiceImpl implements PrismService {

    private PrismRepository prismRepository;

    @Autowired
    public PrismServiceImpl(PrismRepository prismRepository) {
        this.prismRepository = prismRepository;
    }


    @Override
    public void logAccess(HttpServletRequest request) {
        save(getBaseUrl(request),getRemoteIp(request));
    }

    // PRIVATE URL
    public String getLocalIp(HttpServletRequest request) {
        return request.getRemoteAddr();
    }

    public String getRemoteIp(HttpServletRequest request) {
        return request.getRemoteAddr();
    }

    private String getBaseUrl(HttpServletRequest request){
        return request.getRequestURL().toString();
    }

    public void save(String url, String ip) {
        PrismDao prismDao = new PrismDao(url, ip);
        prismRepository.save(prismDao);
    }

}
