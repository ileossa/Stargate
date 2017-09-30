package com.ileossa.project.mobile.android;

import com.ileossa.project.api.service.GallerieService;
import com.ileossa.project.uploadFiles.dao.File;
import com.ileossa.project.uploadFiles.service.FileService;
import com.ileossa.project.uploadFiles.storage.StorageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

/**
 * Created by ileossa on 30/09/2017.
 */
@Controller
@RequestMapping("/v1")
public class AndroidController {

    private final Logger LOG = LoggerFactory.getLogger(this.getClass());

    private final StorageService storageService;
    private final FileService fileService;
    private final GallerieService gallerieService;

    @Autowired
    public AndroidController(StorageService storageService, FileService fileService, GallerieService gallerieService) {
        this.storageService = storageService;
        this.fileService = fileService;
        this.gallerieService = gallerieService;
    }

    @RequestMapping(value = "/all", method = GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<File> getAllElement(){
        return  gallerieService.listAll();
    }


    @RequestMapping(value = "/upload", method = POST)
    public int upoadFile(@RequestParam("files") MultipartFile[] uplMultipartFiles) throws IOException {
        for (MultipartFile file : uplMultipartFiles) {
            storageService.store(file);
        }
        return HttpServletResponse.SC_OK;
    }



}
