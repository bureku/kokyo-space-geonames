package space.kokyo.services.geonames;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.io.Resource;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.index.CompoundIndexDefinition;
import org.springframework.data.mongodb.core.index.GeospatialIndex;
import org.springframework.stereotype.Component;

import java.io.FileReader;
import java.io.Reader;

/**
 * Runner to load GeoNames dataset from a defined directory. Configure in application.properties
 *
 * @author Blake Ong
 * @since 1.8
 */
@Component
@Profile("{prod,dev}")
public class LoadMongoDataRunner implements CommandLineRunner {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Value("classpath*:${space.kokyo.data.filesPattern}")
    private Resource[] datafiles;
    
    @Value("${space.kokyo.loadDataOnStartup}")
    private boolean loadData;

    private final GeoNameRepository repository;

    private final MongoTemplate mongoTemplate;

    private final Converter<CSVRecord, GeoName> csvRecordToGeoNameConverter;

    @Autowired
    public LoadMongoDataRunner(GeoNameRepository repository, MongoTemplate mongoTemplate,
                               Converter<CSVRecord, GeoName> csvRecordToGeoNameConverter) {
        this.repository = repository;
        this.mongoTemplate = mongoTemplate;
        this.csvRecordToGeoNameConverter = csvRecordToGeoNameConverter;
    }

    @Override
    public void run(String... args) throws Exception {
        if(loadData) {
            log.info("Refresh Mongodb with new data");
            mongoTemplate.dropCollection(GeoName.class);

            for(Resource postalCodeDataSet: datafiles) {
                Reader in = new FileReader(postalCodeDataSet.getFile());
                Iterable<CSVRecord> records = CSVFormat.TDF.parse(in);

                for(CSVRecord record: records) {
                    repository.save(csvRecordToGeoNameConverter.convert(record));
                }

                log.debug("Inserted dataset from: " + postalCodeDataSet.getFilename());

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
            log.info("Using existing data");
        }

    }
}
