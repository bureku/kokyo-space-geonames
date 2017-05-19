package space.kokyo.services.geonames;


import org.springframework.data.annotation.Id;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigInteger;

/**
 *
 * Document that follow the GeoNames.org datasets
 *
 * @author blakeong
 * @since 1.8
 */
@Document(collection = "postalcodes")
public class GeoName {
    @Id
    private BigInteger id;
    private String countryCode;
    private String postalCode;
    private String placeName;
    private String adminName1;
    private String adminCode1;
    private String adminName2;
    private String adminCode2;
    private String adminName3;
    private String adminCode3;
    private Integer accuracy;
    private Point location;

    public GeoName(){}

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    public String getAdminName1() {
        return adminName1;
    }

    public void setAdminName1(String adminName1) {
        this.adminName1 = adminName1;
    }

    public String getAdminCode1() {
        return adminCode1;
    }

    public void setAdminCode1(String adminCode1) {
        this.adminCode1 = adminCode1;
    }

    public String getAdminName2() {
        return adminName2;
    }

    public void setAdminName2(String adminName2) {
        this.adminName2 = adminName2;
    }

    public String getAdminCode2() {
        return adminCode2;
    }

    public void setAdminCode2(String adminCode2) {
        this.adminCode2 = adminCode2;
    }

    public String getAdminName3() {
        return adminName3;
    }

    public void setAdminName3(String adminName3) {
        this.adminName3 = adminName3;
    }

    public String getAdminCode3() {
        return adminCode3;
    }

    public void setAdminCode3(String adminCode3) {
        this.adminCode3 = adminCode3;
    }

    public Integer getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(Integer accuracy) {
        this.accuracy = accuracy;
    }

    public Point getLocation() {
        return location;
    }

    public void setLocation(Point location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return "{\"_class\":\"space.kokyo.services.geonames.GeoName\", " +
                "\"id\":" + (id == null ? "null" : id) + ", " +
                "\"countryCode\":" + (countryCode == null ? "null" : "\"" + countryCode + "\"") + ", " +
                "\"postalCode\":" + (postalCode == null ? "null" : "\"" + postalCode + "\"") + ", " +
                "\"placeName\":" + (placeName == null ? "null" : "\"" + placeName + "\"") + ", " +
                "\"adminName1\":" + (adminName1 == null ? "null" : "\"" + adminName1 + "\"") + ", " +
                "\"adminCode1\":" + (adminCode1 == null ? "null" : "\"" + adminCode1 + "\"") + ", " +
                "\"adminName2\":" + (adminName2 == null ? "null" : "\"" + adminName2 + "\"") + ", " +
                "\"adminCode2\":" + (adminCode2 == null ? "null" : "\"" + adminCode2 + "\"") + ", " +
                "\"adminName3\":" + (adminName3 == null ? "null" : "\"" + adminName3 + "\"") + ", " +
                "\"adminCode3\":" + (adminCode3 == null ? "null" : "\"" + adminCode3 + "\"") + ", " +
                "\"accuracy\":" + (accuracy == null ? "null" : "\"" + accuracy + "\"") + ", " +
                "\"location\":" + (location == null ? "null" : location) +
                "}";
    }
}
