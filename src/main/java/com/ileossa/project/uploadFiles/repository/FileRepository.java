package com.ileossa.project.uploadFiles.repository;

import com.ileossa.project.uploadFiles.dao.File;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by ileossa on 14/08/2017.
 */
public interface FileRepository extends JpaRepository<File, Long>{

    File findFileByName(String name);
}
