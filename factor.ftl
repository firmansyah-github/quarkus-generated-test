<#-- 
g=global
p=private
c=class
f=field
i=import
v=variable (field class or field method)
m=method
def=definition (type and name)
pk=primary key
fk=foreign key
-->
<#macro INS>${entity.instanceName}</#macro>
<#assign INT><@INS/></#assign>


<#-- 1. global variable generic: fieldType fieldName -->
<#macro gv f c=false
			 preType="" 
			 sufType="" 
			 preName="" 
			 sufName="" 
			 unCapName=true>
	<#assign vName>${f.fieldName}</#assign>
	<#if unCapName><#assign vName=vName?uncap_first/><#else><#assign vName=vName?cap_first/></#if>
	<#if c>${preType}${f.simpleFieldType}${sufType}</#if> ${preName}${vName}${sufName}
</#macro>


<#-- 2. global variable importKey: fieldType fieldName -->
<#macro gv_imp i c=false
				 preType="" 
				 sufType="" 
				 preName="" 
				 sufName="" 
				 unCapName=true>
	<#assign vName>${i.pkBaseName?uncap_first}${columnToField(i.fkColumnName)}</#assign>
	<#if unCapName><#assign vName=vName?uncap_first/><#else><#assign vName=vName?cap_first/></#if>
	<#if c>${preType}${i.pkBaseName?cap_first}${sufType}</#if> ${preName}${vName}${sufName}
</#macro>



<#macro gv_imps preType="" 
				sufType="" 
				preName="" 
				sufName="" 
				c=false 
				unCapName=true 
				isFirstVar=false 
				sep="">
	<#list entity.importedKeys as i>
		<#if i?is_first && !isFirstVar>${sep}</#if>
		<@gv_imp i=i c=c unCapName=unCapName
		             preType="${preType}" 
				     sufType="${sufType}" 
				     preName="${preName}" 
				     sufName="${sufName}"/><#sep>${sep}
	</#list>
</#macro>


<#-- 3. global variable exportKey: fieldType fieldName -->
<#macro gv_exp e c=false
				 preType="" 
				 sufType="" 
				 preName="" 
				 sufName="" 
				 unCapName=false>
	<#assign X>${e.fkBaseName?uncap_first}${columnToField(e.fkColumnName)}</#assign>
	<#if preName?has_content><#assign X=X?cap_first/></#if>
	<#if unCapName><#assign X=X?uncap_first/></#if>
	<#if c>${preType}${e.fkBaseName?cap_first}${sufType}</#if> ${preName}${X}${sufName}
</#macro>



<#macro gv_exps preType="" 
				sufType="" 
				preName="" 
				sufName="" 
				c=false 
				unCapName=false 
				isFirstVar=false 
				sep=""
				isFindFKType=false
				modifier="">
	<#list entity.exportedKeys as e>
		<#if e?is_first && !isFirstVar>${sep}</#if>
		<#assign fkType>
			<#compress>
				<#if isFindFKType>${findDataType(e.fkTableName, e.fkColumnName)}
				<#else>${preType}${e.fkBaseName?cap_first}${sufType}
				</#if>
			</#compress>
		</#assign>
		<#assign preNM><#if c>${modifier} List<${fkType}> ${preName}<#else>${preName}</#if></#assign>
		<@gv_exp e=e c=false unCapName=unCapName 
				     preName="${preNM}" 
				     sufName="${sufName}"/><#sep>${sep}
	</#list>
</#macro>



<#macro gv_keys preType="" 
				sufType="" 
				preName="" 
				sufName="" 
				c=false 
				unCapName=true 
				isFirstVar=true 
				sep="">
	<#list entity.primaryKeyFieldList as k>
		<#if k?is_first && !isFirstVar>${sep}</#if>
        <#if k.create >
			<@gv f=k c=c unCapName=unCapName
		             preType="${preType}" 
				     sufType="${sufType}" 
				     preName="${preName}" 
				     sufName="${sufName}"/>
		</#if>
		<#sep>${sep}		     
	</#list>
