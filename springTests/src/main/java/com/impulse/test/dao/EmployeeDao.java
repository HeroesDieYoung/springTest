package com.impulse.test.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.impulse.test.entity.Department;
import com.impulse.test.entity.Employee;

@Repository
public class EmployeeDao {

	@PersistenceContext
	private EntityManager em;

	@Transactional(propagation = Propagation.REQUIRED)
	public void saveEmployee(Employee e) {
		em.merge(e);
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void deleteEmployee(Employee e) {
		em.remove(em.merge(e));
	}

	public List<Employee> getAllEmployees() {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Employee> cq = cb.createQuery(Employee.class);
		Root<Employee> root = cq.from(Employee.class);
		cq.select(root);
		TypedQuery<Employee> q = em.createQuery(cq);
		return q.getResultList();
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void saveDepartment(Department d) {
		em.merge(d);
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void deleteDepartment(Department e) {
		em.remove(em.merge(e));
	}

	public List<Department> getAllDepartments() {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Department> cq = cb.createQuery(Department.class);
		Root<Department> root = cq.from(Department.class);
		cq.select(root);
		TypedQuery<Department> q = em.createQuery(cq);
		return q.getResultList();
	}

}
