**This project is for converting the html formatted file to pdf.** 
 
######  Requirements
` JAVA 8 `\
` Docker`\
 `Maven` 
  
  and
  use  <a href="https://itextpdf.com/en/demos">itextpdf</a>
  
 You can run the project directly. Or with maven.
 
 >mvnw spring-boot:run
 
 
##### You can run the project with Docker using the following steps :
~~~~
mvn clean package
docker build -t html2pdf -f Dockerfile .   
docker run -p 8080:8080 html2pdf (windows) || sudo docker run -p 8080:8080 html2pdf (linux)
~~~~

You can test the services by opening the "Html2Pdf.postman_collection.json" file with Postman.
 