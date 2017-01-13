package cloud.molddata.parser.cv.config;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import javax.persistence.EntityManagerFactory;
import java.beans.PropertyVetoException;
import java.util.Properties;

/**
 * Created by Андрей on 15.07.2016.
 */
@Configuration
@EnableTransactionManagement
//@EnableJpaRepositories
@ComponentScan(basePackages = {"cloud.molddata.parser.cv"})
//@PropertySource("classpath:database.properties") //or set in PlaceSourceHolder
public class RootConfig {
    @Value("${jdbc.driverClassName}")
    String driverClass;
    @Value("${jdbc.url}")
    String jdbcUrl;
    @Value("${jdbc.username}")
    String user;
    @Value("${jdbc.password}")
    String password;
    @Value("${hibernate.dialect}")
    String dialect;

    @Bean
    public static PropertySourcesPlaceholderConfigurer  propertyPlaceholderConfigurer() {
        PropertySourcesPlaceholderConfigurer  propertyPlaceholderConfigurer =
                new PropertySourcesPlaceholderConfigurer ();
        propertyPlaceholderConfigurer.setLocation(new ClassPathResource("database.properties"));
        // Allow for other PropertyPlaceholderConfigurer instances.
        propertyPlaceholderConfigurer.setIgnoreUnresolvablePlaceholders(true);
        return propertyPlaceholderConfigurer;
        //return new PropertySourcesPlaceholderConfigurer();
    }
    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() throws PropertyVetoException {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource());
        em.setPackagesToScan("cloud.molddata.parser.cv.model");

        JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);
        em.setJpaProperties(getHibernateProperties());

        return em;
    }
    @Bean
    public BasicDataSource dataSource() throws PropertyVetoException {

        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName(driverClass);
        dataSource.setUrl(jdbcUrl);
        dataSource.setUsername(user);
        dataSource.setPassword(password);
        //dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        //dataSource.setUrl("jdbc:mysql://localhost:3306/dropzone_test?createDatabaseIfNotExist=true&amp;amp;useUnicode=true&amp;amp;characterEncoding=utf-8&amp;amp;autoReconnect=true\n");
        //dataSource.setUsername("root");
        //dataSource.setPassword("root");
        return dataSource;
    }

    @Bean (name = "hibernateProperties")
    public Properties getHibernateProperties() {

        Properties hibernateProperties = new Properties();

        hibernateProperties.put("hibernate.hbm2ddl.auto", "update");
        hibernateProperties.put("hibernate.dialect",dialect);
        //hibernateProperties.put("hibernate.dialect", "org.hibernate.dialect.MySQL5InnoDBDialect");
        hibernateProperties.put("hibernate.show_sql", true);
        hibernateProperties.put("hibernate.generate_statistics", false);
        hibernateProperties.put("hibernate.use_sql_comments", false);

        return hibernateProperties;
    }

    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory emf){
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(emf);

        return transactionManager;
    }

    public PersistenceExceptionTranslationPostProcessor exceptionTranslationPostProcessor(){
        return new PersistenceExceptionTranslationPostProcessor();
    }
}

