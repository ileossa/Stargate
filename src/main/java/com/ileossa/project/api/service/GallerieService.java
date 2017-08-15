package com.ileossa.project.api.service;

import com.ileossa.project.api.controller.GallerieController;
import com.ileossa.project.uploadFiles.dao.File;
import com.ileossa.project.uploadFiles.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ileossa on 15/08/2017.
 */
@Service
public class GallerieService {

    private static FileService fileService;

    @Autowired
    public GallerieService(FileService fileService) {
        this.fileService = fileService;
    }


    public List<File> listAll(){
        List<File> files = new ArrayList<>();
        fileService.findAll().forEach(files::add);
        for (File file: files) {
            String classpath = MvcUriComponentsBuilder.fromMethodName(GallerieController.class, "serveFile", file.getName().toString()).build().toString();
            file.setClasspath(classpath);
        }
        return files;
    }

}