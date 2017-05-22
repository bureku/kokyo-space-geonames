package space.kokyo.services.geonames;

import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;

import java.math.BigInteger;

/**
 * GeoNames mongodb repository
 *
 * @author blakeong
 * @since 1.8
 */
public interface GeoNameRepository extends MongoRepository<GeoName, BigInteger> {

    @RestResource(path = "near")
    Iterable<GeoName> findByLocationNear(@Param("location") Point location, @Param("distance") Distance distance);

    @RestResource(path = "postalcode")
    Iterable<GeoName> findByCountryCodeAndPostalCode(@Param("country") String countryCode, @Param("postalcode") String postalCode);

    
}
