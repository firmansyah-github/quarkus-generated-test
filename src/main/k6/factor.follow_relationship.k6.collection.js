// created by the factor : Dec 7, 2023, 4:03:00 PM  

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
      id: "2023-12-07-16-03-00-000",
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
      id: "2023-12-07-16-03-00-000",
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
      name: "Create users for create follow_relationship",
      id: "2023-12-07-16-03-00-000941",
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

        tests["Response code is 201 OK - Create users for create follow_relationship"] = is201Response;

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
          		pm.globals.set('follow_relationship.users', users.id);
          }
           
          tests['Global variable "follow_relationship.users" has been set'] = pm.globals.get('follow_relationship.users') === users.id;
				      
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
      name: "Create users for create follow_relationship",
      id: "2023-12-07-16-03-00-000941",
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

        tests["Response code is 201 OK - Create users for create follow_relationship"] = is201Response;

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
          		pm.globals.set('follow_relationship.users', users.id);
          }
           
          tests['Global variable "follow_relationship.users" has been set'] = pm.globals.get('follow_relationship.users') === users.id;
				      
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
      name: "Create follow_relationship for create follow_relationship",
      id: "2023-12-07-16-03-00-000941",
      method: "POST",
      options: { timeout: timeout },
      address: "{{APIURL}}/firmansyah/followRelationship",
      data:
        '{"followRelationship":{"followedId":"{{follow_relationship.users}}","userId":"{{follow_relationship.users}}"}}',
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

        tests["Response code is 201 OK - Create follow_relationship for create follow_relationship"] = is201Response;

        if (!environment.isIntegrationTest && is201Response) {
          var responseJSON = JSON.parse(responseBody);

          tests[
            'Response contains "followRelationship" property'
          ] = responseJSON.hasOwnProperty("followRelationship");

          var followRelationship = responseJSON.followRelationship || {};
		  tests['followRelationship has "followedId" property'] = followRelationship.hasOwnProperty('usersFollowedIdResponse');
		  
		  if(tests['followRelationship has "followedId" property']){
                pm.globals.set('followRelationship.followedId', followRelationship.usersFollowedIdResponse.id);
           }
                  
          tests['Global variable "followRelationship.followedId" has been set'] = pm.globals.get('followRelationship.followedId') === followRelationship.usersFollowedIdResponse.id;
		  tests['followRelationship has "userId" property'] = followRelationship.hasOwnProperty('usersUserIdResponse');
		  
		  if(tests['followRelationship has "userId" property']){
                pm.globals.set('followRelationship.userId', followRelationship.usersUserIdResponse.id);
           }
                  
          tests['Global variable "followRelationship.userId" has been set'] = pm.globals.get('followRelationship.userId') === followRelationship.usersUserIdResponse.id;
        }
       }
      });sleep(delay);
        	
	
	postman[Request]({
      name: "Find followRelationship By Primary Key",
      id: "2023-12-07-16-03-00-000941",
      method: "GET",
      options: { timeout: timeout },
      address: "{{APIURL}}/firmansyah/followRelationship/find?followedId={{followRelationship.followedId}}&userId={{followRelationship.userId}}",
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
                       
        tests['Response code is 200 OK - Find followRelationship By Primary Key'] = is200Response;
        
	    if (!(environment.isIntegrationTest) && is200Response) {
	    	var responseJSON = JSON.parse(responseBody);
	                 
	        tests['Response contains "followRelationship" property'] = responseJSON.hasOwnProperty('followRelationship');
	        
	        var followRelationship = responseJSON.followRelationship || {};
	       
			tests['followRelationship has "followedId" property'] = followRelationship.hasOwnProperty('usersFollowedIdResponse');
				
		    if(tests['followRelationship has "followedId" property']){
            	pm.globals.set('followRelationship.followedId', followRelationship.usersFollowedIdResponse.id);
            }
            
            tests['Global variable "followRelationship.followedId" has been set'] = pm.globals.get('followRelationship.followedId') === followRelationship.usersFollowedIdResponse.id;
			
			tests['followRelationship has "userId" property'] = followRelationship.hasOwnProperty('usersUserIdResponse');
				
		    if(tests['followRelationship has "userId" property']){
            	pm.globals.set('followRelationship.userId', followRelationship.usersUserIdResponse.id);
            }
            
            tests['Global variable "followRelationship.userId" has been set'] = pm.globals.get('followRelationship.userId') === followRelationship.usersUserIdResponse.id;
			
	    }
      }
    });sleep(delay);
    
    
    postman[Request]({
      name:
        "Find follow_relationship By Filter With Offset0 And Limit10, All Fields, Equals, Or, Sort Desc",
      id: "2023-12-07-16-03-00-000941",
      method: "GET",
      options: { timeout: timeout },
      address:
        "{{APIURL}}/firmansyah/followRelationship?offset=0&limit=10&filter=followedId%7Ceq%7C{{followRelationship.followedId}}%7C%7CuserId%7Ceq%7C{{followRelationship.userId}}&conjunctions=OR,OR&sort=-followedId,-userId",
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
                       
        tests['Find follow_relationship By Filter With Offset0 And Limit10, All Fields, Equals, Or, Sort Desc - Response code is 200 OK'] = is200Response;
        
	    if (!(environment.isIntegrationTest) && is200Response) {
	    	var responseJSON = JSON.parse(responseBody);
	        
	        tests['Response contains "followRelationship" property'] = responseJSON.hasOwnProperty('followRelationship');
	           
	        tests['Response contains "followRelationshipCount" property'] = responseJSON.hasOwnProperty('followRelationshipCount');
	                 
	        var followRelationship = responseJSON.followRelationship || {};
	        if (typeof followRelationship === 'object' && Array.isArray(followRelationship) && followRelationship.length > 0) {

			    // Check if 'articles' is an array and has length above 0
			  	check(followRelationship, {
			    '	Response should be an array': (arr) => Array.isArray(arr) && arr.length > 0,
			  	});
			
			  	// Check if 'followRelationship' has the same length as the response array length
			  	check(followRelationship, {
			    '	Response array length should match': (arr) => arr.length === followRelationship.length,
			  	});
	                   
				tests['followRelationship[0] has "followedId" property'] = followRelationship[0].hasOwnProperty('usersFollowedIdResponse');
				tests['followRelationship[0] has "userId" property'] = followRelationship[0].hasOwnProperty('usersUserIdResponse');
			}                  
	      }
      }
    });sleep(delay);
    
    postman[Request]({
      name:
        "Find follow_relationship By Filter With Offset0 And Limit10, All Fields, Equals, Or, Sort Asc",
      id: "2023-12-07-16-03-00-000941",
      method: "GET",
      options: { timeout: timeout },
      address:
        "{{APIURL}}/firmansyah/followRelationship?offset=0&limit=10&filter=followedId%7Ceq%7C{{followRelationship.followedId}}%7C%7CuserId%7Ceq%7C{{followRelationship.userId}}&conjunctions=OR,OR&sort=followedId,userId",
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
                       
        tests['Find follow_relationship By Filter With Offset0 And Limit10, All Fields, Equals, Or, Sort Asc - Response code is 200 OK'] = is200Response;
        
	    if (!(environment.isIntegrationTest) && is200Response) {
	    	var responseJSON = JSON.parse(responseBody);
	        
	        tests['Response contains "followRelationship" property'] = responseJSON.hasOwnProperty('followRelationship');
	           
	        tests['Response contains "followRelationshipCount" property'] = responseJSON.hasOwnProperty('followRelationshipCount');
	                 
	        var followRelationship = responseJSON.followRelationship || {};
	        if (typeof followRelationship === 'object' && Array.isArray(followRelationship) && followRelationship.length > 0) {

			    // Check if 'articles' is an array and has length above 0
			  	check(followRelationship, {
			    '	Response should be an array': (arr) => Array.isArray(arr) && arr.length > 0,
			  	});
			
			  	// Check if 'followRelationship' has the same length as the response array length
			  	check(followRelationship, {
			    '	Response array length should match': (arr) => arr.length === followRelationship.length,
			  	});
	                   
				tests['followRelationship[0] has "followedId" property'] = followRelationship[0].hasOwnProperty('usersFollowedIdResponse');
				tests['followRelationship[0] has "userId" property'] = followRelationship[0].hasOwnProperty('usersUserIdResponse');
			}                
	      }
      }
    });sleep(delay);
    
    postman[Request]({
      name:
        "Find follow_relationship By Filter With Offset0 And Limit10, All Fields, Equals, Or Conjunctions",
      id: "2023-12-07-16-03-00-000941",
      method: "GET",
      options: { timeout: timeout },
      address:
        "{{APIURL}}/firmansyah/followRelationship?offset=0&limit=10&filter=followedId%7Ceq%7C{{followRelationship.followedId}}%7C%7CuserId%7Ceq%7C{{followRelationship.userId}}&conjunctions=OR,OR",
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
                       
        tests['Find follow_relationship By Filter With Offset0 And Limit10, All Fields, Equals, Or Conjunctions - Response code is 200 OK'] = is200Response;
        
	    if (!(environment.isIntegrationTest) && is200Response) {
	    	var responseJSON = JSON.parse(responseBody);
	        
	        tests['Response contains "followRelationship" property'] = responseJSON.hasOwnProperty('followRelationship');
	           
	        tests['Response contains "followRelationshipCount" property'] = responseJSON.hasOwnProperty('followRelationshipCount');
	                 
	        var followRelationship = responseJSON.followRelationship || {};
	        if (typeof followRelationship === 'object' && Array.isArray(followRelationship) && followRelationship.length > 0) {

			    // Check if 'articles' is an array and has length above 0
			  	check(followRelationship, {
			    '	Response should be an array': (arr) => Array.isArray(arr) && arr.length > 0,
			  	});
			
			  	// Check if 'followRelationship' has the same length as the response array length
			  	check(followRelationship, {
			    '	Response array length should match': (arr) => arr.length === followRelationship.length,
			  	});
	                   
				tests['followRelationship[0] has "followedId" property'] = followRelationship[0].hasOwnProperty('usersFollowedIdResponse');
				tests['followRelationship[0] has "userId" property'] = followRelationship[0].hasOwnProperty('usersUserIdResponse');
			}                
	      }
      }
    });sleep(delay);
    
    
    postman[Request]({
      name:
        "Find follow_relationship By Filter With Offset0 And Limit10, All Fields, Equals, And Conjunctions",
      id: "2023-12-07-16-03-00-000941",
      method: "GET",
      options: { timeout: timeout },
      address:
        "{{APIURL}}/firmansyah/followRelationship?offset=0&limit=10&filter=followedId%7Ceq%7C{{followRelationship.followedId}}%7C%7CuserId%7Ceq%7C{{followRelationship.userId}}&conjunctions=AND,AND",
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
                       
        tests['Find follow_relationship By Filter With Offset0 And Limit10, All Fields, Equals, And Conjunctions - Response code is 200 OK'] = is200Response;
        
	    if (!(environment.isIntegrationTest) && is200Response) {
	    	var responseJSON = JSON.parse(responseBody);
	        
	        tests['Response contains "followRelationship" property'] = responseJSON.hasOwnProperty('followRelationship');
	           
	        tests['Response contains "followRelationshipCount" property'] = responseJSON.hasOwnProperty('followRelationshipCount');
	                 
	        var followRelationship = responseJSON.followRelationship || {};
	        if (typeof followRelationship === 'object' && Array.isArray(followRelationship) && followRelationship.length > 0) {

			    // Check if 'articles' is an array and has length above 0
			  	check(followRelationship, {
			    '	Response should be an array': (arr) => Array.isArray(arr) && arr.length > 0,
			  	});
			
			  	// Check if 'followRelationship' has the same length as the response array length
			  	check(followRelationship, {
			    '	Response array length should match': (arr) => arr.length === followRelationship.length,
			  	});
	                   
				tests['followRelationship[0] has "followedId" property'] = followRelationship[0].hasOwnProperty('usersFollowedIdResponse');
				tests['followRelationship[0] has "userId" property'] = followRelationship[0].hasOwnProperty('usersUserIdResponse');
			}                  
	      }
      }
    });sleep(delay);
    
    postman[Request]({
      name:
        "Find follow_relationship By Filter With Offset0 And Limit10, All Fields, Not Equals, Or Conjunctions",
      id: "2023-12-07-16-03-00-000941",
      method: "GET",
      options: { timeout: timeout },
      address:
        "{{APIURL}}/firmansyah/followRelationship?offset=0&limit=10&filter=followedId%7Cneq%7C{{followRelationship.followedId}}%7C%7CuserId%7Cneq%7C{{followRelationship.userId}}&conjunctions=OR,OR",
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
                       
        tests['Find follow_relationship By Filter With Offset0 And Limit10, All Fields, Not Equals, Or Conjunctions - Response code is 200 OK'] = is200Response;
        
	    if (!(environment.isIntegrationTest) && is200Response) {
	    	var responseJSON = JSON.parse(responseBody);
	        
	        tests['Response contains "followRelationship" property'] = responseJSON.hasOwnProperty('followRelationship');
	           
	        tests['Response contains "followRelationshipCount" property'] = responseJSON.hasOwnProperty('followRelationshipCount');
	                 
	        var followRelationship = responseJSON.followRelationship || {};
            
            if (typeof followRelationship === 'object' && Array.isArray(followRelationship) && followRelationship.length > 0) {
			    // Check if 'articles' is an array and has length above 0
			  	check(followRelationship, {
			    '	Response should be an array': (arr) => Array.isArray(arr) && arr.length > 0,
			  	});
			
			  	// Check if 'followRelationship' has the same length as the response array length
			  	check(followRelationship, {
			    '	Response array length should match': (arr) => arr.length === followRelationship.length,
			  	});
	                   
				tests['followRelationship[0] has "followedId" property'] = followRelationship[0].hasOwnProperty('usersFollowedIdResponse');
				tests['followRelationship[0] has "userId" property'] = followRelationship[0].hasOwnProperty('usersUserIdResponse');
			}                 
	      }
      }
    });sleep(delay);
    
    postman[Request]({
      name:
        "Find follow_relationship By Filter With Offset0 And Limit10, All Fields, Like, And Conjunctions",
      id: "2023-12-07-16-03-00-000941",
      method: "GET",
      options: { timeout: timeout },
      address:
        "{{APIURL}}/firmansyah/followRelationship?offset=0&limit=10&filter=followedId%7Clike%7C{{followRelationship.followedId}}%7C%7CuserId%7Clike%7C{{followRelationship.userId}}&conjunctions=AND,AND",
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
                       
        tests['Find follow_relationship By Filter With Offset0 And Limit10, All Fields, Like, And Conjunctions - Response code is 200 OK'] = is200Response;
        
	    if (!(environment.isIntegrationTest) && is200Response) {
	    	var responseJSON = JSON.parse(responseBody);
	        
	        tests['Response contains "followRelationship" property'] = responseJSON.hasOwnProperty('followRelationship');
	           
	        tests['Response contains "followRelationshipCount" property'] = responseJSON.hasOwnProperty('followRelationshipCount');
	                 
	        var followRelationship = responseJSON.followRelationship || {};
	        if (typeof followRelationship === 'object' && Array.isArray(followRelationship) && followRelationship.length > 0) {

			    // Check if 'articles' is an array and has length above 0
			  	check(followRelationship, {
			    '	Response should be an array': (arr) => Array.isArray(arr) && arr.length > 0,
			  	});
			
			  	// Check if 'followRelationship' has the same length as the response array length
			  	check(followRelationship, {
			    '	Response array length should match': (arr) => arr.length === followRelationship.length,
			  	});
	                   
				tests['followRelationship[0] has "followedId" property'] = followRelationship[0].hasOwnProperty('usersFollowedIdResponse');
				tests['followRelationship[0] has "userId" property'] = followRelationship[0].hasOwnProperty('usersUserIdResponse');
			}                  
	      }
      }
    });sleep(delay);
    
    postman[Request]({
      name:
        "Find follow_relationship By Filter With Offset0 And Limit10, All Fields, Not Like, Or Conjunctions",
      id: "2023-12-07-16-03-00-000941",
      method: "GET",
      options: { timeout: timeout },
      address:
        "{{APIURL}}/firmansyah/followRelationship?offset=0&limit=10&filter=followedId%7Cnlike%7C{{followRelationship.followedId}}%7C%7CuserId%7Cnlike%7C{{followRelationship.userId}}&conjunctions=OR,OR",
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
                       
        tests['Find follow_relationship By Filter With Offset0 And Limit10, All Fields, Not Like, Or Conjunctions - Response code is 200 OK'] = is200Response;
        
	    if (!(environment.isIntegrationTest) && is200Response) {
	    	var responseJSON = JSON.parse(responseBody);
	        
	        tests['Response contains "followRelationship" property'] = responseJSON.hasOwnProperty('followRelationship');
	           
	        tests['Response contains "followRelationshipCount" property'] = responseJSON.hasOwnProperty('followRelationshipCount');
	                 
	        var followRelationship = responseJSON.followRelationship || {};

			if (typeof followRelationship === 'object' && Array.isArray(followRelationship) && followRelationship.length > 0) {
			    // Check if 'articles' is an array and has length above 0
			  	check(followRelationship, {
			    '	Response should be an array': (arr) => Array.isArray(arr) && arr.length > 0,
			  	});
			
			  	// Check if 'followRelationship' has the same length as the response array length
			  	check(followRelationship, {
			    '	Response array length should match': (arr) => arr.length === followRelationship.length,
			  	});
	                   
				tests['followRelationship[0] has "followedId" property'] = followRelationship[0].hasOwnProperty('usersFollowedIdResponse');
				tests['followRelationship[0] has "userId" property'] = followRelationship[0].hasOwnProperty('usersUserIdResponse');
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
  
  //const LocalDateTimeNow = "k6/reports/follow_relationship.pt."+formattedDate+myVar+".html";
  return {
    [rpt] : htmlReport(data),
    stdout: textSummary(data, { indent: " ", enableColors: true }),
  };
}
