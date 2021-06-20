package com.ecare.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.ui.context.support.ResourceBundleThemeSource;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ThemeResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.theme.CookieThemeResolver;
import org.springframework.web.servlet.theme.ThemeChangeInterceptor;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import javax.sql.DataSource;
import java.util.List;
import java.util.Properties;

@Configuration
@ComponentScan(basePackages = "com.ecare")
@EnableWebMvc
@EnableTransactionManagement
public class Config implements WebMvcConfigurer {

    @Bean
    public ViewResolver viewResolver() {
        InternalResourceViewResolver internalResourceViewResolver = new InternalResourceViewResolver();
        internalResourceViewResolver.setPrefix("/WEB-INF/view/");
        internalResourceViewResolver.setSuffix(".jsp");
        return internalResourceViewResolver;
    }

    //region # not used, for css
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        /*  registry.addResourceHandler("/scripts/**").addResourceLocations("/WEB-INF/scripts/");*/
     /*  registry.addResourceHandler("/styles/**")
               .addResourceLocations("/WEB-INF/view/resources/styles/");
        registry.addResourceHandler("/images/**")
                .addResourceLocations("/WEB-INF/view/resources/images/");
        registry.addResourceHandler("/themes/**")
                .addResourceLocations("/WEB-INF/view/resources/themes/");*/
        /*  registry.addResourceHandler("/fonts/**").addResourceLocations("/WEB-INF/fonts/");*/
        registry.addResourceHandler("/resources/**")
                .addResourceLocations("/resources/");
    }

    /**
     * addInterceptors, ThemeChangeInterceptor - for change thema
     *
     * @param registry todo after security
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(themeChangeInterceptor());
    }

    @Bean
    public ThemeChangeInterceptor themeChangeInterceptor() {
        ThemeChangeInterceptor interceptor = new ThemeChangeInterceptor();
        interceptor.setParamName("theme");
        return interceptor;
    }

    @Bean
    public ResourceBundleThemeSource resourceBundleThemeSource() {
        return new ResourceBundleThemeSource();
    }

    @Bean
    public ThemeResolver themeResolver() {
        CookieThemeResolver themeResolver = new CookieThemeResolver();
        themeResolver.setDefaultThemeName("black");
        return themeResolver;
    }
//endregion

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }


    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();  //new BasicDataSource ?
        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUrl("jdbc:postgresql://localhost:5432/eCare");
        dataSource.setUsername("postgres");
        dataSource.setPassword("root");
        return dataSource;
    }


    private Properties hibernateProperties() {
        Properties hibernateProperties = new Properties();
        hibernateProperties.setProperty("hibernate.hbm2ddl.auto", "validate");
        hibernateProperties.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQL10Dialect");
        hibernateProperties.setProperty("hibernate.connection.Charset", "UTF-8");
        hibernateProperties.setProperty("hibernate.connection.CharacterEncoding", "UTF-8");
        hibernateProperties.setProperty("hibernate.connection.Useunicode", "true");
        hibernateProperties.setProperty("hibernate.connection.sql-script-encoding", "UTF-8");
        hibernateProperties.setProperty("hibernate.show_sql", "true");
        hibernateProperties.setProperty("hibernate.format_sql", "true");
        return hibernateProperties;
    }

    @Bean
    public LocalSessionFactoryBean sessionFactory() {
        LocalSessionFactoryBean sessionFactory = new
                LocalSessionFactoryBean();
        sessionFactory.setDataSource(dataSource());
        sessionFactory.setPackagesToScan("com.ecare.domain");
        sessionFactory.setHibernateProperties(hibernateProperties());
        return sessionFactory;
    }

    @Bean
    public HibernateTransactionManager hibernateTransactionManager() {
        HibernateTransactionManager transactionManager
                = new HibernateTransactionManager();
        transactionManager.setSessionFactory(sessionFactory().
                getObject());
        return transactionManager;
    }

    @Bean  //https://stackoverflow.com/questions/29888458/spring-security-role-hierarchy-not-working-using-java-config
    public RoleHierarchy roleHierarchy() {
        RoleHierarchyImpl hierarchy = new RoleHierarchyImpl();
        hierarchy.setHierarchy("ROLE_ADMIN >ROLE_ROLE_EMPLOYEE and ROLE_EMPLOYEE > ROLE_CLIENT");
        return hierarchy;
    }

}