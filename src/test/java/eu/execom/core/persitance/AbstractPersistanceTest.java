package eu.execom.core.persitance;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import eu.execom.core.ExecomCoreTest;
import eu.execom.core.TestConfiguration;
import eu.execom.core.persistence.PersistenceConfiguration;
import eu.execom.core.service.ServiceConfiguration;

/**
 * {@link ExecomCoreTest} persistence test base class.
 * 
 * @author Dusko Vesin
 */
@ContextConfiguration(loader = AnnotationConfigContextLoader.class, classes = {TestConfiguration.class,
        PersistenceConfiguration.class, ServiceConfiguration.class})
public abstract class AbstractPersistanceTest extends ExecomCoreTest {

}
