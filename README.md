# About the Project
This is Booking ticket project, based on  Spring MVC module which has the below controllers
1. Event - This controller performs `CRUD` operation for event entity.
2. User - Uses user entity to perform `CRUD` operations.
3. Ticket - This controller books tickets, finds tickets for given 
   1. user by id 
   2. event by id

# How to run the project
To run the project you require any linux based terminal and run the below script
```shell
run-local.sh
```

### Debugging the Application
This application uses remote debugger concept. You're required to attach remote debugger to your application on the port `8000` 
and run the below command

```shell
run-local.sh debug
```

# Hitting via postman
Postman collection for the repo is attached in the location
``postman/SpringMVC.postman_collection.json``

