package com.ileossa.project.cronTask;

import com.ileossa.project.shorter.UrlShorterRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by ileossa on 04/08/2017.
 */
@Component
public class BobyTask {

    private static final Logger log = LoggerFactory.getLogger(BobyTask.class);

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    private UrlShorterRepository urlShorterRepository;

    @Autowired
    public BobyTask(UrlShorterRepository urlShorterRepository) {
        this.urlShorterRepository = urlShorterRepository;
    }


    @Scheduled(fixedRate = 5000000)
    public void cleanExpiredShortUrl(){
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        // 1728000 == 20 days
        long instantPlus20Days = timestamp.toInstant().getEpochSecond();
        urlShorterRepository.findByTimeOfValidityOrderByTimeOfValidityAsc(instantPlus20Days);
        log.info("cron cleanExpiredShortUrl executed");
    }
}
