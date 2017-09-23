package com.ileossa.project.shorter;

import com.ileossa.project.exception.UrlShortException;
import com.ileossa.project.prism.PrismService;
import com.ileossa.project.uploadFiles.service.FileService;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.output.ByteArrayOutputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Path;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import static org.springframework.web.bind.annotation.RequestMethod.GET;


/**
 * Created by ileossa on 03/08/2017.
 */
@Controller
public class UrlShorterController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private UrlShorterService urlShorterService;
    private PrismService prismService;
    private FileService fileService;

    private static final DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");



    @Autowired
    public UrlShorterController(UrlShorterServiceImpl urlShorterService, PrismService prismService, FileService fileService) {
        this.urlShorterService = urlShorterService;
        this.prismService = prismService;
        this.fileService = fileService;
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


    @RequestMapping(value = "/share/{shortUrl}", method = GET, produces = MediaType.IMAGE_JPEG_VALUE)
    @ResponseBody
    public byte[] redirectOriginalUrl(@PathVariable String shortUrl, HttpServletRequest request, HttpServletResponse response) {

        String urlOriginal = urlShorterService.getOriginalUrl(shortUrl);
        String relativePath = fileService.findFile(urlOriginal).getClasspath();
        // register acitivity
        prismService.logAccess(request);
        try {
            BufferedImage img = urlShorterService.watermark(urlOriginal);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ImageIO.write(img, FilenameUtils.getExtension(urlOriginal), bos);
            return bos.toByteArray();
        } catch (FileNotFoundException e) {
            logger.error( e.getLocalizedMessage() + " Fail loading file, not found from url shorter: " + shortUrl);
        } catch (IOException e) {
            logger.error(e.getLocalizedMessage() + " Fail loading file, not found from url shorter: " + shortUrl);
        } catch (IllegalArgumentException e){
            logger.error(e.getLocalizedMessage() + " Fail loading file, not found from url shorter: " + shortUrl);
        }
       return "Error loading, file not found.".getBytes();
    }
}