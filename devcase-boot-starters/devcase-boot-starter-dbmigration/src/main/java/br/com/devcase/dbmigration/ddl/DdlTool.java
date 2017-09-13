package br.com.devcase.dbmigration.ddl;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

import javax.sql.DataSource;

import org.hibernate.boot.MetadataBuilder;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.spi.MetadataImplementor;
import org.hibernate.cfg.AvailableSettings;
import org.hibernate.engine.config.spi.ConfigurationService;
import org.hibernate.engine.jdbc.connections.spi.JdbcConnectionAccess;
import org.hibernate.engine.jdbc.env.spi.JdbcEnvironment;
import org.hibernate.engine.jdbc.spi.JdbcServices;
import org.hibernate.tool.hbm2ddl.UniqueConstraintSchemaUpdateStrategy;
import org.hibernate.tool.schema.extract.internal.DatabaseInformationImpl;
import org.hibernate.tool.schema.extract.spi.DatabaseInformation;
import org.hibernate.tool.schema.spi.SchemaManagementTool;
import org.hibernate.tool.schema.spi.SchemaMigrator;
import org.hibernate.tool.schema.spi.Target;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.orm.hibernate5.LocalSessionFactoryBuilder;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;

/**
 * 
 * @author hirata
 *
 */
public class DdlTool {
	@Autowired
	private LocalContainerEntityManagerFactoryBean entityManagerFactoryBean;
	@Autowired
	private DataSource dataSource;

	public String generateUpdateScript() {

		List<Class> annotatedClasses = entityManagerFactoryBean.getPersistenceUnitInfo().getManagedClassNames().stream()
				.map(v -> {
					try {
						return Class.forName(v);
					} catch (ClassNotFoundException e1) {
						return null;
					}
				}).collect(Collectors.toList());

		LocalSessionFactoryBean sessionFactoryBean = new LocalSessionFactoryBean();
		sessionFactoryBean.setDataSource(dataSource);
		Properties properties = new Properties();
		properties.putAll(entityManagerFactoryBean.getJpaPropertyMap());
		sessionFactoryBean.setHibernateProperties(properties);
		sessionFactoryBean.setAnnotatedClasses(annotatedClasses.toArray(new Class[annotatedClasses.size()]));
		try {
			sessionFactoryBean.afterPropertiesSet();
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			LocalSessionFactoryBuilder sfb = (LocalSessionFactoryBuilder) sessionFactoryBean.getConfiguration();
			StandardServiceRegistry serviceRegistry = sfb.getStandardServiceRegistryBuilder()
					.applySetting(AvailableSettings.UNIQUE_CONSTRAINT_SCHEMA_UPDATE_STRATEGY,
							UniqueConstraintSchemaUpdateStrategy.RECREATE_QUIETLY)
					.build();

			MetadataBuilder metadataBuilder = sessionFactoryBean.getMetadataSources()
					.getMetadataBuilder(serviceRegistry);
			
			
			MetadataImplementor metadata = (MetadataImplementor) metadataBuilder.build();

//			SchemaUpdate schemaUpdate = new SchemaUpdate(metadata);
//			schemaUpdate.setDelimiter(";");
//			schemaUpdate.setOutputFile("target/ddl-update-dont-commit.sql");
//			schemaUpdate.execute(Target.SCRIPT);

			final StringBuilder buffer = new StringBuilder();
			
			List<org.hibernate.tool.schema.spi.Target> toolTargets = new ArrayList<org.hibernate.tool.schema.spi.Target>();
			org.hibernate.tool.schema.spi.Target bufferTarget = new Target() {
				@Override
				public void release() {
				}
				
				@Override
				public void prepare() {
				}
				
				@Override
				public boolean acceptsImportScriptActions() {
					return false;
				}
				
				@Override
				public void accept(String action) {
					buffer.append(action).append(";").append("\n");
				}
			};
			toolTargets.add(bufferTarget);
			
			//copiado de SchemaUpdate (construtor)
			final JdbcConnectionAccess jdbcConnectionAccess = serviceRegistry.getService( JdbcServices.class ).getBootstrapJdbcConnectionAccess();

			//copiado de SchemaUpdate.execute
			final ConfigurationService cfgService = serviceRegistry.getService( ConfigurationService.class );
			final SchemaMigrator schemaMigrator = serviceRegistry.getService( SchemaManagementTool.class )
					.getSchemaMigrator( cfgService.getSettings() );

			final JdbcServices jdbcServices = serviceRegistry.getService( JdbcServices.class );
			final DatabaseInformation databaseInformation;
			try {
				databaseInformation = new DatabaseInformationImpl(
						serviceRegistry,
						serviceRegistry.getService( JdbcEnvironment.class ),
						jdbcConnectionAccess,
						metadata.getDatabase().getDefaultNamespace().getPhysicalName().getCatalog(),
						metadata.getDatabase().getDefaultNamespace().getPhysicalName().getSchema()
				);
			}
			catch (SQLException e) {
				throw jdbcServices.getSqlExceptionHelper().convert(
						e,
						"Error creating DatabaseInformation for schema migration"
				);
			}

			try {
				schemaMigrator.doMigration( metadata, databaseInformation, true, toolTargets );
			}
			finally {
				databaseInformation.cleanup();
			}

			return buffer.toString();
		} finally {
			sessionFactoryBean.destroy();
		}

	}
}
