movie-star: run

run: install
	java -jar target/movies-0.0.1-SNAPSHOT.jar

install: database
	 mvn -f ./pom.xml  install -Dmaven.test.skip

database:
	 docker-compose -f ./docker-compose.yml  up -d

clean:
	 docker-compose -f ./docker-compose.yml  down




