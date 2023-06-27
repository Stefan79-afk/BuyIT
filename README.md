# Running the Back-End

First of all, to effectively run the back-end, the user must setup a connection to
a MongoDB database. To do that in Spring Boot, they will need to add some
information to the framework’s application.properties file (located in BE/buy-it/src/main/resources)
This file represents specific properties of a Spring Boot application. Numerous
things can be configured from here, such as the logging level of the application
(whether to display more messages in case a developer needs to debug the
application, in which case debug messages can be configured to show), as well as
generally setting up specific features. In our specific case, we need to add the link
to our MongoDB database.

As shown above, the file is in the resources folder of the main package. We need
to add the following text:

`spring.data.mongodb.uri=mongodb+srv://<username>:<password>.mongodb.net/buy_it?retryWrites=true&w=majority`
`logging.level.org.springframework.data.mongodb.core.MongoTemplate=DEBUG`

where the ”username” is the MongoDB database username, and the
”password” is the MongoDB database password. These credentials are set up
when creating a new database. Replace the placeholders with your credentials. You can also declare your
credentials separately as other config properties.

The second line enables debug messages to appear in the Spring Boot Console. They
are useful for checking out queries as they appear when sent to MongoDB. This has
personally helped me countless time to figure out the issues with my queries.

After this step, simply run the application. IntelliJ should add the configuration
necessary, therefore you just need to run it from the UI (the green play button
at the top right). Another way is to simply type: **./gradlew bootRun** into the
terminal (make sure you’re at the root directory of the project: BE/buy-it).
Assuming that the MongoDB connection was correctly written, the app should be
connected properly to the database
Now, to actually properly run your application, you will need to add data to your
database. In the data directory located in the root project’s directory, you will find
a bunch of .json files, each labeled by the collection name. Go ahead and create a
collection for each, and then import the data into the respective collection.
After this, you are good to go!

With only the backend started, you can use an HTTP Client tool like Postman or
Insomnia to make requests to the server (or you can just use the curl command
to perform the same operations). For the recommendation system, make a POST
request to the following URL: **http://localhost:8080/odata/BuyITService/quiz** , and
add a JSON object in the body (this is the filterObject, you can have a look in the
PCRequest class to see all the properties and values it can have, as well as in the
questions in the database to see the possible values in order to generate answers for
the recommendation system). Furthermore, you can also make a GET request
to this URL: **http://localhost:8080/odata/BuyITService/Questions(id)**
(where the id is the id / number of the question you want to fetch) to get a specific
question.

# Starting the Front-End 
Starting the Front-End is easier than the back-end. All you need to do is run the
following command: **npm install** in the front-end root folder (FE/buy-it). This
will install the npm packages that the app uses. After the installation is done, just
enter the following command: **npm run dev**. And the front-end server should
start. And that is it! With the back-end also started, the app is fully functional
and ready to be used.

