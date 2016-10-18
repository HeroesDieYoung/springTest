package com.impulse.test.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.apache.commons.lang.builder.CompareToBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

@Entity
public class Employee implements Comparable<Employee> {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	private String firstName;
	private String lastName;
	@ManyToOne
	@JoinColumn(name = "department")
	private Department department;

	public Employee() {

	}

	public Employee(String firstName, String lastName) {
		this.firstName = firstName;
		this.lastName = lastName;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this)
				.append("id", this.getId())
				.append("firstName", this.getFirstName())
				.append("lastName", this.getLastName())
				.append("department", this.getDepartment() == null ? "null" : this.getDepartment().getName())
				.toString();
	}

	@Override
	public int compareTo(Employee other) {
		return new CompareToBuilder()
				.append(this.getId(), other.getId())
				.append(this.getFirstName(), other.getFirstName())
				.append(this.getLastName(), other.getLastName())
				.toComparison();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this) return true;
		if (obj == null) return false;
		if (obj instanceof Employee) {
			Employee other = (Employee) obj;
			return new EqualsBuilder()
					.append(this.getId(), other.getId())
					.append(this.getFirstName(), other.getFirstName())
					.append(this.getLastName(), other.getLastName())
					.isEquals();
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return 0;
	}

	public String getFirstName() {
		return firstName;
	}

	public Employee setFirstName(String firstName) {
		this.firstName = firstName;
		return this;
	}

	public String getLastName() {
		return lastName;
	}

	public Employee setLastName(String lastName) {
		this.lastName = lastName;
		return this;
	}

	public long getId() {
		return id;
	}

	public Employee setId(long id) {
		this.id = id;
		return this;
	}

	public Department getDepartment() {
		return department;
	}

	public Employee setDepartment(Department department) {
		this.department = department;
		return this;
	}

}
