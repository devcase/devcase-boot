package br.com.devcase.dbmigration.ddl;

import java.io.IOException;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;

import javax.sql.DataSource;

import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataBuilder;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.cfg.AvailableSettings;
import org.hibernate.engine.config.spi.ConfigurationService;
import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.hibernate.tool.hbm2ddl.Target;
import org.hibernate.tool.hbm2ddl.UniqueConstraintSchemaUpdateStrategy;
import org.hibernate.tool.schema.TargetType;
import org.hibernate.tool.schema.internal.ExceptionHandlerCollectingImpl;
import org.hibernate.tool.schema.spi.ExecutionOptions;
import org.hibernate.tool.schema.spi.SchemaManagementTool;
import org.hibernate.tool.schema.spi.SchemaManagementToolCoordinator;
import org.hibernate.tool.schema.spi.SchemaMigrator;
import org.hibernate.tool.schema.spi.TargetDescriptor;
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
			throw new RuntimeException(e);
		}

		LocalSessionFactoryBuilder sfb = (LocalSessionFactoryBuilder) sessionFactoryBean.getConfiguration();
		StandardServiceRegistry serviceRegistry = sfb.getStandardServiceRegistryBuilder()
				.applySetting(AvailableSettings.UNIQUE_CONSTRAINT_SCHEMA_UPDATE_STRATEGY,
						UniqueConstraintSchemaUpdateStrategy.RECREATE_QUIETLY)
				.build();

		final ConfigurationService cfgService = serviceRegistry.getService(ConfigurationService.class);

		MetadataBuilder metadataBuilder = sessionFactoryBean.getMetadataSources().getMetadataBuilder(serviceRegistry);
		Metadata metadata = metadataBuilder.build();

		SchemaManagementTool schemaTool = serviceRegistry.getService(SchemaManagementTool.class);
		SchemaMigrator schemaMigrator = schemaTool.getSchemaMigrator(cfgService.getSettings());

		Map config = new HashMap();
		config.putAll(serviceRegistry.getService(ConfigurationService.class).getSettings());
		config.put(AvailableSettings.HBM2DDL_DELIMITER, ";");
		config.put(AvailableSettings.FORMAT_SQL, true);

		final SchemaManagementTool tool = serviceRegistry.getService(SchemaManagementTool.class);

		final ExceptionHandlerCollectingImpl exceptionHandler = new ExceptionHandlerCollectingImpl();
		final ExecutionOptions executionOptions = SchemaManagementToolCoordinator.buildExecutionOptions(config,
				exceptionHandler);

		final TargetDescriptor targetDescriptor = SchemaExport.buildTargetDescriptor(EnumSet.of(TargetType.STDOUT), null,
				serviceRegistry);

		schemaMigrator.doMigration(metadata, executionOptions, targetDescriptor);
		return null;
	}

	// List<Class> annotatedClasses =
	// entityManagerFactoryBean.getPersistenceUnitInfo().getManagedClassNames().stream()
	// .map(v -> {
	// try {
	// return Class.forName(v);
	// } catch (ClassNotFoundException e1) {
	// return null;
	// }
	// }).collect(Collectors.toList());
	//
	// LocalSessionFactoryBean sessionFactoryBean = new LocalSessionFactoryBean();
	// sessionFactoryBean.setDataSource(dataSource);
	// Properties properties = new Properties();
	// properties.putAll(entityManagerFactoryBean.getJpaPropertyMap());
	// sessionFactoryBean.setHibernateProperties(properties);
	// sessionFactoryBean.setAnnotatedClasses(annotatedClasses.toArray(new
	// Class[annotatedClasses.size()]));
	// try {
	// sessionFactoryBean.afterPropertiesSet();
	// } catch (IOException e) {
	// e.printStackTrace();
	// }
	// try {
	// LocalSessionFactoryBuilder sfb = (LocalSessionFactoryBuilder)
	// sessionFactoryBean.getConfiguration();
	// StandardServiceRegistry serviceRegistry =
	// sfb.getStandardServiceRegistryBuilder()
	// .applySetting(AvailableSettings.UNIQUE_CONSTRAINT_SCHEMA_UPDATE_STRATEGY,
	// UniqueConstraintSchemaUpdateStrategy.RECREATE_QUIETLY)
	// .build();
	//
	// MetadataBuilder metadataBuilder = sessionFactoryBean.getMetadataSources()
	// .getMetadataBuilder(serviceRegistry);
	//
	//
	// MetadataImplementor metadata = (MetadataImplementor) metadataBuilder.build();
	//
	//// SchemaUpdate schemaUpdate = new SchemaUpdate(metadata);
	//// schemaUpdate.setDelimiter(";");
	//// schemaUpdate.setOutputFile("target/ddl-update-dont-commit.sql");
	//// schemaUpdate.execute(Target.SCRIPT);
	//
	// final StringBuilder buffer = new StringBuilder();
	//
	// List<org.hibernate.tool.schema.spi.TargetDescriptor> toolTargets = new
	// ArrayList<org.hibernate.tool.schema.spi.TargetDescriptor>();
	// org.hibernate.tool.schema.spi.TargetDescriptor bufferTarget = new
	// TargetDescriptor() {
	//
	// @Override
	// public EnumSet<TargetType> getTargetTypes() {
	// // TODO Auto-generated method stub
	// return null;
	// }
	//
	// @Override
	// public ScriptTargetOutput getScriptTargetOutput() {
	// // TODO Auto-generated method stub
	// return null;
	// }
	//
	// @Override
	// public void release() {
	// }
	//
	// @Override
	// public void prepare() {
	// }
	//
	// @Override
	// public boolean acceptsImportScriptActions() {
	// return false;
	// }
	//
	// @Override
	// public void accept(String action) {
	// buffer.append(action).append(";").append("\n");
	// }
	// };
	//// toolTargets.add(bufferTarget);
	//
	// //copiado de SchemaUpdate (construtor)
	// final JdbcConnectionAccess jdbcConnectionAccess = serviceRegistry.getService(
	// JdbcServices.class ).getBootstrapJdbcConnectionAccess();
	//
	// //copiado de SchemaUpdate.execute
	// final ConfigurationService cfgService = serviceRegistry.getService(
	// ConfigurationService.class );
	// final SchemaMigrator schemaMigrator = serviceRegistry.getService(
	// SchemaManagementTool.class )
	// .getSchemaMigrator( cfgService.getSettings() );
	//
	// final JdbcServices jdbcServices = serviceRegistry.getService(
	// JdbcServices.class );
	// final DatabaseInformation databaseInformation;
	// try {
	// databaseInformation = new DatabaseInformationImpl(
	// serviceRegistry,
	// serviceRegistry.getService( JdbcEnvironment.class ),
	// jdbcConnectionAccess,
	// metadata.getDatabase().getDefaultNamespace().getPhysicalName().getCatalog(),
	// metadata.getDatabase().getDefaultNamespace().getPhysicalName().getSchema()
	// );
	// }
	// catch (SQLException e) {
	// throw jdbcServices.getSqlExceptionHelper().convert(
	// e,
	// "Error creating DatabaseInformation for schema migration"
	// );
	// }
	//
	// try {
	// schemaMigrator.doMigration( metadata, databaseInformation, true, toolTargets
	// );
	// }
	// finally {
	// databaseInformation.cleanup();
	// }
	//
	// return buffer.toString();
	// } finally {
	// sessionFactoryBean.destroy();
	// }
	//
	// }
}
