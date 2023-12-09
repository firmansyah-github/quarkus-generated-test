<#-- 
${PUB_DB_TYPE|postgresql|Description}
${PUB_DB_IP|localhost|Description}
${PUB_DB_PORT|5432|Description}
${PUB_DB_USER|postgres|Description}
${PUB_DB_NAME|postgres|Description}
${PUB_DB_PASS|xWmGrW0hi4|Description}
  -->
<#macro app2db_docker_compose >
<#switch PUB_DB_TYPE>
  <#case 'db2'>
      DB_URL: jdbc:${PUB_DB_TYPE}://${PRV_DB_IP}:${PUB_DB_PORT}/${PUB_DB_NAME}:user=${PUB_DB_USER};password=${PUB_DB_PASS};
      <#break>
  <#case 'postgresql'>
      #5432
      DB_URL: jdbc:${PUB_DB_TYPE}://${PRV_DB_IP}:${PUB_DB_PORT}/${PUB_DB_NAME}
      <#break>
  <#case 'oracle'>
      #1521
      DB_URL: jdbc:${PUB_DB_TYPE}:thin:@${PRV_DB_IP}:${PUB_DB_PORT}:${PUB_DB_NAME}
      <#break>
  <#case 'sqlserver'>
      #1433
      DB_URL: jdbc:${PUB_DB_TYPE}://${PRV_DB_IP}:${PUB_DB_PORT};databaseName=${PUB_DB_NAME}
      <#break>
  <#case 'mysql'>
      #3306
      DB_URL: jdbc:${PUB_DB_TYPE}://${PRV_DB_IP}:${PUB_DB_PORT}/${PUB_DB_NAME}
      <#break>
  <#default>
      #5432
      DB_URL: jdbc:${PUB_DB_TYPE}://${PRV_DB_IP}:${PUB_DB_PORT}/${PUB_DB_NAME}
</#switch>
      DB_USER: ${PUB_DB_USER}
      DB_PASSWORD: ${PUB_DB_PASS}
</#macro>

