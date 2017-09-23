package com.ileossa.project.uploadFiles.dao;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Created by ileossa on 14/08/2017.
 */
@Entity
public class File {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO )
    long id;

    private String name;

    private String tag;

    private String classpath;

    protected File() {
    }

    public File(String name, String tag, String classpath) {
        this.name = name;
        this.tag = tag;
        this.classpath = classpath;
    }

    public String getClasspath() {
        return classpath;
    }

    public void setClasspath(String classpath) {
        this.classpath = classpath;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}
