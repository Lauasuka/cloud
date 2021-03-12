package io.amoe.cloud.product.handler;

import io.amoe.cloud.base.AbstractProvider;
import io.amoe.cloud.entity.R;
import io.amoe.cloud.enums.BizResponseStatus;
import io.amoe.cloud.exception.AbstractException;
import io.amoe.cloud.exception.BizException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.annotation.Nullable;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * @author Amoe
 */
@Slf4j
@Order(-1)
@ControllerAdvice
public class GlobalExceptionHandler extends AbstractProvider {

    private R<Void> customizeExceptionHandle(HttpServletResponse response, AbstractException ex) {
        BizResponseStatus status = ex.getStatus();
        response.setStatus(status.getCode());
        R<Void> r = getResponse(status);
        logError(ex, r.getMsg());
        return r;
    }

    private void logError(Exception ex, @Nullable String msg) {
        Optional<StackTraceElement> error = Arrays.stream(ex.getStackTrace()).findFirst();
        log.error(
                "[Exception:{}]:[{}],[Exception position:{}]-[line:{}]",
                ex.getClass().getSimpleName(),
                StringUtils.isNotBlank(msg) ? msg : ex.getMessage(),
                error.map(StackTraceElement::getClassName).orElse("Unknown"),
                error.map(StackTraceElement::getLineNumber).orElse(-1)
        );
    }

    @ResponseBody
    @ExceptionHandler(BizException.class)
    public R<Void> bizExceptionHandle(HttpServletResponse response, BizException ex) {
        return customizeExceptionHandle(response, ex);
    }

    @ResponseBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public R<Void> argsNotValid(MethodArgumentNotValidException ex) {
        BindingResult bindingResult = ex.getBindingResult();
        List<ObjectError> errors = bindingResult.getAllErrors();
        String msg = errors.stream().findFirst().map(DefaultMessageSourceResolvable::getDefaultMessage).orElseThrow(() -> new BizException(BizResponseStatus.PARAM_ERROR));
        if (msg.startsWith("{") && msg.endsWith("}")) {
            String placeholder = msg.replace("{", "").replace("}", "");
            msg = mg.getMessage(placeholder, null, getCurrentLocale());
        }
        logError(ex, msg);
        R<Void> response = getResponse(BizResponseStatus.PARAM_ERROR);
        response.setMsg(msg);
        return response;
    }

    @ResponseBody
    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public R<Void> unprocessableEntity(HttpMessageNotReadableException ex) {
        logError(ex, null);
        return getResponse(BizResponseStatus.UNSUPPORTED_MEDIA_TYPE);
    }

    @ResponseBody
    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public R<Void> commonExceptionHandle(RuntimeException ex) {
        logError(ex, null);
        return error();
    }
}
