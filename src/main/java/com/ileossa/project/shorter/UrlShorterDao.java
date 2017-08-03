package com.ileossa.project.shorter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.security.Timestamp;

/**
 * Created by ileossa on 03/08/2017.
 */
@Entity
public class UrlShorterDao {

    @Id
    @GeneratedValue
    private long id;

    @NotNull
    private String originalUrl;

    @NotNull
    private String shortUrl;

    @NotNull
    private long timeOfValidity;

    public UrlShorterDao() {
    }

    public UrlShorterDao(String originalUrl, String shortUrl, long timeOfValidity) {
        this.originalUrl = originalUrl;
        this.shortUrl = shortUrl;
        this.timeOfValidity = timeOfValidity;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getOriginalUrl() {
        return originalUrl;
    }

    public void setOriginalUrl(String originalUrl) {
        this.originalUrl = originalUrl;
    }

    public String getShortUrl() {
        return shortUrl;
    }

    public void setShortUrl(String shortUrl) {
        this.shortUrl = shortUrl;
    }

    public long getTimeOfValidity() {
        return timeOfValidity;
    }

    public void setTimeOfValidity(long timeOfValidity) {
        this.timeOfValidity = timeOfValidity;
    }
}
