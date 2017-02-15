package pl.sda.dao;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import pl.sda.domain.Department;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by pzawa on 02.02.2017.
 */
public class DeptDAOJdbcTemplateImpl implements DeptDAO{
    private static String QUERY_BY_ID  = "SELECT deptno, dname, location FROM Dept WHERE deptno = :deptno";
    private static String INSERT_STMT = "INSERT INTO Dept(deptno, dname, location) VALUES(:deptno,:dname,:location)";
    private static String UPDATE_STMT= "UPDATE Dept set dname = :dname, location = :location WHERE deptno = :deptno";
    private static String DELETE_STMT= "DELETE FROM Dept WHERE deptno = :deptno";

    private final JdbcTemplateConnectionManager jdbcTemplateConnectionManager;

    public DeptDAOJdbcTemplateImpl(JdbcTemplateConnectionManager jdbcTemplateConnectionManager) {
        this.jdbcTemplateConnectionManager = jdbcTemplateConnectionManager;
    }

    private static RowMapper<Department> rowMapper = (rs, rowNum) -> {
        int deptNo = rs.getInt("deptno");
        String dname = rs.getString("dname");
        String location = rs.getString("location");
        return new Department(deptNo, dname, location);
    };

    @Override
    public Department findById(int id) throws SQLException {
        NamedParameterJdbcTemplate jdbcTemplate = jdbcTemplateConnectionManager.getNamedParameterJdbcTemplate();

        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("deptno", id);

        try {
            return jdbcTemplate.queryForObject(QUERY_BY_ID, parameters, rowMapper);
        }catch(EmptyResultDataAccessException e){
            return null;
        }
    }

    @Override
    public void create(Department department) throws SQLException {
        NamedParameterJdbcTemplate jdbcTemplate = jdbcTemplateConnectionManager.getNamedParameterJdbcTemplate();

        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("deptno", department.getDeptno());
        parameters.put("dname", department.getDname());
        parameters.put("location", department.getLocation());

        int numberOfAffectedRows = jdbcTemplate.update(INSERT_STMT, parameters);
        System.out.println("DeptDAO.create() number of affected rows: " + numberOfAffectedRows);
    }

    @Override
    public void update(Department department) throws SQLException {
        NamedParameterJdbcTemplate jdbcTemplate = jdbcTemplateConnectionManager.getNamedParameterJdbcTemplate();

        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("deptno", department.getDeptno());
        parameters.put("dname", department.getDname());
        parameters.put("location", department.getLocation());

        int numberOfAffectedRows = jdbcTemplate.update(UPDATE_STMT, parameters);
        System.out.println("DeptDAO.update() number of affected rows: " + numberOfAffectedRows);
    }

    @Override
    public void delete(int id) throws SQLException {
        NamedParameterJdbcTemplate jdbcTemplate = jdbcTemplateConnectionManager.getNamedParameterJdbcTemplate();

        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("deptno", id);
        ;

        int numberOfAffectedRows = jdbcTemplate.update(DELETE_STMT, parameters);
        System.out.println("DeptDAO.delete() number of affected rows: " + numberOfAffectedRows);
    }
}
