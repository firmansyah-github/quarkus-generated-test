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
      name: "Create articles for create tag_relationship",
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

        tests["Response code is 201 OK - Create articles for create tag_relationship"] = is201Response;

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
          		pm.globals.set('tag_relationship.articles', articles.id);
          }
           
          tests['Global variable "tag_relationship.articles" has been set'] = pm.globals.get('tag_relationship.articles') === articles.id;
				      
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
      name: "Create tags for create tag_relationship",
      id: "2024-02-23-06-45-22-000578",
      method: "POST",
      options: { timeout: timeout },
      address: "{{APIURL}}/firmansyah/tags",
      data:
        '{"tags":{"id":"{{UNIQUE}}","name":"name"}}',
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

        tests["Response code is 201 OK - Create tags for create tag_relationship"] = is201Response;

        if (!environment.isIntegrationTest && is201Response) {
          var responseJSON = JSON.parse(responseBody);

          tests[
            'Response contains "tags" property'
          ] = responseJSON.hasOwnProperty("tags");

          var tags = responseJSON.tags || {};
		  tests['tags has "id" property'] = tags.hasOwnProperty("id"); 
				
	      if(tests['tags has "id" property']){
          		pm.globals.set('tag_relationship.tags', tags.id);
          }
           
          tests['Global variable "tag_relationship.tags" has been set'] = pm.globals.get('tag_relationship.tags') === tags.id;
				      
		  if(tests['tags has "id" property']){
                pm.globals.set('tags.id', tags.id);
          }
          
          tests['Global variable "tags.id" has been set'] = pm.globals.get('tags.id') === tags.id;
		  tests['tags has "name" property'] = tags.hasOwnProperty("name"); 
				
        }
       }
      });sleep(delay);
        	

	postman[Request]({
      name: "Create tag_relationship for create tag_relationship",
      id: "2024-02-23-06-45-22-000578",
      method: "POST",
      options: { timeout: timeout },
      address: "{{APIURL}}/firmansyah/tagRelationship",
      data:
        '{"tagRelationship":{"articleId":"{{tag_relationship.articles}}","tagId":"{{tag_relationship.tags}}"}}',
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

        tests["Response code is 201 OK - Create tag_relationship for create tag_relationship"] = is201Response;

        if (!environment.isIntegrationTest && is201Response) {
          var responseJSON = JSON.parse(responseBody);

          tests[
            'Response contains "tagRelationship" property'
          ] = responseJSON.hasOwnProperty("tagRelationship");

          var tagRelationship = responseJSON.tagRelationship || {};
		  tests['tagRelationship has "articleId" property'] = tagRelationship.hasOwnProperty('articlesArticleIdResponse');
		  
		  if(tests['tagRelationship has "articleId" property']){
                pm.globals.set('tagRelationship.articleId', tagRelationship.articlesArticleIdResponse.id);
           }
                  
          tests['Global variable "tagRelationship.articleId" has been set'] = pm.globals.get('tagRelationship.articleId') === tagRelationship.articlesArticleIdResponse.id;
		  tests['tagRelationship has "tagId" property'] = tagRelationship.hasOwnProperty('tagsTagIdResponse');
		  
		  if(tests['tagRelationship has "tagId" property']){
                pm.globals.set('tagRelationship.tagId', tagRelationship.tagsTagIdResponse.id);
           }
                  
          tests['Global variable "tagRelationship.tagId" has been set'] = pm.globals.get('tagRelationship.tagId') === tagRelationship.tagsTagIdResponse.id;
        }
       }
      });sleep(delay);
        	
	
	postman[Request]({
      name: "Find tagRelationship By Primary Key",
      id: "2024-02-23-06-45-22-000578",
      method: "GET",
      options: { timeout: timeout },
      address: "{{APIURL}}/firmansyah/tagRelationship/find?articleId={{tagRelationship.articleId}}&tagId={{tagRelationship.tagId}}",
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
                       
        tests['Response code is 200 OK - Find tagRelationship By Primary Key'] = is200Response;
        
	    if (!(environment.isIntegrationTest) && is200Response) {
	    	var responseJSON = JSON.parse(responseBody);
	                 
	        tests['Response contains "tagRelationship" property'] = responseJSON.hasOwnProperty('tagRelationship');
	        
	        var tagRelationship = responseJSON.tagRelationship || {};
	       
			tests['tagRelationship has "articleId" property'] = tagRelationship.hasOwnProperty('articlesArticleIdResponse');
				
		    if(tests['tagRelationship has "articleId" property']){
            	pm.globals.set('tagRelationship.articleId', tagRelationship.articlesArticleIdResponse.id);
            }
            
            tests['Global variable "tagRelationship.articleId" has been set'] = pm.globals.get('tagRelationship.articleId') === tagRelationship.articlesArticleIdResponse.id;
			
			tests['tagRelationship has "tagId" property'] = tagRelationship.hasOwnProperty('tagsTagIdResponse');
				
		    if(tests['tagRelationship has "tagId" property']){
            	pm.globals.set('tagRelationship.tagId', tagRelationship.tagsTagIdResponse.id);
            }
            
            tests['Global variable "tagRelationship.tagId" has been set'] = pm.globals.get('tagRelationship.tagId') === tagRelationship.tagsTagIdResponse.id;
			
	    }
      }
    });sleep(delay);
    
    
    postman[Request]({
      name:
        "Find tag_relationship By Filter With Offset0 And Limit10, All Fields, Equals, Or, Sort Desc",
      id: "2024-02-23-06-45-22-000578",
      method: "GET",
      options: { timeout: timeout },
      address:
        "{{APIURL}}/firmansyah/tagRelationship?offset=0&limit=10&filter=articleId%7Ceq%7C{{tagRelationship.articleId}}%7C%7CtagId%7Ceq%7C{{tagRelationship.tagId}}&conjunctions=OR,OR&sort=-articleId,-tagId",
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
                       
        tests['Find tag_relationship By Filter With Offset0 And Limit10, All Fields, Equals, Or, Sort Desc - Response code is 200 OK'] = is200Response;
        
	    if (!(environment.isIntegrationTest) && is200Response) {
	    	var responseJSON = JSON.parse(responseBody);
	        
	        tests['Response contains "tagRelationship" property'] = responseJSON.hasOwnProperty('tagRelationship');
	           
	        tests['Response contains "tagRelationshipCount" property'] = responseJSON.hasOwnProperty('tagRelationshipCount');
	                 
	        var tagRelationship = responseJSON.tagRelationship || {};
	        if (typeof tagRelationship === 'object' && Array.isArray(tagRelationship) && tagRelationship.length > 0) {

			    // Check if 'articles' is an array and has length above 0
			  	check(tagRelationship, {
			    '	Response should be an array': (arr) => Array.isArray(arr) && arr.length > 0,
			  	});
			
			  	// Check if 'tagRelationship' has the same length as the response array length
			  	check(tagRelationship, {
			    '	Response array length should match': (arr) => arr.length === tagRelationship.length,
			  	});
	                   
				tests['tagRelationship[0] has "articleId" property'] = tagRelationship[0].hasOwnProperty('articlesArticleIdResponse');
				tests['tagRelationship[0] has "tagId" property'] = tagRelationship[0].hasOwnProperty('tagsTagIdResponse');
			}                  
	      }
      }
    });sleep(delay);
    
    postman[Request]({
      name:
        "Find tag_relationship By Filter With Offset0 And Limit10, All Fields, Equals, Or, Sort Asc",
      id: "2024-02-23-06-45-22-000578",
      method: "GET",
      options: { timeout: timeout },
      address:
        "{{APIURL}}/firmansyah/tagRelationship?offset=0&limit=10&filter=articleId%7Ceq%7C{{tagRelationship.articleId}}%7C%7CtagId%7Ceq%7C{{tagRelationship.tagId}}&conjunctions=OR,OR&sort=articleId,tagId",
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
                       
        tests['Find tag_relationship By Filter With Offset0 And Limit10, All Fields, Equals, Or, Sort Asc - Response code is 200 OK'] = is200Response;
        
	    if (!(environment.isIntegrationTest) && is200Response) {
	    	var responseJSON = JSON.parse(responseBody);
	        
	        tests['Response contains "tagRelationship" property'] = responseJSON.hasOwnProperty('tagRelationship');
	           
	        tests['Response contains "tagRelationshipCount" property'] = responseJSON.hasOwnProperty('tagRelationshipCount');
	                 
	        var tagRelationship = responseJSON.tagRelationship || {};
	        if (typeof tagRelationship === 'object' && Array.isArray(tagRelationship) && tagRelationship.length > 0) {

			    // Check if 'articles' is an array and has length above 0
			  	check(tagRelationship, {
			    '	Response should be an array': (arr) => Array.isArray(arr) && arr.length > 0,
			  	});
			
			  	// Check if 'tagRelationship' has the same length as the response array length
			  	check(tagRelationship, {
			    '	Response array length should match': (arr) => arr.length === tagRelationship.length,
			  	});
	                   
				tests['tagRelationship[0] has "articleId" property'] = tagRelationship[0].hasOwnProperty('articlesArticleIdResponse');
				tests['tagRelationship[0] has "tagId" property'] = tagRelationship[0].hasOwnProperty('tagsTagIdResponse');
			}                
	      }
      }
    });sleep(delay);
    
    postman[Request]({
      name:
        "Find tag_relationship By Filter With Offset0 And Limit10, All Fields, Equals, Or Conjunctions",
      id: "2024-02-23-06-45-22-000578",
      method: "GET",
      options: { timeout: timeout },
      address:
        "{{APIURL}}/firmansyah/tagRelationship?offset=0&limit=10&filter=articleId%7Ceq%7C{{tagRelationship.articleId}}%7C%7CtagId%7Ceq%7C{{tagRelationship.tagId}}&conjunctions=OR,OR",
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
                       
        tests['Find tag_relationship By Filter With Offset0 And Limit10, All Fields, Equals, Or Conjunctions - Response code is 200 OK'] = is200Response;
        
	    if (!(environment.isIntegrationTest) && is200Response) {
	    	var responseJSON = JSON.parse(responseBody);
	        
	        tests['Response contains "tagRelationship" property'] = responseJSON.hasOwnProperty('tagRelationship');
	           
	        tests['Response contains "tagRelationshipCount" property'] = responseJSON.hasOwnProperty('tagRelationshipCount');
	                 
	        var tagRelationship = responseJSON.tagRelationship || {};
	        if (typeof tagRelationship === 'object' && Array.isArray(tagRelationship) && tagRelationship.length > 0) {

			    // Check if 'articles' is an array and has length above 0
			  	check(tagRelationship, {
			    '	Response should be an array': (arr) => Array.isArray(arr) && arr.length > 0,
			  	});
			
			  	// Check if 'tagRelationship' has the same length as the response array length
			  	check(tagRelationship, {
			    '	Response array length should match': (arr) => arr.length === tagRelationship.length,
			  	});
	                   
				tests['tagRelationship[0] has "articleId" property'] = tagRelationship[0].hasOwnProperty('articlesArticleIdResponse');
				tests['tagRelationship[0] has "tagId" property'] = tagRelationship[0].hasOwnProperty('tagsTagIdResponse');
			}                
	      }
      }
    });sleep(delay);
    
    
    postman[Request]({
      name:
        "Find tag_relationship By Filter With Offset0 And Limit10, All Fields, Equals, And Conjunctions",
      id: "2024-02-23-06-45-22-000578",
      method: "GET",
      options: { timeout: timeout },
      address:
        "{{APIURL}}/firmansyah/tagRelationship?offset=0&limit=10&filter=articleId%7Ceq%7C{{tagRelationship.articleId}}%7C%7CtagId%7Ceq%7C{{tagRelationship.tagId}}&conjunctions=AND,AND",
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
                       
        tests['Find tag_relationship By Filter With Offset0 And Limit10, All Fields, Equals, And Conjunctions - Response code is 200 OK'] = is200Response;
        
	    if (!(environment.isIntegrationTest) && is200Response) {
	    	var responseJSON = JSON.parse(responseBody);
	        
	        tests['Response contains "tagRelationship" property'] = responseJSON.hasOwnProperty('tagRelationship');
	           
	        tests['Response contains "tagRelationshipCount" property'] = responseJSON.hasOwnProperty('tagRelationshipCount');
	                 
	        var tagRelationship = responseJSON.tagRelationship || {};
	        if (typeof tagRelationship === 'object' && Array.isArray(tagRelationship) && tagRelationship.length > 0) {

			    // Check if 'articles' is an array and has length above 0
			  	check(tagRelationship, {
			    '	Response should be an array': (arr) => Array.isArray(arr) && arr.length > 0,
			  	});
			
			  	// Check if 'tagRelationship' has the same length as the response array length
			  	check(tagRelationship, {
			    '	Response array length should match': (arr) => arr.length === tagRelationship.length,
			  	});
	                   
				tests['tagRelationship[0] has "articleId" property'] = tagRelationship[0].hasOwnProperty('articlesArticleIdResponse');
				tests['tagRelationship[0] has "tagId" property'] = tagRelationship[0].hasOwnProperty('tagsTagIdResponse');
			}                  
	      }
      }
    });sleep(delay);
    
    postman[Request]({
      name:
        "Find tag_relationship By Filter With Offset0 And Limit10, All Fields, Not Equals, Or Conjunctions",
      id: "2024-02-23-06-45-22-000578",
      method: "GET",
      options: { timeout: timeout },
      address:
        "{{APIURL}}/firmansyah/tagRelationship?offset=0&limit=10&filter=articleId%7Cneq%7C{{tagRelationship.articleId}}%7C%7CtagId%7Cneq%7C{{tagRelationship.tagId}}&conjunctions=OR,OR",
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
                       
        tests['Find tag_relationship By Filter With Offset0 And Limit10, All Fields, Not Equals, Or Conjunctions - Response code is 200 OK'] = is200Response;
        
	    if (!(environment.isIntegrationTest) && is200Response) {
	    	var responseJSON = JSON.parse(responseBody);
	        
	        tests['Response contains "tagRelationship" property'] = responseJSON.hasOwnProperty('tagRelationship');
	           
	        tests['Response contains "tagRelationshipCount" property'] = responseJSON.hasOwnProperty('tagRelationshipCount');
	                 
	        var tagRelationship = responseJSON.tagRelationship || {};
            
            if (typeof tagRelationship === 'object' && Array.isArray(tagRelationship) && tagRelationship.length > 0) {
			    // Check if 'articles' is an array and has length above 0
			  	check(tagRelationship, {
			    '	Response should be an array': (arr) => Array.isArray(arr) && arr.length > 0,
			  	});
			
			  	// Check if 'tagRelationship' has the same length as the response array length
			  	check(tagRelationship, {
			    '	Response array length should match': (arr) => arr.length === tagRelationship.length,
			  	});
	                   
				tests['tagRelationship[0] has "articleId" property'] = tagRelationship[0].hasOwnProperty('articlesArticleIdResponse');
				tests['tagRelationship[0] has "tagId" property'] = tagRelationship[0].hasOwnProperty('tagsTagIdResponse');
			}                 
	      }
      }
    });sleep(delay);
    
    postman[Request]({
      name:
        "Find tag_relationship By Filter With Offset0 And Limit10, All Fields, Like, And Conjunctions",
      id: "2024-02-23-06-45-22-000578",
      method: "GET",
      options: { timeout: timeout },
      address:
        "{{APIURL}}/firmansyah/tagRelationship?offset=0&limit=10&filter=articleId%7Clike%7C{{tagRelationship.articleId}}%7C%7CtagId%7Clike%7C{{tagRelationship.tagId}}&conjunctions=AND,AND",
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
                       
        tests['Find tag_relationship By Filter With Offset0 And Limit10, All Fields, Like, And Conjunctions - Response code is 200 OK'] = is200Response;
        
	    if (!(environment.isIntegrationTest) && is200Response) {
	    	var responseJSON = JSON.parse(responseBody);
	        
	        tests['Response contains "tagRelationship" property'] = responseJSON.hasOwnProperty('tagRelationship');
	           
	        tests['Response contains "tagRelationshipCount" property'] = responseJSON.hasOwnProperty('tagRelationshipCount');
	                 
	        var tagRelationship = responseJSON.tagRelationship || {};
	        if (typeof tagRelationship === 'object' && Array.isArray(tagRelationship) && tagRelationship.length > 0) {

			    // Check if 'articles' is an array and has length above 0
			  	check(tagRelationship, {
			    '	Response should be an array': (arr) => Array.isArray(arr) && arr.length > 0,
			  	});
			
			  	// Check if 'tagRelationship' has the same length as the response array length
			  	check(tagRelationship, {
			    '	Response array length should match': (arr) => arr.length === tagRelationship.length,
			  	});
	                   
				tests['tagRelationship[0] has "articleId" property'] = tagRelationship[0].hasOwnProperty('articlesArticleIdResponse');
				tests['tagRelationship[0] has "tagId" property'] = tagRelationship[0].hasOwnProperty('tagsTagIdResponse');
			}                  
	      }
      }
    });sleep(delay);
    
    postman[Request]({
      name:
        "Find tag_relationship By Filter With Offset0 And Limit10, All Fields, Not Like, Or Conjunctions",
      id: "2024-02-23-06-45-22-000578",
      method: "GET",
      options: { timeout: timeout },
      address:
        "{{APIURL}}/firmansyah/tagRelationship?offset=0&limit=10&filter=articleId%7Cnlike%7C{{tagRelationship.articleId}}%7C%7CtagId%7Cnlike%7C{{tagRelationship.tagId}}&conjunctions=OR,OR",
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
                       
        tests['Find tag_relationship By Filter With Offset0 And Limit10, All Fields, Not Like, Or Conjunctions - Response code is 200 OK'] = is200Response;
        
	    if (!(environment.isIntegrationTest) && is200Response) {
	    	var responseJSON = JSON.parse(responseBody);
	        
	        tests['Response contains "tagRelationship" property'] = responseJSON.hasOwnProperty('tagRelationship');
	           
	        tests['Response contains "tagRelationshipCount" property'] = responseJSON.hasOwnProperty('tagRelationshipCount');
	                 
	        var tagRelationship = responseJSON.tagRelationship || {};

			if (typeof tagRelationship === 'object' && Array.isArray(tagRelationship) && tagRelationship.length > 0) {
			    // Check if 'articles' is an array and has length above 0
			  	check(tagRelationship, {
			    '	Response should be an array': (arr) => Array.isArray(arr) && arr.length > 0,
			  	});
			
			  	// Check if 'tagRelationship' has the same length as the response array length
			  	check(tagRelationship, {
			    '	Response array length should match': (arr) => arr.length === tagRelationship.length,
			  	});
	                   
				tests['tagRelationship[0] has "articleId" property'] = tagRelationship[0].hasOwnProperty('articlesArticleIdResponse');
				tests['tagRelationship[0] has "tagId" property'] = tagRelationship[0].hasOwnProperty('tagsTagIdResponse');
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
  
  //const LocalDateTimeNow = "k6/reports/tag_relationship.pt."+formattedDate+myVar+".html";
  return {
    [rpt] : htmlReport(data),
    stdout: textSummary(data, { indent: " ", enableColors: true }),
  };
}
