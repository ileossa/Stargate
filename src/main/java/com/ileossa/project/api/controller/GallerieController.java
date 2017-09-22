package com.ileossa.project.api.controller;

import com.ileossa.project.api.service.GallerieService;
import com.ileossa.project.uploadFiles.service.FileService;
import com.ileossa.project.uploadFiles.storage.StorageFileNotFoundException;
import com.ileossa.project.uploadFiles.storage.StorageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller
@RequestMapping()
public class GallerieController {

    private final Logger LOG = LoggerFactory.getLogger(this.getClass());

    private final StorageService storageService;
    private final FileService fileService;
    private final GallerieService gallerieService;

    @Autowired
    public GallerieController(StorageService storageService, FileService fileService, GallerieService gallerieService) {
        this.storageService = storageService;
        this.fileService = fileService;
        this.gallerieService = gallerieService;
    }

    @RequestMapping(value ="/gallerie", method = GET)
    public String listUploadedFiles(Model model) throws IOException {
        model.addAttribute("files", gallerieService.listAll());
        return "gallerie";
    }


    @RequestMapping(value = "/files/{filename:.+}", method = GET)
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {

        Resource file = storageService.loadAsResource(filename);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }

    @RequestMapping(value = "/gallerie", method = POST)
    public ModelAndView handleFileUpload(ModelAndView modelAndView, @RequestParam("file") MultipartFile file,
                                         RedirectAttributes redirectAttributes) {

        storageService.store(file);
        redirectAttributes.addFlashAttribute("message",
                "You successfully uploaded " + file.getOriginalFilename() + "!");

        modelAndView.setViewName("gallerie");
        return modelAndView;
    }

    @ExceptionHandler(StorageFileNotFoundException.class)
    public ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException exc) {
        return ResponseEntity.notFound().build();
    }

}
