package com.impulse.test;

import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.transaction.annotation.Transactional;

import com.impulse.test.config.TestConfig;
import com.impulse.test.dao.EmployeeDao;
import com.impulse.test.entity.Department;
import com.impulse.test.entity.Employee;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class, classes = { TestConfig.class })
@Transactional
public class SpringTest {

	@Autowired
	private EmployeeDao dao;
	private Logger log = Logger.getLogger(getClass());

	@Test
	@Sql(statements = { "ALTER TABLE employee ALTER COLUMN id RESTART WITH 1",
			"ALTER TABLE department ALTER COLUMN id RESTART WITH 1" })
	public void singleEmployee() {

		Employee e1 = new Employee("Travis", "Dimmig");
		dao.saveEmployee(e1);

		// Now set the ID to what it should have been once saved
		e1.setId(1);
		List<Employee> expected = new LinkedList<>();
		expected.add(e1);

		List<Employee> saved = dao.getAllEmployees();

		logEmployeeLists(expected, saved);

		Assert.assertTrue("List size is not 1", saved.size() == 1);
		Assert.assertTrue("List did not contain saved Employee", saved.contains(e1));
	}

	@Test
	@Sql(statements = { "ALTER TABLE employee ALTER COLUMN id RESTART WITH 1",
			"ALTER TABLE department ALTER COLUMN id RESTART WITH 1" })
	public void multipleEmployees() {

		Employee e1 = new Employee("Travis", "Dimmig");
		Employee e2 = new Employee("Jeff", "Price");
		dao.saveEmployee(e1);
		dao.saveEmployee(e2);

		// Now set the ID to what it should have been once saved
		e1.setId(1);
		e2.setId(2);
		List<Employee> expected = new LinkedList<>();
		expected.add(e1);
		expected.add(e2);

		List<Employee> saved = dao.getAllEmployees();

		logEmployeeLists(expected, saved);

		Assert.assertTrue("List size is not 2", saved.size() == 2);
		Assert.assertTrue("List did not contain saved Employee Travis", saved.contains(e1));
		Assert.assertTrue("List did not contain saved Employee Jeff", saved.contains(e2));
	}

	@Test
	@Sql(statements = { "ALTER TABLE employee ALTER COLUMN id RESTART WITH 1",
			"ALTER TABLE department ALTER COLUMN id RESTART WITH 1" })
	public void singleEmployeeAndDepartment() {

		Employee e1 = new Employee("Travis", "Dimmig");
		Department dev = new Department("Development");
		dev.addEmployee(e1);
		dao.saveDepartment(dev);

		// Now set the ID to what it should have been once saved
		e1.setId(1);
		List<Employee> expectedEmployees = new LinkedList<>();
		expectedEmployees.add(e1);

		dev.setId(1);
		List<Department> expectedDepartments = new LinkedList<>();
		expectedDepartments.add(dev);

		List<Employee> savedEmp = dao.getAllEmployees();

		logEmployeeLists(expectedEmployees, savedEmp);

		Assert.assertTrue("List size is not 1 employee", savedEmp.size() == 1);
		Assert.assertTrue("List did not contain saved Employee", savedEmp.contains(e1));

		List<Department> savedDep = dao.getAllDepartments();

		logDepartmentLists(expectedDepartments, savedDep);

		Assert.assertTrue("List size is not 1 department", savedDep.size() == 1);
		Assert.assertTrue("List did not contain saved Department", savedDep.contains(dev));
	}

