package eu.execom.core.util;

import java.io.IOException;
import java.util.Map;

import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import freemarker.template.Configuration;
import freemarker.template.TemplateException;

/**
 * Free marker {@link TemplateGenerator} implementation.
 * 
 * @author Dusko Vesin
 * 
 */
public class FreeMarkerGeneratorImpl implements TemplateGenerator {

    private final Configuration configuration;
    private final String templateFileName;

    public FreeMarkerGeneratorImpl(final Configuration configuration, final String templateFileName) {
        super();
        this.configuration = configuration;
        this.templateFileName = templateFileName;
    }

    @Override
    public String generetaContent(final Map<String, Object> vars) throws TemplateGeneratorExcpetion {
        String mailContent;
        try {
            mailContent = FreeMarkerTemplateUtils.processTemplateIntoString(
                    configuration.getTemplate(templateFileName), vars);
        } catch (final IOException e) {
            throw new TemplateGeneratorExcpetion(e);
        } catch (final TemplateException e) {
            throw new TemplateGeneratorExcpetion(e);
        }
        return mailContent;
    }

}
