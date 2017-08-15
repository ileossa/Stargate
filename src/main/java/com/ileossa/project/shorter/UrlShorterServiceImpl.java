package com.ileossa.project.shorter;

import com.ileossa.project.exception.UrlShortException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.util.Random;

/**
 * Created by ileossa on 03/08/2017.
 */
@Component
public class UrlShorterServiceImpl implements UrlShorterService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private UrlShorterRepository urlShorterRepository;

    @Autowired
    public UrlShorterServiceImpl(UrlShorterRepository urlShorterRepository) {
        this.urlShorterRepository = urlShorterRepository;
    }


    @Override
    public UrlShorterDao save(UrlShorterDao urlShorterDao) throws UrlShortException {
        if(isExist(urlShorterDao.getShortUrl())){
            throw new UrlShortException("l'url exist already in database, please convert an another String");
        }
        return urlShorterRepository.save(urlShorterDao);
    }

    @Override
    public void delete(long id) {
        urlShorterRepository.delete(id);
    }

    /**
     * If url passing on parameter exist in DB return TRUE.
     * url not exist in DB return FALSE.
     * @param url
     * @return
     */
    @Override
    public boolean isExist(String url) {
        //check for originalUrl
        if(urlShorterRepository.findByOriginalUrlEquals(url) != null){
            return true;
        }
        // check for shortUrl
        if(urlShorterRepository.findByShortUrlEquals(url) != null){
            return true;
        }
        return false;
    }

    @Override
    public String getOriginalUrl(String shortUrl) {
        UrlShorterDao object = urlShorterRepository.findByShortUrlEquals(shortUrl);
        try {
            return java.net.URLDecoder.decode(object.getOriginalUrl(), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            logger.error("Cannot decode URI: " + object.getOriginalUrl());
        }
        return object.getShortUrl();
    }

    @Override
    public String getShorterUrl(String originalUrl) {
        UrlShorterDao object = urlShorterRepository.findByOriginalUrlEquals(originalUrl);
        return object.getShortUrl();
    }

    @Override
    public UrlShorterDao getPojo(String url) throws UrlShortException {
        UrlShorterDao object = urlShorterRepository.findUrlShorterDaoByOriginalUrlEqualsOrShortUrlEquals(url, url);
        if(object != null){
            return object;
        }
        throw new UrlShortException("Cannot find UrlShorterDao with this url: " + url);
    }

    @Override
    public String generateShortUrl(String originalUrl) {
        String randUrl = getRandomString();
        UrlShorterDao object = urlShorterRepository.findByShortUrlEquals(randUrl);
        if( object == null){
            return randUrl;
        }
        return generateShortUrl(originalUrl);
    }


    // PRIVATE

    private String getRandomString() {
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 10) { // length of the random string.
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        String saltStr = salt.toString();
        logger.debug("random String generate: {}", saltStr);
        return saltStr;
    }
}
