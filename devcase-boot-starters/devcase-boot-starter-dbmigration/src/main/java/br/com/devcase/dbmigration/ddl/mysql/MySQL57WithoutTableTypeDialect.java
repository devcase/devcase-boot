package br.com.devcase.dbmigration.ddl.mysql;

import org.hibernate.dialect.MySQL57Dialect;

public class MySQL57WithoutTableTypeDialect extends MySQL57Dialect {

	@Override
	public String getTableTypeString() {
		return "";
	}

}
