// created by the factor : May 30, 2024, 6:48:44 AM  
package firmansyah.utils;

import org.hibernate.dialect.H2Dialect;

import java.sql.Types;

public class H2CustomDialect extends H2Dialect {

  public H2CustomDialect() {
    super();
    //registerColumnType(Types.BINARY, "varbinary");
  }
}
