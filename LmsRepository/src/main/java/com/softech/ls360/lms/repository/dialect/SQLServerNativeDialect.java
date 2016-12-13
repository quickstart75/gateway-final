package com.softech.ls360.lms.repository.dialect;

import java.sql.Types;

import org.hibernate.dialect.SQLServer2012Dialect;
import org.hibernate.type.StandardBasicTypes;

public class SQLServerNativeDialect extends SQLServer2012Dialect {
	
	public SQLServerNativeDialect() {
        super();
        registerHibernateType(Types.NVARCHAR, StandardBasicTypes.STRING.getName());
    }

}
