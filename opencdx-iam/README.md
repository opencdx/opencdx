# OpenCDX IAM
> Template Module for an OpenCDX Service. Used to quickly setup new services.

## Docker Image
opencdx/iam

## Docker Port
- RestAPI > Host 8080 : Container 8080
- Grpc > Host 9090 : Container 9090

## Links
_**Links are part of the build not available from GitHub.**_
- [All Test Results](build/reports/tests/test/index.html)
- [All Code Coverage](build/reports/jacoco/test/html/index.html)
- [Sonarlint Report](build/reports/sonarlint/sonarlintMain.html)

## Interfaces

## OpenCDx IAM Service

The OpenCDx-IAM service allows the creation, modification, deletion, retrieval 
of individual and list elements of the following entities in OpenCDx.

- Users
- UserProfiles
- Organization
- Workspace
- Provider

For a detailed look at the REST and gRPC APIs for all operations, please refer 
to the JAVA-Docs or the SWAGGER-Docs links provided above.

## User Management

OpenCDX creates a default system user for each service on initial run. 
These users are named after the service. The following lists the default users that
are setup by the system. It also creates a regular user that can be used to 
login to the system using the dashboard UI

| User              | System                 | Type                  |
|-------------------|------------------------|-----------------------|
| iam               | OpenCDX-IAM            | IAM_USER_TYPE_SYSTEM  |
| audit             | OpenCDX-Audit          | IAM_USER_TYPE_SYSTEM  |
| communications    | OpenCDX-Communications | IAM_USER_TYPE_SYSTEM  |
| connected-test    | OpenCDX-Connected-Test | IAM_USER_TYPE_SYSTEM  |
| media             | OpenCDX-Media          | IAM_USER_TYPE_SYSTEM  |
| protector         | OpenCDX-Protector      | IAM_USER_TYPE_SYSTEM  |
| routine           | OpenCDX-Routine        | IAM_USER_TYPE_SYSTEM  |
| tinkar            | OpenCDX-Tinkar         | IAM_USER_TYPE_SYSTEM  |
| classification    | OpenCDX-Classification | IAM_USER_TYPE_SYSTEM  |
| predictor         | OpenCDX-Predictor      | IAM_USER_TYPE_SYSTEM  |
| questionnaire     | OpenCDX-Questionnaire  | IAM_USER_TYPE_SYSTEM  |
| connected-lab     | OpenCDX-Connected-Lab  | IAM_USER_TYPE_SYSTEM  |
| shipping          | OpenCDX-Shipping       | IAM_USER_TYPE_SYSTEM  |
| admin@opencdx.org | -                      | IAM_USER_TYPE_REGULAR |

## User Types
- IAM_USER_TYPE_SYSTEM - This is a system user type that is used for a unique system user 
for every service in OpenCDx. They can be used for system to system communications.
- IAM_USER_TYPE_REGULAR - This is a user type that is created for any regular user that 
accessed the system through the dashboard UI or the form renderer apps.
- IAM_USER_TYPE_TRIAL - Trial user than can be setup in OpenCDx for demo purposes.
- IAM_USER_TYPE_UNSPECIFIED - This user type is returned by the system, when an user is 
setup with no type mentioned.

## User Status
- IAM_USER_STATUS_ACTIVE - Active user
- IAM_USER_STATUS_INACTIVE - Inactive user
- IAM_USER_STATUS_DELETED - User deleted form the system
- IAM_USER_STATUS_UNSPECIFIED - User status returned by the system when none is mentioned

## User Management
The following set of instructions can be used for managing users in OpenCDx. 

### Setup new User
To setup a new user in the system, use the following curl command.
```
curl --location 'https://localhost:8080/iam/user/signup' \
--header 'Content-Type: application/json' \
--data-raw '{ "type": "IAM_USER_TYPE_REGULAR",
                "firstName": "John",
                "lastName": "Doe",
                "systemName": "OpenCDX-IAM",
                "username": "jonh.doe@opencdx.org",
                "password": "password"
        }'
```

### Change password
To change password for an user, use the following curl command.
```
curl --location 'https://localhost:8080/iam/user/password' \
--header 'Content-Type: application/json' \
--data-raw '{ "id": "user-id",
              "oldPassword": "old_password",
              "newPassword": "new_password",
              "newPasswordConfirmation": "new_password"
        }'
```

### User Login
To Login to the system, use the following curl command.
```
curl --location 'https://localhost:8080/iam/user/login' \
--header 'Content-Type: application/json' \
--data-raw '{ "userName": "user-id",
              "password": "password"
        }'
```

### User Profile
To create a profile for the new user, use the following command. The data snippet provided below 
is minimal. For a list of all the options available for populating a user profile, please refer
to the swagger and java docs.
```
curl --location 'https://localhost:8080/iam/profile' \
--header 'Content-Type: application/json' \
--header 'Authorization: Bearer <<id_token>>' \
--data-raw '{
 "userprofile": {
  "userId": "65f070844be852310a8dd630",
  "nationalHealthId": "NHID-123",
  "fullName": {
   "firstName": "John",
   "lastName": "Doe"
  },
  "gender": "GENDER_MALE",
  "isActive": true,
  "email": [ 
   {
    "email": "jonh.doe@opencdx.org",
    "type": "EMAIL_TYPE_WORK"
   }
  ],
  "phone": [
   {
    "number": "703-123-1234",
    "type": "PHONE_TYPE_WORK"
   }
  ]
 }
}'
```
