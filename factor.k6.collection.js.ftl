<#macro auto pre="" suf="">${pre} by the factor : ${adv.buildDate?datetime} ${suf} 
</#macro>
<#macro all_k6_collection entity>
group("${entity.tableName}", function() {
	<#assign myString = entity.tableName+'# #'+entity.tableName+'# '+','+getParentRecursive(entity)>
	<#assign myList = (myString?trim?remove_ending(','))?split(",")>
	<#list myList?reverse as item>
		<#assign fkTableName = (item?split("#")[0]!''?string)>
		<#assign fkColumnName = (item?split("#")[1]!''?string)>
		<#assign pkTableName = (item?split("#")[2]!''?string)>
		<#assign pkColumnName = (item?split("#")[3]!''?string)>
  		<#list adv.entities as entity>
      		<#if pkTableName == entity.tableName>

	postman[Request]({
      name: "Create ${entity.tableName} for create ${fkTableName}",
      id: "${adv.buildDate?datetime?string["yyyy-MM-dd-HH-mm-ss-SSSSSS"]}",
      method: "POST",
      options: { timeout: timeout },
      address: "{{APIURL}}/${PUB_GLOBAL_CONTEXT}/${entity.instanceName}",
      data:
        '{"${entity.instanceName}":{<#list entity.fieldListSortByOrdinalPosition as fld><#if fld.create && !isImportedKeyWithEntity(entity,fld.columnName)!false>"${fld.fieldName}":"${createDummyDataPostman(fld)}"<#sep>,</#if><#if fld.create  && isImportedKeyWithEntity(entity,fld.columnName)!false><#list entity.importedKeys as i><#if fld.columnName == i.fkColumnName>"${fld.fieldName}":"{{${i.fkTableName}.${i.pkTableName}}}"</#if></#list><#sep>,</#if></#list>}}',
      headers: {
        "Content-Type": "application/json",
        "X-Requested-With": "XMLHttpRequest",
        Authorization: "Token {{token}}"
      },
      pre() {
        //console.log('Running beforeTest script...');
        // Pre-request Script
        const uniqueID = uuidv4();
        pm.globals.set("UNIQUE", uniqueID);
        //console.log(formattedDate);
      },
      post(response) {
	    //console.log('Request URL: '+ response.request.url);
        //console.log('Request Method: '+ response.request.method);
        //console.log('Request Body: '+ response.request.body);

        //console.log('Response Status: '+ response.status);
        //console.log('Response Body: '+ response.body);
        //console.log(pm.globals.get("UNIQUE"));
        var is201Response = responseCode.code === 201;

        tests["Response code is 201 OK - Create ${entity.tableName} for create ${fkTableName}"] = is201Response;

        if (!environment.isIntegrationTest && is201Response) {
          var responseJSON = JSON.parse(responseBody);

          tests[
            'Response contains "${entity.instanceName}" property'
          ] = responseJSON.hasOwnProperty("${entity.instanceName}");

          var ${entity.instanceName} = responseJSON.${entity.instanceName} || {};
          <#list entity.fieldListSortByOrdinalPosition as fld>
			<#if fld.create && !isImportedKeyWithEntity(entity,fld.columnName)!false>
		  tests['${entity.instanceName} has "${fld.fieldName}" property'] = ${entity.instanceName}.hasOwnProperty("${fld.fieldName}"); 
				<#if fld.fieldType == "java.time.LocalDateTime">
		  tests['${entity.instanceName} of "${fld.fieldName}" property is an ISO 8601 timestamp'] = /^\d{4}-\d{2}-\d{2}T\d{2}:\d{2}:\d{2}\.\d{6}$/.test(${entity.instanceName}.${fld.fieldName});
				</#if>
				
				<#if fld.pkPosition != 0>
	      if(tests['${entity.instanceName} has "${fld.fieldName}" property']){
          		pm.globals.set('${fkTableName}.${pkTableName}', ${entity.instanceName}.${fld.fieldName});
          }
           
          tests['Global variable "${fkTableName}.${pkTableName}" has been set'] = pm.globals.get('${fkTableName}.${pkTableName}') === ${entity.instanceName}.${fld.fieldName};
				      
		  if(tests['${entity.instanceName} has "${fld.fieldName}" property']){
                pm.globals.set('${entity.instanceName}.${fld.fieldName}', ${entity.instanceName}.${fld.fieldName});
          }
          
          tests['Global variable "${entity.instanceName}.${fld.fieldName}" has been set'] = pm.globals.get('${entity.instanceName}.${fld.fieldName}') === ${entity.instanceName}.${fld.fieldName};
	            </#if>	
		    </#if>
			<#if fld.create  && isImportedKeyWithEntity(entity,fld.columnName)!false>
				<#list entity.importedKeys as i>
					<#if fld.columnName == i.fkColumnName>
						<#if fld.pkPosition == 0>
		  tests['${entity.instanceName} has "${fld.fieldName}" property'] = ${entity.instanceName}.hasOwnProperty('${i.pkBaseName?uncap_first}${columnToField(i.fkColumnName)}Response');
						</#if>
					    <#if fld.pkPosition != 0>
		  tests['${entity.instanceName} has "${fld.fieldName}" property'] = ${entity.instanceName}.hasOwnProperty('${i.pkBaseName?uncap_first}${columnToField(i.fkColumnName)}Response');
		  
		  if(tests['${entity.instanceName} has "${fld.fieldName}" property']){
                pm.globals.set('${entity.instanceName}.${fld.fieldName}', ${entity.instanceName}.${i.pkBaseName?uncap_first}${columnToField(i.fkColumnName)}Response.${columnToField(i.pkColumnName)?uncap_first});
           }
                  
          tests['Global variable "${entity.instanceName}.${fld.fieldName}" has been set'] = pm.globals.get('${entity.instanceName}.${fld.fieldName}') === ${entity.instanceName}.${i.pkBaseName?uncap_first}${columnToField(i.fkColumnName)}Response.${columnToField(i.pkColumnName)?uncap_first};
				       </#if>
				    </#if>
				 </#list>
		     </#if>
		  </#list>
        }
       }
      });sleep(delay);
        	
      		</#if>
    	</#list>
	</#list>
	
	postman[Request]({
      name: "Find ${entity.instanceName} By Primary Key",
      id: "${adv.buildDate?datetime?string["yyyy-MM-dd-HH-mm-ss-SSSSSS"]}",
      method: "GET",
      options: { timeout: timeout },
      address: "{{APIURL}}/${PUB_GLOBAL_CONTEXT}/${entity.instanceName}/find?<#list entity.primaryKeyFieldList as fld><#if fld.create  && fld.pkPosition != 0>${fld.fieldName}={{${entity.instanceName}.${fld.fieldName}}}</#if><#sep>&</#list>",
      headers: {
        "Content-Type": "application/json",
        "X-Requested-With": "XMLHttpRequest",
        Authorization: "Token {{token}}"
      },
      post(response) {
        //console.log('Request URL: '+ response.request.url);
        //console.log('Request Method: '+ response.request.method);
        //console.log('Request Body: '+ response.request.body);

        //console.log('Response Status: '+ response.status);
        //console.log('Response Body: '+ response.body);
        var is200Response = responseCode.code === 200;
                       
        tests['Response code is 200 OK - Find ${entity.instanceName} By Primary Key'] = is200Response;
        
	    if (!(environment.isIntegrationTest) && is200Response) {
	    	var responseJSON = JSON.parse(responseBody);
	                 
	        tests['Response contains "${entity.instanceName}" property'] = responseJSON.hasOwnProperty('${entity.instanceName}');
	        
	        var ${entity.instanceName} = responseJSON.${entity.instanceName} || {};
	       
	        <#list entity.fieldListSortByOrdinalPosition as fld>
				<#if fld.create && !isImportedKeyWithEntity(entity,fld.columnName)!false>
			tests['${entity.instanceName} has "${fld.fieldName}" property'] = ${entity.instanceName}.hasOwnProperty('${fld.fieldName}'); 
					<#if fld.fieldType == "java.time.LocalDateTime">
		    tests['${entity.instanceName} of "${fld.fieldName}" property is an ISO 8601 timestamp'] = /^\d{4}-\d{2}-\d{2}T\d{2}:\d{2}:\d{2}\.\d{6}$/.test(${entity.instanceName}.${fld.fieldName});
				    </#if>
		
		    if(tests['${entity.instanceName} has "${fld.fieldName}" property']){
            	pm.globals.set('${entity.instanceName}.${fld.fieldName}', ${entity.instanceName}.${fld.fieldName});
            }
            
            tests['Global variable "${entity.instanceName}.${fld.fieldName}" has been set'] = pm.globals.get('${entity.instanceName}.${fld.fieldName}') === ${entity.instanceName}.${fld.fieldName};
		
				</#if>
				<#if fld.create  && isImportedKeyWithEntity(entity,fld.columnName)!false>
					<#list entity.importedKeys as i>
						<#if fld.columnName == i.fkColumnName>
			tests['${entity.instanceName} has "${fld.fieldName}" property'] = ${entity.instanceName}.hasOwnProperty('${i.pkBaseName?uncap_first}${columnToField(i.fkColumnName)}Response');
				
		    if(tests['${entity.instanceName} has "${fld.fieldName}" property']){
            	pm.globals.set('${entity.instanceName}.${fld.fieldName}', ${entity.instanceName}.${i.pkBaseName?uncap_first}${columnToField(i.fkColumnName)}Response.${columnToField(i.pkColumnName)?uncap_first});
            }
            
            tests['Global variable "${entity.instanceName}.${fld.fieldName}" has been set'] = pm.globals.get('${entity.instanceName}.${fld.fieldName}') === ${entity.instanceName}.${i.pkBaseName?uncap_first}${columnToField(i.fkColumnName)}Response.${columnToField(i.pkColumnName)?uncap_first};
			
								</#if>
							</#list>
						</#if>
					</#list>
	    }
      }
    });sleep(delay);
    
    
    postman[Request]({
      name:
        "Find ${entity.tableName} By Filter With Offset0 And Limit10, All Fields, Equals, Or, Sort Desc",
      id: "${adv.buildDate?datetime?string["yyyy-MM-dd-HH-mm-ss-SSSSSS"]}",
      method: "GET",
      options: { timeout: timeout },
      address:
        "{{APIURL}}/${PUB_GLOBAL_CONTEXT}/${entity.instanceName}?offset=0&limit=10&filter=<@xxxpostmancoll_findbyfilter_allfields operator="eq" entity=entity/>&conjunctions=<#list entity.fieldListSortByOrdinalPosition as fld><#if fld.create>OR<#sep>,</#if></#list>&sort=<#list entity.fieldListSortByOrdinalPosition as fld><#if fld.create>-${fld.fieldName}<#sep>,</#if></#list>",
      headers: {
        "Content-Type": "application/json",
        "X-Requested-With": "XMLHttpRequest",
        Authorization: "Token {{token}}"
      },
      post(response) {
        //console.log('Request URL: '+ response.request.url);
        //console.log('Request Method: '+ response.request.method);
        //console.log('Request Body: '+ response.request.body);

        //console.log('Response Status: '+ response.status);
        //console.log('Response Body: '+ response.body);
        var is200Response = responseCode.code === 200;
                       
        tests['Find ${entity.tableName} By Filter With Offset0 And Limit10, All Fields, Equals, Or, Sort Desc - Response code is 200 OK'] = is200Response;
        
	    if (!(environment.isIntegrationTest) && is200Response) {
	    	var responseJSON = JSON.parse(responseBody);
	        
	        tests['Response contains "${entity.instanceName}" property'] = responseJSON.hasOwnProperty('${entity.instanceName}');
	           
	        tests['Response contains "${entity.instanceName}Count" property'] = responseJSON.hasOwnProperty('${entity.instanceName}Count');
	                 
	        var ${entity.instanceName} = responseJSON.${entity.instanceName} || {};
	        if (typeof ${entity.instanceName} === 'object' && Array.isArray(${entity.instanceName}) && ${entity.instanceName}.length > 0) {

			    // Check if 'articles' is an array and has length above 0
			  	check(${entity.instanceName}, {
			    '	Response should be an array': (arr) => Array.isArray(arr) && arr.length > 0,
			  	});
			
			  	// Check if '${entity.instanceName}' has the same length as the response array length
			  	check(${entity.instanceName}, {
			    '	Response array length should match': (arr) => arr.length === ${entity.instanceName}.length,
			  	});
	                   
		        <#list entity.fieldListSortByOrdinalPosition as fld>
					<#if fld.create && !isImportedKeyWithEntity(entity,fld.columnName)!false>
				tests['${entity.instanceName}[0] has "${fld.fieldName}" property'] = ${entity.instanceName}[0].hasOwnProperty('${fld.fieldName}');
					<#if fld.fieldType == "java.time.LocalDateTime">
			    tests['${entity.instanceName}[0] of "${fld.fieldName}" property is an ISO 8601 timestamp'] = /^\d{4}-\d{2}-\d{2}T\d{2}:\d{2}:\d{2}\.\d{6}$/.test(${entity.instanceName}[0].${fld.fieldName});
					</#if>
					  
				    </#if>
					<#if fld.create  && isImportedKeyWithEntity(entity,fld.columnName)!false>
						<#list entity.importedKeys as i>
							<#if fld.columnName == i.fkColumnName>
				tests['${entity.instanceName}[0] has "${fld.fieldName}" property'] = ${entity.instanceName}[0].hasOwnProperty('${i.pkBaseName?uncap_first}${columnToField(i.fkColumnName)}Response');
			                </#if>
						</#list>
					</#if>
				</#list>     
			}             
	      }
      }
    });sleep(delay);
    
    postman[Request]({
      name:
        "Find ${entity.tableName} By Filter With Offset0 And Limit10, All Fields, Equals, Or, Sort Asc",
      id: "${adv.buildDate?datetime?string["yyyy-MM-dd-HH-mm-ss-SSSSSS"]}",
      method: "GET",
      options: { timeout: timeout },
      address:
        "{{APIURL}}/${PUB_GLOBAL_CONTEXT}/${entity.instanceName}?offset=0&limit=10&filter=<@xxxpostmancoll_findbyfilter_allfields operator="eq" entity=entity/>&conjunctions=<#list entity.fieldListSortByOrdinalPosition as fld><#if fld.create>OR<#sep>,</#if></#list>&sort=<#list entity.fieldListSortByOrdinalPosition as fld><#if fld.create>${fld.fieldName}<#sep>,</#if></#list>",
      headers: {
        "Content-Type": "application/json",
        "X-Requested-With": "XMLHttpRequest",
        Authorization: "Token {{token}}"
      },
      post(response) {
        //console.log('Request URL: '+ response.request.url);
        //console.log('Request Method: '+ response.request.method);
        //console.log('Request Body: '+ response.request.body);

        //console.log('Response Status: '+ response.status);
        //console.log('Response Body: '+ response.body);
        var is200Response = responseCode.code === 200;
                       
        tests['Find ${entity.tableName} By Filter With Offset0 And Limit10, All Fields, Equals, Or, Sort Asc - Response code is 200 OK'] = is200Response;
        
	    if (!(environment.isIntegrationTest) && is200Response) {
	    	var responseJSON = JSON.parse(responseBody);
	        
	        tests['Response contains "${entity.instanceName}" property'] = responseJSON.hasOwnProperty('${entity.instanceName}');
	           
	        tests['Response contains "${entity.instanceName}Count" property'] = responseJSON.hasOwnProperty('${entity.instanceName}Count');
	                 
	        var ${entity.instanceName} = responseJSON.${entity.instanceName} || {};
	        if (typeof ${entity.instanceName} === 'object' && Array.isArray(${entity.instanceName}) && ${entity.instanceName}.length > 0) {

			    // Check if 'articles' is an array and has length above 0
			  	check(${entity.instanceName}, {
			    '	Response should be an array': (arr) => Array.isArray(arr) && arr.length > 0,
			  	});
			
			  	// Check if '${entity.instanceName}' has the same length as the response array length
			  	check(${entity.instanceName}, {
			    '	Response array length should match': (arr) => arr.length === ${entity.instanceName}.length,
			  	});
	                   
		        <#list entity.fieldListSortByOrdinalPosition as fld>
					<#if fld.create && !isImportedKeyWithEntity(entity,fld.columnName)!false>
				tests['${entity.instanceName}[0] has "${fld.fieldName}" property'] = ${entity.instanceName}[0].hasOwnProperty('${fld.fieldName}');
					<#if fld.fieldType == "java.time.LocalDateTime">
			    tests['${entity.instanceName}[0] of "${fld.fieldName}" property is an ISO 8601 timestamp'] = /^\d{4}-\d{2}-\d{2}T\d{2}:\d{2}:\d{2}\.\d{6}$/.test(${entity.instanceName}[0].${fld.fieldName});
					</#if>
					  
				    </#if>
					<#if fld.create  && isImportedKeyWithEntity(entity,fld.columnName)!false>
						<#list entity.importedKeys as i>
							<#if fld.columnName == i.fkColumnName>
				tests['${entity.instanceName}[0] has "${fld.fieldName}" property'] = ${entity.instanceName}[0].hasOwnProperty('${i.pkBaseName?uncap_first}${columnToField(i.fkColumnName)}Response');
			                </#if>
						</#list>
					</#if>
				</#list>
			}                  
	      }
      }
    });sleep(delay);
    
    postman[Request]({
      name:
        "Find ${entity.tableName} By Filter With Offset0 And Limit10, All Fields, Equals, Or Conjunctions",
      id: "${adv.buildDate?datetime?string["yyyy-MM-dd-HH-mm-ss-SSSSSS"]}",
      method: "GET",
      options: { timeout: timeout },
      address:
        "{{APIURL}}/${PUB_GLOBAL_CONTEXT}/${entity.instanceName}?offset=0&limit=10&filter=<@xxxpostmancoll_findbyfilter_allfields operator="eq" entity=entity/>&conjunctions=<#list entity.fieldListSortByOrdinalPosition as fld><#if fld.create>OR<#sep>,</#if></#list>",
      headers: {
        "Content-Type": "application/json",
        "X-Requested-With": "XMLHttpRequest",
        Authorization: "Token {{token}}"
      },
      post(response) {
        //console.log('Request URL: '+ response.request.url);
        //console.log('Request Method: '+ response.request.method);
        //console.log('Request Body: '+ response.request.body);

        //console.log('Response Status: '+ response.status);
        //console.log('Response Body: '+ response.body);
        var is200Response = responseCode.code === 200;
                       
        tests['Find ${entity.tableName} By Filter With Offset0 And Limit10, All Fields, Equals, Or Conjunctions - Response code is 200 OK'] = is200Response;
        
	    if (!(environment.isIntegrationTest) && is200Response) {
	    	var responseJSON = JSON.parse(responseBody);
	        
	        tests['Response contains "${entity.instanceName}" property'] = responseJSON.hasOwnProperty('${entity.instanceName}');
	           
	        tests['Response contains "${entity.instanceName}Count" property'] = responseJSON.hasOwnProperty('${entity.instanceName}Count');
	                 
	        var ${entity.instanceName} = responseJSON.${entity.instanceName} || {};
	        if (typeof ${entity.instanceName} === 'object' && Array.isArray(${entity.instanceName}) && ${entity.instanceName}.length > 0) {

			    // Check if 'articles' is an array and has length above 0
			  	check(${entity.instanceName}, {
			    '	Response should be an array': (arr) => Array.isArray(arr) && arr.length > 0,
			  	});
			
			  	// Check if '${entity.instanceName}' has the same length as the response array length
			  	check(${entity.instanceName}, {
			    '	Response array length should match': (arr) => arr.length === ${entity.instanceName}.length,
			  	});
	                   
		        <#list entity.fieldListSortByOrdinalPosition as fld>
					<#if fld.create && !isImportedKeyWithEntity(entity,fld.columnName)!false>
				tests['${entity.instanceName}[0] has "${fld.fieldName}" property'] = ${entity.instanceName}[0].hasOwnProperty('${fld.fieldName}');
					<#if fld.fieldType == "java.time.LocalDateTime">
			    tests['${entity.instanceName}[0] of "${fld.fieldName}" property is an ISO 8601 timestamp'] = /^\d{4}-\d{2}-\d{2}T\d{2}:\d{2}:\d{2}\.\d{6}$/.test(${entity.instanceName}[0].${fld.fieldName});
					</#if>
					  
				    </#if>
					<#if fld.create  && isImportedKeyWithEntity(entity,fld.columnName)!false>
						<#list entity.importedKeys as i>
							<#if fld.columnName == i.fkColumnName>
				tests['${entity.instanceName}[0] has "${fld.fieldName}" property'] = ${entity.instanceName}[0].hasOwnProperty('${i.pkBaseName?uncap_first}${columnToField(i.fkColumnName)}Response');
			                </#if>
						</#list>
					</#if>
				</#list>     
			}             
	      }
      }
    });sleep(delay);
    
    
    postman[Request]({
      name:
        "Find ${entity.tableName} By Filter With Offset0 And Limit10, All Fields, Equals, And Conjunctions",
      id: "${adv.buildDate?datetime?string["yyyy-MM-dd-HH-mm-ss-SSSSSS"]}",
      method: "GET",
      options: { timeout: timeout },
      address:
        "{{APIURL}}/${PUB_GLOBAL_CONTEXT}/${entity.instanceName}?offset=0&limit=10&filter=<@xxxpostmancoll_findbyfilter_allfields operator="eq" entity=entity/>&conjunctions=<#list entity.fieldListSortByOrdinalPosition as fld><#if fld.create>AND<#sep>,</#if></#list>",
      headers: {
        "Content-Type": "application/json",
        "X-Requested-With": "XMLHttpRequest",
        Authorization: "Token {{token}}"
      },
      post(response) {
        //console.log('Request URL: '+ response.request.url);
        //console.log('Request Method: '+ response.request.method);
        //console.log('Request Body: '+ response.request.body);

        //console.log('Response Status: '+ response.status);
        //console.log('Response Body: '+ response.body);
        var is200Response = responseCode.code === 200;
                       
        tests['Find ${entity.tableName} By Filter With Offset0 And Limit10, All Fields, Equals, And Conjunctions - Response code is 200 OK'] = is200Response;
        
	    if (!(environment.isIntegrationTest) && is200Response) {
	    	var responseJSON = JSON.parse(responseBody);
	        
	        tests['Response contains "${entity.instanceName}" property'] = responseJSON.hasOwnProperty('${entity.instanceName}');
	           
	        tests['Response contains "${entity.instanceName}Count" property'] = responseJSON.hasOwnProperty('${entity.instanceName}Count');
	                 
	        var ${entity.instanceName} = responseJSON.${entity.instanceName} || {};
	        if (typeof ${entity.instanceName} === 'object' && Array.isArray(${entity.instanceName}) && ${entity.instanceName}.length > 0) {

			    // Check if 'articles' is an array and has length above 0
			  	check(${entity.instanceName}, {
			    '	Response should be an array': (arr) => Array.isArray(arr) && arr.length > 0,
			  	});
			
			  	// Check if '${entity.instanceName}' has the same length as the response array length
			  	check(${entity.instanceName}, {
			    '	Response array length should match': (arr) => arr.length === ${entity.instanceName}.length,
			  	});
	                   
		        <#list entity.fieldListSortByOrdinalPosition as fld>
					<#if fld.create && !isImportedKeyWithEntity(entity,fld.columnName)!false>
				tests['${entity.instanceName}[0] has "${fld.fieldName}" property'] = ${entity.instanceName}[0].hasOwnProperty('${fld.fieldName}');
					<#if fld.fieldType == "java.time.LocalDateTime">
			    tests['${entity.instanceName}[0] of "${fld.fieldName}" property is an ISO 8601 timestamp'] = /^\d{4}-\d{2}-\d{2}T\d{2}:\d{2}:\d{2}\.\d{6}$/.test(${entity.instanceName}[0].${fld.fieldName});
					</#if>
					  
				    </#if>
					<#if fld.create  && isImportedKeyWithEntity(entity,fld.columnName)!false>
						<#list entity.importedKeys as i>
							<#if fld.columnName == i.fkColumnName>
				tests['${entity.instanceName}[0] has "${fld.fieldName}" property'] = ${entity.instanceName}[0].hasOwnProperty('${i.pkBaseName?uncap_first}${columnToField(i.fkColumnName)}Response');
			                </#if>
						</#list>
					</#if>
				</#list> 
			}                 
	      }
      }
    });sleep(delay);
    
    postman[Request]({
      name:
        "Find ${entity.tableName} By Filter With Offset0 And Limit10, All Fields, Not Equals, Or Conjunctions",
      id: "${adv.buildDate?datetime?string["yyyy-MM-dd-HH-mm-ss-SSSSSS"]}",
      method: "GET",
      options: { timeout: timeout },
      address:
        "{{APIURL}}/${PUB_GLOBAL_CONTEXT}/${entity.instanceName}?offset=0&limit=10&filter=<@xxxpostmancoll_findbyfilter_allfields operator="neq" entity=entity/>&conjunctions=<#list entity.fieldListSortByOrdinalPosition as fld><#if fld.create>OR<#sep>,</#if></#list>",
      headers: {
        "Content-Type": "application/json",
        "X-Requested-With": "XMLHttpRequest",
        Authorization: "Token {{token}}"
      },
      post(response) {
        //console.log('Request URL: '+ response.request.url);
        //console.log('Request Method: '+ response.request.method);
        //console.log('Request Body: '+ response.request.body);

        //console.log('Response Status: '+ response.status);
        //console.log('Response Body: '+ response.body);
        var is200Response = responseCode.code === 200;
                       
        tests['Find ${entity.tableName} By Filter With Offset0 And Limit10, All Fields, Not Equals, Or Conjunctions - Response code is 200 OK'] = is200Response;
        
	    if (!(environment.isIntegrationTest) && is200Response) {
	    	var responseJSON = JSON.parse(responseBody);
	        
	        tests['Response contains "${entity.instanceName}" property'] = responseJSON.hasOwnProperty('${entity.instanceName}');
	           
	        tests['Response contains "${entity.instanceName}Count" property'] = responseJSON.hasOwnProperty('${entity.instanceName}Count');
	                 
	        var ${entity.instanceName} = responseJSON.${entity.instanceName} || {};
            
            if (typeof ${entity.instanceName} === 'object' && Array.isArray(${entity.instanceName}) && ${entity.instanceName}.length > 0) {
			    // Check if 'articles' is an array and has length above 0
			  	check(${entity.instanceName}, {
			    '	Response should be an array': (arr) => Array.isArray(arr) && arr.length > 0,
			  	});
			
			  	// Check if '${entity.instanceName}' has the same length as the response array length
			  	check(${entity.instanceName}, {
			    '	Response array length should match': (arr) => arr.length === ${entity.instanceName}.length,
			  	});
	                   
		        <#list entity.fieldListSortByOrdinalPosition as fld>
					<#if fld.create && !isImportedKeyWithEntity(entity,fld.columnName)!false>
				tests['${entity.instanceName}[0] has "${fld.fieldName}" property'] = ${entity.instanceName}[0].hasOwnProperty('${fld.fieldName}');
					<#if fld.fieldType == "java.time.LocalDateTime">
			    tests['${entity.instanceName}[0] of "${fld.fieldName}" property is an ISO 8601 timestamp'] = /^\d{4}-\d{2}-\d{2}T\d{2}:\d{2}:\d{2}\.\d{6}$/.test(${entity.instanceName}[0].${fld.fieldName});
					</#if>
					  
				    </#if>
					<#if fld.create  && isImportedKeyWithEntity(entity,fld.columnName)!false>
						<#list entity.importedKeys as i>
							<#if fld.columnName == i.fkColumnName>
				tests['${entity.instanceName}[0] has "${fld.fieldName}" property'] = ${entity.instanceName}[0].hasOwnProperty('${i.pkBaseName?uncap_first}${columnToField(i.fkColumnName)}Response');
			                </#if>
						</#list>
					</#if>
				</#list> 
			}                 
	      }
      }
    });sleep(delay);
    
    postman[Request]({
      name:
        "Find ${entity.tableName} By Filter With Offset0 And Limit10, All Fields, Like, And Conjunctions",
      id: "${adv.buildDate?datetime?string["yyyy-MM-dd-HH-mm-ss-SSSSSS"]}",
      method: "GET",
      options: { timeout: timeout },
      address:
        "{{APIURL}}/${PUB_GLOBAL_CONTEXT}/${entity.instanceName}?offset=0&limit=10&filter=<@xxxpostmancoll_findbyfilter_allfields_like_operator operator="like" entity=entity/>&conjunctions=<#list entity.fieldListSortByOrdinalPosition as fld><#if fld.create && fld.fieldType != "java.time.LocalDateTime">AND<#sep>,</#if></#list>",
      headers: {
        "Content-Type": "application/json",
        "X-Requested-With": "XMLHttpRequest",
        Authorization: "Token {{token}}"
      },
      post(response) {
        //console.log('Request URL: '+ response.request.url);
        //console.log('Request Method: '+ response.request.method);
        //console.log('Request Body: '+ response.request.body);

        //console.log('Response Status: '+ response.status);
        //console.log('Response Body: '+ response.body);
        var is200Response = responseCode.code === 200;
                       
        tests['Find ${entity.tableName} By Filter With Offset0 And Limit10, All Fields, Like, And Conjunctions - Response code is 200 OK'] = is200Response;
        
	    if (!(environment.isIntegrationTest) && is200Response) {
	    	var responseJSON = JSON.parse(responseBody);
	        
	        tests['Response contains "${entity.instanceName}" property'] = responseJSON.hasOwnProperty('${entity.instanceName}');
	           
	        tests['Response contains "${entity.instanceName}Count" property'] = responseJSON.hasOwnProperty('${entity.instanceName}Count');
	                 
	        var ${entity.instanceName} = responseJSON.${entity.instanceName} || {};
	        if (typeof ${entity.instanceName} === 'object' && Array.isArray(${entity.instanceName}) && ${entity.instanceName}.length > 0) {

			    // Check if 'articles' is an array and has length above 0
			  	check(${entity.instanceName}, {
			    '	Response should be an array': (arr) => Array.isArray(arr) && arr.length > 0,
			  	});
			
			  	// Check if '${entity.instanceName}' has the same length as the response array length
			  	check(${entity.instanceName}, {
			    '	Response array length should match': (arr) => arr.length === ${entity.instanceName}.length,
			  	});
	                   
		        <#list entity.fieldListSortByOrdinalPosition as fld>
					<#if fld.create && !isImportedKeyWithEntity(entity,fld.columnName)!false>
				tests['${entity.instanceName}[0] has "${fld.fieldName}" property'] = ${entity.instanceName}[0].hasOwnProperty('${fld.fieldName}');
					<#if fld.fieldType == "java.time.LocalDateTime">
			    tests['${entity.instanceName}[0] of "${fld.fieldName}" property is an ISO 8601 timestamp'] = /^\d{4}-\d{2}-\d{2}T\d{2}:\d{2}:\d{2}\.\d{6}$/.test(${entity.instanceName}[0].${fld.fieldName});
					</#if>
					  
				    </#if>
					<#if fld.create  && isImportedKeyWithEntity(entity,fld.columnName)!false>
						<#list entity.importedKeys as i>
							<#if fld.columnName == i.fkColumnName>
				tests['${entity.instanceName}[0] has "${fld.fieldName}" property'] = ${entity.instanceName}[0].hasOwnProperty('${i.pkBaseName?uncap_first}${columnToField(i.fkColumnName)}Response');
			                </#if>
						</#list>
					</#if>
				</#list>  
			}                
	      }
      }
    });sleep(delay);
    
    postman[Request]({
      name:
        "Find ${entity.tableName} By Filter With Offset0 And Limit10, All Fields, Not Like, Or Conjunctions",
      id: "${adv.buildDate?datetime?string["yyyy-MM-dd-HH-mm-ss-SSSSSS"]}",
      method: "GET",
      options: { timeout: timeout },
      address:
        "{{APIURL}}/${PUB_GLOBAL_CONTEXT}/${entity.instanceName}?offset=0&limit=10&filter=<@xxxpostmancoll_findbyfilter_allfields_like_operator operator="nlike" entity=entity/>&conjunctions=<#list entity.fieldListSortByOrdinalPosition as fld><#if fld.create && fld.fieldType != "java.time.LocalDateTime">OR<#sep>,</#if></#list>",
      headers: {
        "Content-Type": "application/json",
        "X-Requested-With": "XMLHttpRequest",
        Authorization: "Token {{token}}"
      },
      post(response) {
        //console.log('Request URL: '+ response.request.url);
        //console.log('Request Method: '+ response.request.method);
        //console.log('Request Body: '+ response.request.body);

        //console.log('Response Status: '+ response.status);
        //console.log('Response Body: '+ response.body);
        var is200Response = responseCode.code === 200;
                       
        tests['Find ${entity.tableName} By Filter With Offset0 And Limit10, All Fields, Not Like, Or Conjunctions - Response code is 200 OK'] = is200Response;
        
	    if (!(environment.isIntegrationTest) && is200Response) {
	    	var responseJSON = JSON.parse(responseBody);
	        
	        tests['Response contains "${entity.instanceName}" property'] = responseJSON.hasOwnProperty('${entity.instanceName}');
	           
	        tests['Response contains "${entity.instanceName}Count" property'] = responseJSON.hasOwnProperty('${entity.instanceName}Count');
	                 
	        var ${entity.instanceName} = responseJSON.${entity.instanceName} || {};

			if (typeof ${entity.instanceName} === 'object' && Array.isArray(${entity.instanceName}) && ${entity.instanceName}.length > 0) {
			    // Check if 'articles' is an array and has length above 0
			  	check(${entity.instanceName}, {
			    '	Response should be an array': (arr) => Array.isArray(arr) && arr.length > 0,
			  	});
			
			  	// Check if '${entity.instanceName}' has the same length as the response array length
			  	check(${entity.instanceName}, {
			    '	Response array length should match': (arr) => arr.length === ${entity.instanceName}.length,
			  	});
	                   
		        <#list entity.fieldListSortByOrdinalPosition as fld>
					<#if fld.create && !isImportedKeyWithEntity(entity,fld.columnName)!false>
				tests['${entity.instanceName}[0] has "${fld.fieldName}" property'] = ${entity.instanceName}[0].hasOwnProperty('${fld.fieldName}');
					<#if fld.fieldType == "java.time.LocalDateTime">
			    tests['${entity.instanceName}[0] of "${fld.fieldName}" property is an ISO 8601 timestamp'] = /^\d{4}-\d{2}-\d{2}T\d{2}:\d{2}:\d{2}\.\d{6}$/.test(${entity.instanceName}[0].${fld.fieldName});
					</#if>
					  
				    </#if>
					<#if fld.create  && isImportedKeyWithEntity(entity,fld.columnName)!false>
						<#list entity.importedKeys as i>
							<#if fld.columnName == i.fkColumnName>
				tests['${entity.instanceName}[0] has "${fld.fieldName}" property'] = ${entity.instanceName}[0].hasOwnProperty('${i.pkBaseName?uncap_first}${columnToField(i.fkColumnName)}Response');
			                </#if>
						</#list>
					</#if>
				</#list> 
			}                 
	      }
      }
    });sleep(delay);
    
});    
</#macro>
<#function createDummyDataPostman fld>
	<#switch fld.fieldType>
  				<#case "java.lang.String">
					<#assign inx><#if fld.unique>{{UNIQUE}}<#else>${fld.fieldName}</#if></#assign>
					<#return inx?trim>			
     				<#break>
			  <#case "java.time.LocalDateTime">
					<#assign inx>{{LocalDateTimeNow}}</#assign>
					<#return inx>
			     	<#break>
			  <#default>
					<#assign inx>${fld.fieldName}</#assign>	
					<#return inx>	    
	</#switch>
