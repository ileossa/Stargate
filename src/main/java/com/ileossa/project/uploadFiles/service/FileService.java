package com.ileossa.project.uploadFiles.service;

import com.ileossa.project.uploadFiles.dao.File;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * Created by ileossa on 15/08/2017.
 */
public interface FileService {

    public void save(File file);
    public void delete(long idFile);

    public void update(File file) throws Exception;

    public File newFileObject(MultipartFile file, long random, String classpath);
    public String detectTagInImage(String classpth);

    public String getTag(String name);

    public List<File> findAll();

    public File findFile(String originalURL);
}
