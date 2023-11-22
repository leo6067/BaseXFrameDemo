package com.xy.demo.model;

import com.google.gson.annotations.SerializedName;

/**
 * author: Leo
 * createDate: 2023/11/20 17:04
 */
public class IpModel {


    @SerializedName("ip")
    private String ip;
    @SerializedName("ip_decimal")
    private Integer ipDecimal;
    @SerializedName("country")
    private String country;
    @SerializedName("country_iso")
    private String countryIso;
    @SerializedName("country_eu")
    private Boolean countryEu;
    @SerializedName("region_name")
    private String regionName;
    @SerializedName("region_code")
    private String regionCode;
    @SerializedName("city")
    private String city;
    @SerializedName("latitude")
    private Double latitude;
    @SerializedName("longitude")
    private Double longitude;
    @SerializedName("time_zone")
    private String timeZone;
    @SerializedName("asn")
    private String asn;
    @SerializedName("asn_org")
    private String asnOrg;
    @SerializedName("hostname")
    private String hostname;
    @SerializedName("user_agent")
    private UserAgentDTO userAgent;

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Integer getIpDecimal() {
        return ipDecimal;
    }

    public void setIpDecimal(Integer ipDecimal) {
        this.ipDecimal = ipDecimal;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCountryIso() {
        return countryIso;
    }

    public void setCountryIso(String countryIso) {
        this.countryIso = countryIso;
    }

    public Boolean getCountryEu() {
        return countryEu;
    }

    public void setCountryEu(Boolean countryEu) {
        this.countryEu = countryEu;
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    public String getRegionCode() {
        return regionCode;
    }

    public void setRegionCode(String regionCode) {
        this.regionCode = regionCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }

    public String getAsn() {
        return asn;
    }

    public void setAsn(String asn) {
        this.asn = asn;
    }

    public String getAsnOrg() {
        return asnOrg;
    }

    public void setAsnOrg(String asnOrg) {
        this.asnOrg = asnOrg;
    }

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public UserAgentDTO getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(UserAgentDTO userAgent) {
        this.userAgent = userAgent;
    }

    public static class UserAgentDTO {
        @SerializedName("product")
        private String product;
        @SerializedName("version")
        private String version;
        @SerializedName("comment")
        private String comment;
        @SerializedName("raw_value")
        private String rawValue;

        public String getProduct() {
            return product;
        }

        public void setProduct(String product) {
            this.product = product;
        }

        public String getVersion() {
            return version;
        }

        public void setVersion(String version) {
            this.version = version;
        }

        public String getComment() {
            return comment;
        }

        public void setComment(String comment) {
            this.comment = comment;
        }

        public String getRawValue() {
            return rawValue;
        }

        public void setRawValue(String rawValue) {
            this.rawValue = rawValue;
        }
    }
}
