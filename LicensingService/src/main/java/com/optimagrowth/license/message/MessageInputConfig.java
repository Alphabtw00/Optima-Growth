package com.optimagrowth.license.message;

import com.optimagrowth.license.model.Organization;
import com.optimagrowth.license.repository.OrganizationRedisRepository;
import com.optimagrowth.license.service.feignClient.OrganizationFeignClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;

import java.util.function.Consumer;


@Configuration
@Slf4j
public class MessageInputConfig {
    private OrganizationFeignClient organizationFeignClient;
    private OrganizationRedisRepository organizationRedisRepository;

    @Autowired
    public MessageInputConfig(OrganizationFeignClient organizationFeignClient,
                              OrganizationRedisRepository organizationRedisRepository) {
        this.organizationFeignClient = organizationFeignClient;
        this.organizationRedisRepository = organizationRedisRepository;
    }


    @Bean
    public Consumer<OrganizationChangeModel> consumer(){
        return orgChange-> {
            log.info("Received an {} event for organization id {}",
                    orgChange.getAction(), orgChange.getOrganizationId());

            String action = orgChange.getAction();
            ResponseEntity<Organization> feignResponse =
                    organizationFeignClient.getOrganization(orgChange.getOrganizationId());
            Organization organization = feignResponse.getBody();
            switch (action) {
                case "CREATED":
                    organizationRedisRepository.save(organization);
                    break;
                case "UPDATED":
                    if (organization != null) {
                        organizationRedisRepository.save(organization);
                    } else {
                        log.warn("Failed to fetch updated organization details for id: {}",
                                orgChange.getOrganizationId());
                    }
                    break;
                case "DELETED":
                    organizationRedisRepository.deleteById(orgChange.getOrganizationId());
                    break;
                default:
                    log.warn("Unknown action received: {}", action);
                    break;
            };
        };
    };
}
