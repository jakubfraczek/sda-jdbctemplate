package pl.sda.dao;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import pl.sda.domain.Employee;

/**
 * Created by pzawa on 02.02.2017.
 */
public class EmpDAOJdbcTemplateImpl implements EmpDAO {
	private static String QUERY_BY_ID = "SELECT empno, ename, job, manager, hiredate, salary, commision, deptno FROM emp WHERE empno = :empno";
	private static String QUERY_BY_ALL = "SELECT * FROM emp";
	private static String UPDATE_STMT = "UPDATE emp set ename = :ename, job = :job, manager = :manager, hiredate = :hiredate, salary = :salary, commision = :commision, deptno = :deptno WHERE empno = :empno";
	private static String DELETE_STMT = "DELETE FROM emp WHERE empno = :empno";

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

	private Map<String, Object> employeeMapper(Employee employee) {
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("empno", employee.getEmpno());
		parameters.put("ename", employee.getEname());
		parameters.put("job", employee.getJob());
		parameters.put("manager", employee.getManager());
		parameters.put("hiredate", employee.getHiredate());
		parameters.put("salary", employee.getSalary());
		parameters.put("commision", employee.getCommision());
		parameters.put("deptno", employee.getDeptno());
		return parameters;
	}
	
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
		DataSource ds = dataSourceFactory.getDataSource();
		NamedParameterJdbcTemplate jdbcTemplate = new NamedParameterJdbcTemplate(ds);

		try {
			return jdbcTemplate.query(QUERY_BY_ALL, rowMapper);
		} catch (EmptyResultDataAccessException e) {
			return null;
		}

	}

	@Override
	public void create(Employee employee) throws Exception {
		DataSource ds = dataSourceFactory.getDataSource();
		TransactionTemplate transactionTemplate = new TransactionTemplate(new DataSourceTransactionManager(ds));
		
		transactionTemplate.execute(satus ->{
		SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(ds)
				.withTableName("emp")
				.usingColumns("empno", "ename", "job", "manager", "hiredate", "salary", "commision", "deptno");
		
		Map<String, Object> parameters = employeeMapper(employee);
		
		simpleJdbcInsert.execute(parameters);
		
		return null;
		});
		
	}


	@Override
	public void update(Employee employee) throws Exception {
		DataSource ds = dataSourceFactory.getDataSource();
        TransactionTemplate transactionTemplate = new TransactionTemplate(new DataSourceTransactionManager(ds));
		
		transactionTemplate.execute(status -> {
			NamedParameterJdbcTemplate jdbcTemplate = new NamedParameterJdbcTemplate(ds);
			
			Map<String, Object> parameters = employeeMapper(employee);
			
			jdbcTemplate.update(UPDATE_STMT, parameters);
			
			return null;
		});
		
	}

	@Override
	public void delete(int id) throws Exception {
		DataSource ds = dataSourceFactory.getDataSource();
        TransactionTemplate transactionTemplate = new TransactionTemplate(new DataSourceTransactionManager(ds));
		
        transactionTemplate.execute(status -> {
        	NamedParameterJdbcTemplate jdbcTemplate = new NamedParameterJdbcTemplate(ds);
    		
        	Map<String, Object> parameters = new HashMap<String, Object>();
    		parameters.put("empno", id);
    		
    		jdbcTemplate.update(DELETE_STMT, parameters);
        	
        	return null;
        });
	}
}
