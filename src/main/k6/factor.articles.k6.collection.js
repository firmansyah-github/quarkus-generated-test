// created by the factor : Feb 23, 2024, 6:45:22 AM  

import "./libs/shim/core.js";
import "./libs/shim/urijs.js";
import { check } from 'k6';
import { uuidv4 } from "./libs/jslib.k6.io_k6-utils_1.4.0_index.js";
//import { uuidv4 } from 'https://jslib.k6.io/k6-utils/1.4.0/index.js';
import { htmlReport } from "./libs/htmlReport.js";
//import { htmlReport } from "https://raw.githubusercontent.com/benc-uk/k6-reporter/2.2.0/dist/bundle.js";
import { textSummary } from "./libs/jslib.k6.io_k6-summary_0.0.1_index.js";
//import { textSummary } from "https://jslib.k6.io/k6-summary/0.0.1/index.js";
import { sleep } from 'k6';
import { group } from "k6";

export let options = {
  maxRedirects: 4
};

const Request = Symbol.for("request");
postman[Symbol.for("initial")]({
  options
});

const delay = 0.25;
const timeout = '2000s';
const URL = __ENV.URL;

export default function() {
  group("Registration and Authentication", function() {
    postman[Request]({
      name: "Registration",
      id: "2024-02-23-06-45-22-022",
      method: "POST",
      options: { timeout: timeout },
      address: "{{APIURL}}/users",
      data:
        '{"user":{"email":"{{EMAIL}}", "password":"{{PASSWORD}}", "username":"{{USERNAME}}"}}',
      headers: {
        "Content-Type": "application/json",
        "X-Requested-With": "XMLHttpRequest"
      },
      pre() {
        //console.log('Running beforeTest script...');
        // Pre-request Script
        const now = new Date();
        const formattedDate = now
          .toISOString()
          .replace(/[-:T.]/g, "")
          .slice(0, -1);
        const uniqueID = uuidv4();
        const LocalDateTimeNow = now.toISOString().slice(0, -1);
        //console.log(LocalDateTimeNow);
        pm.globals.set("APIURL", URL);
        pm.globals.set("UNIQUE", uniqueID);
        pm.globals.set("LocalDateTimeNow", LocalDateTimeNow);
        pm.globals.set("USERNAME", "u" + uniqueID);
        pm.globals.set("EMAIL", "u" + uniqueID + "@mail.com");
        pm.globals.set("PASSWORD", "p" + uniqueID);
        //console.log(formattedDate);
        //console.log(uniqueID);
      },
      post(response) {
        //console.log('Request URL: '+ response.request.url);
        //console.log('Request Method: '+ response.request.method);
        //console.log('Request Body: '+ response.request.body);

        //console.log('Response Status: '+ response.status);
        //console.log('Response Body: '+ response.body);
        var is201Response = responseCode.code === 201;

        tests["Response code is 201 OK - Registration"] = is201Response;
        
        if (!environment.isIntegrationTest && is201Response) {
          var responseJSON = JSON.parse(responseBody);

          tests[
            'Response contains "user" property'
          ] = responseJSON.hasOwnProperty("user");

          var user = responseJSON.user || {};

          tests['User has "email" property'] = user.hasOwnProperty("email");
          tests['User has "username" property'] = user.hasOwnProperty(
            "username"
          );
          tests['User has "bio" property'] = user.hasOwnProperty("bio");
          tests['User has "image" property'] = user.hasOwnProperty("image");
          tests['User has "token" property'] = user.hasOwnProperty("token");
        }
      }
    });sleep(delay);

    postman[Request]({
      name: "Login and Remember Token",
      id: "2024-02-23-06-45-22-022",
      method: "POST",
      options: { timeout: timeout },
      address: "{{APIURL}}/users/login",
      data: '{"user":{"email":"{{EMAIL}}", "password":"{{PASSWORD}}"}}',
      headers: {
        "Content-Type": "application/json",
        "X-Requested-With": "XMLHttpRequest"
      },
      post(response) {
        //console.log('Request URL: '+ response.request.url);
        //console.log('Request Method: '+ response.request.method);
        //console.log('Request Body: '+ response.request.body);

        //console.log('Response Status: '+ response.status);
        //console.log('Response Body: '+ response.body);
        var is200Response = responseCode.code === 200;

        tests["Response code is 200 OK - Login and Remember Token"] = is200Response;
        if (!environment.isIntegrationTest && is200Response) {
	        var responseJSON = JSON.parse(responseBody);
	
	        tests[
	          'Response contains "user" property'
	        ] = responseJSON.hasOwnProperty("user");
	
	        var user = responseJSON.user || {};
	
	        tests['User has "email" property'] = user.hasOwnProperty("email");
	        tests['User has "username" property'] = user.hasOwnProperty("username");
	        tests['User has "bio" property'] = user.hasOwnProperty("bio");
	        tests['User has "image" property'] = user.hasOwnProperty("image");
	        tests['User has "token" property'] = user.hasOwnProperty("token");
	
	        if (tests['User has "token" property']) {
	          pm.globals.set("token", user.token);
	        }
	
	        tests['Global variable "token" has been set'] =
	          pm.globals.get("token") === user.token;
	     }
      }
    });sleep(delay);


	postman[Request]({
      name: "Create users for create articles",
      id: "2024-02-23-06-45-22-000578",
      method: "POST",
      options: { timeout: timeout },
      address: "{{APIURL}}/firmansyah/users",
      data:
        '{"users":{"bio":"bio","email":"email","id":"{{UNIQUE}}","image":"image","password":"password","username":"username"}}',
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

        tests["Response code is 201 OK - Create users for create articles"] = is201Response;

        if (!environment.isIntegrationTest && is201Response) {
          var responseJSON = JSON.parse(responseBody);

          tests[
            'Response contains "users" property'
          ] = responseJSON.hasOwnProperty("users");

          var users = responseJSON.users || {};
		  tests['users has "bio" property'] = users.hasOwnProperty("bio"); 
				
		  tests['users has "email" property'] = users.hasOwnProperty("email"); 
				
		  tests['users has "id" property'] = users.hasOwnProperty("id"); 
				
	      if(tests['users has "id" property']){
          		pm.globals.set('articles.users', users.id);
          }
           
          tests['Global variable "articles.users" has been set'] = pm.globals.get('articles.users') === users.id;
				      
		  if(tests['users has "id" property']){
                pm.globals.set('users.id', users.id);
          }
          
          tests['Global variable "users.id" has been set'] = pm.globals.get('users.id') === users.id;
		  tests['users has "image" property'] = users.hasOwnProperty("image"); 
				
		  tests['users has "password" property'] = users.hasOwnProperty("password"); 
				
		  tests['users has "username" property'] = users.hasOwnProperty("username"); 
				
        }
       }
      });sleep(delay);
        	

	postman[Request]({
      name: "Create articles for create articles",
      id: "2024-02-23-06-45-22-000578",
      method: "POST",
      options: { timeout: timeout },
      address: "{{APIURL}}/firmansyah/articles",
      data:
        '{"articles":{"createdat":"{{LocalDateTimeNow}}","updatedat":"{{LocalDateTimeNow}}","authorId":"{{articles.users}}","body":"body","description":"description","id":"{{UNIQUE}}","slug":"slug","title":"title"}}',
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

        tests["Response code is 201 OK - Create articles for create articles"] = is201Response;

        if (!environment.isIntegrationTest && is201Response) {
          var responseJSON = JSON.parse(responseBody);

          tests[
            'Response contains "articles" property'
          ] = responseJSON.hasOwnProperty("articles");

          var articles = responseJSON.articles || {};
		  tests['articles has "createdat" property'] = articles.hasOwnProperty("createdat"); 
		  tests['articles of "createdat" property is an ISO 8601 timestamp'] = /^\d{4}-\d{2}-\d{2}T\d{2}:\d{2}:\d{2}\.\d{6}$/.test(articles.createdat);
				
		  tests['articles has "updatedat" property'] = articles.hasOwnProperty("updatedat"); 
		  tests['articles of "updatedat" property is an ISO 8601 timestamp'] = /^\d{4}-\d{2}-\d{2}T\d{2}:\d{2}:\d{2}\.\d{6}$/.test(articles.updatedat);
				
		  tests['articles has "authorId" property'] = articles.hasOwnProperty('usersAuthorIdResponse');
		  tests['articles has "body" property'] = articles.hasOwnProperty("body"); 
				
		  tests['articles has "description" property'] = articles.hasOwnProperty("description"); 
				
		  tests['articles has "id" property'] = articles.hasOwnProperty("id"); 
				
	      if(tests['articles has "id" property']){
          		pm.globals.set('articles.articles', articles.id);
          }
           
          tests['Global variable "articles.articles" has been set'] = pm.globals.get('articles.articles') === articles.id;
				      
		  if(tests['articles has "id" property']){
                pm.globals.set('articles.id', articles.id);
          }
          
          tests['Global variable "articles.id" has been set'] = pm.globals.get('articles.id') === articles.id;
		  tests['articles has "slug" property'] = articles.hasOwnProperty("slug"); 
				
		  tests['articles has "title" property'] = articles.hasOwnProperty("title"); 
				
        }
       }
      });sleep(delay);
        	
	
	postman[Request]({
      name: "Find articles By Primary Key",
      id: "2024-02-23-06-45-22-000578",
      method: "GET",
      options: { timeout: timeout },
      address: "{{APIURL}}/firmansyah/articles/find?id={{articles.id}}",
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
                       
        tests['Response code is 200 OK - Find articles By Primary Key'] = is200Response;
        
	    if (!(environment.isIntegrationTest) && is200Response) {
	    	var responseJSON = JSON.parse(responseBody);
	                 
	        tests['Response contains "articles" property'] = responseJSON.hasOwnProperty('articles');
	        
	        var articles = responseJSON.articles || {};
	       
			tests['articles has "createdat" property'] = articles.hasOwnProperty('createdat'); 
		    tests['articles of "createdat" property is an ISO 8601 timestamp'] = /^\d{4}-\d{2}-\d{2}T\d{2}:\d{2}:\d{2}\.\d{6}$/.test(articles.createdat);
		
		    if(tests['articles has "createdat" property']){
            	pm.globals.set('articles.createdat', articles.createdat);
            }
            
            tests['Global variable "articles.createdat" has been set'] = pm.globals.get('articles.createdat') === articles.createdat;
		
			tests['articles has "updatedat" property'] = articles.hasOwnProperty('updatedat'); 
		    tests['articles of "updatedat" property is an ISO 8601 timestamp'] = /^\d{4}-\d{2}-\d{2}T\d{2}:\d{2}:\d{2}\.\d{6}$/.test(articles.updatedat);
		
		    if(tests['articles has "updatedat" property']){
            	pm.globals.set('articles.updatedat', articles.updatedat);
            }
            
            tests['Global variable "articles.updatedat" has been set'] = pm.globals.get('articles.updatedat') === articles.updatedat;
		
			tests['articles has "authorId" property'] = articles.hasOwnProperty('usersAuthorIdResponse');
				
		    if(tests['articles has "authorId" property']){
            	pm.globals.set('articles.authorId', articles.usersAuthorIdResponse.id);
            }
            
            tests['Global variable "articles.authorId" has been set'] = pm.globals.get('articles.authorId') === articles.usersAuthorIdResponse.id;
			
			tests['articles has "body" property'] = articles.hasOwnProperty('body'); 
		
		    if(tests['articles has "body" property']){
            	pm.globals.set('articles.body', articles.body);
            }
            
            tests['Global variable "articles.body" has been set'] = pm.globals.get('articles.body') === articles.body;
		
			tests['articles has "description" property'] = articles.hasOwnProperty('description'); 
		
		    if(tests['articles has "description" property']){
            	pm.globals.set('articles.description', articles.description);
            }
            
            tests['Global variable "articles.description" has been set'] = pm.globals.get('articles.description') === articles.description;
		
			tests['articles has "id" property'] = articles.hasOwnProperty('id'); 
		
		    if(tests['articles has "id" property']){
            	pm.globals.set('articles.id', articles.id);
            }
            
            tests['Global variable "articles.id" has been set'] = pm.globals.get('articles.id') === articles.id;
		
			tests['articles has "slug" property'] = articles.hasOwnProperty('slug'); 
		
		    if(tests['articles has "slug" property']){
            	pm.globals.set('articles.slug', articles.slug);
            }
            
            tests['Global variable "articles.slug" has been set'] = pm.globals.get('articles.slug') === articles.slug;
		
			tests['articles has "title" property'] = articles.hasOwnProperty('title'); 
		
		    if(tests['articles has "title" property']){
            	pm.globals.set('articles.title', articles.title);
            }
            
            tests['Global variable "articles.title" has been set'] = pm.globals.get('articles.title') === articles.title;
		
	    }
      }
    });sleep(delay);
    
    
    postman[Request]({
      name:
        "Find articles By Filter With Offset0 And Limit10, All Fields, Equals, Or, Sort Desc",
      id: "2024-02-23-06-45-22-000578",
      method: "GET",
      options: { timeout: timeout },
      address:
        "{{APIURL}}/firmansyah/articles?offset=0&limit=10&filter=createdat%7Ceq%7C{{articles.createdat}}%7C%7Cupdatedat%7Ceq%7C{{articles.updatedat}}%7C%7CauthorId%7Ceq%7C{{articles.authorId}}%7C%7Cbody%7Ceq%7C{{articles.body}}%7C%7Cdescription%7Ceq%7C{{articles.description}}%7C%7Cid%7Ceq%7C{{articles.id}}%7C%7Cslug%7Ceq%7C{{articles.slug}}%7C%7Ctitle%7Ceq%7C{{articles.title}}&conjunctions=OR,OR,OR,OR,OR,OR,OR,OR&sort=-createdat,-updatedat,-authorId,-body,-description,-id,-slug,-title",
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
                       
        tests['Find articles By Filter With Offset0 And Limit10, All Fields, Equals, Or, Sort Desc - Response code is 200 OK'] = is200Response;
        
	    if (!(environment.isIntegrationTest) && is200Response) {
	    	var responseJSON = JSON.parse(responseBody);
	        
	        tests['Response contains "articles" property'] = responseJSON.hasOwnProperty('articles');
	           
	        tests['Response contains "articlesCount" property'] = responseJSON.hasOwnProperty('articlesCount');
	                 
	        var articles = responseJSON.articles || {};
	        if (typeof articles === 'object' && Array.isArray(articles) && articles.length > 0) {

			    // Check if 'articles' is an array and has length above 0
			  	check(articles, {
			    '	Response should be an array': (arr) => Array.isArray(arr) && arr.length > 0,
			  	});
			
			  	// Check if 'articles' has the same length as the response array length
			  	check(articles, {
			    '	Response array length should match': (arr) => arr.length === articles.length,
			  	});
	                   
				tests['articles[0] has "createdat" property'] = articles[0].hasOwnProperty('createdat');
			    tests['articles[0] of "createdat" property is an ISO 8601 timestamp'] = /^\d{4}-\d{2}-\d{2}T\d{2}:\d{2}:\d{2}\.\d{6}$/.test(articles[0].createdat);
					  
				tests['articles[0] has "updatedat" property'] = articles[0].hasOwnProperty('updatedat');
			    tests['articles[0] of "updatedat" property is an ISO 8601 timestamp'] = /^\d{4}-\d{2}-\d{2}T\d{2}:\d{2}:\d{2}\.\d{6}$/.test(articles[0].updatedat);
					  
				tests['articles[0] has "authorId" property'] = articles[0].hasOwnProperty('usersAuthorIdResponse');
				tests['articles[0] has "body" property'] = articles[0].hasOwnProperty('body');
					  
				tests['articles[0] has "description" property'] = articles[0].hasOwnProperty('description');
					  
				tests['articles[0] has "id" property'] = articles[0].hasOwnProperty('id');
					  
				tests['articles[0] has "slug" property'] = articles[0].hasOwnProperty('slug');
					  
				tests['articles[0] has "title" property'] = articles[0].hasOwnProperty('title');
					  
			}                  
	      }
      }
    });sleep(delay);
    
    postman[Request]({
      name:
        "Find articles By Filter With Offset0 And Limit10, All Fields, Equals, Or, Sort Asc",
      id: "2024-02-23-06-45-22-000578",
      method: "GET",
      options: { timeout: timeout },
      address:
        "{{APIURL}}/firmansyah/articles?offset=0&limit=10&filter=createdat%7Ceq%7C{{articles.createdat}}%7C%7Cupdatedat%7Ceq%7C{{articles.updatedat}}%7C%7CauthorId%7Ceq%7C{{articles.authorId}}%7C%7Cbody%7Ceq%7C{{articles.body}}%7C%7Cdescription%7Ceq%7C{{articles.description}}%7C%7Cid%7Ceq%7C{{articles.id}}%7C%7Cslug%7Ceq%7C{{articles.slug}}%7C%7Ctitle%7Ceq%7C{{articles.title}}&conjunctions=OR,OR,OR,OR,OR,OR,OR,OR&sort=createdat,updatedat,authorId,body,description,id,slug,title",
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
                       
        tests['Find articles By Filter With Offset0 And Limit10, All Fields, Equals, Or, Sort Asc - Response code is 200 OK'] = is200Response;
        
	    if (!(environment.isIntegrationTest) && is200Response) {
	    	var responseJSON = JSON.parse(responseBody);
	        
	        tests['Response contains "articles" property'] = responseJSON.hasOwnProperty('articles');
	           
	        tests['Response contains "articlesCount" property'] = responseJSON.hasOwnProperty('articlesCount');
	                 
	        var articles = responseJSON.articles || {};
	        if (typeof articles === 'object' && Array.isArray(articles) && articles.length > 0) {

			    // Check if 'articles' is an array and has length above 0
			  	check(articles, {
			    '	Response should be an array': (arr) => Array.isArray(arr) && arr.length > 0,
			  	});
			
			  	// Check if 'articles' has the same length as the response array length
			  	check(articles, {
			    '	Response array length should match': (arr) => arr.length === articles.length,
			  	});
	                   
				tests['articles[0] has "createdat" property'] = articles[0].hasOwnProperty('createdat');
			    tests['articles[0] of "createdat" property is an ISO 8601 timestamp'] = /^\d{4}-\d{2}-\d{2}T\d{2}:\d{2}:\d{2}\.\d{6}$/.test(articles[0].createdat);
					  
				tests['articles[0] has "updatedat" property'] = articles[0].hasOwnProperty('updatedat');
			    tests['articles[0] of "updatedat" property is an ISO 8601 timestamp'] = /^\d{4}-\d{2}-\d{2}T\d{2}:\d{2}:\d{2}\.\d{6}$/.test(articles[0].updatedat);
					  
				tests['articles[0] has "authorId" property'] = articles[0].hasOwnProperty('usersAuthorIdResponse');
				tests['articles[0] has "body" property'] = articles[0].hasOwnProperty('body');
					  
				tests['articles[0] has "description" property'] = articles[0].hasOwnProperty('description');
					  
				tests['articles[0] has "id" property'] = articles[0].hasOwnProperty('id');
					  
				tests['articles[0] has "slug" property'] = articles[0].hasOwnProperty('slug');
					  
				tests['articles[0] has "title" property'] = articles[0].hasOwnProperty('title');
					  
			}                
	      }
      }
    });sleep(delay);
    
    postman[Request]({
      name:
        "Find articles By Filter With Offset0 And Limit10, All Fields, Equals, Or Conjunctions",
      id: "2024-02-23-06-45-22-000578",
      method: "GET",
      options: { timeout: timeout },
      address:
        "{{APIURL}}/firmansyah/articles?offset=0&limit=10&filter=createdat%7Ceq%7C{{articles.createdat}}%7C%7Cupdatedat%7Ceq%7C{{articles.updatedat}}%7C%7CauthorId%7Ceq%7C{{articles.authorId}}%7C%7Cbody%7Ceq%7C{{articles.body}}%7C%7Cdescription%7Ceq%7C{{articles.description}}%7C%7Cid%7Ceq%7C{{articles.id}}%7C%7Cslug%7Ceq%7C{{articles.slug}}%7C%7Ctitle%7Ceq%7C{{articles.title}}&conjunctions=OR,OR,OR,OR,OR,OR,OR,OR",
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
                       
        tests['Find articles By Filter With Offset0 And Limit10, All Fields, Equals, Or Conjunctions - Response code is 200 OK'] = is200Response;
        
	    if (!(environment.isIntegrationTest) && is200Response) {
	    	var responseJSON = JSON.parse(responseBody);
	        
	        tests['Response contains "articles" property'] = responseJSON.hasOwnProperty('articles');
	           
	        tests['Response contains "articlesCount" property'] = responseJSON.hasOwnProperty('articlesCount');
	                 
	        var articles = responseJSON.articles || {};
	        if (typeof articles === 'object' && Array.isArray(articles) && articles.length > 0) {

			    // Check if 'articles' is an array and has length above 0
			  	check(articles, {
			    '	Response should be an array': (arr) => Array.isArray(arr) && arr.length > 0,
			  	});
			
			  	// Check if 'articles' has the same length as the response array length
			  	check(articles, {
			    '	Response array length should match': (arr) => arr.length === articles.length,
			  	});
	                   
				tests['articles[0] has "createdat" property'] = articles[0].hasOwnProperty('createdat');
			    tests['articles[0] of "createdat" property is an ISO 8601 timestamp'] = /^\d{4}-\d{2}-\d{2}T\d{2}:\d{2}:\d{2}\.\d{6}$/.test(articles[0].createdat);
					  
				tests['articles[0] has "updatedat" property'] = articles[0].hasOwnProperty('updatedat');
			    tests['articles[0] of "updatedat" property is an ISO 8601 timestamp'] = /^\d{4}-\d{2}-\d{2}T\d{2}:\d{2}:\d{2}\.\d{6}$/.test(articles[0].updatedat);
					  
				tests['articles[0] has "authorId" property'] = articles[0].hasOwnProperty('usersAuthorIdResponse');
				tests['articles[0] has "body" property'] = articles[0].hasOwnProperty('body');
					  
				tests['articles[0] has "description" property'] = articles[0].hasOwnProperty('description');
					  
				tests['articles[0] has "id" property'] = articles[0].hasOwnProperty('id');
					  
				tests['articles[0] has "slug" property'] = articles[0].hasOwnProperty('slug');
					  
				tests['articles[0] has "title" property'] = articles[0].hasOwnProperty('title');
					  
			}                
	      }
      }
    });sleep(delay);
    
    
    postman[Request]({
      name:
        "Find articles By Filter With Offset0 And Limit10, All Fields, Equals, And Conjunctions",
      id: "2024-02-23-06-45-22-000578",
      method: "GET",
      options: { timeout: timeout },
      address:
        "{{APIURL}}/firmansyah/articles?offset=0&limit=10&filter=createdat%7Ceq%7C{{articles.createdat}}%7C%7Cupdatedat%7Ceq%7C{{articles.updatedat}}%7C%7CauthorId%7Ceq%7C{{articles.authorId}}%7C%7Cbody%7Ceq%7C{{articles.body}}%7C%7Cdescription%7Ceq%7C{{articles.description}}%7C%7Cid%7Ceq%7C{{articles.id}}%7C%7Cslug%7Ceq%7C{{articles.slug}}%7C%7Ctitle%7Ceq%7C{{articles.title}}&conjunctions=AND,AND,AND,AND,AND,AND,AND,AND",
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
                       
        tests['Find articles By Filter With Offset0 And Limit10, All Fields, Equals, And Conjunctions - Response code is 200 OK'] = is200Response;
        
	    if (!(environment.isIntegrationTest) && is200Response) {
	    	var responseJSON = JSON.parse(responseBody);
	        
	        tests['Response contains "articles" property'] = responseJSON.hasOwnProperty('articles');
	           
	        tests['Response contains "articlesCount" property'] = responseJSON.hasOwnProperty('articlesCount');
	                 
	        var articles = responseJSON.articles || {};
	        if (typeof articles === 'object' && Array.isArray(articles) && articles.length > 0) {

			    // Check if 'articles' is an array and has length above 0
			  	check(articles, {
			    '	Response should be an array': (arr) => Array.isArray(arr) && arr.length > 0,
			  	});
			
			  	// Check if 'articles' has the same length as the response array length
			  	check(articles, {
			    '	Response array length should match': (arr) => arr.length === articles.length,
			  	});
	                   
				tests['articles[0] has "createdat" property'] = articles[0].hasOwnProperty('createdat');
			    tests['articles[0] of "createdat" property is an ISO 8601 timestamp'] = /^\d{4}-\d{2}-\d{2}T\d{2}:\d{2}:\d{2}\.\d{6}$/.test(articles[0].createdat);
					  
				tests['articles[0] has "updatedat" property'] = articles[0].hasOwnProperty('updatedat');
			    tests['articles[0] of "updatedat" property is an ISO 8601 timestamp'] = /^\d{4}-\d{2}-\d{2}T\d{2}:\d{2}:\d{2}\.\d{6}$/.test(articles[0].updatedat);
					  
				tests['articles[0] has "authorId" property'] = articles[0].hasOwnProperty('usersAuthorIdResponse');
				tests['articles[0] has "body" property'] = articles[0].hasOwnProperty('body');
					  
				tests['articles[0] has "description" property'] = articles[0].hasOwnProperty('description');
					  
				tests['articles[0] has "id" property'] = articles[0].hasOwnProperty('id');
					  
				tests['articles[0] has "slug" property'] = articles[0].hasOwnProperty('slug');
					  
				tests['articles[0] has "title" property'] = articles[0].hasOwnProperty('title');
					  
			}                  
	      }
      }
    });sleep(delay);
    
    postman[Request]({
      name:
        "Find articles By Filter With Offset0 And Limit10, All Fields, Not Equals, Or Conjunctions",
      id: "2024-02-23-06-45-22-000578",
      method: "GET",
      options: { timeout: timeout },
      address:
        "{{APIURL}}/firmansyah/articles?offset=0&limit=10&filter=createdat%7Cneq%7C{{articles.createdat}}%7C%7Cupdatedat%7Cneq%7C{{articles.updatedat}}%7C%7CauthorId%7Cneq%7C{{articles.authorId}}%7C%7Cbody%7Cneq%7C{{articles.body}}%7C%7Cdescription%7Cneq%7C{{articles.description}}%7C%7Cid%7Cneq%7C{{articles.id}}%7C%7Cslug%7Cneq%7C{{articles.slug}}%7C%7Ctitle%7Cneq%7C{{articles.title}}&conjunctions=OR,OR,OR,OR,OR,OR,OR,OR",
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
                       
        tests['Find articles By Filter With Offset0 And Limit10, All Fields, Not Equals, Or Conjunctions - Response code is 200 OK'] = is200Response;
        
	    if (!(environment.isIntegrationTest) && is200Response) {
	    	var responseJSON = JSON.parse(responseBody);
	        
	        tests['Response contains "articles" property'] = responseJSON.hasOwnProperty('articles');
	           
	        tests['Response contains "articlesCount" property'] = responseJSON.hasOwnProperty('articlesCount');
	                 
	        var articles = responseJSON.articles || {};
            
            if (typeof articles === 'object' && Array.isArray(articles) && articles.length > 0) {
			    // Check if 'articles' is an array and has length above 0
			  	check(articles, {
			    '	Response should be an array': (arr) => Array.isArray(arr) && arr.length > 0,
			  	});
			
			  	// Check if 'articles' has the same length as the response array length
			  	check(articles, {
			    '	Response array length should match': (arr) => arr.length === articles.length,
			  	});
	                   
				tests['articles[0] has "createdat" property'] = articles[0].hasOwnProperty('createdat');
			    tests['articles[0] of "createdat" property is an ISO 8601 timestamp'] = /^\d{4}-\d{2}-\d{2}T\d{2}:\d{2}:\d{2}\.\d{6}$/.test(articles[0].createdat);
					  
				tests['articles[0] has "updatedat" property'] = articles[0].hasOwnProperty('updatedat');
			    tests['articles[0] of "updatedat" property is an ISO 8601 timestamp'] = /^\d{4}-\d{2}-\d{2}T\d{2}:\d{2}:\d{2}\.\d{6}$/.test(articles[0].updatedat);
					  
				tests['articles[0] has "authorId" property'] = articles[0].hasOwnProperty('usersAuthorIdResponse');
				tests['articles[0] has "body" property'] = articles[0].hasOwnProperty('body');
					  
				tests['articles[0] has "description" property'] = articles[0].hasOwnProperty('description');
					  
				tests['articles[0] has "id" property'] = articles[0].hasOwnProperty('id');
					  
				tests['articles[0] has "slug" property'] = articles[0].hasOwnProperty('slug');
					  
				tests['articles[0] has "title" property'] = articles[0].hasOwnProperty('title');
					  
			}                 
	      }
      }
    });sleep(delay);
    
    postman[Request]({
      name:
        "Find articles By Filter With Offset0 And Limit10, All Fields, Like, And Conjunctions",
      id: "2024-02-23-06-45-22-000578",
      method: "GET",
      options: { timeout: timeout },
      address:
        "{{APIURL}}/firmansyah/articles?offset=0&limit=10&filter=%7C%7C%7C%7CauthorId%7Clike%7C{{articles.authorId}}%7C%7Cbody%7Clike%7C{{articles.body}}%7C%7Cdescription%7Clike%7C{{articles.description}}%7C%7Cid%7Clike%7C{{articles.id}}%7C%7Cslug%7Clike%7C{{articles.slug}}%7C%7Ctitle%7Clike%7C{{articles.title}}&conjunctions=AND,AND,AND,AND,AND,AND",
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
                       
        tests['Find articles By Filter With Offset0 And Limit10, All Fields, Like, And Conjunctions - Response code is 200 OK'] = is200Response;
        
	    if (!(environment.isIntegrationTest) && is200Response) {
	    	var responseJSON = JSON.parse(responseBody);
	        
	        tests['Response contains "articles" property'] = responseJSON.hasOwnProperty('articles');
	           
	        tests['Response contains "articlesCount" property'] = responseJSON.hasOwnProperty('articlesCount');
	                 
	        var articles = responseJSON.articles || {};
	        if (typeof articles === 'object' && Array.isArray(articles) && articles.length > 0) {

			    // Check if 'articles' is an array and has length above 0
			  	check(articles, {
			    '	Response should be an array': (arr) => Array.isArray(arr) && arr.length > 0,
			  	});
			
			  	// Check if 'articles' has the same length as the response array length
			  	check(articles, {
			    '	Response array length should match': (arr) => arr.length === articles.length,
			  	});
	                   
				tests['articles[0] has "createdat" property'] = articles[0].hasOwnProperty('createdat');
			    tests['articles[0] of "createdat" property is an ISO 8601 timestamp'] = /^\d{4}-\d{2}-\d{2}T\d{2}:\d{2}:\d{2}\.\d{6}$/.test(articles[0].createdat);
					  
				tests['articles[0] has "updatedat" property'] = articles[0].hasOwnProperty('updatedat');
			    tests['articles[0] of "updatedat" property is an ISO 8601 timestamp'] = /^\d{4}-\d{2}-\d{2}T\d{2}:\d{2}:\d{2}\.\d{6}$/.test(articles[0].updatedat);
					  
				tests['articles[0] has "authorId" property'] = articles[0].hasOwnProperty('usersAuthorIdResponse');
				tests['articles[0] has "body" property'] = articles[0].hasOwnProperty('body');
					  
				tests['articles[0] has "description" property'] = articles[0].hasOwnProperty('description');
					  
				tests['articles[0] has "id" property'] = articles[0].hasOwnProperty('id');
					  
				tests['articles[0] has "slug" property'] = articles[0].hasOwnProperty('slug');
					  
				tests['articles[0] has "title" property'] = articles[0].hasOwnProperty('title');
					  
			}                  
	      }
      }
    });sleep(delay);
    
    postman[Request]({
      name:
        "Find articles By Filter With Offset0 And Limit10, All Fields, Not Like, Or Conjunctions",
      id: "2024-02-23-06-45-22-000578",
      method: "GET",
      options: { timeout: timeout },
      address:
        "{{APIURL}}/firmansyah/articles?offset=0&limit=10&filter=%7C%7C%7C%7CauthorId%7Cnlike%7C{{articles.authorId}}%7C%7Cbody%7Cnlike%7C{{articles.body}}%7C%7Cdescription%7Cnlike%7C{{articles.description}}%7C%7Cid%7Cnlike%7C{{articles.id}}%7C%7Cslug%7Cnlike%7C{{articles.slug}}%7C%7Ctitle%7Cnlike%7C{{articles.title}}&conjunctions=OR,OR,OR,OR,OR,OR",
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
                       
        tests['Find articles By Filter With Offset0 And Limit10, All Fields, Not Like, Or Conjunctions - Response code is 200 OK'] = is200Response;
        
	    if (!(environment.isIntegrationTest) && is200Response) {
	    	var responseJSON = JSON.parse(responseBody);
	        
	        tests['Response contains "articles" property'] = responseJSON.hasOwnProperty('articles');
	           
	        tests['Response contains "articlesCount" property'] = responseJSON.hasOwnProperty('articlesCount');
	                 
	        var articles = responseJSON.articles || {};

			if (typeof articles === 'object' && Array.isArray(articles) && articles.length > 0) {
			    // Check if 'articles' is an array and has length above 0
			  	check(articles, {
			    '	Response should be an array': (arr) => Array.isArray(arr) && arr.length > 0,
			  	});
			
			  	// Check if 'articles' has the same length as the response array length
			  	check(articles, {
			    '	Response array length should match': (arr) => arr.length === articles.length,
			  	});
	                   
				tests['articles[0] has "createdat" property'] = articles[0].hasOwnProperty('createdat');
			    tests['articles[0] of "createdat" property is an ISO 8601 timestamp'] = /^\d{4}-\d{2}-\d{2}T\d{2}:\d{2}:\d{2}\.\d{6}$/.test(articles[0].createdat);
					  
				tests['articles[0] has "updatedat" property'] = articles[0].hasOwnProperty('updatedat');
			    tests['articles[0] of "updatedat" property is an ISO 8601 timestamp'] = /^\d{4}-\d{2}-\d{2}T\d{2}:\d{2}:\d{2}\.\d{6}$/.test(articles[0].updatedat);
					  
				tests['articles[0] has "authorId" property'] = articles[0].hasOwnProperty('usersAuthorIdResponse');
				tests['articles[0] has "body" property'] = articles[0].hasOwnProperty('body');
					  
				tests['articles[0] has "description" property'] = articles[0].hasOwnProperty('description');
					  
				tests['articles[0] has "id" property'] = articles[0].hasOwnProperty('id');
					  
				tests['articles[0] has "slug" property'] = articles[0].hasOwnProperty('slug');
					  
				tests['articles[0] has "title" property'] = articles[0].hasOwnProperty('title');
					  
			}                 
	      }
      }
    });sleep(delay);
});     
}

export function handleSummary(data) {
  // Access the environment variable 'VAR' using the __ENV object
  const rpt = __ENV.RPT;
  //const now = new Date();
  //const year = now.getFullYear();
  //const month = String(now.getMonth() + 1).padStart(2, '0');
  //const day = String(now.getDate()).padStart(2, '0');
  //const hours = String(now.getHours()).padStart(2, '0');
  //const minutes = String(now.getMinutes()).padStart(2, '0');
  //const seconds = String(now.getSeconds()).padStart(2, '0');
  //const milliseconds = String(now.getMilliseconds()).padStart(3, '0');

  //const formattedDate = year+'-'+month+'-'+day+'T'+hours+':'+minutes+':'+seconds+'.'+milliseconds;
  
  //const LocalDateTimeNow = "k6/reports/articles.pt."+formattedDate+myVar+".html";
  return {
    [rpt] : htmlReport(data),
    stdout: textSummary(data, { indent: " ", enableColors: true }),
  };
}
