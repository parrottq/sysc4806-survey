<<<<<<< HEAD
:: Run maven cleanup and package scripts
call mvn clean -f pom.xml
call mvn package -f pom.xml

:: Executing the created jar file
cd target
java -jar survey-1.0-SNAPSHOT.jar
=======
:: Maven clean and package
call mvn clean -f pom.xml
call mvn package -f pom.xml

:: Execute built target jar
java -jar target/survey-1.0-SNAPSHOT.jar
>>>>>>> main

pause