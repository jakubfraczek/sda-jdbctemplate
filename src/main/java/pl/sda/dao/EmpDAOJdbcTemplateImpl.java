package pl.sda.dao;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import pl.sda.domain.Department;
import pl.sda.domain.Employee;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by pzawa on 02.02.2017.
 */
public class EmpDAOJdbcTemplateImpl implements EmpDAO {
    private final DataSourceFactory dataSourceFactory;

    public EmpDAOJdbcTemplateImpl(DataSourceFactory dataSourceFactory){
        this.dataSourceFactory = dataSourceFactory;
    }

    @Override
    public Employee findById(int id) throws Exception {
        return null;
    }

    @Override
    public List<Employee> findByAll() throws Exception {
        return null;
    }

    @Override
    public void create(Employee employee) throws Exception {

    }

    @Override
    public void update(Employee employee) throws Exception {

    }

    @Override
    public void delete(int id) throws Exception {

    }
}
