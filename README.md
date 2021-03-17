# Dextra-Challenge

Dextra backend project challenge

### How to run:
1. Insert the keys from [Marvel API](https://developer.marvel.com/account) to the file **src/main/resources/env.properties**
          
2. Run **mvn clean package** to build the project
    - This will download all dependencies, run the tests, and build the project.
3. After build, run **java -jar target/dextra-challenge-0.0.1-SNAPSHOT.jar** at the project root folder
    
4. Its done, the server will run on port 8080.

The list of endpoints is:
- [Characters](http://localhost:8080/v1/public/characters")
- [Characters by ID](http://localhost:8080/v1/public/characters/{id}")
- [Character Comics by Character Id](http://localhost:8080/v1/public/characters/{id}/comics")
- [Character Events by Character Id](http://localhost:8080/v1/public/characters/{id}/events")
- [Character Series by Character Id](http://localhost:8080/v1/public/characters/{id}/series")
- [Character Stories by Character Id](http://localhost:8080/v1/public/characters/{id}/stories")