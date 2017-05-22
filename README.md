# Kokyo.space PostalCode Service 
A simple microservice to expose GeoNames.org postalcode dataset as JSON objects. 
 
 * Spring Boot
 * Mongodb
 * Spring Data Rest
 
 TODO
 * Unit tests   
 * Security 
 * ElasticSearch?  
 
 
## Setup
1. Download datasets from [GeoNames.org Free Postal Code Data](http://download.geonames.org/export/zip/ "Postal Code")
2. Unzip into a directory. 
3. Update space.kokyo.data.filesPattern in application.properties if necessary.  