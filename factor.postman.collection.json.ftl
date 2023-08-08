<#macro all_postman_collection entity>
   
    {
      "name": "${entity.tableName}",
      "item": [
      	{
          "name": "Login and Remember Token",
          "event": [
            {
              "listen": "test",
              "script": {
                "id": "Jul 16, 2023, 1:02:22 PM",
                "type": "text/javascript",
                "exec": [
                  "var is200Response = responseCode.code === 200;",
                  "",
                  "tests['Response code is 200 OK - Login and Remember Token'] = is200Response;",
                  "var responseJSON = JSON.parse(responseBody);",
                  "",
                  "tests['Response contains \"user\" property'] = responseJSON.hasOwnProperty('user');",
                  "",
                  "var user = responseJSON.user || {};",
                  "",
                  "tests['User has \"email\" property'] = user.hasOwnProperty('email');",
                  "tests['User has \"username\" property'] = user.hasOwnProperty('username');",
                  "tests['User has \"bio\" property'] = user.hasOwnProperty('bio');",
                  "tests['User has \"image\" property'] = user.hasOwnProperty('image');",
                  "tests['User has \"token\" property'] = user.hasOwnProperty('token');",
                  "",
                  "if(tests['User has \"token\" property']){",
                  "    pm.globals.set('token', user.token);",
                  "}",
                  "",
                  "tests['Global variable \"token\" has been set'] = pm.globals.get('token') === user.token;",
                  ""
                ]
              }
            }
          ],
          "request": {
            "method": "POST",
            "header": [
              {
                "key": "Content-Type",
                "value": "application/json"
              },
              {
                "key": "X-Requested-With",
                "value": "XMLHttpRequest"
              }
            ],
            "body": {
              "mode": "raw",
              "raw": "{\"user\":{\"email\":\"{{EMAIL}}\", \"password\":\"{{PASSWORD}}\"}}"
            },
            "url": {
              "raw": "{{APIURL}}/user/login",
              "host": [
                "{{APIURL}}"
              ],
              "path": [
                "user",
                "login"
              ]
            }
          },
          "response": []
        },
    <#assign myString = entity.tableName+'# #'+entity.tableName+'# '+','+getParentRecursive(entity)>
    <#assign myList = (myString?trim?remove_ending(','))?split(",")>
	<#list myList?reverse as item>
	    <#assign fkTableName = (item?split("#")[0]!''?string)>
		<#assign fkColumnName = (item?split("#")[1]!''?string)>
		<#assign pkTableName = (item?split("#")[2]!''?string)>
		<#assign pkColumnName = (item?split("#")[3]!''?string)>
  		<#list adv.entities as entity>
      		<#if pkTableName == entity.tableName>
        		{      		
          "name": "Create ${entity.tableName} for create ${fkTableName}",
          "event":[
          	{
      			"listen": "prerequest",
      			"script": {
        			"exec": [
          			"//console.log('Running beforeTest script...');",
          			"// Pre-request Script",
              		"const now = new Date();",
              		"const formattedDate = now.toISOString().replace(/[-:T.]/g, '').slice(0, -1);",
              		"pm.globals.set('UNIQUE', formattedDate);",
                    "console.log(formattedDate);"
        			],
        		"type": "text/javascript"
      			}
    		},      
    		{
          		"listen": "test",
                "script": {
	                "type": "text/javascript",
	                "exec": [
	                  "console.log('Request Payload:', pm.request.body.raw);",
	                  "var is201Response = responseCode.code === 201;",
                       "",
                      "tests['Response code is 201 OK - Create ${entity.tableName} for create ${fkTableName}'] = is201Response;",
                       "",
	                  "if (!(environment.isIntegrationTest) && is201Response) {",
	                  "var responseJSON = JSON.parse(responseBody);",
	                  "",
	                  "tests['Response contains \"${entity.instanceName}\" property'] = responseJSON.hasOwnProperty('${entity.instanceName}');",
	                  "",
	                  "var ${entity.instanceName} = responseJSON.${entity.instanceName} || {};",
	                  "",
	                  <#list entity.fieldListSortByOrdinalPosition as fld>
						<#if fld.create && !isImportedKeyWithEntity(entity,fld.columnName)!false>
					 "tests['${entity.instanceName} has \"${fld.fieldName}\" property'] = ${entity.instanceName}.hasOwnProperty('${fld.fieldName}');", 
				      		<#if fld.fieldType == "java.time.LocalDateTime">
				      "tests['${entity.instanceName} of \"${fld.fieldName}\" property is an ISO 8601 timestamp'] = /^\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}\\.\\d{6}$/.test(${entity.instanceName}.${fld.fieldName});",
				      	 	</#if>
				      	 	<#if fld.pkPosition != 0>
				      "",
				      "if(tests['${entity.instanceName} has \"${fld.fieldName}\" property']){",
                      "    pm.globals.set('${fkTableName}.${pkTableName}', ${entity.instanceName}.${fld.fieldName});",
                      "}",
                      "",
                      "tests['Global variable \"${fkTableName}.${pkTableName}\" has been set'] = pm.globals.get('${fkTableName}.${pkTableName}') === ${entity.instanceName}.${fld.fieldName};",
				      "",
				      "",
				      "if(tests['${entity.instanceName} has \"${fld.fieldName}\" property']){",
                      "    pm.globals.set('${entity.instanceName}.${fld.fieldName}', ${entity.instanceName}.${fld.fieldName});",
                      "}",
                      "",
                      "tests['Global variable \"${entity.instanceName}.${fld.fieldName}\" has been set'] = pm.globals.get('${entity.instanceName}.${fld.fieldName}') === ${entity.instanceName}.${fld.fieldName};",
				      "",
				      	 	</#if>	
						</#if>
						<#if fld.create  && isImportedKeyWithEntity(entity,fld.columnName)!false>
							<#list entity.importedKeys as i>
								<#if fld.columnName == i.fkColumnName>
								<#if fld.pkPosition == 0>
					 "tests['${entity.instanceName} has \"${fld.fieldName}\" property'] = ${entity.instanceName}.hasOwnProperty('${i.pkBaseName?uncap_first}${columnToField(i.fkColumnName)}Response');",
								</#if>
								<#if fld.pkPosition != 0>
					  "tests['${entity.instanceName} has \"${fld.fieldName}\" property'] = ${entity.instanceName}.hasOwnProperty('${i.pkBaseName?uncap_first}${columnToField(i.fkColumnName)}Response');",
				      "",
				      "if(tests['${entity.instanceName} has \"${fld.fieldName}\" property']){",
                      "    pm.globals.set('${entity.instanceName}.${fld.fieldName}', ${entity.instanceName}.${i.pkBaseName?uncap_first}${columnToField(i.fkColumnName)}Response.${columnToField(i.pkColumnName)?uncap_first});",
                      "}",
                      "",
                      "tests['Global variable \"${entity.instanceName}.${fld.fieldName}\" has been set'] = pm.globals.get('${entity.instanceName}.${fld.fieldName}') === ${entity.instanceName}.${i.pkBaseName?uncap_first}${columnToField(i.fkColumnName)}Response.${columnToField(i.pkColumnName)?uncap_first};",
				      "",
				      	 		</#if>
				      	 		</#if>
							</#list>
						</#if>
					   "",
					    
					</#list>
	                  "}",
	                  ""
                     ]
                  }
          	}
          ],
          "request":{
          	"method": "POST",
            "header": [
              {
                "key": "Content-Type",
                "value": "application/json"
              },
              {
                "key": "X-Requested-With",
                "value": "XMLHttpRequest"
              },
              {
                "key": "Authorization",
                "value": "Token {{token}}"
              }
            ],
            "body": {
              "mode": "raw",
              "raw": "{\"${entity.instanceName}\":{<#list entity.fieldListSortByOrdinalPosition as fld><#if fld.create && !isImportedKeyWithEntity(entity,fld.columnName)!false>\"${fld.fieldName}\":\"${createDummyDataPostman(fld)}\"<#sep>,</#if><#if fld.create  && isImportedKeyWithEntity(entity,fld.columnName)!false><#list entity.importedKeys as i><#if fld.columnName == i.fkColumnName>\"${fld.fieldName}\":\"{{${i.fkTableName}.${i.pkTableName}}}\"</#if></#list><#sep>,</#if></#list>}}"
            },
            "url": {
              "raw": "{{APIURL}}/${entity.instanceName}",
              "host": [
                "{{APIURL}}"
              ],
              "path": [
                "${entity.instanceName}"
              ]
            }
          },
          "response": []
      	}
      		</#if>
    	</#list>
    	<#sep>,
	</#list>,
        {      		
          "name": "Find ${entity.tableName} By Primary Key",
          "event":[      
    		{
          		"listen": "test",
                "script": {
	                "type": "text/javascript",
	                "exec": [
	                  "var is200Response = responseCode.code === 200;",
                       
                      "tests['Response code is 200 OK - Find ${entity.tableName} By Primary Key'] = is200Response;",
                       "",
	                  "if (!(environment.isIntegrationTest) && is200Response) {",
	                  "var responseJSON = JSON.parse(responseBody);",
	                  "",
	                  "tests['Response contains \"${entity.instanceName}\" property'] = responseJSON.hasOwnProperty('${entity.instanceName}');",
	                  "",
	                  "var ${entity.instanceName} = responseJSON.${entity.instanceName} || {};",
	                  "",
	                  <#list entity.fieldListSortByOrdinalPosition as fld>
						<#if fld.create && !isImportedKeyWithEntity(entity,fld.columnName)!false>
					 "tests['${entity.instanceName} has \"${fld.fieldName}\" property'] = ${entity.instanceName}.hasOwnProperty('${fld.fieldName}');", 
				      		<#if fld.fieldType == "java.time.LocalDateTime">
				     "tests['${entity.instanceName} of \"${fld.fieldName}\" property is an ISO 8601 timestamp'] = /^\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}\\.\\d{6}$/.test(${entity.instanceName}.${fld.fieldName});",
				      	 	</#if>
				      "",
				      "if(tests['${entity.instanceName} has \"${fld.fieldName}\" property']){",
                      "    pm.globals.set('${entity.instanceName}.${fld.fieldName}', ${entity.instanceName}.${fld.fieldName});",
                      "}",
                      "",
                      "tests['Global variable \"${entity.instanceName}.${fld.fieldName}\" has been set'] = pm.globals.get('${entity.instanceName}.${fld.fieldName}') === ${entity.instanceName}.${fld.fieldName};",
				      "",
						</#if>
						<#if fld.create  && isImportedKeyWithEntity(entity,fld.columnName)!false>
							<#list entity.importedKeys as i>
								<#if fld.columnName == i.fkColumnName>
					 "tests['${entity.instanceName} has \"${fld.fieldName}\" property'] = ${entity.instanceName}.hasOwnProperty('${i.pkBaseName?uncap_first}${columnToField(i.fkColumnName)}Response');",
					 "",
				      "if(tests['${entity.instanceName} has \"${fld.fieldName}\" property']){",
                      "    pm.globals.set('${entity.instanceName}.${fld.fieldName}', ${entity.instanceName}.${i.pkBaseName?uncap_first}${columnToField(i.fkColumnName)}Response.${columnToField(i.pkColumnName)?uncap_first});",
                      "}",
                      "",
                      "tests['Global variable \"${entity.instanceName}.${fld.fieldName}\" has been set'] = pm.globals.get('${entity.instanceName}.${fld.fieldName}') === ${entity.instanceName}.${i.pkBaseName?uncap_first}${columnToField(i.fkColumnName)}Response.${columnToField(i.pkColumnName)?uncap_first};",
				      "",			
								</#if>
							</#list>
						</#if>
					</#list>
	                  "}",
	                  ""
                     ]
                  }
          	}
          ],
          "request":{
          	"method": "GET",
            "header": [
              {
                "key": "Content-Type",
                "value": "application/json"
              },
              {
                "key": "X-Requested-With",
                "value": "XMLHttpRequest"
              },
              {
                "key": "Authorization",
                "value": "Token {{token}}"
              }
            ],
            "body": {
              "mode": "raw",
              "raw": ""
            },
            "url": {
              "raw": "{{APIURL}}/${entity.instanceName}/find",
              "host": [
                "{{APIURL}}"
              ],
              "path": [
                "${entity.instanceName}",
                "find"
              ],
              "query": [
               
                  <#list entity.primaryKeyFieldList as fld>
                  	<#if fld.create  && fld.pkPosition != 0>
                  	{
              			"key": "${fld.fieldName}",
              			"value": "{{${entity.instanceName}.${fld.fieldName}}}"
                    }
                    </#if>
                    <#sep>,
                  </#list>
                
              ]
            }
          },
          "response": []
      	},
      	{      		
          "name": "Find ${entity.tableName} By Filter With Offset0 And Limit10, All Fields, Equals, Or, Sort Desc",
          "event":[      
    		{
          		"listen": "test",
                "script": {
	                "type": "text/javascript",
	                "exec": [
	                  "var is200Response = responseCode.code === 200;",
                       
                      "tests['Response code is 200 OK - Find ${entity.tableName} By Filter With Offset0 And Limit10, All Fields, Equals, Or, Sort Desc'] = is200Response;",
                       "",
	                  "if (!(environment.isIntegrationTest) && is200Response) {",
	                  "var responseJSON = JSON.parse(responseBody);",
	                  "",
	                  "tests['Response contains \"${entity.instanceName}\" property'] = responseJSON.hasOwnProperty('${entity.instanceName}');",
	                  "",
	                  "tests['Response contains \"${entity.instanceName}Count\" property'] = responseJSON.hasOwnProperty('${entity.instanceName}Count');",
	                  "",
	                  "var ${entity.instanceName} = responseJSON.${entity.instanceName} || {};",
	                  "",
	                  "pm.test('Response should be an array', function () {",
    				  "    pm.expect(${entity.instanceName}).to.be.an('array');",
    				  "    pm.expect(${entity.instanceName}).to.have.length.above(0); ",
    				  "    pm.expect(${entity.instanceName}).to.have.lengthOf(${entity.instanceName}.length); ",
                      "});",
                      "",
	                  <#list entity.fieldListSortByOrdinalPosition as fld>
						<#if fld.create && !isImportedKeyWithEntity(entity,fld.columnName)!false>
					 "tests['${entity.instanceName}[0] has \"${fld.fieldName}\" property'] = ${entity.instanceName}[0].hasOwnProperty('${fld.fieldName}');", 
				      		<#if fld.fieldType == "java.time.LocalDateTime">
				     "tests['${entity.instanceName}[0] of \"${fld.fieldName}\" property is an ISO 8601 timestamp'] = /^\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}\\.\\d{6}$/.test(${entity.instanceName}[0].${fld.fieldName});",
				      	 	</#if>
				      "",
						</#if>
						<#if fld.create  && isImportedKeyWithEntity(entity,fld.columnName)!false>
							<#list entity.importedKeys as i>
								<#if fld.columnName == i.fkColumnName>
					 "tests['${entity.instanceName}[0] has \"${fld.fieldName}\" property'] = ${entity.instanceName}[0].hasOwnProperty('${i.pkBaseName?uncap_first}${columnToField(i.fkColumnName)}Response');",
					 "",			
								</#if>
							</#list>
						</#if>
					</#list>                  
	                  "}",
	                  ""
                     ]
                  }
          	}
          ],
          "request":{
          	"method": "GET",
            "header": [
              {
                "key": "Content-Type",
                "value": "application/json"
              },
              {
                "key": "X-Requested-With",
                "value": "XMLHttpRequest"
              },
              {
                "key": "Authorization",
                "value": "Token {{token}}"
              }
            ],
            "body": {
              "mode": "raw",
              "raw": ""
            },
            "url": {
              "raw": "{{APIURL}}/${entity.instanceName}",
              "host": [
                "{{APIURL}}"
              ],
              "path": [
                "${entity.instanceName}"
              ],
              "query": [
                    {
              			"key": "offset",
              			"value": "0"
                    },
                    {
              			"key": "limit",
              			"value": "10"
                    },
                    {
              			"key": "filter",
              			"value": "<@xxxpostmancoll_findbyfilter_allfields operator="eq" entity=entity/>"
                    },
                    {
              			"key": "conjunctions",
              			"value": "<#list entity.fieldListSortByOrdinalPosition as fld><#if fld.create>OR<#sep>,</#if></#list>"
                    },
                    {
              			"key": "sort",
              			"value": "<#list entity.fieldListSortByOrdinalPosition as fld><#if fld.create>-${fld.fieldName}<#sep>,</#if></#list>"
                    }
                    
                  
                
              ]
            }
          },
          "response": []
      	},
      	{      		
          "name": "Find ${entity.tableName} By Filter With Offset0 And Limit10, All Fields, Equals, Or, Sort Asc",
          "event":[      
    		{
          		"listen": "test",
                "script": {
	                "type": "text/javascript",
	                "exec": [
	                  "var is200Response = responseCode.code === 200;",
                       
                      "tests['Response code is 200 OK - Find ${entity.tableName} By Filter With Offset0 And Limit10, All Fields, Equals, Or, Sort Asc'] = is200Response;",
                       "",
	                  "if (!(environment.isIntegrationTest) && is200Response) {",
	                  "var responseJSON = JSON.parse(responseBody);",
	                  "",
	                  "tests['Response contains \"${entity.instanceName}\" property'] = responseJSON.hasOwnProperty('${entity.instanceName}');",
	                  "",
	                  "tests['Response contains \"${entity.instanceName}Count\" property'] = responseJSON.hasOwnProperty('${entity.instanceName}Count');",
	                  "",
	                  "var ${entity.instanceName} = responseJSON.${entity.instanceName} || {};",
	                  "",
	                  "pm.test('Response should be an array', function () {",
    				  "    pm.expect(${entity.instanceName}).to.be.an('array');",
    				  "    pm.expect(${entity.instanceName}).to.have.length.above(0); ",
    				  "    pm.expect(${entity.instanceName}).to.have.lengthOf(${entity.instanceName}.length); ",
                      "});",
                      "",
	                  <#list entity.fieldListSortByOrdinalPosition as fld>
						<#if fld.create && !isImportedKeyWithEntity(entity,fld.columnName)!false>
					 "tests['${entity.instanceName}[0] has \"${fld.fieldName}\" property'] = ${entity.instanceName}[0].hasOwnProperty('${fld.fieldName}');", 
				      		<#if fld.fieldType == "java.time.LocalDateTime">
				     "tests['${entity.instanceName}[0] of \"${fld.fieldName}\" property is an ISO 8601 timestamp'] = /^\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}\\.\\d{6}$/.test(${entity.instanceName}[0].${fld.fieldName});",
				      	 	</#if>
				      "",
						</#if>
						<#if fld.create  && isImportedKeyWithEntity(entity,fld.columnName)!false>
							<#list entity.importedKeys as i>
								<#if fld.columnName == i.fkColumnName>
					 "tests['${entity.instanceName}[0] has \"${fld.fieldName}\" property'] = ${entity.instanceName}[0].hasOwnProperty('${i.pkBaseName?uncap_first}${columnToField(i.fkColumnName)}Response');",
					 "",			
								</#if>
							</#list>
						</#if>
					</#list>                  
	                  "}",
	                  ""
                     ]
                  }
          	}
          ],
          "request":{
          	"method": "GET",
            "header": [
              {
                "key": "Content-Type",
                "value": "application/json"
              },
              {
                "key": "X-Requested-With",
                "value": "XMLHttpRequest"
              },
              {
                "key": "Authorization",
                "value": "Token {{token}}"
              }
            ],
            "body": {
              "mode": "raw",
              "raw": ""
            },
            "url": {
              "raw": "{{APIURL}}/${entity.instanceName}",
              "host": [
                "{{APIURL}}"
              ],
              "path": [
                "${entity.instanceName}"
              ],
              "query": [
                    {
              			"key": "offset",
              			"value": "0"
                    },
                    {
              			"key": "limit",
              			"value": "10"
                    },
                    {
              			"key": "filter",
              			"value": "<@xxxpostmancoll_findbyfilter_allfields operator="eq" entity=entity/>"
                    },
                    {
              			"key": "conjunctions",
              			"value": "<#list entity.fieldListSortByOrdinalPosition as fld><#if fld.create>OR<#sep>,</#if></#list>"
                    },
                    {
              			"key": "sort",
              			"value": "<#list entity.fieldListSortByOrdinalPosition as fld><#if fld.create>${fld.fieldName}<#sep>,</#if></#list>"
                    }
                    
                  
                
              ]
            }
          },
          "response": []
      	},
      	{      		
          "name": "Find ${entity.tableName} By Filter With Offset0 And Limit10, All Fields, Equals, Or Conjunction",
          "event":[      
    		{
          		"listen": "test",
                "script": {
	                "type": "text/javascript",
	                "exec": [
	                  "var is200Response = responseCode.code === 200;",
                       
                      "tests['Response code is 200 OK - Find ${entity.tableName} By Filter With Offset0 And Limit10, All Fields, Equals, Or Conjunction'] = is200Response;",
                       "",
	                  "if (!(environment.isIntegrationTest) && is200Response) {",
	                  "var responseJSON = JSON.parse(responseBody);",
	                  "",
	                  "tests['Response contains \"${entity.instanceName}\" property'] = responseJSON.hasOwnProperty('${entity.instanceName}');",
	                  "",
	                  "tests['Response contains \"${entity.instanceName}Count\" property'] = responseJSON.hasOwnProperty('${entity.instanceName}Count');",
	                  "",
	                  "var ${entity.instanceName} = responseJSON.${entity.instanceName} || {};",
	                  "",
	                  "pm.test('Response should be an array', function () {",
    				  "    pm.expect(${entity.instanceName}).to.be.an('array');",
    				  "    pm.expect(${entity.instanceName}).to.have.length.above(0); ",
    				  "    pm.expect(${entity.instanceName}).to.have.lengthOf(${entity.instanceName}.length); ",
                      "});",
                      "",
	                  <#list entity.fieldListSortByOrdinalPosition as fld>
						<#if fld.create && !isImportedKeyWithEntity(entity,fld.columnName)!false>
					 "tests['${entity.instanceName}[0] has \"${fld.fieldName}\" property'] = ${entity.instanceName}[0].hasOwnProperty('${fld.fieldName}');", 
				      		<#if fld.fieldType == "java.time.LocalDateTime">
				     "tests['${entity.instanceName}[0] of \"${fld.fieldName}\" property is an ISO 8601 timestamp'] = /^\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}\\.\\d{6}$/.test(${entity.instanceName}[0].${fld.fieldName});",
				      	 	</#if>
				      "",
						</#if>
						<#if fld.create  && isImportedKeyWithEntity(entity,fld.columnName)!false>
							<#list entity.importedKeys as i>
								<#if fld.columnName == i.fkColumnName>
					 "tests['${entity.instanceName}[0] has \"${fld.fieldName}\" property'] = ${entity.instanceName}[0].hasOwnProperty('${i.pkBaseName?uncap_first}${columnToField(i.fkColumnName)}Response');",
					 "",			
								</#if>
							</#list>
						</#if>
					</#list>                  
	                  "}",
	                  ""
                     ]
                  }
          	}
          ],
          "request":{
          	"method": "GET",
            "header": [
              {
                "key": "Content-Type",
                "value": "application/json"
              },
              {
                "key": "X-Requested-With",
                "value": "XMLHttpRequest"
              },
              {
                "key": "Authorization",
                "value": "Token {{token}}"
              }
            ],
            "body": {
              "mode": "raw",
              "raw": ""
            },
            "url": {
              "raw": "{{APIURL}}/${entity.instanceName}",
              "host": [
                "{{APIURL}}"
              ],
              "path": [
                "${entity.instanceName}"
              ],
              "query": [
                    {
              			"key": "offset",
              			"value": "0"
                    },
                    {
              			"key": "limit",
              			"value": "10"
                    },
                    {
              			"key": "filter",
              			"value": "<@xxxpostmancoll_findbyfilter_allfields operator="eq" entity=entity/>"
                    },
                    {
              			"key": "conjunctions",
              			"value": "<#list entity.fieldListSortByOrdinalPosition as fld><#if fld.create>OR<#sep>,</#if></#list>"
                    }
                
              ]
            }
          },
          "response": []
      	},
      	{      		
          "name": "Find ${entity.tableName} By Filter With Offset0 And Limit10, All Fields, Equals, And Conjunction",
          "event":[      
    		{
          		"listen": "test",
                "script": {
	                "type": "text/javascript",
	                "exec": [
	                  "var is200Response = responseCode.code === 200;",
                       
                      "tests['Response code is 200 OK - Find ${entity.tableName} By Filter With Offset0 And Limit10, All Fields, Equals, And Conjunction'] = is200Response;",
                       "",
	                  "if (!(environment.isIntegrationTest) && is200Response) {",
	                  "var responseJSON = JSON.parse(responseBody);",
	                  "",
	                  "tests['Response contains \"${entity.instanceName}\" property'] = responseJSON.hasOwnProperty('${entity.instanceName}');",
	                  "",
	                  "tests['Response contains \"${entity.instanceName}Count\" property'] = responseJSON.hasOwnProperty('${entity.instanceName}Count');",
	                  "",
	                  "var ${entity.instanceName} = responseJSON.${entity.instanceName} || {};",
	                  "",
	                  "pm.test('Response should be an array', function () {",
    				  "    pm.expect(${entity.instanceName}).to.be.an('array');",
    				  "    pm.expect(${entity.instanceName}).to.have.length.above(0); ",
    				  "    pm.expect(${entity.instanceName}).to.have.lengthOf(${entity.instanceName}.length); ",
                      "});",
                      "",
	                  <#list entity.fieldListSortByOrdinalPosition as fld>
						<#if fld.create && !isImportedKeyWithEntity(entity,fld.columnName)!false>
					 "tests['${entity.instanceName}[0] has \"${fld.fieldName}\" property'] = ${entity.instanceName}[0].hasOwnProperty('${fld.fieldName}');", 
				      		<#if fld.fieldType == "java.time.LocalDateTime">
				     "tests['${entity.instanceName}[0] of \"${fld.fieldName}\" property is an ISO 8601 timestamp'] = /^\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}\\.\\d{6}$/.test(${entity.instanceName}[0].${fld.fieldName});",
				      	 	</#if>
				      "",
						</#if>
						<#if fld.create  && isImportedKeyWithEntity(entity,fld.columnName)!false>
							<#list entity.importedKeys as i>
								<#if fld.columnName == i.fkColumnName>
					 "tests['${entity.instanceName}[0] has \"${fld.fieldName}\" property'] = ${entity.instanceName}[0].hasOwnProperty('${i.pkBaseName?uncap_first}${columnToField(i.fkColumnName)}Response');",
					 "",			
								</#if>
							</#list>
						</#if>
					</#list>                  
	                  "}",
	                  ""
                     ]
                  }
          	}
          ],
          "request":{
          	"method": "GET",
            "header": [
              {
                "key": "Content-Type",
                "value": "application/json"
              },
              {
                "key": "X-Requested-With",
                "value": "XMLHttpRequest"
              },
              {
                "key": "Authorization",
                "value": "Token {{token}}"
              }
            ],
            "body": {
              "mode": "raw",
              "raw": ""
            },
            "url": {
              "raw": "{{APIURL}}/${entity.instanceName}",
              "host": [
                "{{APIURL}}"
              ],
              "path": [
                "${entity.instanceName}"
              ],
              "query": [
                    {
              			"key": "offset",
              			"value": "0"
                    },
                    {
              			"key": "limit",
              			"value": "10"
                    },
                    {
              			"key": "filter",
              			"value": "<@xxxpostmancoll_findbyfilter_allfields operator="eq" entity=entity/>"
                    },
                    {
              			"key": "conjunctions",
              			"value": "<#list entity.fieldListSortByOrdinalPosition as fld><#if fld.create>AND<#sep>,</#if></#list>"
                    }
                
              ]
            }
          },
          "response": []
      	},
      	{      		
          "name": "Find ${entity.tableName} By Filter With Offset0 And Limit10, All Fields, Not Equals, Or Conjunction",
          "event":[      
    		{
          		"listen": "test",
                "script": {
	                "type": "text/javascript",
	                "exec": [
	                  "var is200Response = responseCode.code === 200;",
                       
                      "tests['Response code is 200 OK - Find ${entity.tableName} By Filter With Offset0 And Limit10, All Fields, Not Equals, Or Conjunction'] = is200Response;",
                       "",
	                  "if (!(environment.isIntegrationTest) && is200Response) {",
	                  "var responseJSON = JSON.parse(responseBody);",
	                  "",
	                  "tests['Response contains \"${entity.instanceName}\" property'] = responseJSON.hasOwnProperty('${entity.instanceName}');",
	                  "",
	                  "tests['Response contains \"${entity.instanceName}Count\" property'] = responseJSON.hasOwnProperty('${entity.instanceName}Count');",
	                  "",
	                  "var ${entity.instanceName} = responseJSON.${entity.instanceName} || {};",
	                  "",
	                  "if (typeof ${entity.instanceName} === 'object' && Array.isArray(${entity.instanceName}) && ${entity.instanceName}.length > 0) {",
	                  "pm.test('Response should be an array', function () {",
    				  "    pm.expect(${entity.instanceName}).to.be.an('array');",
    				  "    pm.expect(${entity.instanceName}).to.have.length.above(0); ",
    				  "    pm.expect(${entity.instanceName}).to.have.lengthOf(${entity.instanceName}.length); ",
                      "});",
                      "",
	                  <#list entity.fieldListSortByOrdinalPosition as fld>
						<#if fld.create && !isImportedKeyWithEntity(entity,fld.columnName)!false>
					 "tests['${entity.instanceName}[0] has \"${fld.fieldName}\" property'] = ${entity.instanceName}[0].hasOwnProperty('${fld.fieldName}');", 
				      		<#if fld.fieldType == "java.time.LocalDateTime">
				     "tests['${entity.instanceName}[0] of \"${fld.fieldName}\" property is an ISO 8601 timestamp'] = /^\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}\\.\\d{6}$/.test(${entity.instanceName}[0].${fld.fieldName});",
				      	 	</#if>
				      "",
						</#if>
						<#if fld.create  && isImportedKeyWithEntity(entity,fld.columnName)!false>
							<#list entity.importedKeys as i>
								<#if fld.columnName == i.fkColumnName>
					 "tests['${entity.instanceName}[0] has \"${fld.fieldName}\" property'] = ${entity.instanceName}[0].hasOwnProperty('${i.pkBaseName?uncap_first}${columnToField(i.fkColumnName)}Response');",
					 "",			
								</#if>
							</#list>
						</#if>
					</#list>                  
	                  "}",
	                  "}",
	                  ""
                     ]
                  }
          	}
          ],
          "request":{
          	"method": "GET",
            "header": [
              {
                "key": "Content-Type",
                "value": "application/json"
              },
              {
                "key": "X-Requested-With",
                "value": "XMLHttpRequest"
              },
              {
                "key": "Authorization",
                "value": "Token {{token}}"
              }
            ],
            "body": {
              "mode": "raw",
              "raw": ""
            },
            "url": {
              "raw": "{{APIURL}}/${entity.instanceName}",
              "host": [
                "{{APIURL}}"
              ],
              "path": [
                "${entity.instanceName}"
              ],
              "query": [
                    {
              			"key": "offset",
              			"value": "0"
                    },
                    {
              			"key": "limit",
              			"value": "10"
                    },
                    {
              			"key": "filter",
              			"value": "<@xxxpostmancoll_findbyfilter_allfields operator="neq" entity=entity/>"
                    },
                    {
              			"key": "conjunctions",
              			"value": "<#list entity.fieldListSortByOrdinalPosition as fld><#if fld.create>OR<#sep>,</#if></#list>"
                    }
                
              ]
            }
          },
          "response": []
      	},
      	{      		
          "name": "Find ${entity.tableName} By Filter With Offset0 And Limit10, All Fields, Like, And Conjunction",
          "event":[      
    		{
          		"listen": "test",
                "script": {
	                "type": "text/javascript",
	                "exec": [
	                  "var is200Response = responseCode.code === 200;",
                       
                      "tests['Response code is 200 OK - Find ${entity.tableName} By Filter With Offset0 And Limit10, All Fields, Like, And Conjunction'] = is200Response;",
                       "",
	                  "if (!(environment.isIntegrationTest) && is200Response) {",
	                  "var responseJSON = JSON.parse(responseBody);",
	                  "",
	                  "tests['Response contains \"${entity.instanceName}\" property'] = responseJSON.hasOwnProperty('${entity.instanceName}');",
	                  "",
	                  "tests['Response contains \"${entity.instanceName}Count\" property'] = responseJSON.hasOwnProperty('${entity.instanceName}Count');",
	                  "",
	                  "var ${entity.instanceName} = responseJSON.${entity.instanceName} || {};",
	                  "",
	                  "pm.test('Response should be an array', function () {",
    				  "    pm.expect(${entity.instanceName}).to.be.an('array');",
    				  "    pm.expect(${entity.instanceName}).to.have.length.above(0); ",
    				  "    pm.expect(${entity.instanceName}).to.have.lengthOf(${entity.instanceName}.length); ",
                      "});",
                      "",
	                  <#list entity.fieldListSortByOrdinalPosition as fld>
						<#if fld.create && !isImportedKeyWithEntity(entity,fld.columnName)!false>
					 "tests['${entity.instanceName}[0] has \"${fld.fieldName}\" property'] = ${entity.instanceName}[0].hasOwnProperty('${fld.fieldName}');", 
				      		<#if fld.fieldType == "java.time.LocalDateTime">
				     "tests['${entity.instanceName}[0] of \"${fld.fieldName}\" property is an ISO 8601 timestamp'] = /^\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}\\.\\d{6}$/.test(${entity.instanceName}[0].${fld.fieldName});",
				      	 	</#if>
				      "",
						</#if>
						<#if fld.create  && isImportedKeyWithEntity(entity,fld.columnName)!false>
							<#list entity.importedKeys as i>
								<#if fld.columnName == i.fkColumnName>
					 "tests['${entity.instanceName}[0] has \"${fld.fieldName}\" property'] = ${entity.instanceName}[0].hasOwnProperty('${i.pkBaseName?uncap_first}${columnToField(i.fkColumnName)}Response');",
					 "",			
								</#if>
							</#list>
						</#if>
					</#list>                  
	                  "}",
	                  ""
                     ]
                  }
          	}
          ],
          "request":{
          	"method": "GET",
            "header": [
              {
                "key": "Content-Type",
                "value": "application/json"
              },
              {
                "key": "X-Requested-With",
                "value": "XMLHttpRequest"
              },
              {
                "key": "Authorization",
                "value": "Token {{token}}"
              }
            ],
            "body": {
              "mode": "raw",
              "raw": ""
            },
            "url": {
              "raw": "{{APIURL}}/${entity.instanceName}",
              "host": [
                "{{APIURL}}"
              ],
              "path": [
                "${entity.instanceName}"
              ],
              "query": [
                    {
              			"key": "offset",
              			"value": "0"
                    },
                    {
              			"key": "limit",
              			"value": "10"
                    },
                    {
              			"key": "filter",
              			"value": "<@xxxpostmancoll_findbyfilter_allfields_like_operator operator="like" entity=entity/>"
                    },
                    {
              			"key": "conjunctions",
              			"value": "<#list entity.fieldListSortByOrdinalPosition as fld><#if fld.create && fld.fieldType != "java.time.LocalDateTime">AND<#sep>,</#if></#list>"
                    }
                
              ]
            }
          },
          "response": []
      	},
      	{      		
          "name": "Find ${entity.tableName} By Filter With Offset0 And Limit10, All Fields, Not Like, Or Conjunction",
          "event":[      
    		{
          		"listen": "test",
                "script": {
	                "type": "text/javascript",
	                "exec": [
	                  "var is200Response = responseCode.code === 200;",
                       
                      "tests['Response code is 200 OK - Find ${entity.tableName} By Filter With Offset0 And Limit10, All Fields, Not Like, Or Conjunction'] = is200Response;",
                       "",
	                  "if (!(environment.isIntegrationTest) && is200Response) {",
	                  "var responseJSON = JSON.parse(responseBody);",
	                  "",
	                  "tests['Response contains \"${entity.instanceName}\" property'] = responseJSON.hasOwnProperty('${entity.instanceName}');",
	                  "",
	                  "tests['Response contains \"${entity.instanceName}Count\" property'] = responseJSON.hasOwnProperty('${entity.instanceName}Count');",
	                  "",
	                  "var ${entity.instanceName} = responseJSON.${entity.instanceName} || {};",
	                  "",
	                  "if (typeof ${entity.instanceName} === 'object' && Array.isArray(${entity.instanceName}) && ${entity.instanceName}.length > 0) {",
	                  "pm.test('Response should be an array', function () {",
    				  "    pm.expect(${entity.instanceName}).to.be.an('array');",
    				  "    pm.expect(${entity.instanceName}).to.have.length.above(0); ",
    				  "    pm.expect(${entity.instanceName}).to.have.lengthOf(${entity.instanceName}.length); ",
                      "});",
                      "",
	                  <#list entity.fieldListSortByOrdinalPosition as fld>
						<#if fld.create && !isImportedKeyWithEntity(entity,fld.columnName)!false>
					 "tests['${entity.instanceName}[0] has \"${fld.fieldName}\" property'] = ${entity.instanceName}[0].hasOwnProperty('${fld.fieldName}');", 
				      		<#if fld.fieldType == "java.time.LocalDateTime">
				     "tests['${entity.instanceName}[0] of \"${fld.fieldName}\" property is an ISO 8601 timestamp'] = /^\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}\\.\\d{6}$/.test(${entity.instanceName}[0].${fld.fieldName});",
				      	 	</#if>
				      "",
						</#if>
						<#if fld.create  && isImportedKeyWithEntity(entity,fld.columnName)!false>
							<#list entity.importedKeys as i>
								<#if fld.columnName == i.fkColumnName>
					 "tests['${entity.instanceName}[0] has \"${fld.fieldName}\" property'] = ${entity.instanceName}[0].hasOwnProperty('${i.pkBaseName?uncap_first}${columnToField(i.fkColumnName)}Response');",
					 "",			
								</#if>
							</#list>
						</#if>
					</#list>                  
	                  "}",
	                  "}",
	                  ""
                     ]
                  }
          	}
          ],
          "request":{
          	"method": "GET",
            "header": [
              {
                "key": "Content-Type",
                "value": "application/json"
              },
              {
                "key": "X-Requested-With",
                "value": "XMLHttpRequest"
              },
              {
                "key": "Authorization",
                "value": "Token {{token}}"
              }
            ],
            "body": {
              "mode": "raw",
              "raw": ""
            },
            "url": {
              "raw": "{{APIURL}}/${entity.instanceName}",
              "host": [
                "{{APIURL}}"
              ],
              "path": [
                "${entity.instanceName}"
              ],
              "query": [
                    {
              			"key": "offset",
              			"value": "0"
                    },
                    {
              			"key": "limit",
              			"value": "10"
                    },
                    {
              			"key": "filter",
              			"value": "<@xxxpostmancoll_findbyfilter_allfields_like_operator operator="nlike" entity=entity/>"
                    },
                    {
              			"key": "conjunctions",
              			"value": "<#list entity.fieldListSortByOrdinalPosition as fld><#if fld.create && fld.fieldType != "java.time.LocalDateTime">OR<#sep>,</#if></#list>"
                    }
                
              ]
            }
          },
          "response": []
      	},
      	{      		
          "name": "Update ${entity.tableName}",
          "event":[
          	{
          		"listen": "test",
                "script": {
	                "type": "text/javascript",
	                "exec": [
	                  "var is200Response = responseCode.code === 200;",
                       "",
                      "tests['Response code is 200 OK - Update ${entity.tableName}'] = is200Response;",
                       "",
	                  "if (!(environment.isIntegrationTest) && is200Response) {",
	                  "var responseJSON = JSON.parse(responseBody);",
	                  "",
	                  "tests['Response contains \"${entity.instanceName}\" property'] = responseJSON.hasOwnProperty('${entity.instanceName}');",
	                  "",
	                  "var ${entity.instanceName} = responseJSON.${entity.instanceName} || {};",
	                  "",
	               <#list entity.fieldListSortByOrdinalPosition as fld>
						<#if fld.create && !isImportedKeyWithEntity(entity,fld.columnName)!false>
					 "tests['${entity.instanceName} has \"${fld.fieldName}\" property'] = ${entity.instanceName}.hasOwnProperty('${fld.fieldName}');", 
				      		<#if fld.fieldType == "java.time.LocalDateTime">
				     "tests['${entity.instanceName} of \"${fld.fieldName}\" property is an ISO 8601 timestamp'] = /^\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}\\.\\d{6}$/.test(${entity.instanceName}.${fld.fieldName});",
				      	 	</#if>
				      "",
						</#if>
						<#if fld.create  && isImportedKeyWithEntity(entity,fld.columnName)!false>
							<#list entity.importedKeys as i>
								<#if fld.columnName == i.fkColumnName>
					 "tests['${entity.instanceName} has \"${fld.fieldName}\" property'] = ${entity.instanceName}.hasOwnProperty('${i.pkBaseName?uncap_first}${columnToField(i.fkColumnName)}Response');",
					 "",			
								</#if>
							</#list>
						</#if>
					</#list>
	                  "}",
	                  ""
                     ]
                  }
          	}
          ],
          "request":{
          	"method": "PUT",
            "header": [
              {
                "key": "Content-Type",
                "value": "application/json"
              },
              {
                "key": "X-Requested-With",
                "value": "XMLHttpRequest"
              },
              {
                "key": "Authorization",
                "value": "Token {{token}}"
              }
            ],
            "body": {
              "mode": "raw",
              "raw": "{\"${entity.instanceName}\":{<#list entity.fieldListSortByOrdinalPosition as fld><#if fld.create && !isImportedKeyWithEntity(entity,fld.columnName)!false>\"${fld.fieldName}\":\"${createDummyDataPostman(fld)}\"<#sep>,</#if><#if fld.create  && isImportedKeyWithEntity(entity,fld.columnName)!false><#list entity.importedKeys as i><#if fld.columnName == i.fkColumnName>\"${fld.fieldName}\":\"{{${i.fkTableName}.${i.pkTableName}}}\"</#if></#list><#sep>,</#if></#list>}}"
            },
            "url": {
              "raw": "{{APIURL}}/${entity.instanceName}",
              "host": [
                "{{APIURL}}"
              ],
              "path": [
                "${entity.instanceName}"
              ]
            }
          },
          "response": []
      	},
      	{      		
          "name": "Delete ${entity.tableName}",
          "event":[
          	{
          		"listen": "test",
                "script": {
	                "type": "text/javascript",
	                "exec": [
	                  "var is200Response = responseCode.code === 200;",
                       "",
                      "tests['Response code is 200 OK - Delete ${entity.tableName}'] = is200Response;",
                       "",
	                  ""
                     ]
                  }
          	}
          ],
          "request":{
          	"method": "DELETE",
            "header": [
              {
                "key": "Content-Type",
                "value": "application/json"
              },
              {
                "key": "X-Requested-With",
                "value": "XMLHttpRequest"
              },
              {
                "key": "Authorization",
                "value": "Token {{token}}"
              }
            ],
            "body": {
              "mode": "raw",
              "raw": ""
              },
            "url": {
              "raw": "{{APIURL}}/${entity.instanceName}",
              "host": [
                "{{APIURL}}"
              ],
              "path": [
                "${entity.instanceName}"
              ],
              "query": [
               
                  <#list entity.primaryKeyFieldList as fld>
                  	<#if fld.create  && fld.pkPosition != 0>
                  	{
              			"key": "${fld.fieldName}",
              			"value": "{{${entity.instanceName}.${fld.fieldName}}}"
                    }
                    </#if>
                    <#sep>,
                  </#list>
                
              ]
            }
          },
          "response": []
      	}
      ]
    }
 
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
	    ${fld.fieldName}${suffixField}%7C${operator}%7C
		<#if fld.create>
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