<#macro db_docker_compose >
<#switch PUB_DB_TYPE>
  <#case 'db2'>
  <#--
  ISSUES: Quarkus Native: ERROR [io.qua.run.Application] (main) Failed to start application (with profile prod): com.ibm.db2.jcc.am.SqlSyntaxErrorException: [jcc]Missing resource bundle: A resource bundle could not be found in the com.ibm.db2.jcc package for IBM Data Server Driver for JDBC and SQLJ ERRORCODE=-4461, SQLSTATE=42815
  LINKS: https://github.com/quarkusio/quarkus/issues/11395
  SOLUTION: Upgrade Quarkus version or use Quarkus JVM
  -->

  ${PRV_DB_IP}:
    image: icr.io/db2_community/db2 # https://www.ibm.com/docs/en/db2/11.5?topic=deployments-db2-community-edition-docker
    container_name: ${PUB_DB_TYPE}-database
    platform: linux/x86_64
    privileged: true
    ports:
      - "${PUB_DB_PORT}:${PUB_DB_PORT}"  # Map host port to Db2 container's port
    environment:
      - LICENSE=accept
      - DB2INSTANCE=db2inst1
      - DB2INST1_PASSWORD=${PUB_DB_PASS}
      - DBNAME=${PUB_DB_NAME}
      - IS_OSXFS=true
      - BLU=false
      - ENABLE_ORACLE_COMPATIBILITY=false
      - HEALTH_CHECK_TIMEOUT=30  # Adjust as needed
    volumes:
      - ${PUB_DB_TYPE}-data:/database  # Persist Db2 data
    healthcheck:
      test: ["CMD", "db2", "connect", "to", "${PUB_DB_NAME}", "${PUB_DB_USER}", "db2inst1", "using", "${PUB_DB_PASS}"]
      # test: ["CMD-SHELL", "su - db2inst1 -c \"db2gcf -s\""]
      interval: 10s
      timeout: 5s
      retries: 3
      start_period: 30s  # Give Db2 some time to initialize
    
  	<#break>
  <#case 'postgresql'>
  ${PRV_DB_IP}:
    image: postgres:latest
    container_name: ${PUB_DB_TYPE}-database
    environment:
      POSTGRES_USER: ${PUB_DB_USER}
      POSTGRES_PASSWORD: ${PUB_DB_PASS}
      POSTGRES_DB: ${PUB_DB_NAME}
    ports:
      - "${PUB_DB_PORT}:${PUB_DB_PORT}"
    networks:
      - ${PUB_DOCKER_NT}
    healthcheck:
      test: ["CMD", "pg_isready", "-U", "${PUB_DB_USER}"]
      interval: 5s
      retries: 10
    volumes:
      - ${PUB_DB_TYPE}-data:/var/lib/postgresql/data  # Persist PostgreSQL data
  	<#break>
  <#case 'oracle'>
  ${PRV_DB_IP}:
    image: container-registry.oracle.com/database/enterprise:latest  # Replace with your actual Oracle DB Docker image
    container_name: ${PUB_DB_TYPE}-database
    ports:
      - "${PUB_DB_PORT}:${PUB_DB_PORT}" #"1521:1521"  # Map host port to Oracle container's port
    networks:
      - ${PUB_DOCKER_NT}
    environment:
      - ORACLE_SID=${PUB_DB_USER} #ORCLCDB
      - ORACLE_PDB=${PUB_DB_NAME} #ORCLPDB1
      - ORACLE_PWD=${PUB_DB_PASS}
      #- INIT_SGA_SIZE=<your_database_SGA_memory_MB> # The total memory in MB that should be used for all SGA components (optional)
      #- INIT_PGA_SIZE=<your_database_PGA_memory_MB> # The target aggregate PGA memory in MB that should be used for all server processes attached to the instance (optional)
      #- ORACLE_EDITION=<your_database_edition> # The Oracle Database Edition (enterprise/standard, default: enterprise)
      #- ORACLE_CHARACTERSET=<your_character_set>  # The character set to use when creating the database (default: AL32UTF8)
      #- ENABLE_ARCHIVELOG=true # To enable archive log mode when creating the database (default: false). Supported 19.3 onwards.
    volumes:
      - ${PUB_DB_TYPE}-data:/opt/oracle/oradata  # Persist Oracle data 
      # - oracle-backup:/opt/oracle/backup
      # ./:/opt/oracle/scripts/startup | /docker-entrypoint-initdb.d/startup
      # ./:/opt/oracle/scripts/setup | /docker-entrypoint-initdb.d/setup
    healthcheck:
      test: ["CMD", "bash", "-c", "echo 'SELECT 1 FROM DUAL;' | sqlplus system/$ORACLE_PWD@//${PRV_DB_IP}:${PUB_DB_PORT}/$ORACLE_SID"]
      #test: ["CMD", "sqlplus", "-L", "sys/Oracle_123@//${PRV_DB_IP}:${PUB_DB_PORT}/$ORACLE_SID as sysdba", "@healthcheck.sql"]
      interval: 10s
      timeout: 5s
      retries: 3
      start_period: 15s
  	<#break>
  <#case 'sqlserver'>
  ${PRV_DB_IP}:
    image: mcr.microsoft.com/mssql/server
    container_name: ${PUB_DB_TYPE}-database
    ports:
      - "${PUB_DB_PORT}:${PUB_DB_PORT}" #"1433:1433"  # Map host port to SQL Server container's port
    networks:
      - ${PUB_DOCKER_NT}
    environment:
      - ACCEPT_EULA=Y
      - SA_PASSWORD=${PUB_DB_PASS}
      - MSSQL_PID=${PUB_DB_USER}
    volumes:
      - ${PUB_DB_TYPE}-data:/var/opt/mssql  # Persist SQL Server data
    healthcheck:
      test: ["CMD-SHELL", "sqlcmd -S ${PRV_DB_IP} -U SA -P ${PUB_DB_PASS} -Q 'SELECT 1'"]
      interval: 10s
      timeout: 5s
      retries: 3
      start_period: 15s
  	<#break>
  <#case 'mysql'>
  ${PRV_DB_IP}:
    image: mysql
    container_name: ${PUB_DB_TYPE}-database
    ports:
      - "${PUB_DB_PORT}:${PUB_DB_PORT}"  #"3306:3306"  # Map host port to MySQL container's port
    environment:
      - MYSQL_ROOT_PASSWORD=root_password
      - MYSQL_DATABASE=${PUB_DB_NAME}
      - MYSQL_USER=${PUB_DB_USER}
      - MYSQL_PASSWORD=${PUB_DB_PASS}
    networks:
      - ${PUB_DOCKER_NT}
    volumes:
      - ${PUB_DB_TYPE}-data:/var/lib/mysql  # Persist MySQL data
    healthcheck:
      test: ["CMD", "mysqladmin", "ping", "-h", "${PRV_DB_IP}", "-u", "${PUB_DB_USER}", "-p", "${PUB_DB_PASS}"]
      interval: 10s
      timeout: 5s
      retries: 3
      start_period: 15s
  	<#break>
  <#default>
  ${PRV_DB_IP}:
    image: postgres:latest
    container_name: postgres-database
    environment:
      POSTGRES_USER: ${PUB_DB_USER}
      POSTGRES_PASSWORD: ${PUB_DB_PASS}
      POSTGRES_DB: ${PUB_DB_NAME}
    ports:
      - "${PUB_DB_PORT}:${PUB_DB_PORT}"
    networks:
      - ${PUB_DOCKER_NT}
    healthcheck:
      test: ["CMD", "pg_isready", "-U", "${PUB_DB_USER}"]
      interval: 5s
      retries: 10
    volumes:
      - ${PUB_DB_TYPE}-data:/var/lib/postgresql/data  # Persist PostgreSQL data
 </#switch>
 </#macro>

 
