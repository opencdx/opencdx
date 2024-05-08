### Swagger API

The Swagger API page can be launched from the admin page menu once the application admin dashboard is up.
Each service has its own menu item, clicking upon will launch the swagger API page in a separate tab.
Every API except the login API is authenticated by a JWT Bearer token. 

## Generating a JWT Bearer token
- Open the IAM service Swagger page
- Access the following endpoint
> /user/login
- Click on 'Try it out'
- Replace the request body with the following snippet.
> {
"userName": "admin@opencdx.org",
"password": "password"
}
- Click on Execute
- The token value in the response body represents the Bearer Token to be used for other APIs
- To generate token for specific users, please update the userName and password appropriately

## Authenticating APIs
- To Authenticate all APIs in a swagger page, Click on the Authorize button on the top right
- Copy the token value from the previous step and paste it here and click the Authorize button
- This will authorize all the APIs in the swagger page with the Bearer token.

## Using Postman
- Each service has a separate json file with the swagger api specification
- Import the file in postman to create all the APIs for a particular service.
- Make sure to include the bearer token in the authorization tab with the Auth type selected as **Bearer Token**