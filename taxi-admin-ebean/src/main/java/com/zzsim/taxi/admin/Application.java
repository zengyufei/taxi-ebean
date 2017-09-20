package com.zzsim.taxi.admin;

import com.zzsim.taxi.admin.base.CurrentUser;
import io.ebean.EbeanServer;
import io.ebean.EbeanServerFactory;
import io.ebean.Transaction;
import io.ebean.config.ServerConfig;
import io.ebean.config.UnderscoreNamingConvention;
import io.ebean.dbmigration.ddl.DdlRunner;
import io.ebean.spring.txn.SpringJdbcTransactionManager;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.persistence.PersistenceException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;

import static org.springframework.beans.factory.config.BeanDefinition.SCOPE_SINGLETON;

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
		runScript(false, ebeanServer);
		return ebeanServer;
	}

	private void runScript(boolean expectErrors, EbeanServer ebeanServer){
		StringBuffer sb = new StringBuffer();
		try(InputStream stream = Application.class.getClassLoader().getResourceAsStream("sql/init.sql");
		java.util.Scanner scan = new java.util.Scanner(stream)) {
			// scan.useDelimiter("\\s");
			while (scan.hasNext()){
				sb.append(scan.nextLine());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		DdlRunner runner = new DdlRunner(expectErrors, "sql/init.sql");

		Transaction transaction = ebeanServer.createTransaction();
		Connection connection = transaction.getConnection();
		try {
			if (expectErrors) {
				connection.setAutoCommit(true);
			}
			int count = runner.runAll(sb.toString(), connection);
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

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(Application.class);
		app.run(args);
	}

}
