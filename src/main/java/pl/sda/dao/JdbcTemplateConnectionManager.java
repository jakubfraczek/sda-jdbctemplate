package pl.sda.dao;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import pl.sda.DbConfiguration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by pzawa on 02.02.2017.
 */
public class JdbcTemplateConnectionManager {

    private final DbConfiguration dbConfiguration;

    public JdbcTemplateConnectionManager(DbConfiguration dbConfiguration) throws ClassNotFoundException {
        this.dbConfiguration = dbConfiguration;
        Class.forName("org.postgresql.Driver");

    }

    public NamedParameterJdbcTemplate getNamedParameterJdbcTemplate() throws SQLException {
        SimpleDriverDataSource ds = new SimpleDriverDataSource();
        ds.setDriver(new org.postgresql.Driver());
        ds.setUrl(dbConfiguration.getJdbcUrl());
        ds.setUsername(dbConfiguration.getUsername());
        ds.setPassword(dbConfiguration.getPassword());
        ds.setSchema(dbConfiguration.getSchema());

        NamedParameterJdbcTemplate jdbcTemplate = new NamedParameterJdbcTemplate(ds);
        return jdbcTemplate;
    }

}
