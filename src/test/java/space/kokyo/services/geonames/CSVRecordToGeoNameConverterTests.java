package space.kokyo.services.geonames;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.net.URL;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Blake Ong
 * @since 1.8
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class CSVRecordToGeoNameConverterTests {

    @Autowired
    private CSVRecordToGeoNameConverter csvRecordToGeoNameConverter;

    @Test
    public void convertCSVRecordToGeoNameTest() throws IOException {
        ClassLoader classLoader = getClass().getClassLoader();
        URL resource = classLoader.getResource("data/TestData.txt");
        assertThat(resource).isNotNull();
        File file = new File(resource.getPath());

        Reader in = new FileReader(file);
        GeoName[] geoNames = new GeoName[3];
        int i = 0;

        Iterable<CSVRecord> records = CSVFormat.TDF.parse(in);

        for(CSVRecord record: records) {
            geoNames[i] = csvRecordToGeoNameConverter.convert(record);
            i++;
        }

        assertThat(geoNames[0].getCountryCode()).isEqualTo("AU");
        assertThat(geoNames[0].getPostalCode()).isEqualTo("0200");
        assertThat(geoNames[0].getLocation().getX()).isEqualTo(149.1189);

        assertThat(geoNames[1].getCountryCode()).isEqualTo("AU");
        assertThat(geoNames[1].getPostalCode()).isEqualTo("0221");
        assertThat(geoNames[1].getLocation().getY()).isEqualTo(-35.3049);
        assertThat(geoNames[1].getAccuracy()).isEqualTo(4);

        // CSVRecord for this is empty, therefore, it should default to 3
        assertThat(geoNames[2].getAccuracy()).isEqualTo(3);
    }
}
