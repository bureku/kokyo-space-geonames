package space.kokyo.services.geonames;

import org.apache.commons.csv.CSVRecord;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.geo.Point;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 *
 * Covert CSVRecord from *.txt to GeoName object
 *
 * @author Blake Ong
 * @since 1.8
 */
@Component
public class CSVRecordToGeoNameConverter implements Converter<CSVRecord, GeoName> {
    @Override
    public GeoName convert(CSVRecord record) {
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
        try {
            geoName.setAccuracy(Integer.valueOf(record.get(11)));
        }catch (Exception e) {
            geoName.setAccuracy(3);
        }

        return geoName;
    }
}