<#macro db_application_properties > 
 # Database configuration
quarkus.datasource.db-kind=${PUB_DB_TYPE}
quarkus.datasource.username=<#noparse>${</#noparse>DB_USER:${PUB_DB_USER}}
quarkus.datasource.password=<#noparse>${</#noparse>DB_PASSWORD:${PUB_DB_PASS}}

  <#switch PUB_DB_TYPE>
	<#case 'db2'>
quarkus.datasource.jdbc.url=<#noparse>${</#noparse>DB_URL:jdbc:${PUB_DB_TYPE}://${PUB_DB_IP}:${PUB_DB_PORT}/${PUB_DB_NAME}:user=${PUB_DB_USER};password=${PUB_DB_PASS};}
quarkus.datasource.jdbc.driver=com.ibm.db2.jcc.DB2Driver
      <#break>
	<#case 'postgresql'>
#5432
quarkus.datasource.jdbc.url=<#noparse>${</#noparse>DB_URL:jdbc:${PUB_DB_TYPE}://${PUB_DB_IP}:${PUB_DB_PORT}/${PUB_DB_NAME}}
quarkus.datasource.jdbc.driver=org.${PUB_DB_TYPE}.Driver
      <#break>
	<#case 'oracle'>
#1521
quarkus.datasource.jdbc.url=<#noparse>${</#noparse>DB_URL:jdbc:${PUB_DB_TYPE}:thin:@${PUB_DB_IP}:${PUB_DB_PORT}:${PUB_DB_NAME}}
quarkus.datasource.jdbc.driver=oracle.jdbc.OracleDriver
       <#break>
	<#case 'sqlserver'>
#1433
quarkus.datasource.jdbc.url=<#noparse>${</#noparse>DB_URL:jdbc:${PUB_DB_TYPE}://${PUB_DB_IP}:${PUB_DB_PORT};databaseName=${PUB_DB_NAME}}
quarkus.datasource.jdbc.driver=com.microsoft.${PUB_DB_TYPE}.jdbc.SQLServerDriver
       <#break>
	<#case 'mysql'>
#3306
quarkus.datasource.jdbc.url=<#noparse>${</#noparse>DB_URL:jdbc:${PUB_DB_TYPE}://${PUB_DB_IP}:${PUB_DB_PORT}/${PUB_DB_NAME}}
quarkus.datasource.jdbc.driver=com.${PUB_DB_TYPE}.cj.jdbc.Driver
  </#switch>
</#macro>
docker run -i \
       -e DB_URL="jdbc:postgresql://$(ipconfig getifaddr en0):5432/postgres" \
       -e DB_USER="postgres" \
       -e DB_PASSWORD="xWmGrW0hi4" \
       --rm -p 8080:8080 firmansyahprofess/realworld-api-quarkus-jvm

<#macro db_readme prefix=""> 
  <#switch PUB_DB_TYPE>
	<#case 'db2'>
${prefix}       -e DB_URL="jdbc:${PUB_DB_TYPE}://$(ipconfig getifaddr en0):${PUB_DB_PORT}/${PUB_DB_NAME}:user=${PUB_DB_USER};password=${PUB_DB_PASS};" \
      <#break>
	<#case 'postgresql'>
${prefix}       -e DB_URL="jdbc:${PUB_DB_TYPE}://$(ipconfig getifaddr en0):${PUB_DB_PORT}/${PUB_DB_NAME}" \
      <#break>
	<#case 'oracle'>
${prefix}       -e DB_URL="jdbc:${PUB_DB_TYPE}:thin:@$(ipconfig getifaddr en0):${PUB_DB_PORT}:${PUB_DB_NAME}" \
       <#break>
	<#case 'sqlserver'>
${prefix}       -e DB_URL="jdbc:${PUB_DB_TYPE}://$(ipconfig getifaddr en0):${PUB_DB_PORT};databaseName=${PUB_DB_NAME}" \
       <#break>
	<#case 'mysql'>
${prefix}       -e DB_URL="jdbc:${PUB_DB_TYPE}://$(ipconfig getifaddr en0):${PUB_DB_PORT}/${PUB_DB_NAME}" \
  </#switch>
${prefix}       -e DB_USER="${PUB_DB_USER}" \
 ${prefix}      -e DB_PASSWORD="${PUB_DB_PASS}" \
</#macro>