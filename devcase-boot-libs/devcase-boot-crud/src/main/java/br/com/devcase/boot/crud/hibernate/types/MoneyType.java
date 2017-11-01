package br.com.devcase.boot.crud.hibernate.types;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.type.BigDecimalType;
import org.hibernate.type.StringType;
import org.hibernate.usertype.UserType;
import org.javamoney.moneta.Money;

public class MoneyType implements UserType {
	private final int[] SQL_TYPES = {Types.VARCHAR, Types.DECIMAL};

	@Override
	public int[] sqlTypes() {
		return SQL_TYPES;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Class returnedClass() {
		return Money.class;
	}

	@Override
	public boolean equals(Object x, Object y) throws HibernateException {
		return x == null && y == null 
				|| (x != null && y !=null && x.equals(y));
	}

	@Override
	public int hashCode(Object x) throws HibernateException {
		return x.hashCode();
	}
	
	


	@Override
	public Object nullSafeGet(ResultSet rs, String[] names, SharedSessionContractImplementor session, Object owner)
			throws HibernateException, SQLException {
		String currencyCode = StringType.INSTANCE.nullSafeGet(rs, names[0], session);
		BigDecimal value = BigDecimalType.INSTANCE.nullSafeGet(rs, names[1], session);
		
		if(currencyCode == null || value == null) return null;
		return Money.of(value, currencyCode);
	}

	@Override
	public void nullSafeSet(PreparedStatement st, Object value, int index, SharedSessionContractImplementor session)
			throws HibernateException, SQLException {
		Money money = (Money) value;
		final String currencyValue = money != null ? money.getCurrency().getCurrencyCode() : null;
		final BigDecimal numberValue = money != null ? money.getNumberStripped() : null;
		
		StringType.INSTANCE.nullSafeSet(st, currencyValue, index, session);
		BigDecimalType.INSTANCE.nullSafeSet(st, numberValue, index+1, session);
	}

	@Override
	public Object deepCopy(Object value) throws HibernateException {
		return value;
	}

	@Override
	public boolean isMutable() {
		return false;
	}

	@Override
	public Serializable disassemble(Object value) throws HibernateException {
		return (Money) value;
	}

	@Override
	public Object assemble(Serializable cached, Object owner) throws HibernateException {
		return (Money) cached;
	}

	@Override
	public Object replace(Object original, Object target, Object owner) throws HibernateException {
		return (Money) original;
	}
	
}
