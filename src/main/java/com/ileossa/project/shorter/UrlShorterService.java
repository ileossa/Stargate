package com.ileossa.project.shorter;

import com.ileossa.project.exception.UrlShortException;

import java.awt.image.BufferedImage;

/**
 * Created by ileossa on 03/08/2017.
 */
public interface UrlShorterService {

    public UrlShorterDao save(UrlShorterDao urlShorterDao) throws UrlShortException;
    public void delete(long id);
    public boolean isExist(String url);

    public String getOriginalUrl(String shortUrl);
    public String getShorterUrl(String originalUrl);

    public UrlShorterDao getPojo(String url) throws UrlShortException;

    public String generateShortUrl(String originalUrl);

    public BufferedImage watermark(String url);


}
