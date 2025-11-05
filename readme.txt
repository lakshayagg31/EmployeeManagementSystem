=================================================
Microservices Project - EmployeeManagementSystem
=================================================
This EmployeeManagementSystem project implements a microservices architecture with 
modular services for employee, department, salary management, and authentication, 
integrated via an API Gateway and secured with manual authentication to provide a 
scalable and maintainable enterprise solution.

Team Members:
- Lakshay Aggarwal 22CSU272
- Ribhav Bhalla 22CSU261

-----------------------------------
Working Services and Functionality
-----------------------------------

- In primary services (Employee, Department, Salary Service): 
    ->All CRUD and detail endpoints working
    ->Manual Authentication working
    ->Pagination endpoints working

- AuthService: Signup and authentication working.

- API Gateway and Service Discovery: 
    ->All routing working
    ->Manual Authentication working

- Config Server: Working with Native as well as with Git repo.


----------------------
Services and Ownership
----------------------

PORT numbers:-
    Api Gateway - 9005
    Service Discovery - 8761
    Configuration Server - 8888
    Employee Service - 9002
    Department Service - 9003
    Salary Service - 9004
    Auth Service - 9006


- Employee Service:
    - Coded by: Lakshay Aggarwal 22CSU272
    - Major endpoints:
        - GET    /employees/
        - POST   /employees/employee
        - GET    /employees/employee/{employeeId}
        - GET    /employees/employee?email={email}
        - DELETE /employees/employee/{email}

- Department Service:
    - Coded by: Lakshay Aggarwal 22CSU272
    - Major endpoints:
        - GET    /departments/
        - POST   /departments/department
        - GET    /departments/department/{departmentId}
        - GET    /departments/department?departmentname={name}

- Salary Service:
    - Coded by: Ribhav Bhalla 22CSU261
    - Major endpoints:
        - GET    /salaries/
        - POST   /salaries/salary
        - GET    /salaries/salary/{employeeId}
        - DELETE /salaries/salary/{employeeId}

- Auth Service (for authentication):
    - Coded by: Lakshay Aggarwal 22CSU272
    - Endpoints:
        - POST   /auth/signup
        - POST   /auth/authenticate

- Common Code (API Gateway, Service Discovery, Config Server):
    API Gateway
        - Coded by: Lakshay Aggarwal 22CSU272
    Service Discovery 
        - Coded by: Ribhav Bhalla 22CSU261
    Config Server
        - Coded by: Lakshay Aggarwal 22CSU272 & Ribhav Bhalla 22CSU261


--------------------------------------------------------
Example Calls for Each Endpoint and Pagination Endpoints
--------------------------------------------------------

Employee Service:

1. Add Employee (POST):
    URL: http://localhost:9005/employees/employee
    Body:
    {
      "name": "Test User",
      "email": "testuser@example.com",
      "jobtitle": "Developer",
      "departmentid": 2
    }

2. Get Employee or Employee by Id (GET):
    URL: http://localhost:9005/employees/
    URL: http://localhost:9005/employees/employee/1


Department Service:

1. Add Department (POST):
    URL: http://localhost:9005/departments/department
    Body:
    {
        "departmentname": "HR",
        "headid": 16
    }

2. Get Department or Department by Id (GET):
    URL: http://localhost:9005/departments/
    URL: http://localhost:9005/departments/department/{departmentId}

Salary Service:

1. Add Salary (POST):
    URL: http://localhost:9005/salaries/salary
    Body:
    {
        "employeeid": 31,
        "basesalary": 75000
    }

2. Get Salary by Id (GET):
    URL: http://localhost:9005/salaries/

Auth Service:
    
1. Signup (POST):
    URL: http://localhost:9005/auth/signup
    Body:
        {
            "email": "user@example.com",
            "password": "Password123!",
            "name": "John Doe"
        }

2. Authenticate (POST):
    URL: http://localhost:9005/auth/authenticate
    Body:
        {
            "email": "user@example.com",
            "password": "Password123!"
        }

Pagination Endpoints

->DepartmentService

1. Paginated Endpoint
Get 10 departments on page 0 (first page):
GET http://localhost:9005/departments/nextpage?page=0&size=10

2. Custom Page Size
Get 5 departments from page 2 (third page):
GET http://localhost:9005/departments/nextpage?page=2&size=5

3. Range Endpoint
Get departments 10 through 19:
GET http://localhost:9005/departments/nextpage/range?start=10&end=19


->EmployeeService

1. Get page 0 with default 10:
GET http://localhost:9005/employees/nextpage?page=0

2. Get page 2 with 5 employees per page:
GET http://localhost:9005/employees/nextpage?page=2&size=5

3. Get employees from row 10 to 19:
GET http://localhost:9005/employees/nextpage/range?start=10&end=19


->SalaryService

1. Default 10 salaries on page 0:
GET http://localhost:9005/salaries/nextpage?page=0

2. Custom size (e.g., 5 records) on page 2:
GET http://localhost:9005/salaries/nextpage?page=2&size=5

3. Range of salaries:
GET http://localhost:9005/salaries/nextpage/range?start=10&end=19

-----------------------------------
Note:
Database Schema is there in the data folder inside the zip.
-----------------------------------
