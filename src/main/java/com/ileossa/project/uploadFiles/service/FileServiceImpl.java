package com.ileossa.project.uploadFiles.service;

import com.ileossa.project.classifier.LabelImage;
import com.ileossa.project.uploadFiles.dao.File;
import com.ileossa.project.uploadFiles.repository.FileRepository;
import com.ileossa.project.uploadFiles.storage.StorageProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created by ileossa on 15/08/2017.
 */
@Service
public class FileServiceImpl implements FileService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final FileRepository  fileRepository;
    private final LabelImage labelImage;

    @Value("${clasifier.modelPB}")
    private String classpthModelPB;

    @Autowired
    public FileServiceImpl(FileRepository fileRepository, LabelImage labelImage) {
        this.fileRepository = fileRepository;
        this.labelImage = labelImage;
    }


    @Override
    public void save(File file) {
        if(isExist(file)){
            try {
                throw new Exception(" File already exist in database: " + file.getName());
            } catch (Exception e) {
                logger.error(String.valueOf(e));
            }
        }
        fileRepository.save(file);
    }

    @Override
    public void delete(long idFile) {
        fileRepository.delete(idFile);
    }

    @Override
    public void update(File file) throws Exception {
        if(isExist(file)){
            fileRepository.save(file);
        }
        save(file);
    }

    @Override
    public File newFileObject(MultipartFile file, long random, String classpath) {
        String tag = detectTagInImage(classpath);
        File fileStore = new File(random + file.getOriginalFilename(), tag, classpath);
        return fileStore;
    }

    @Override
    public String detectTagInImage(String classpth) {
        String[] args = new String[2];
        args[0] = classpthModelPB;
        args[1] = classpth;
        String res = labelImage.startUp(args);
        return res;
    }


    // PRIVATE

    /**
     * If file (param) exist in dataBase @fileRepository , this method return TRUE.
     * @param file
     * @return
     */
    private boolean isExist(File file) {
        if(fileRepository.findFileByName(file.getName()) == null) {
            return false;
        }
        return true;
    }
}
