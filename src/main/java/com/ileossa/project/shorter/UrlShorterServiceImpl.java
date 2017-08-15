package com.ileossa.project.shorter;

import com.ileossa.project.exception.UrlShortException;
import com.ileossa.project.uploadFiles.service.FileService;
import com.ileossa.project.uploadFiles.storage.StorageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Random;

/**
 * Created by ileossa on 03/08/2017.
 */
@Component
public class UrlShorterServiceImpl implements UrlShorterService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private static UrlShorterRepository urlShorterRepository;
    private static StorageService storageService;
    private static FileService fileService;
    @Value("${watermark.text}")
    private String textWatermark;

    @Autowired
    public UrlShorterServiceImpl(UrlShorterRepository urlShorterRepository, FileService fileService, StorageService storageService) {
        this.urlShorterRepository = urlShorterRepository;
        this.fileService = fileService;
        this.storageService = storageService;
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

    @Override
    public BufferedImage watermark(String originalURL) {
        com.ileossa.project.uploadFiles.dao.File imageSave = fileService.findFile(originalURL);
        File sourceImageFile = new File(imageSave.getClasspath());
        try {
            BufferedImage sourceImage = ImageIO.read(sourceImageFile);
            Graphics2D g2d = (Graphics2D) sourceImage.getGraphics();

            // initializes necessary graphic properties
            AlphaComposite alphaChannel = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.8f);
            g2d.setComposite(alphaChannel);
            g2d.setColor(Color.BLUE);
            g2d.setFont(new Font("Arial", Font.BOLD, 90));
            FontMetrics fontMetrics = g2d.getFontMetrics();
            Rectangle2D rect = fontMetrics.getStringBounds(textWatermark, g2d);

            // calculates the coordinate where the String is painted
            int centerX = (sourceImage.getWidth() - (int) rect.getWidth()) / 2;
            int centerY = sourceImage.getHeight() / 2;

            // paints the textual watermark
            g2d.drawString(textWatermark, centerX, centerY);

            g2d.dispose();
            return sourceImage;

        } catch (IOException e) {
            logger.error("Error process watermark on image %s %s", sourceImageFile.getPath(), String.valueOf(e));
        }
        return null;
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
