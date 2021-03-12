package io.amoe.cloud.order.config;

import com.google.common.collect.Lists;
import io.amoe.cloud.constant.GlobalConstant;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

/**
 * @author Amoe
 * @date 2020/5/27 16:30
 */
@Configuration
public class MessageSourceConfig {

    @Value("${spring.messages.basename}")
    private String basenamePaths;

    @Bean("messageSource")
    public ResourceBundleMessageSource messageSource() {
        ResourceBundleMessageSource  source = new ResourceBundleMessageSource ();
        source.setBasename(basenamePaths);
        source.setUseCodeAsDefaultMessage(true);
        source.setDefaultEncoding(StandardCharsets.UTF_8.name());

        if (StringUtils.isBlank(basenamePaths)) {
            source.setBasename(GlobalConstant.I18N_BASENAME);
        } else {
            String[] names = StringUtils.split(basenamePaths, ",");
            ArrayList<String> basenameList = Lists.newArrayList(names);
            if (!basenameList.contains(GlobalConstant.I18N_BASENAME)) {
                basenameList.add(GlobalConstant.I18N_BASENAME);
            }
            String[] array =  basenameList.toArray(new String[]{});
            source.setBasenames(array);
        }
        return source;
    }
}
