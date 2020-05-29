package io.amoe.cloud.constant;

import java.time.format.DateTimeFormatter;

/**
 * @author Amoe
 * @date 2020/5/18 11:20
 */
public interface GlobalConstant {
    String DATE_FORMATTER_PATTERN = "yyyy-MM-dd";

    String DATE_TIME_FORMATTER_PATTERN = "yyyy-MM-dd HH:mm:ss";

    DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern(DATE_FORMATTER_PATTERN);
    DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern(DATE_TIME_FORMATTER_PATTERN);

    String I18N_BASENAME = "i18n/response";
}
