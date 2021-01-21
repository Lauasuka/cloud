package io.amoe.cloud.tools;

import io.amoe.cloud.enums.BizResponseStatus;
import io.amoe.cloud.exception.BizException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

/**
 * @author Amoe
 * @date 2020/7/28 15:39
 */
public final class FileUtils {
    @SneakyThrows
    public static String getFileType(InputStream inputStream) {
        if (inputStream == null) {
            throw new BizException(BizResponseStatus.PARAM_ERROR);
        }
        byte[] bytes = new byte[28];
        try {
            int readBytes = inputStream.read(bytes, 0, 28);
            if (readBytes <= 0) {
                throw new BizException(BizResponseStatus.PARAM_ERROR);
            }
            StringBuilder stringBuilder = new StringBuilder();
            for (byte item : bytes) {
                int v = item & 0xFF;
                String hv = Integer.toHexString(v);
                if (hv.length() < 2) {
                    stringBuilder.append(0);
                }
                stringBuilder.append(hv);
            }
            String magic = stringBuilder.toString();
            FileMagicType type = FileMagicType.getByMagic(magic);
            return type == null ? null : type.getFileType();
        } finally {
            inputStream.close();
        }
    }

    public static String getFileType(File file) {
        if (file == null) {
            throw new BizException(BizResponseStatus.PARAM_ERROR);
        }
        try {
            return getFileType(new FileInputStream(file));
        } catch (Exception e) {
            throw new BizException(BizResponseStatus.PARAM_ERROR);
        }
    }

    public static boolean isImage(String type) {
        if (StringUtils.isBlank(type)) {
            throw new BizException(BizResponseStatus.PARAM_ERROR);
        }
        return FileMagicType.JPG.getFileType().equalsIgnoreCase(type)
                || FileMagicType.PNG.getFileType().equalsIgnoreCase(type)
                || FileMagicType.GIF.getFileType().equalsIgnoreCase(type);
    }

    @Getter
    @AllArgsConstructor
    private enum FileMagicType {
        /* Image format */
        JPG("FFD8FF", "jpg"),
        PNG("89504E47", "png"),
        GIF("47494638", "gif"),

        /* Professional software format */
        CAD("41433130", "dwg"),
        ADOBE_PHOTOSHOP("38425053", "psd"),
        PDF("255044462D312E", "pdf"),
        TIFF("49492A00", "tif"),
        WINDOWS_BITMAP("424D", "bmp"),

        /* Compressing format */
        B_ZIP("425A", "bz"),
        G_ZIP("1F8B", "gz"),
        ZIP("504b0304", "zip"),
        TAR("7573746172", "tar"),

        /* Media format */
        MP3("49443303000000002176", "mp3");

        private final String magic;
        private final String fileType;

        public static FileMagicType getByMagic(String magic) {
            if (StringUtils.isBlank(magic)) {
                throw new IllegalArgumentException("Not support file type");
            }
            FileMagicType[] values = values();
            for (FileMagicType item : values) {
                if (magic.toUpperCase().startsWith(item.getMagic().toUpperCase())) {
                    return item;
                }
            }
            return null;
        }
    }
}
