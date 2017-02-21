package pl.sda.dao;

import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import pl.sda.DbConfiguration;

import javax.sql.DataSource;

/**
 * Created by pzawa on 02.02.2017.
 */
public class DataSourceFactory {

    private final DbConfiguration dbConfiguration;

    public DataSourceFactory(DbConfiguration dbConfiguration) throws ClassNotFoundException {
        this.dbConfiguration = dbConfiguration;
        Class.forName("org.postgresql.Driver");

    }

    public DataSource getDataSource(){
        SimpleDriverDataSource ds = new SimpleDriverDataSource();
        ds.setDriver(new org.postgresql.Driver());
        ds.setUrl(dbConfiguration.getJdbcUrl());
        ds.setUsername(dbConfiguration.getUsername());
        ds.setPassword(dbConfiguration.getPassword());
        ds.setSchema(dbConfiguration.getSchema());

        return ds;
    }
}
