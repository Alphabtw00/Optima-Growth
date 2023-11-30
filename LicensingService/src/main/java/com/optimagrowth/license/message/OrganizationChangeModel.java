package com.optimagrowth.license.message;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor //if no final fields then @data only makes a noArg constructor
public class OrganizationChangeModel {
    private String type;
    private String action;
    private String organizationId;
    private String correlationId;
}
