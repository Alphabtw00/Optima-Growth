package com.optimagrowth.license.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "licenses")
public class License {
    @Id
    @Column(nullable = false)
    private String licenseId;
    @Column(nullable = false)
    private String description;
    @Column(nullable = false)
    private String organizationId;
    @Column(nullable = false)
    private String productName;
    @Column(nullable = false)
    private String licenseType;
    private String comment;
    @Transient //doesnt make a table
    private String organizationName;
    @Transient
    private String contactName;
    @Transient
    private String contactPhone;
    @Transient
    private String contactEmail;

    public License withComment(String comment){
        this.setComment(comment);
        return this;
    }
}