</#macro>



<#macro gmv_pk c=false>
	<#assign ky>
		<#compress>
			<#list entity.primaryKeyFieldList as fld>${fld.fieldName?uncap_first}<#sep>, 
			</#list>
		</#compress>
	</#assign>
${ky?trim?replace('\n','')}</#macro>



<#macro gmv_pk entity c=true >
<#assign ky>
		<#compress>
			<#list entity.primaryKeyFieldList as fld><@gv c=c f=fld/><#sep>, 
			</#list>
		</#compress>
	</#assign>
${ky?trim?replace('\n','')}</#macro>



<#macro pmv_entityutils_xxx isIncludeParent=false isIncludeChild=false suffix="" entity=entity>
	<#if isIncludeParent>
		<#assign seq = entity.fieldListExceptForeignKey >
	<#else>
		<#assign seq = entity.fieldListSortByOrdinalPosition >
	</#if>
	<#assign var>
	<#compress>
	<#list seq as fld>
		<#if fld.create>
			<#if (entity.primaryKeyFieldList?size gt 1) && isIncludeParent && fld.pkPosition gt 0>
				${entity.getInstanceName()}Entity.getPrimaryKey().${fld.getterMethodName}()<#sep>,
			<#else>
				${entity.getInstanceName()}Entity.${fld.getterMethodName}()<#sep>,
			</#if>
		</#if>
	</#list>
	<#compress><#if isIncludeChild><#list entity.exportedKeys as m><#if m?is_first>,</#if>null<#sep>, </#list></#if></#compress>
	<#if isIncludeParent>
		<#list entity.importedKeys as m>
		<#if (m?is_first) && ((entity.exportedKeys?size gt 0) || (seq?size gt 0))>,</#if>${m.pkBaseName?uncap_first}(${entity.getInstanceName()}Entity.get${m.pkBaseName?cap_first}${columnToField(m.fkColumnName)}())<#sep>, 
		</#list>
	</#if>
	</#compress>
	</#assign>
				${var?trim?replace('\n','')?replace(' ','')?replace(',',',\n\t\t\t\t')}
</#macro>



<#macro pmb_resourcesintegrationtest_create_dto prefix="" suffix="" entity=entity isIncludeParent=false>
	<#assign inx>${prefix}${entity.className}${suffix}</#assign>
	<#assign ins>${inx?uncap_first}</#assign>
		final var ${ins} = new ${prefix}${entity.className}${suffix}();
	<#--fields of PK ONLY -->
	<#if entity.primaryKeyFieldList?size gt 1>
		final var ${entity.getInstanceName()}EntityKey = new ${entity.className}EntityKey();
		<#list entity.primaryKeyFieldList as pkField>
		    <#--NOT IMPORTED KEYS  -->
			<#if pkField.create  && !isImportedKey(pkField.columnName,entity)!false>
		${entity.getInstanceName()}EntityKey.set${pkField.fieldName?cap_first}(${createDummyData(pkField,"unique")});
			</#if>
			<#-- IMPORTED KEYS -->
			<#if pkField.create  && isImportedKey(pkField.columnName,entity)!false>
				<#list entity.importedKeys as m>
					<#if pkField.columnName == m.fkColumnName>
		${entity.getInstanceName()}EntityKey.set${m.pkBaseName?cap_first}${columnToField(m.fkColumnName)}(${m.pkBaseName?uncap_first}${columnToField(m.fkColumnName)}${suffix});
					</#if>
				</#list>
			</#if>
		</#list>
		${ins}.setPrimaryKey(${entity.getInstanceName()}EntityKey);
	</#if>
	<#--fields of all sort by ordinal position -->
	<#list entity.fieldListSortByOrdinalPosition  as fld>
		<#--NOT IMPORTED KEYS  AND PK-->
		<#if fld.create  && (!isImportedKey(fld.columnName,entity)!false)  && (fld.pkPosition gt 0) && (entity.primaryKeyFieldList?size == 1)>
		${ins}.set${fld.fieldName?cap_first}(${createDummyData(fld,"unique")});
		</#if>
		<#--NOT IMPORTED KEYS  AND NOT PK-->
		<#if fld.create  && (!isImportedKey(fld.columnName,entity)!false)  && (fld.pkPosition == 0) >
		${ins}.set${fld.fieldName?cap_first}(${createDummyData(fld,"unique")});
		</#if>
	</#list>
	<#--fields of imported keys -->		
	<#if isIncludeParent>
		<#list entity.importedKeys as m>
		${ins}.set${m.pkBaseName?cap_first}${columnToField(m.fkColumnName)}(${m.pkBaseName?uncap_first}${columnToField(m.fkColumnName)}${suffix});
		</#list>
	</#if>
