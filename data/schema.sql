CREATE SCHEMA IF NOT EXISTS departmentdb;
USE departmentdb;
CREATE TABLE IF NOT EXISTS department (
    department_id INT PRIMARY KEY AUTO_INCREMENT,
    department_name VARCHAR(50) NOT NULL UNIQUE,
    head_id INT,
    FOREIGN KEY (head_id) REFERENCES employeedb.employee(employee_id)
);


CREATE SCHEMA IF NOT EXISTS employeedb;
USE employeedb;
CREATE TABLE IF NOT EXISTS employee (
    employee_id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    job_title VARCHAR(50) NOT NULL,
    department_id INT NOT NULL,
    FOREIGN KEY (department_id) REFERENCES departmentdb.department(department_id)
);


CREATE SCHEMA IF NOT EXISTS salarydb;
USE salarydb;

CREATE TABLE IF NOT EXISTS salary (
    salary_id INT PRIMARY KEY AUTO_INCREMENT,
    employee_id INT NOT NULL,
    base_salary DECIMAL(12,2) NOT NULL,
    FOREIGN KEY (employee_id) REFERENCES employeedb.employee(employee_id)
);