</#function>
<#function isImportedKeyWithEntity entity column>
	<#list entity.importedKeys as i>
	<#if i.fkColumnName == column>
    	<#return true>
  	</#if>
	</#list>
</#function>
<#function getParentRecursive eny>
  <#assign result = ''>
  <#list eny.importedKeys as i>
    <#list adv.entities as ent>
      <#if i.pkTableName == ent.tableName>
        <#assign result = result + i.fkTableName+'#'+i.fkColumnName+'#'+i.pkTableName +'#'+i.pkColumnName + ',' + getParentRecursive(ent)>
      </#if>
    </#list>
  </#list>
  <#return result>
</#function>
<#function columnToField columnName>
	<#return columnName?replace('_',' ')?capitalize?replace(' ','')>
</#function>
<#macro xxxpostmancoll_findbyfilter_allfields operator="eq" suffixField="" suffixValue="" entity=entity>
<#assign pu>
    <#compress>
	<#list entity.fieldListSortByOrdinalPosition as fld>
        <#if fld.create>
		${fld.fieldName}${suffixField}%7C${operator}%7C
		{{${entity.instanceName}.${fld.fieldName}}}
		</#if>
		<#sep>%7C%7C
	</#list>
	</#compress>
	</#assign>
${pu?trim?replace('\n','')}</#macro>
<#macro xxxpostmancoll_findbyfilter_allfields_like_operator operator="like" suffixField="" suffixValue="" entity=entity>
<#assign pu>
    <#compress>
	<#list entity.fieldListSortByOrdinalPosition as fld>
        <#if fld.create && fld.fieldType != "java.time.LocalDateTime">
		${fld.fieldName}${suffixField}%7C${operator}%7C
		{{${entity.instanceName}.${fld.fieldName}}}
		</#if>
		<#sep>%7C%7C
	</#list>
	</#compress>
	</#assign>
${pu?trim?replace('\n','')}</#macro>