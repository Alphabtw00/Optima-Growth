package com.optimagrowth.organization.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "organizations")
public class Organization {
    @Id
    @Column(name = "organization_id", nullable = false)
    String id;

    @Column(nullable = false)
    String name;

    @Column(nullable = false)
    String contactName;

    @Column(nullable = false)
    String contactEmail;

    @Column(nullable = false)
    String contactPhone;

}
