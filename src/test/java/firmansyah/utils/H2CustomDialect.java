// created by the factor : Jan 29, 2024, 10:05:08 AM  
package firmansyah.utils;

import org.hibernate.dialect.H2Dialect;

import java.sql.Types;

public class H2CustomDialect extends H2Dialect {

  public H2CustomDialect() {
    super();
    //registerColumnType(Types.BINARY, "varbinary");
  }
}
