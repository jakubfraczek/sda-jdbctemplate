package pl.sda.dao;

import org.junit.Before;
import org.junit.Test;
import pl.sda.DbConfiguration;
import pl.sda.domain.Employee;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by pzawa on 02.02.2017.
 */
public class EmpDAOJdbcImplTest {
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    private EmpDAO empDAO;

    @Before
    public void init() throws IOException, ClassNotFoundException, SQLException {
        DataSourceFactory dataSourceFactory = new DataSourceFactory(DbConfiguration.loadConfiguration());
        empDAO =  new EmpDAOJdbcTemplateImpl(dataSourceFactory);
        TestUtil.cleanUpDatabase(dataSourceFactory);
    }

    @Test
    public void findById() throws Exception {
        Employee employee = empDAO.findById(7369);

        assertNotNull(employee);
        assertEquals(20, employee.getDeptno());
        assertEquals("SMITH", employee.getEname());
        assertEquals("CLERK", employee.getJob());
        assertEquals(sdf.parse("1993-06-13"), employee.getHiredate());
        assertTrue(BigDecimal.valueOf(800.00).compareTo(employee.getSalary())==0);
        assertTrue(BigDecimal.valueOf(0.00).compareTo(employee.getCommision())==0);

    }

    @Test
    public void findAll() throws Exception {
        List<Employee> employees = empDAO.findByAll();

        assertNotNull(employees);
        assertTrue(employees.size() == 14);
    }

    @Test
    public void create() throws Exception {
        Employee newEmployee = new Employee(9000, "JKOWALSKI", "Manager", 7839, sdf.parse("2017-01-01"), BigDecimal.valueOf(10000), BigDecimal.valueOf(10.0), 20);

        empDAO.create(newEmployee);

        Employee employeeFromDB = empDAO.findById(9000);

        assertNotNull(employeeFromDB);
        assertEquals(employeeFromDB.getEmpno(), newEmployee.getEmpno());
        assertEquals(employeeFromDB.getEname(), newEmployee.getEname());
        assertEquals(employeeFromDB.getJob(), newEmployee.getJob());
        assertEquals(employeeFromDB.getHiredate(), newEmployee.getHiredate());
        assertTrue(employeeFromDB.getSalary().compareTo(newEmployee.getSalary()) ==0);
        assertTrue(employeeFromDB.getCommision().compareTo(newEmployee.getCommision()) ==0);
        assertEquals(employeeFromDB.getDeptno(), newEmployee.getDeptno());
    }

    @Test
    public void update() throws Exception {
        Employee employee = empDAO.findById(7369);
        assertNotNull(employee);

        employee.setJob("SUPERCLERK");
        empDAO.update(employee);
        employee = empDAO.findById(7369);

        assertNotNull(employee);
        assertEquals(20, employee.getDeptno());
        assertEquals("SMITH", employee.getEname());
        assertEquals("SUPERCLERK", employee.getJob());
        assertEquals(sdf.parse("1993-06-13"), employee.getHiredate());
        assertTrue(BigDecimal.valueOf(800).compareTo(employee.getSalary()) == 0);
        assertTrue(BigDecimal.valueOf(800.00).compareTo(employee.getSalary()) == 0);

    }

    @Test
    public void delete() throws Exception {
        Employee employee = empDAO.findById(7369);
        assertNotNull(employee);

        empDAO.delete(7369);

        employee = empDAO.findById(7369);
        assertNull(employee);
    }
}
