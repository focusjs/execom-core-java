package eu.execom.core.persistence;

import java.beans.PropertyVetoException;
import java.util.Properties;

import javax.sql.DataSource;

import liquibase.integration.spring.SpringLiquibase;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import eu.execom.core.model.User;

/**
 * Persistence layer configuration.
 * 
 * @author Dusko Vesin
 * 
 */
@Configuration
@ComponentScan(basePackages = {"eu.execom.core.persistence"})
public class PersistenceConfiguration {

    /**
     * Get data source for accessing to DB.
     * 
     * @param driver
     *            to be used
     * @param url
     *            to the db location
     * @param useraname
     *            for accessing to DB
     * @param password
     *            of DB user
     * @return {@link DataSource}
     * @throws PropertyVetoException
     *             if Driver class is not valid.
     */
    @Bean
    public DataSource dataSource(@Value("${jdbc.driver}") final String driver, @Value("${jdbc.url}") final String url,
            @Value("${jdbc.username}") final String useraname, @Value("${jdbc.password}") final String password)
            throws PropertyVetoException {

        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        dataSource.setDriverClass(driver);
        dataSource.setJdbcUrl(url);
        dataSource.setUser(useraname);
        dataSource.setPassword(password);
        return dataSource;
    }

    /**
     * TODO add comments.
     * 
     * @param liquibase
     *            is necessary to be sure that data migration is performed before hibernate validation
     * @param dialect
     *            to be used for communication with DB
     * @param dataSource
     *            to be used for DB accessing
     * @return {@link LocalSessionFactoryBean}
     */
    @Bean
    public LocalSessionFactoryBean sessionFactoryBean(final SpringLiquibase liquibase,
            @Value("${hibernate.dialect}") final String dialect, final DataSource dataSource) {
        Properties props = new Properties();
        props.put("hibernate.dialect", dialect);
        props.put("hibernate.format_sql", "true");
        props.put("hibernate.hbm2ddl.auto", "validate");

        LocalSessionFactoryBean bean = new LocalSessionFactoryBean();
        bean.setAnnotatedClasses(new Class[] {User.class});
        bean.setHibernateProperties(props);
        bean.setDataSource(dataSource);
        return bean;
    }

    /**
     * Create hibernate transactional manager.
     * 
     * @param sessionFactoryBean
     *            session factory
     * @return new {@link HibernateTransactionManager}
     */
    @Bean
    public HibernateTransactionManager transactionManager(final LocalSessionFactoryBean sessionFactoryBean) {
        return new HibernateTransactionManager(sessionFactoryBean.getObject());
    }

    /**
     * Liquibase initialization.
     * 
     * @param dataSource
     *            to be used for DB comunication.
     * @return {@link SpringLiquibase}.
     */
    @Bean
    public SpringLiquibase liquiBase(final DataSource dataSource) {
        SpringLiquibase liquibase = new SpringLiquibase();
        liquibase.setContexts("test, production");
        liquibase.setChangeLog("classpath:META-INF/db/db-changelog.xml");
        liquibase.setDataSource(dataSource);
        return liquibase;
    }

}
