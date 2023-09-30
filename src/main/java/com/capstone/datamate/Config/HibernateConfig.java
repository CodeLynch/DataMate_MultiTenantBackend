package com.capstone.datamate.Config;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.hibernate.cfg.Environment;
import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.hibernate.engine.jdbc.connections.spi.MultiTenantConnectionProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

@Configuration
public class HibernateConfig {
    @Autowired
    private JpaProperties jpaProperties;

    @Bean
    JpaVendorAdapter jpaVendorAdapter(){
        return new HibernateJpaVendorAdapter();
    }

    @Bean
    LocalContainerEntityManagerFactoryBean entityManagerFactoryBean(
        DataSource dataSource,
        MultiTenantConnectionProvider multiTenantConnectionProvider,
        CurrentTenantIdentifierResolver currentTenantIdentifierResolver
    ){
        Map<String, Object> jpaPropertiesMap =  new HashMap<>(jpaProperties.getProperties());
        jpaPropertiesMap.put(Environment.MULTI_TENANT_CONNECTION_PROVIDER, multiTenantConnectionProvider);
        jpaPropertiesMap.put(Environment.MULTI_TENANT_IDENTIFIER_RESOLVER, currentTenantIdentifierResolver);
        jpaPropertiesMap.put(Environment.FORMAT_SQL, true);
        jpaPropertiesMap.put(Environment.SHOW_SQL, true);

        LocalContainerEntityManagerFactoryBean entManager = new LocalContainerEntityManagerFactoryBean();
        entManager.setDataSource(dataSource);
        entManager.setPackagesToScan("com.capstone");
        entManager.setJpaVendorAdapter(this.jpaVendorAdapter());
        entManager.setJpaPropertyMap(jpaPropertiesMap); 
        return entManager;
    }
}
