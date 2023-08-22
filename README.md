# Online Learning Platform :book: :books:

This project is focused on being a platform where users interested in different courses can register, 
enter and select their own learning path through videos, documentation and exams to obtain their certification.

- This App is responsible of creating a new User and enroll to some courses.

```http
  curl --location --request POST 'localhost:8080/registration/user' \
--header 'Content-Type: application/json' \
--data-raw '{
    "name": "Name",
    "lastName": "LastName",
    "email": "email@example.com",
    "password": "password",
    "passwordCode": 12345
}'
```

## What should you find?
- [x] :cloud: **API Design:** REST API with Model/View/Controller implementation and OpenAPI specification in > src -> main -> java/com/example/onlineLearning -> openAPI  and > v3 file. 

## How was it builded?
The application was developed in SpringBoot, PostgreSQL and Spring Security



