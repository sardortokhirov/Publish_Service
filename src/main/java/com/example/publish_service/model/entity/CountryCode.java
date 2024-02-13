package com.example.publish_service.model.entity;

import jakarta.persistence.*;

/**
 * Date-21/01/2024
 * By Sardor Tokhirov
 */
@Entity
@Table(name = "country_codes")
public class CountryCode {
    @Id
    @Column(name = "country_code_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "country_code_sequence")
    @SequenceGenerator(name = "country_code_sequence", sequenceName = "country_code_sequence_name", allocationSize = 1)
    private Integer countryCodeId;

    @Column(name = "country_code", unique = true)
    private Integer countryCode;

    public CountryCode() {
    }

    public CountryCode(Integer countryCodeId) {
        this.countryCodeId = countryCodeId;
    }

    public Integer getCountryCodeId() {
        return countryCodeId;
    }

    public void setCountryCodeId(Integer countryCodeId) {
        this.countryCodeId = countryCodeId;
    }

    public Integer getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(Integer countryCode) {
        this.countryCode = countryCode;
    }
}
