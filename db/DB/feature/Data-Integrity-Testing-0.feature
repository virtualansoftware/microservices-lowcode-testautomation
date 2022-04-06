Feature: Data-Integrity-Testing - Workflow

  Scenario: Load initial set of data
    Given provided all the feature level parameters from file

  @ddl @sql @employee
  Scenario: Create employee Table - database action
    Given as a user perform sql to define employee table action
    When execute DDL for the given sql details on the on employee
      | create table employees (emp_no int, birth_date date,first_name VARCHAR(50),last_name VARCHAR(50), gender VARCHAR(50),hire_date date) |

  @sql-insert @sql @employee
  Scenario: Insert Employee info - database action
    Given as a user perform sql to insert employee record action
    When execute INSERT for the given sql details on the on employee
      | insert into employees (emp_no,birth_date,first_name,last_name, gender,hire_date) values  (1, DATE '1983-11-17','Danny', 'Ray', 'Male',DATE  '2012-03-24') |

  @sql-select @sql-verfiy @sql @store_sql_response @employee
  Scenario: Verify record - database action
    Given as a user perform sql verify record action
    When read details on the given query on employee
      | select emp_no, to_char(birth_date, 'YYYY-mm-DD') birth_date ,first_name,last_name, gender,to_char(hire_date , 'YYYY-mm-DD') hire_date from employees where emp_no = 1 |
    Then validate information on the given details on employee
      | EMP_NO,BIRTH_DATE,FIRST_NAME,LAST_NAME,GENDER,HIRE_DATE |
      | i~1,1983-11-17,Danny,Ray,Male,2012-03-24                |
    And store empId as key and query's [0].EMP_NO as value

  @sql-delete @sql @employee
  Scenario: Delete record - database action
    Given as a user perform sql verify record action
    When execute DELETE for the given sql details on the on employee
      | delete from employees where emp_no = [empId] |
