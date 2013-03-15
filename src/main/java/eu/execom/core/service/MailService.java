package eu.execom.core.service;

import java.util.Map;

import eu.execom.core.service.FileServiceImpl.ImageByteArrayDto;
import eu.execom.core.util.TemplateGenerator;

/**
 * Interface defining mail sending service.
 * 
 */
public interface MailService {

    /**
     * Send specified email message to given address with recipients name and subject.
     * 
     * @param toAddress
     *            email address of email receiver
     * @param toName
     *            name of email receiver
     * @param subject
     *            of the email
     * @param variables
     *            for email generation
     * @param files
     *            that are part of email
     * @param templateGenerator
     *            to be used to generate mail context
     */
    void sendEmail(String toAddress, String toName, String subject, Map<String, Object> variables,
            Map<String, ImageByteArrayDto> files, TemplateGenerator templateGenerator);

}
