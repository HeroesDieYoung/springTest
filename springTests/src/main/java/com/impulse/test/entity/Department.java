package com.impulse.test.entity;

import java.util.Set;
import java.util.TreeSet;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.apache.commons.lang.builder.CompareToBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

@Entity
public class Department implements Comparable<Department> {

	@Id
	private long id;
	private String name;
	@OneToMany(mappedBy = "department", orphanRemoval = true)
	private Set<Employee> employees;

	public Department() {
		this.employees = new TreeSet<>();
	}

	public Department(String name) {
		this.employees = new TreeSet<>();
		this.name = name;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this)
				.append("id", this.getId())
				.append("name", this.getName())
				.toString();
	}

	@Override
	public int compareTo(Department other) {
		return new CompareToBuilder()
				.append(this.getId(), other.getId())
				.append(this.getName(), other.getName())
				.append(this.getEmployees(), other.getEmployees())
				.toComparison();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this) return true;
		if (obj == null) return false;
		if (obj instanceof Department) {
			Department other = (Department) obj;
			return new EqualsBuilder()
					.append(this.getId(), other.getId())
					.append(this.getName(), other.getName())
					.append(this.getEmployees(), other.getEmployees())
					.isEquals();
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return 0;
	}

	public Department addEmployee(Employee e) {
		this.employees.add(e);
		e.setDepartment(this);
		return this;
	}

	public void removeEmployee(Employee e) {
		if (employees.remove(e)) {
			e.setDepartment(null);
		}
	}

	public String getName() {
		return name;
	}

	public Department setName(String name) {
		this.name = name;
		return this;
	}

	public Set<Employee> getEmployees() {
		return employees;
	}

	public Department setEmployees(Set<Employee> employees) {
		this.employees = employees;
		return this;
	}

	public long getId() {
		return id;
	}

	public Department setId(long id) {
		this.id = id;
		return this;
	}

}
