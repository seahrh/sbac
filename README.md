# Autocomplete & Search

![Autocomplete suggestions](https://drive.google.com/open?id=0B7gBv2Jut0VxdGg3U0ZGcTZTQXc)

![Search results](https://drive.google.com/open?id=0B7gBv2Jut0VxVElINFNnU29FZms)

## Data

Dataset contains 1.7 million name records in the tuple {`name`, `gender`, `count`, `year`}. See how names change in popularity from 1890 to 2010. Source: [US Social Security Administration](https://www.data.gov/) 

## Setup

cd to workspace directory and clone this git repo:

> git clone [https://github.com/seahrh/sbac](https://github.com/seahrh/sbac)

Install frontend dependencies (npm packages)

> npm install

> gulp update

Install Java dependencies

> mvn clean install

## Database

Install mongodb 

[Download the data dump (206MB)](https://drive.google.com/open?id=0B7gBv2Jut0VxT29uQ0RLb2duNkU)

Import the data dump (assuming data is stored at default path /data/db):

> mongoimport --db sbac --collection Name Name.json --jsonArray

Start the mongo daemon

> mongod --auth

Access is password protected.

user: dba, password: dba (all privileges)

user: sbacu, password: sbacu (read/write privilege on sbac database)

Login as sbacu:

> mongo --port 27017 -u "sbacu" -p "sbacu" --authenticationDatabase "sbac"

## Build

Frontend is built with gulp e.g. concatenate and minify js, css.

[`gulpfile.js`](gulpfile.js)

> gulp 

Backend java is built with maven.

[`pom.xml`](pom.xml)

> mvn clean package

## Tests

Tests are run with maven. Unit tests are run in `test` phase and integration tests are run in `verify` phase of the Maven build lifecycle. Tests are contained in [`src/test/java` package](src/test/java/sbac); `*Test.java` are unit tests and `*IT.java` are integration tests. 

The REST API is tested as an integration test because it requires a live mongodb connection and the API must be deployed on a server.

If mongod has not already started, 

> mongod --auth

To run the tests,

> mvn verify

## One-Step Deploy

To do the localhost in one step, run the following batch script (Windows): [`dev.bat`](dev.bat)

> dev

This will run the gulp build, then maven build including all unit and integration tests.

Open the following url in browser:

http://localhost:8080/

## Configuration

App configuration (e.g. database credentials) is stored in a configuration file separate from source code. So that if we need to change configuration, we don’t need to recompile.

[`src/main/resources/config/config.properties`](src/main/resources/config/config.properties)

## System Design & Time Spent

1. Use MongoDB as the main database (4 hours)

    1. database=`sbac`

    2. collection=`Name` (same as model classname [`Name.java`](src/main/java/sbac/model/Name.java), following DRY principle)

    3. Schema: {name, gender, count, year}

    4. example query: `db.Name.find()`

    5. Used Morphia as ORM and mongodb Java driver

    6. Did not shard as data is not big

2. Use MongoDB textsearch for autocomplete feature (1 hour)

    7. Built a full text search index on two fields {`name`, `year`} as these are the likely fields to search

3. Have a simple AutoComplete Search Bar (3 hours) 

    8. Handle the autocomplete on the client side to make the user experience responsive in real time

    9. Used a 3rd party JavaScript widget: bootstrap-3-typeahead. Fetches a json file [`src/main/webapp/names.json`](src/main/webapp/names.json) from the server to populate the widget.

    10. Autocomplete suggestions are limited to names that have at least count=10. This reduces the size of the json file, so that the autocomplete/network latency is more performant.

    11. Reduce load on datastore by linking the autocomplete to json file

        1. Basically the results of the query are cached in the json file. 

        2. Nature of data is static (names), so do not need to update the json file frequently

    12. Call search api when button is clicked

4. A simple result page for the search terms (3 hours)

    13. Made with jquery/bootstrap

    14. When data is returned from api, use JavaScript to show search results

5. REST API done with Jersey (8 hours)

    15. see [`sbac.api`](src/main/java/sbac/api) package

    16. No api key as the api is public. Instead log session id. If user misbehaves, rate limit by session id (or ip address).

