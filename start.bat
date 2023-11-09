
:: Maven clean and package
call mvn clean -f pom.xml
call mvn package -f pom.xml

:: Execute built target jar
java -jar target/survey-1.0-SNAPSHOT.jar


pause