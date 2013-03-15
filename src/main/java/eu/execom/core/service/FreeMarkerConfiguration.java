package eu.execom.core.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ui.freemarker.FreeMarkerConfigurationFactoryBean;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import freemarker.template.TemplateException;
import freemarker.template.utility.HtmlEscape;
import freemarker.template.utility.XmlEscape;

/**
 * Free marker configuration.
 * 
 * @author Dusko Vesin
 * 
 */
@Configuration
public class FreeMarkerConfiguration {

    @Bean
    public FreeMarkerConfigurationFactoryBean freeMarkerConfigurationFactoryBean(final XmlEscape xmlEscape,
            final HtmlEscape htmlEscape) {

        FreeMarkerConfigurationFactoryBean freeMarkerConfigurationFactoryBean = new FreeMarkerConfigurationFactoryBean();

        Properties freeMarkerSettings = new Properties();
        freeMarkerSettings.setProperty("datetime_format", "MM/dd/yyyy");
        freeMarkerSettings.setProperty("number_format", "0.##");
        freeMarkerSettings.setProperty("whitespace_stripping", "true");
        freeMarkerConfigurationFactoryBean.setFreemarkerSettings(freeMarkerSettings);

        HashMap<String, Object> variables = new HashMap<String, Object>();
        variables.put("xml_escape", xmlEscape);
        variables.put("html_escape", htmlEscape);
        freeMarkerConfigurationFactoryBean.setFreemarkerVariables(variables);

        freeMarkerConfigurationFactoryBean.setTemplateLoaderPath("classpath:META-INF/templates");

        return freeMarkerConfigurationFactoryBean;
    }

    @Bean
    public XmlEscape fmXmlEscape() {
        return new XmlEscape();
    }

    @Bean
    public HtmlEscape fmHtmlEscape() {
        return new HtmlEscape();
    }

    @Bean
    public FreeMarkerConfigurer freeMarkerConfig(
            final FreeMarkerConfigurationFactoryBean freeMarkerConfigurationFactoryBean) throws IOException,
            TemplateException {

        FreeMarkerConfigurer freeMarkerConfigurer = new FreeMarkerConfigurer();
        freeMarkerConfigurer.setConfiguration(freeMarkerConfigurationFactoryBean.createConfiguration());
        return freeMarkerConfigurer;

    }

}
