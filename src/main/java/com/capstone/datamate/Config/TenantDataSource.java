package com.capstone.datamate.Config;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.stereotype.Component;

import com.capstone.datamate.Repository.DatasourceConfigRepository;

import jakarta.annotation.PostConstruct;

@Component
public class TenantDataSource implements Serializable{
    private HashMap<String, DataSource> dataSources = new HashMap<>();

    @Autowired
    private DatasourceConfigRepository configRepo;

    private DataSource createDataSource(String name) {
        DatasourceConfig config = configRepo.findByName(name);
        if (config != null) {
            DataSourceBuilder factory = DataSourceBuilder
                    .create().driverClassName(config.getDriverClassName())
                    .username(config.getUsername())
                    .password(config.getPassword())
                    .url(config.getUrl());
            DataSource ds = factory.build();
            return ds;
        }
        return null;
    }

    public DataSource getDataSource(String name){
        if(dataSources.get(name) != null){
            return dataSources.get(name);
        }
        DataSource dataSource = createDataSource(name);
        
        if(dataSource != null){
            dataSources.put(name, dataSource);    
        }
        return dataSource;
    }

    @PostConstruct
    public Map<String, DataSource> getAll() {
        List<DatasourceConfig> configList = configRepo.findAll();
        Map<String, DataSource> result = new HashMap<>();
        for (DatasourceConfig config : configList) {
            DataSource dataSource = getDataSource(config.getName());
            result.put(config.getName(), dataSource);
        }
        return result;
    }


}
