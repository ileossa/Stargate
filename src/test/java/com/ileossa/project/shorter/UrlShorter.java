package com.ileossa.project.shorter;

import com.ileossa.project.email.config.EmailConfig;
import com.ileossa.project.mail.impl.EmailServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.net.URL;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

/**
 * Created by ileossa on 03/08/2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(classes = UrlShorterConfiguration.class)
@Import(UrlShorterServiceImpl.class)
public class UrlShorter {

    @LocalServerPort
    private int port;

    private URL base;
    private String shortUrl;

    @Autowired
    private TestRestTemplate template;
    @Autowired
    private UrlShorterServiceImpl urlShorterService;

    @Before
    public void setUp() throws Exception {
        this.base = new URL("http://localhost:" + port + "/");
        this.shortUrl = urlShorterService.getShorterUrl(base.getHost());
    }

    @Test
    public void get_shorter_url() throws Exception {
        String response = urlShorterService.getOriginalUrl(shortUrl);
        assertThat(response, equalTo(shortUrl));
    }
}
