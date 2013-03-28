package eu.execom.core.service;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import eu.execom.core.service.FileServiceImpl.ImageByteArrayDto;
import eu.execom.core.util.TemplateGenerator;
import eu.execom.core.util.TemplateGeneratorExcpetion;

/**
 * {@link MailService} test class.
 * 
 * @author ntrkulja
 * 
 */
public class MailServiceTest extends AbstractServiceTest {

    @Autowired
    private InitServiceImpl initService;
    @Autowired
    private MailServiceImpl mailService;

    @Before
    public void setUp() {

        initService.initAdminUser();

    }

    @Test
    public void testSendEmail() {

        // init
        final Map<String, ImageByteArrayDto> files = new HashMap<String, ImageByteArrayDto>();
        final Map<String, Object> variables = new HashMap<String, Object>();

        // method
        final String toAddress = "email@email.email";
        final String subject = "subject";
        initWizer();
        takeSnapshot();
        mailService.sendEmail(toAddress, "toName", subject, variables, files, new TemplateGenerator() {

            @Override
            public String generetaContent(final Map<String, Object> vars) throws TemplateGeneratorExcpetion {
                return "MailContent";
            }
        });

        // assert
        assertEmail(toAddress, subject);

    }
}
