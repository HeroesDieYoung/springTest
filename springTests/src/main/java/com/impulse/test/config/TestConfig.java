package com.impulse.test.config;

import java.util.HashMap;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@ComponentScan(basePackages = "com.impulse")
public class TestConfig {

	@Bean
	public DataSource localDataSource() {
		return new EmbeddedDatabaseBuilder()
				.generateUniqueName(true)
				.setType(EmbeddedDatabaseType.HSQL)
				.setScriptEncoding("UTF-8")
				.ignoreFailedDrops(false)
				.addScripts("sql/department.sql", "sql/employee.sql")
				.build();
	}

	@Bean
	public LocalContainerEntityManagerFactoryBean localUsersEntityManagerFactory(DataSource localDataSource) {
		LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
		em.setDataSource(localDataSource);
		em.setPersistenceUnitName("springTests");
		em.setPackagesToScan(new String[] { "com.impulse" });
		em.setJpaPropertyMap(new HashMap<String, Object>() {
			private static final long serialVersionUID = 1L;

			{
				put("hibernate.cache.use_second_level_cache", false);
			}
		});

		HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
		vendorAdapter.setDatabasePlatform("org.hibernate.dialect.MySQLInnoDBDialect");
		vendorAdapter.setShowSql(false);
		vendorAdapter.setGenerateDdl(false);
		em.setJpaVendorAdapter(vendorAdapter);

		return em;
	}

	@Bean
	public PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(entityManagerFactory);
		return transactionManager;
	}

}
