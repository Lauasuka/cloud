package io.amoe.cloud.file.upload.autoconfigure.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;

/**
 * @author Amoe
 * @date 2020/7/28 15:01
 */
@Data
public class UploadFile implements Serializable {
    private String internetUrl;
    private String intranetUrl;
    private Long fileSize;
    private String fileHash;
    private String originalName;
    private String name;
    private String type;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer imageWidth;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer imageHeight;
}
