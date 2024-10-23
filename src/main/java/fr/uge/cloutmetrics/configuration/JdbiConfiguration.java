package fr.uge.cloutmetrics.configuration;

import fr.uge.cloutmetrics.model.DetailedGit;
import fr.uge.cloutmetrics.model.Git;
import fr.uge.cloutmetrics.model.Metric;
import javax.sql.DataSource;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.core.h2.H2DatabasePlugin;
import org.jdbi.v3.core.mapper.reflect.ConstructorMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.TransactionAwareDataSourceProxy;

/**
 * Configures the JDBI plugin to be usable with H2 Database.
 */
@Configuration
public class JdbiConfiguration {

  /**
   * Creates a JDBI object which contains every parameter needed to connect to the H2 DB.
   *
   * @param dataSource An object containing connection data to the database.
   * @return JDBI object, which can let the program connect to the H2 database.
   */
  @Bean
  public Jdbi jdbi(DataSource dataSource) {
    var dataSourceProxy = new TransactionAwareDataSourceProxy(dataSource);
    return Jdbi.create(dataSourceProxy).installPlugins()
            .installPlugin(new H2DatabasePlugin())
            .registerRowMapper(Git.class, ConstructorMapper.of(Git.class))
            .registerRowMapper(Metric.class, ConstructorMapper.of(Metric.class))
            .registerRowMapper(DetailedGit.class, ConstructorMapper.of(DetailedGit.class));
  }
}