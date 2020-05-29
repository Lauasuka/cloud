package io.amoe.cloud.base;

import io.amoe.cloud.entity.R;
import io.amoe.cloud.enums.BizResponseStatus;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

/**
 * @author Amoe
 */
public class AbstractProvider {

    private static final String KEY_PREFIX = "response.";

    @Resource
    protected HttpServletRequest request;

    @Resource
    protected HttpServletResponse response;

    @Autowired
    @Qualifier("messageSource")
    protected MessageSource mg;

    public <E> R<E> success() {
        return success(null);
    }

    public <E> R<E> success(E object) {
        return getR(BizResponseStatus.OK, object);
    }

    public <E> R<E> error() {
        return error(null);
    }

    public <E> R<E> error(E object) {
        return getR(BizResponseStatus.ERROR, object);
    }

    public R<?> getResponse(BizResponseStatus status) {
        return getR(status, null);
    }

    public <E> R<E> getResponse(BizResponseStatus status, E object) {
        return getR(status, object);
    }

    private <E> R<E> getR(BizResponseStatus status, E object) {
        Integer code = status.getCode();
        String defaultMessage = status.getMessage();
        String key = (KEY_PREFIX + status.name()).toLowerCase();
        String localeMessage = mg.getMessage(key, null, getCurrentLocale());
        if (StringUtils.isBlank(localeMessage)) {
            localeMessage = defaultMessage;
        }
        return R.getInstance(code, localeMessage, object);
    }

    protected String getHeader(String header) {
        return request.getHeader(header);
    }

    protected void setHeader(String header, String value) {
        response.setHeader(header, value);
    }

    protected Locale getCurrentLocale() {
        return LocaleContextHolder.getLocale();
    }
}
