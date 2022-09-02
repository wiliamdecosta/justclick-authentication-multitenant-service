package com.justclick.authentication.providers;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.hibernate.engine.jdbc.connections.spi.AbstractDataSourceBasedMultiTenantConnectionProviderImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.justclick.authentication.configs.TenantDataSource;


@Component
public class DataSourceMultitenantConnectionProvider extends AbstractDataSourceBasedMultiTenantConnectionProviderImpl{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8007957834883058750L;

	private static final String DEFAULT_TENANT_ID = "public";
    @Autowired
    private DataSource defaultDS;

    @Autowired
    private ApplicationContext context;

    private Map<String, DataSource> map = new HashMap<>();

    boolean init = false;

    @PostConstruct
    public void load() {
        map.put(DEFAULT_TENANT_ID, defaultDS);
    }

    @Override
    protected DataSource selectAnyDataSource() {
        return map.get(DEFAULT_TENANT_ID);
    }

    @Override
    protected DataSource selectDataSource(String tenantIdentifier) {
        if (!init) {
            init = true;
            TenantDataSource tenantDataSource = context.getBean(TenantDataSource.class);
            map.putAll(tenantDataSource.getAll());
        }
        return map.get(tenantIdentifier) != null ? map.get(tenantIdentifier) : map.get(DEFAULT_TENANT_ID);   
    }
    
    
    /**
     * to add data source while running
     * @param schemaName
     */
    public void addDataSource(String schemaName) {
    	TenantDataSource tenantDataSource = context.getBean(TenantDataSource.class);
    	DataSource newDataSouce = tenantDataSource.createDataSource(schemaName);
    	
    	map.put(schemaName, newDataSouce);
    }

}
