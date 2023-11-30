package com.optimagrowth.license.service;

import com.optimagrowth.license.model.Organization;
import com.optimagrowth.license.repository.OrganizationRedisRepository;
import com.optimagrowth.license.service.feignClient.OrganizationFeignClient;
import io.micrometer.tracing.Tracer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class OrganizationRedisFeignClientService {
    private OrganizationFeignClient organizationFeignClient;
    private OrganizationRedisRepository organizationRedisRepository;
    private Tracer tracer;

    @Autowired
    public OrganizationRedisFeignClientService(OrganizationFeignClient organizationFeignClient,
                                               OrganizationRedisRepository organizationRedisRepository) {
        this.organizationFeignClient = organizationFeignClient;
        this.organizationRedisRepository=organizationRedisRepository;
    }


    private Organization checkRedisCache(String organizationId){
        try{
            return organizationRedisRepository.findById(organizationId).orElse(null);
        }catch (Exception exception){
            log.info("Error encountered while trying to retrieve organization {} check Redis Cache. Exception {} ",
                    organizationId,exception.toString());
            return null;
        }
    }

    private void cacheOrganizationObject(Organization organization){
        try{
            organizationRedisRepository.save(organization);
        }catch (Exception exception){
            log.info("Unable to cache organization {} in Redis. Exception {}",
                    organization.getId(), exception.toString());
        }
    }

    public Organization getOrganization(String organizationId){
        log.info("In Licensing Service.getOrganization: {}",
                tracer.currentSpan().context().traceId());
        Organization organization = checkRedisCache(organizationId);
        if (organization!=null){
            log.info("I have successfully retrieved an organization {} from the redis cache: {}",
                    organizationId, organization);
            return organization;
        }
        log.info("Unable to locate organization from the redis cache: {}.",
                organizationId);
        ResponseEntity<Organization> feignResponse = organizationFeignClient.getOrganization(organizationId);
        organization = feignResponse.getBody();
        if(organization!=null){
            cacheOrganizationObject(organization);
        }
        return organization;
    }
}
