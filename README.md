# springBootAuthentication
Data Flow
1. User Login (logInUser Endpoint)

Client Request:

The client sends a POST request to /api/users/login with email and password as request parameters.
Controller (UserController):

Receives the request and delegates the operation to UserService.
Service Layer (UserService):

Calls getUserByEmail(email, password) method on UserRepository.
Retrieves the user from the database based on the email.
Checks if the retrieved User object is null (user not found) or verifies the password using passwordEncoder.
If successful, resets the loginAttempt count and returns the User object.
If authentication fails (wrong password), increments the loginAttempt count and checks if it exceeds the limit (e.g., 3 attempts).
Repository (UserRepository):

Executes the query to fetch the user by email from the database (findByEmail(email)).
Response:

UserService returns a User object if authentication is successful.
UserController sends an HTTP 200 OK response with the User object.
If authentication fails or an exception occurs, UserService throws an exception.
Error Handling (try-catch in UserController):

UserController catches exceptions thrown by UserService.
Returns an HTTP 500 Internal Server Error response with a generic error message.


2. Password Reset (resetPassword Endpoint)
Client Request:

Sends a PATCH request to /api/users/reset-password with fatherName, email, and password as request parameters.
Controller (UserController):

Receives the request and delegates the operation to UserService.
Service Layer (UserService):

Calls processuserAction(fatherName, email, password) method on UserRepository.
Retrieves the user from the database based on the email.
Validates fatherName against the retrieved user.
If valid, updates the user's password and saves the user object to the database.
If fatherName validation fails, logs an error message or throws an exception.
Repository (UserRepository):

Executes the query to fetch the user by email from the database (findByEmail(email)).
Response:

UserService returns a User object with updated password if successful.
UserController sends an HTTP 200 OK response with the User object.
If validation fails or an exception occurs, UserService throws an exception.
Error Handling (try-catch in UserController):

UserController catches exceptions thrown by UserService.
Returns an HTTP 500 Internal Server Error response with a generic error message.


3. Error Handling
Exception Propagation:

Exceptions are thrown from UserService methods (getUserByEmail, processuserAction) if errors occur during user authentication or password reset.
Controller (UserController):

Successful operations return HTTP 200 OK with relevant data (e.g., User object).
Error responses return appropriate HTTP status codes (e.g., 500 Internal Server Error) with error messages.