	@Test
	@Sql(statements = { "ALTER TABLE employee ALTER COLUMN id RESTART WITH 1",
			"ALTER TABLE department ALTER COLUMN id RESTART WITH 1" })
	public void multipleEmployeesSingleDepartment() {
		Employee e1 = new Employee("Travis", "Dimmig");
		Employee e2 = new Employee("Jeff", "Price");
		Department dev = new Department("Development");
		dev.addEmployee(e1);
		dev.addEmployee(e2);
		dao.saveDepartment(dev);

		// Now set the ID to what it should have been once saved
		e1.setId(1);
		e2.setId(2);
		List<Employee> expectedEmployees = new LinkedList<>();
		expectedEmployees.add(e1);
		expectedEmployees.add(e2);

		dev.setId(1);
		List<Department> expectedDepartments = new LinkedList<>();
		expectedDepartments.add(dev);

		List<Employee> savedEmp = dao.getAllEmployees();

		logEmployeeLists(expectedEmployees, savedEmp);

		Assert.assertTrue("List size is not 1 employee", savedEmp.size() == 2);
		Assert.assertTrue("List did not contain saved Employee Travis", savedEmp.contains(e1));
		Assert.assertTrue("List did not contain saved Employee Jeff", savedEmp.contains(e2));

		List<Department> savedDep = dao.getAllDepartments();

		logDepartmentLists(expectedDepartments, savedDep);

		Assert.assertTrue("List size is not 1 department", savedDep.size() == 1);
		Assert.assertTrue("List did not contain saved Department", savedDep.contains(dev));
	}

	@Test
	@Sql(statements = { "ALTER TABLE employee ALTER COLUMN id RESTART WITH 1",
			"ALTER TABLE department ALTER COLUMN id RESTART WITH 1" })
	public void multipleEmployeesMultipleDepartments() {
		Employee e1 = new Employee("Travis", "Dimmig");
		Employee e2 = new Employee("Jeff", "Price");
		Department dev = new Department("Development");
		dev.addEmployee(e1);
		dev.addEmployee(e2);
		dao.saveDepartment(dev);

		Employee e3 = new Employee("Andrew", "Bottoms");
		Employee e4 = new Employee("Nathan", "Preseault");
		Department devops = new Department("Devops");
		devops.addEmployee(e3);
		devops.addEmployee(e4);
		dao.saveDepartment(devops);

		// Now set the ID to what it should have been once saved
		e1.setId(1);
		e2.setId(2);
		e3.setId(3);
		e4.setId(4);
		List<Employee> expectedEmployees = new LinkedList<>();
		expectedEmployees.add(e1);
		expectedEmployees.add(e2);
		expectedEmployees.add(e3);
		expectedEmployees.add(e4);

		dev.setId(1);
		devops.setId(2);
		List<Department> expectedDepartments = new LinkedList<>();
		expectedDepartments.add(dev);
		expectedDepartments.add(devops);

		List<Employee> savedEmp = dao.getAllEmployees();

		logEmployeeLists(expectedEmployees, savedEmp);

		Assert.assertTrue("List size is not 2 employee", savedEmp.size() == 2);
		Assert.assertTrue("List did not contain saved Employee Travis", savedEmp.contains(e1));
		Assert.assertTrue("List did not contain saved Employee Jeff", savedEmp.contains(e2));

		List<Department> savedDep = dao.getAllDepartments();

		logDepartmentLists(expectedDepartments, savedDep);

		Assert.assertTrue("List size is not 2 department", savedDep.size() == 2);
		Assert.assertTrue("List did not contain saved Department", savedDep.contains(dev));
		Assert.assertTrue("List did not contain saved Department", savedDep.contains(devops));
	}

	private void logEmployeeLists(List<Employee> expected, List<Employee> actual) {
		StringBuilder sb = new StringBuilder();
		sb.append("\nExpected employee list:\n");
		for (Employee e : expected) {
			sb.append(e.toString()).append("\n");
		}
		sb.append("Actual employee list:\n");
		for (Employee e : actual) {
			sb.append(e.toString()).append("\n");
		}
		log.info(sb.toString());
	}

	private void logDepartmentLists(List<Department> expected, List<Department> actual) {
		StringBuilder sb = new StringBuilder();
		sb.append("\nExpected employee list:\n");
		for (Department d : expected) {
			sb.append(d.toString()).append("\n");
		}
		sb.append("Actual employee list:\n");
		for (Department d : actual) {
			sb.append(d.toString()).append("\n");
		}
		log.info(sb.toString());
	}

}
