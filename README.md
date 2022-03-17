# jwt-rest-demo

取得jwt:
curl -v -X POST http://127.0.0.1:8080/api/auth/signin -H 'Content-type:application/json' -d '{"username": "harvey", "password": "123"}'

其他api(不包含user):
http://127.0.0.1:8080/api/demo
