# Java Spring with MongoDB Project

This project is a Java Spring application with MongoDB database, focusing on managing information about students and books. It establishes a relationship between students and books for efficient tracking and management.

## Usage

To use this project, you need to have Docker installed on your system. Follow the steps below to get started:

1. Clone the repository using the following command:
   ```sh
   git clone https://github.com/rokas058/Java-Spring-MongoDB.git

2. Navigate to the project directory in your terminal.

3. Run the following command to start Docker Compose:
  docker-compose up

4. Once Docker Compose is running, MongoDB Express will be available at [http://localhost:8081](http://localhost:8081). You need to login with the username "admin" and password "pass" to access the MongoDB admin interface.

5. After starting the project, you can access the Swagger UI at [http://localhost:8080/swagger-ui/index.html#/spreadsheet-controller/getSheets](http://localhost:8080/swagger-ui/index.html#/spreadsheet-controller/getSheets). This interface provides all the HTTP methods available in the project.

6. To begin, you need to create a student using the appropriate endpoint. After creating a student, you can assign books to the student.

## Example

1. To begin, you need to create a student using the POST method at [http://localhost:8080/students](http://localhost:8080/students) and enter the following fields in the request body:
    ```json
   {
     "first_name": "example",
     "last_name": "example",
     "email": "example@example.com"
   }


3. After creating a student, you can retrieve all students using the GET method at [http://localhost:8080/students](http://localhost:8080/students).

4. Select a student and copy their ID. Then you can create a book using the POST method at [http://localhost:8080/books](http://localhost:8080/books) and enter the following fields in the request body:
   ```json
   {
     "authorFullName": "example example",
     "bookName": "example",
     "givenDate": "2024-03-04",
     "returnDate": "2024-03-05",
     "studentId": "example"
   }

Make sure to replace "example" with the actual values. Paste the copied student ID into the "studentId" field.

9. After creating a student and a book, you can go to [http://localhost:8081](http://localhost:8081) and click on "mydatabase" to see two collections: "students" and "books", where the created data is stored.

Enjoy exploring and using the project! If you have any questions or feedback, feel free to reach out. 
