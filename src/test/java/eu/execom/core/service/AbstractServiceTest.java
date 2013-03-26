package eu.execom.core.service;

import java.util.ArrayList;
import java.util.List;

import javax.mail.MessagingException;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.subethamail.wiser.Wiser;
import org.subethamail.wiser.WiserMessage;

import eu.execom.core.ExecomCoreTest;
import eu.execom.core.TestConfiguration;
import eu.execom.core.persistence.PersistenceConfiguration;

/**
 * {@link ExecomCoreTest} service base class.
 * 
 * @author Dusko Vesin
 */
@SuppressWarnings("unchecked")
@ContextConfiguration(loader = AnnotationConfigContextLoader.class, classes = {TestConfiguration.class,
        PersistenceConfiguration.class, ServiceConfiguration.class,})
public abstract class AbstractServiceTest extends ExecomCoreTest {

    @Autowired
    private Wiser wiser;

    private List<WiserMessage> assertedMessages = new ArrayList<WiserMessage>();

    @Before
    public void initWizer() {
        assertedMessages = new ArrayList<WiserMessage>(wiser.getMessages());
    }

    @After
    public void cleanupWiser() throws MessagingException {
        if (assertedMessages.size() != wiser.getMessages().size()) {

            final ArrayList<WiserMessage> notAssertedEmails = new ArrayList<WiserMessage>(wiser.getMessages());
            notAssertedEmails.removeAll(assertedMessages);

            final StringBuilder message = new StringBuilder("Not asserted emails:");

            for (final WiserMessage notAssertedMessage : notAssertedEmails) {
                message.append(notAssertedMessage.getEnvelopeReceiver()).append(" '")
                        .append(notAssertedMessage.getMimeMessage().getSubject()).append("' ,");
            }
            Assert.assertEquals(message.toString(), assertedMessages.size(), wiser.getMessages().size());
        }

        wiser.getMessages().clear();
        assertedMessages = new ArrayList<WiserMessage>();
    }

    protected void assertEmail(final String email, final String subject) {

        final List<WiserMessage> messages = wiser.getMessages();
        for (final WiserMessage wiserMessage : messages) {
            if (!assertedMessages.contains(wiserMessage)) {

                final boolean isValidReceiver = wiserMessage.getEnvelopeReceiver().equalsIgnoreCase(email);
                boolean isValidSubject = false;
                try {
                    isValidSubject = wiserMessage.getMimeMessage().getSubject().equalsIgnoreCase(subject);
                } catch (final MessagingException e) {
                    e.printStackTrace();
                }

                if (isValidReceiver && isValidSubject) {
                    assertedMessages.add(wiserMessage);
                    return;
                }
            }
        }

        Assert.fail("Email on mail " + email + " is not sent");
    }

    protected void assertEmailNotSent(final String email, final String subject) {

        final List<WiserMessage> messages = wiser.getMessages();
        for (final WiserMessage wiserMessage : messages) {
            if (!assertedMessages.contains(wiserMessage)) {

                final boolean isValidReceiver = wiserMessage.getEnvelopeReceiver().equalsIgnoreCase(email);
                boolean isValidSubject = false;
                try {
                    isValidSubject = wiserMessage.getMimeMessage().getSubject().equalsIgnoreCase(subject);
                } catch (final MessagingException e) {
                    e.printStackTrace();
                }

                if (isValidReceiver && isValidSubject) {
                    assertedMessages.add(wiserMessage);
                    Assert.fail("Email on mail " + email + " is sent");
                }
            }
        }

    }

}
