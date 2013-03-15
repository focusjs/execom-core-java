package eu.execom.core.service;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.mail.internet.InternetAddress;
import javax.mail.util.ByteArrayDataSource;

import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import eu.execom.core.model.UserRole;
import eu.execom.core.persistence.UserDao;
import eu.execom.core.service.FileServiceImpl.ImageByteArrayDto;
import eu.execom.core.util.TemplateGenerator;
import eu.execom.core.util.TemplateGeneratorExcpetion;

/**
 * Implementation of {@link MailService}.
 * 
 * @author Dusko Vesin
 */
// FIXME send emails from new thread to avoid problems with big latency of SMTP server
@Service
class MailServiceImpl implements MailService {

    private static final String ERROR_WHILE_SENDING_EMAIL = "Error while sending email.";
    private static final String EMAIL_SUCCESSFULLY_SENT = "Email successfully sent.";

    private static final Logger LOG = LoggerFactory.getLogger(MailServiceImpl.class);

    @Autowired
    private UserService userServiceHelper;
    @Autowired
    private UserDao userDao;

    @Value("${mail.smtp.host}")
    private String hostName;
    @Value("${mail.smtp.port}")
    private int portNumber;

    @Override
    public void sendEmail(final String toAddress, final String toName, final String subject,
            final Map<String, Object> vars, final Map<String, ImageByteArrayDto> files,
            final TemplateGenerator templateGenerator) {

        try {
            final HtmlEmail email = new HtmlEmail();

            final Map<String, Object> variables = new HashMap<String, Object>();
            variables.putAll(vars);
            // from file objects generating
            for (final Entry<String, ImageByteArrayDto> embedFile : files.entrySet()) {
                final ByteArrayDataSource fileDataSource = new ByteArrayDataSource(embedFile.getValue().getImage(),
                        "image/" + embedFile.getValue().getImageType());
                email.embed(fileDataSource, embedFile.getKey());
                variables.put(embedFile.getKey(), embedFile.getKey());
            }

            final String mailContent = templateGenerator.generetaContent(variables);
            email.addTo(toAddress, toName);

            final List<InternetAddress> emailAddress = new ArrayList<InternetAddress>();
            emailAddress.add(new InternetAddress(toAddress, toName));

            email.setTo(emailAddress);
            email.setFrom(userDao.findByRole(UserRole.ADMIN).get(0).getEmail(), "ExeCom core.");
            email.setSubject(subject);

            email.setHtmlMsg(mailContent);
            // email.setContent(mimeMultipart);
            email.setHostName(hostName);
            email.setSmtpPort(portNumber);
            email.setCharset("UTF-8");
            email.send();

            LOG.debug(EMAIL_SUCCESSFULLY_SENT);
        } catch (final EmailException e) {
            LOG.error(ERROR_WHILE_SENDING_EMAIL, e.getMessage());
            e.printStackTrace();
        } catch (final UnsupportedEncodingException e) {
            LOG.error(ERROR_WHILE_SENDING_EMAIL, e.getMessage());
            e.printStackTrace();
        } catch (final TemplateGeneratorExcpetion e) {
            e.printStackTrace();
            throw new IllegalStateException(e);

        }
    }

}
