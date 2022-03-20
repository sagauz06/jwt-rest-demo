# jwt-rest-demo
---jwt---  
curl -v -X POST http://127.0.0.1:8080/api/auth/signin -H 'Content-type:application/json' -d ' {"username": "harvey", "password": "123"}'  
  
---Spring Data REST---  
其他api(不包含user):  
http://127.0.0.1:8080/api/demo  
  
---Spring HATEOAS---  
get,post => http://127.0.0.1:8080/hateoas/accounts  
  
put => http://127.0.0.1:8080/hateoas/accounts/{id}  
json:  
{  
    "content": "測試"  
}