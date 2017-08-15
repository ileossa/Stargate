package com.ileossa.project.shorter;

import com.ileossa.project.exception.UrlShortException;
import com.ileossa.project.prism.PrismService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;



/**
 * Created by ileossa on 03/08/2017.
 */
@Controller
public class UrlShorterController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private UrlShorterServiceImpl urlShorterService;
    private PrismService prismService;

    private static final DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");



    @Autowired
    public UrlShorterController(UrlShorterServiceImpl urlShorterService, PrismService prismService) {
        this.urlShorterService = urlShorterService;
        this.prismService = prismService;
    }

    @PostMapping("/share")
    @ResponseBody
    public String getShorterUrl(@RequestBody String originalUrl) throws UrlShortException {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        // 1728000 == 20 days
        long instantPlus20Days = timestamp.toInstant().getEpochSecond() + 1728000;

        // todo extract in service
        try {
            originalUrl = java.net.URLDecoder.decode(originalUrl, "UTF-8");
            originalUrl = originalUrl.substring(0, originalUrl.length() - 1);
        } catch (UnsupportedEncodingException e) {
            logger.error("Cannot convert URI to URL. %s", originalUrl);
        }

        // todo extract
        UrlShorterDao urlShorterDao = new UrlShorterDao();
        urlShorterDao.setOriginalUrl(originalUrl);

        urlShorterDao.setShortUrl(urlShorterService.generateShortUrl(originalUrl));

        urlShorterDao.setTimeOfValidity(instantPlus20Days);
        UrlShorterDao object = urlShorterService.save(urlShorterDao);

        return "http://localhost:8080/share/" + object.getShortUrl();
    }


    @GetMapping("/share/{shortUrl}")
    public String redirectOriginalUrl(@PathVariable String shortUrl, HttpServletRequest request ){
        String urlOriginal = urlShorterService.getOriginalUrl(shortUrl);
        prismService.logAccess(request);
        return "redirect:" + urlOriginal;
    }
}