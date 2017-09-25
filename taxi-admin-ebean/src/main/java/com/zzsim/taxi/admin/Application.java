package com.zzsim.taxi.admin;

import com.zzsim.taxi.admin.base.CurrentUser;
import io.ebean.EbeanServer;
import io.ebean.EbeanServerFactory;
import io.ebean.Transaction;
import io.ebean.config.ServerConfig;
import io.ebean.config.UnderscoreNamingConvention;
import io.ebean.dbmigration.ddl.DdlRunner;
import io.ebean.spring.txn.SpringJdbcTransactionManager;
import lombok.Cleanup;
import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.EnableLoadTimeWeaving;
import org.springframework.context.annotation.Scope;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.persistence.PersistenceException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;

import static org.springframework.beans.factory.config.BeanDefinition.SCOPE_SINGLETON;

@EnableAspectJAutoProxy(proxyTargetClass=true)
@EnableSwagger2
@SpringBootApplication
public class Application {

	@Value("${spring.profiles.active}")
	private String env;

	@Value("${ebeans.packages}")
	private String packages;

	@Value("${ebeans.dbName}")
	private String dbName;

	@Bean(autowire = Autowire.BY_TYPE)
	@Scope(SCOPE_SINGLETON)
	public EbeanServer getEbeanServer() {
		boolean isGenerateSqlTxt = !"db".equals(dbName) || "create".equals(env);
		ServerConfig config = new ServerConfig();
		config.setName(StringUtils.isNotBlank(dbName) ? dbName : "db");
		config.loadFromProperties();
		config.setDdlGenerate(isGenerateSqlTxt);
		config.setDdlRun(isGenerateSqlTxt);
		config.addPackage(packages);
		config.setExternalTransactionManager(new SpringJdbcTransactionManager());// 事务支持
		config.setNamingConvention(new UnderscoreNamingConvention());// 下划线
		config.setCurrentUserProvider(new CurrentUser());
		EbeanServer ebeanServer = EbeanServerFactory.create(config);
		if (!"create".equals(env)) {
			runScript(false, ebeanServer);
		}
		return ebeanServer;
	}

	@SneakyThrows
	private void runScript(boolean expectErrors, EbeanServer ebeanServer) {
		StringBuffer sb = new StringBuffer();
		String sql1 = addSql("sql/init.sql");
		String sql2 = addSql("sql/t_area.sql");
		sb.append(sql1);
		sb.append(sql2);

		DdlRunner runner = new DdlRunner(expectErrors, "init");

		Transaction transaction = ebeanServer.createTransaction();
		Connection connection = transaction.getConnection();
		try {
			if (expectErrors) {
				connection.setAutoCommit(true);
			}
			runner.runAll(sb.toString(), connection);
			if (expectErrors) {
				connection.setAutoCommit(false);
			}
			transaction.commit();

		} catch (SQLException e) {
			throw new PersistenceException("Failed to run script", e);

		} finally {
			transaction.end();
		}
	}

	@SneakyThrows
	private String addSql(String path) {
		StringBuffer sb = new StringBuffer();
		@Cleanup InputStream stream = Application.class.getClassLoader().getResourceAsStream(path);
		@Cleanup java.util.Scanner scan = new java.util.Scanner(stream);
		while (scan.hasNext()) {
			sb.append(scan.nextLine().concat("\n"));
		}
		return sb.toString();
	}

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(Application.class);
		app.run(args);
	}

}
