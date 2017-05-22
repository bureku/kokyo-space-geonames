package space.kokyo.services.geonames;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.Resource;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.index.CompoundIndexDefinition;
import org.springframework.data.mongodb.core.index.GeospatialIndex;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.FileReader;
import java.io.Reader;

/**
 * Runner to load GeoNames dataset from a defined directory. Configure in application.properties
 *
 * @author Blake Ong
 * @since 1.8
 */
@Component
public class LoadMongoDataRunner implements CommandLineRunner {

    @Value("classpath*:${space.kokyo.data.filesPattern}")
    private Resource[] datafiles;
    
    @Value("${space.kokyo.loadDataOnStartup}")
    private boolean loadData;

    private final GeoNameRepository repository;

    private final MongoTemplate mongoTemplate;

    @Autowired
    public LoadMongoDataRunner(GeoNameRepository repository, MongoTemplate mongoTemplate) {
        this.repository = repository;
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public void run(String... args) throws Exception {
        if(loadData) {
            System.out.println("Refresh Mongodb with new data");
            mongoTemplate.dropCollection(GeoName.class);

            for(Resource postalCodeDataSet: datafiles) {
                Reader in = new FileReader(postalCodeDataSet.getFile());
                Iterable<CSVRecord> records = CSVFormat.TDF.parse(in);

                for(CSVRecord record: records) {
                    GeoName geoName = new GeoName();
                    geoName.setCountryCode(record.get(0));
                    geoName.setPostalCode(record.get(1));
                    geoName.setPlaceName(record.get(2));
                    geoName.setAdminName1(record.get(3));
                    geoName.setAdminCode1(record.get(4));
                    geoName.setAdminName2(record.get(5));
                    geoName.setAdminCode2(record.get(6));
                    geoName.setAdminName3(record.get(7));
                    geoName.setAdminCode3(record.get(8));
                    // the ever confusing longitude, latitude VS latitude, longitude
                    geoName.setLocation(new Point(Double.valueOf(record.get(10)), Double.valueOf(record.get(9))));

                    // some datasets are missing accuracy, will default to 3. 
                    if(StringUtils.isEmpty(record.get(11))) {
                        geoName.setAccuracy(3);
                    } else {
                        geoName.setAccuracy(Integer.valueOf(record.get(11)));
                    }

                    repository.save(geoName);
                }

                System.out.println("Inserted dataset from: " + postalCodeDataSet.getFilename());

                in.close();

                // create indexes
                DBObject indexOptions = new BasicDBObject();
                indexOptions.put("countryCode", 1);
                indexOptions.put("postalCode", 1);
                indexOptions.put("placeName", 1);

                mongoTemplate.indexOps(GeoName.class).ensureIndex(new GeospatialIndex("location"));
                mongoTemplate.indexOps(GeoName.class).ensureIndex(new CompoundIndexDefinition(indexOptions));
            }
        }
        else {
            System.out.println("Using existing data");
        }

    }
}
