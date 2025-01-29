Feature: OAuth Test

Background:

* def baseUrl = 'http://localhost:8080/'
* def client_id = 'izertis-auth-client-example'
* def client_secret = 'secret'
* def username = 'user'
* def password = 'password'
* def redirect_uri = 'http://localhost:8082/swagger-ui/oauth2-redirect.html'

* url baseUrl
* configure followRedirects = false


Scenario: Login, Get Code and Convert To Token

Given path '/oauth2/authorize'
And param response_type = 'code'
And param client_id = client_id
And param redirect_uri = redirect_uri
# And param scope = ['read', 'write', 'admin']
And param scope = 'read write admin profile'
When method get

Given path 'login'
And form field username = username
And form field password = password
When method post
Then status 302
And match responseHeaders['Location'] == '#notnull'
* def locationHeader = responseHeaders['Location'][0]
* print 'Location header:', locationHeader

Given url locationHeader
When method get
Then status 302
And match responseHeaders['Location'] == '#notnull'
* def locationHeader = responseHeaders['Location'][0]
* def extractCode = (url) => url.match(/[?&]code=([^&]+)/)[1]
* def code = call extractCode locationHeader
* print 'Code:', code

Given url baseUrl
And path '/oauth2/token'
And form field grant_type = 'authorization_code'
And form field code = code
And form field client_id = client_id
And form field client_secret = client_secret
And form field redirect_uri = redirect_uri
When method post

Scenario: Fetch Token from Code

Given path '/oauth2/token'
And form field grant_type = 'authorization_code'
And form field code = 'I5upmv9Yc1YNYBQkC8svUCE_3l_ebEpqe6cmc_Xnx_9TFPeuZG4JaZ2xTNQwOfOD2b4ZRUZ6QIvpFq6dLcI_lUuwJhBVoPbQOYZ68K2QBlJEc3yylIZ0_XPzDOyUZRrP'
And form field client_id = 'izertis-auth-client-example'
And form field client_secret = 'secret'
And form field redirect_uri = 'http://localhost:8082/swagger-ui/oauth2-redirect.html'
When method post



