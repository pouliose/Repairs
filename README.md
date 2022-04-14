<div id="top"></div>

<div style="text-align:center">

<h1>Team1</h1>
</div>
<h3>Members</h3>
<p><ul><li>Alexandros Atsalakis</li>
<li>Stavroula Kampylh</li> 
<li>Evangelos Poulios</li></ul>
<br>

## About
<p>A REST API that will support basic needs for a renovation contractor agency. Some of its needs include: </p>
<ul><li>the employees - managers of its platform to have access to information concerning customers and
repairs</li>
<li>ability for customers to oversee the progress of repair/renovation
work on their property.</li></ul>

## How to use it
<p>A. Select one of the following options:<p>
<ol> 
<li>Clone it </li>
<li>Download it as ZIP file</li>
</ol>

<p>B. Manually create a database with name "Team1Technico" 
docker compose</p>
<p>C. Run the application from your preferable IDE</p>

<p>D. Use the application and interact with the REST API either by:</p>
<ol><li>importing to the Postman the collection of the requests</li>
<li>using the Swagger interface provided in the "http://localhost:8080/swagger-ui/index.html" </li></ol>

<p>E. In each case you have firstly to log in with valid credentials. On application start 
up 2 instances of users are being created: 
user1(username: john, password: 1234, role: ROLE_ADMIN), user2(username: jake, password:1234, role: ROLE_MODERATOR)</p>

<p>Postman</p>
<ul><li>The first time when you have to log in, with postman you need to request: localhost:8080/api/login, by providing query params: username: usernameValueOfYourUser, password: passwordValueOfYourUser </li>
<li>Then, you should include in each request a header with (key,value) : ("Authorization", "Bearer " + the accessToken that you got from the client)</li></ul> 

<p>Swagger UI</p>
<ul><li>The first time when you have to log in, with Swagger UI you have to send the request (http://localhost:8080/api/login?username=usernameValueOfYourUser&password=passwordValueOfYourUser) through browser</li>
<li>Afterwards, you have to install a browser extension like <a href="https://chrome.google.com/webstore/detail/modheader/idgpnmonknjnojddfkpgkljpfnnfcklj?hl=en">ModHeader</a> for Chrome. 
The extension enables the option for custom request and response http headers. And you follow the same procedure (like Postman) 
where, you should include in each request header a header with (key,value) : ("Authorization", "Bearer " + the accessToken that you got from the client)</li></ul>

<p style="text-align: right">(<a href="#top">back to top</a>)</p>


## Technologies
<ul><li>Java 17</li>
<li>Spring Framework</li>
<li>MS SQL Server</li>
<li>JSON Web Token</li>
<li>Swagger</li></ul>


<p style="text-align: right">(<a href="#top">back to top</a>)</p>

## Contributing

If you have a suggestion that would make this better, please fork the repo and create a pull request. You can also simply open an issue with the tag "enhancement".
Don't forget to give the project a star! Thanks again!

1. Fork the Project
2. Create your Feature Branch (`git checkout -b feature/AmazingFeature`)
3. Commit your Changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the Branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

<p style="text-align: right">(<a href="#top">back to top</a>)</p>