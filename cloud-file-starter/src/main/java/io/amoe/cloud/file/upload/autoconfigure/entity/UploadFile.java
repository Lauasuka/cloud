package io.amoe.cloud.file.upload.autoconfigure.entity;

import java.io.Serializable;

/**
 * @author Amoe
 * @date 2020/7/28 15:01
 */
public class UploadFile implements Serializable {
    private String internetUrl;
    private String intranetUrl;
    private Long fileSize;
    private String fileHash;
    private String originalName;
    private String name;
    private String type;
    private Integer imageWidth;
    private Integer imageHeight;

    public String getInternetUrl() {
        return internetUrl;
    }

    public void setInternetUrl(String internetUrl) {
        this.internetUrl = internetUrl;
    }

    public String getIntranetUrl() {
        return intranetUrl;
    }

    public void setIntranetUrl(String intranetUrl) {
        this.intranetUrl = intranetUrl;
    }

    public Long getFileSize() {
        return fileSize;
    }

    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }

    public String getFileHash() {
        return fileHash;
    }

    public void setFileHash(String fileHash) {
        this.fileHash = fileHash;
    }

    public String getOriginalName() {
        return originalName;
    }

    public void setOriginalName(String originalName) {
        this.originalName = originalName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getImageWidth() {
        return imageWidth;
    }

    public void setImageWidth(Integer imageWidth) {
        this.imageWidth = imageWidth;
    }

    public Integer getImageHeight() {
        return imageHeight;
    }

    public void setImageHeight(Integer imageHeight) {
        this.imageHeight = imageHeight;
    }
}
