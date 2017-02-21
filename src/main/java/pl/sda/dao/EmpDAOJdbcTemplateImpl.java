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
	private static String QUERY_BY_ID = "SELECT empno, ename, job, manager, hiredate, salary, commision, deptno FROM emp WHERE empno = :empno";
	private static String INSERT_STMT = "INSERT INTO emp(empno, ename, job, manager, hiredate, salary, commision, deptno) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
	private static String UPDATE_STMT = "UPDATE emp set ename = ?, job = ?, manager = ?, hiredate = ? , salary = ? , commision = ? , deptno = ?  WHERE empno = ?";
	private static String DELETE_STMT = "DELETE FROM emp WHERE empno = ?";
	private static String GET_TOTAL_SALARY_BY_DEPT = "{ ?= call sda.calculate_salary_by_dept(?)}";

	private final DataSourceFactory dataSourceFactory;

	public EmpDAOJdbcTemplateImpl(DataSourceFactory dataSourceFactory) {
		this.dataSourceFactory = dataSourceFactory;
	}

	private static RowMapper<Employee> rowMapper = (rs, rowNum) -> {
		int empno = rs.getInt("empno");
		String ename = rs.getString("ename");
		String job = rs.getString("job");
		Integer manager = rs.getInt("manager");
		Date hiredate = rs.getDate("hiredate");
		BigDecimal salary = rs.getBigDecimal("salary");
		BigDecimal commision = rs.getBigDecimal("commision");
		int deptno = rs.getInt("deptno");
		return new Employee(empno, ename, job, manager, hiredate, salary, commision, deptno);

	};

	@Override
	public Employee findById(int id) throws Exception {

		DataSource ds = dataSourceFactory.getDataSource();
		NamedParameterJdbcTemplate jdbcTemplate = new NamedParameterJdbcTemplate(ds);

		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("empno", id);

		try {
			return jdbcTemplate.queryForObject(QUERY_BY_ID, parameters, rowMapper);
		} catch (EmptyResultDataAccessException e) {
			return null;
		}

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