</#macro>



<#macro pmb_resourcesintegrationtest_create_parentdto prefix="" suffix="" entity=entity>
	<#list entity.importedKeys as m>
		final var ${m.pkBaseName?uncap_first}${columnToField(m.fkColumnName)}${suffix}= create${m.pkBaseName?cap_first}(unique);
	</#list>
</#macro>



<#macro pmb_resourcesintegrationtest_findxxxentitybypk entity>
	<#if entity.primaryKeyFieldList?size == 1>
		<#list entity.primaryKeyFieldList as fld>
		return transaction(() -> entityManager.find(${entity.className}Entity.class, ${fld.fieldName?uncap_first}));
		</#list>
	<#elseif entity.primaryKeyFieldList?size gt 1>
		final var ${entity.instanceName}EntityKey = new ${entity.className}EntityKey();
		<#list entity.primaryKeyFieldList as pkField>
			<#if pkField.create  && !isImportedKey(pkField.columnName, entity)!false>
		${entity.className}EntityKey.set${pkField.fieldName?cap_first}(${entity.instanceName}.get${pkField.fieldName?cap_first}());
			</#if>
			<#if pkField.create  && isImportedKey(pkField.columnName, entity)!false>
				<#list entity.importedKeys as i>
					<#if pkField.columnName == i.fkColumnName>
						<#assign epk>
							<#compress>
		<@gv_imp i=i preName="final var " sufName="Entity = entityManager.find("/> 
			${i.pkBaseName?cap_first}Entity.class, ${columnToField(i.fkColumnName)?uncap_first});	
		<@gv_imp i=i unCapName=false preName="${entity.instanceName}EntityKey.set" sufName=""/>(
			<@gv_imp i=i sufName="Entity"/>
			);
						    </#compress>
						 </#assign>
		${epk?trim?replace('\n','')?replace(';',';\n\t\t')}
					</#if>
				</#list>
			</#if>
		</#list>
		
		return transaction(() -> entityManager.find(${entity.className}Entity.class, ${entity.instanceName}EntityKey));
	</#if>
</#macro>



<#function findDataType table column>
	<#list adv.entities as ent>
  		<#list ent.fieldList as fd>
  			<#if table == ent.tableName && column == fd.columnName>
    			<#return fd.fieldType>
  			</#if>
  		</#list>
  	</#list>
</#function>

<#function columnToField columnName>
	<#return columnName?replace('_',' ')?capitalize?replace(' ','')>
</#function>

<#function isImportedKey column entity>
	<#list entity.importedKeys as m>
		<#if m.fkColumnName == column>
    		<#return true>
  		</#if>
	</#list>
</#function>

<#function createDummyData fld unique="">
	<#switch fld.fieldType>
 		<#case "java.lang.String">
			<#if fld.pkPosition == 0>
				<#assign inx>"${fld.fieldName}"+${unique}</#assign>
				<#return inx>
			<#else>	
				<#assign inx>UUID.randomUUID().toString()+${unique}</#assign>
				<#return inx>
			</#if>		
     			<#break>
		<#case "java.time.LocalDateTime">
				<#assign inx>LocalDateTime.now()</#assign>
				<#return inx>
			    <#break>
		<#default>
				<#assign inx>"${fld.fieldName}"+${unique}</#assign>	
				<#return inx>	    
	</#switch>
</#function>






