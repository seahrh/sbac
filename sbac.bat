echo off
echo Building frontend...
call gulp
echo Run all unit tests and integration tests...
call mvn verify
echo Deploying to devserver...
call mvn jetty:run