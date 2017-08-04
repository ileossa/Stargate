package com.ileossa.project.shorter;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by ileossa on 03/08/2017.
 */
public interface UrlShorterRepository extends JpaRepository<UrlShorterDao, Long> {

    UrlShorterDao findByOriginalUrlEquals(String originalUrl);
    UrlShorterDao findByShortUrlEquals(String shortUrl);
    UrlShorterDao findUrlShorterDaoByOriginalUrlEqualsOrShortUrlEquals(String url, String sameUrl);

    List<UrlShorterDao> findByTimeOfValidityOrderByTimeOfValidityAsc(long timeOfValidity);
}
