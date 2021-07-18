package io.amoe.cloud.base;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.amoe.cloud.entity.PageData;
import io.amoe.cloud.entity.R;
import io.amoe.cloud.enums.BizResponseStatus;
import io.amoe.cloud.enums.IStatusEnum;
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
    private static final String HEADER_FORWARD = "Location";
    private static final String ERROR_URI = "/error";

    @Resource
    protected HttpServletRequest request;

    @Resource
    protected HttpServletResponse response;

    @Autowired
    @Qualifier("messageSource")
    protected MessageSource mg;

    public <E> PageData<E> pageDataBuilder(IPage<E> page) {
        if (page == null) {
            return null;
        }
        PageData<E> pd = new PageData<>();
        pd.setCurrentPage(page.getCurrent());
        pd.setPages(page.getPages());
        pd.setPageSize(page.getSize());
        pd.setTotal(page.getTotal());
        pd.setRecords(page.getRecords());
        return pd;
    }

    public R<Void> success() {
        return success(null);
    }

    public <E> R<E> success(E object) {
        return getR(BizResponseStatus.OK, object);
    }

    public R<Void> error() {
        return error(null);
    }

    public <E> R<E> error(E object) {
        return getR(BizResponseStatus.ERROR, object);
    }

    public R<Void> getResponse(IStatusEnum status) {
        return getR(status, null);
    }

    public <E> R<E> getResponse(IStatusEnum status, E object) {
        return getR(status, object);
    }

    public void forward(String location) {
        setHeader(HEADER_FORWARD, StringUtils.isNotBlank(location) ? location : ERROR_URI);
        response.setStatus(HttpServletResponse.SC_FOUND);
    }

    private <E> R<E> getR(IStatusEnum status, E object) {
        Integer code = status.getCode();
        String defaultMessage = status.getMessage();
        String key = (KEY_PREFIX + status.getName()).toLowerCase();
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
