package com.justclick.authentication.configs;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.stereotype.Component;

import com.justclick.authentication.entities.Tenant;
import com.justclick.authentication.repositories.TenantRepository;

@Component
public class TenantDataSource implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6289300755457616073L;

	private HashMap<String, DataSource> dataSources = new HashMap<>();
	
	@Autowired
	TenantRepository tenantRepo;
    
    @Value("${spring.datasource.username}")
    private String usernameDB;
    
    @Value("${spring.datasource.password}")
    private String passwordDB;
    
    @Value("${spring.datasource.url}")
    private String urlDB;

    public DataSource getDataSource(String schemaName) {
        if (dataSources.get(schemaName) != null) {
            return dataSources.get(schemaName);
        }
        DataSource dataSource = createDataSource(schemaName);
        if (dataSource != null) {
            dataSources.put(schemaName, dataSource);
        }
        return dataSource;
    }
    
    
    public Map<String, DataSource> getAll() {
        List<Tenant> tenantList = tenantRepo.findAll();
        Map<String, DataSource> result = new HashMap<>();
        for (Tenant tenant : tenantList) {
        	String schemaName = getSchemaName(tenant.getId());
            DataSource dataSource = getDataSource(schemaName);
            result.put(schemaName, dataSource);
        }
        
        return result;
    }
    
    /**
     * Schema Name in DB is UUID without char "-"
     * @param id
     * @return
     */
    public String getSchemaName(UUID id) {
    	return id.toString().replace("-", "");
    }

    
    public DataSource createDataSource(String schemaName) {
    	DataSourceBuilder<?> factory = DataSourceBuilder
                .create().driverClassName("org.postgresql.Driver")
                .username(usernameDB)
                .password(passwordDB)
                .url(urlDB+"&currentSchema="+schemaName);
        DataSource ds = factory.build();
        return ds;
    }
}
