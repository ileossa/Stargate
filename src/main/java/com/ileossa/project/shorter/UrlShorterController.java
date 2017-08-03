package com.ileossa.project.shorter;

import com.ileossa.project.exception.UrlShortException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Calendar;
import java.util.Date;


/**
 * Created by ileossa on 03/08/2017.
 */
@Controller
public class UrlShorterController {

    private UrlShorterServiceImpl urlShorterService;
    private static final DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");



    @Autowired
    public UrlShorterController(UrlShorterServiceImpl urlShorterService) {
        this.urlShorterService = urlShorterService;
    }

    @PostMapping("/share")
    @ResponseBody
    public String echo( @RequestBody String url) throws UrlShortException {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        // 1728000 == 20 days
        long instantPlus20Days = timestamp.toInstant().getEpochSecond() + 1728000;

        UrlShorterDao urlShorterDao = new UrlShorterDao();
        urlShorterDao.setOriginalUrl(url);
        urlShorterDao.setShortUrl(urlShorterService.generateShortUrl(url));

        urlShorterDao.setTimeOfValidity(instantPlus20Days);
        UrlShorterDao object = urlShorterService.save(urlShorterDao);

        return object.getShortUrl();
    }
}

