# Web-Services
This is my the repository for my web services class. This utilized multiple different frameworks and tools such as: Apache Tomcat, Java Spark, Dropwizard and MySQL

This was a backend application that will generate random Subway sandwich to a User and it was built and improved upon using the multiple different frameworks. Each directory contains some similiar aspects but some are more improved upon than others, as the semester went by. By the end of the semester, the application contained the following endpoints: 

 - GET /Account/all
 - GET /Account/Info/{name}
 - GET /Account/History/{name}
 - GET /Account/Favorites/{name}
 - POST /Account//CreateAccount/{name}{password}
 - POST /Account/DeleteAccount/{name}{password}
 - DELETE /Account/DeleteFavorite/{name}{id}
 - POST /Account/AddFavorite/{name}{id}
 - POST /Account/Login/{name}{password}
 - POST /Account/Logout/{name}{password}
 - POST /Sandwich/GenerateSandwich/{name}
 
Some of these endpoints required authentication in order to be used, so I created a (not very secure) login that checked if a User was already logged in and if so, any changes could be made to their account. More info on this can be found in the final document file. 
 
Note: Downloading any of the directories and attempting to compile the code will not work due to missing hundreds of files and the mysql database, this is posted as a way of rememberance of the work that I did for this semester. 
