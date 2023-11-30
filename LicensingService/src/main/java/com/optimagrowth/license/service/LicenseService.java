package com.optimagrowth.license.service;

import com.optimagrowth.license.caching.Component.ServiceConfig;
import com.optimagrowth.license.customException.LicenseNotFoundException;
import com.optimagrowth.license.model.License;
import com.optimagrowth.license.model.Organization;
import com.optimagrowth.license.repository.LicenseRepository;
import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class LicenseService {
    private ServiceConfig serviceConfig;
    private LicenseRepository licenseRepository;
    private OrganizationRedisFeignClientService organizationRedisFeignClientService;
    @Autowired
    public LicenseService(ServiceConfig serviceConfig, LicenseRepository licenseRepository,
                          OrganizationRedisFeignClientService organizationRedisFeignClientService) {
        this.serviceConfig = serviceConfig;
        this.licenseRepository = licenseRepository;
        this.organizationRedisFeignClientService=organizationRedisFeignClientService;
    }



    @CircuitBreaker(name = "licenseService",fallbackMethod = "fallBackMethod") //fallback method when circuit is opened
    @Retry(name = "licenseService", fallbackMethod = "fallBackMethod") //fallback after no of retries and still fails
    @Bulkhead(name = "licenseService",fallbackMethod ="fallBackMethod" ) //fallback if queue is full or request is killed cause no.of calls are set
    public List<License> getLicensesByOrganization(String organizationId) {
        return licenseRepository.findByOrganizationId(organizationId);
    }
    public List<License> fallBackMethod(String organizationId, Throwable throwable){
        List<License> fallBackList = new ArrayList<>();
        License license = new License();
        license.setLicenseId("0000000-00-00000");
        license.setOrganizationId(organizationId);
        license.setProductName("Sorry no licensing information currently available");
        fallBackList.add(license);
        return fallBackList;
    }

    @CircuitBreaker(name = "organizationService")
    public Organization retrieveOrganizationInfo(String organizationId) {
         Organization organization = organizationRedisFeignClientService.getOrganization(organizationId);
         return organization;
    }
    public License getLicense(String licenseId, String organizationId, String clientId){
        License license = licenseRepository.findByOrganizationIdAndLicenseId(licenseId,organizationId);
        if(license==null){
            throw new LicenseNotFoundException(String.format
                    ("License with ID %s for organization %s not found", licenseId, organizationId));
        }
        Organization organization = retrieveOrganizationInfo(organizationId);
        if (organization!=null) {
            license.setOrganizationName(organization.getName());
            license.setContactName(organization.getContactName());
            license.setContactEmail(organization.getContactEmail());
            license.setContactPhone(organization.getContactPhone());
        }
        return license.withComment(serviceConfig.getProperty());
    }
    public License createLicense(License request, String organizationId) {
        if (request.getLicenseId() == null) {
            request.setLicenseId(UUID.randomUUID().toString());
        }
        request.setOrganizationId(organizationId);
        licenseRepository.save(request);
        return request;
    }
    public License updateLicense(License updatedLicense, String organizationId ) {
        License existingLicense = licenseRepository.findByOrganizationIdAndLicenseId
                (updatedLicense.getLicenseId(), organizationId);
        if (existingLicense == null) {
            throw new LicenseNotFoundException(String.format
                    ("License with ID %s for organization %s not found", updatedLicense.getLicenseId(),organizationId));
        }
        existingLicense.setDescription(updatedLicense.getDescription());
        existingLicense.setProductName(updatedLicense.getProductName());
        existingLicense.setLicenseType(updatedLicense.getLicenseType());
        existingLicense.setComment(updatedLicense.getComment());
        licenseRepository.save(existingLicense);
        return existingLicense;
    }
    public String deleteLicense(String licenseId, String organizationId) {
        License existingLicense = licenseRepository.findByOrganizationIdAndLicenseId
                (licenseId, organizationId);
        if (existingLicense == null) {
            throw new LicenseNotFoundException(String.format
                    ("License with ID %s for organization %s not found", licenseId, organizationId));
        }
        licenseRepository.delete(existingLicense);
        return String.format("License with ID %s has been deleted", licenseId);
    }

}
