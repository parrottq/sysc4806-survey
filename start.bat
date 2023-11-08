:: Run maven cleanup and package scripts
call mvn clean -f pom.xml
call mvn package -f pom.xml

:: Executing the created jar file
cd target
java -jar survey-1.0-SNAPSHOT.jar

pause