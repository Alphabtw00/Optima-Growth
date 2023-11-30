package com.optimagrowth.license.service.feignClient;

import com.optimagrowth.license.model.Organization;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "organization-service", configuration = FeignConfiguration.class) //add the config of which interceptor to use
public interface OrganizationFeignClient {

    @LoadBalanced//gets instances of downstream service from eureka (via fiegnclient name set above) and then uses spring cloud load balancer(client side) to select the instance and then forward request
    @GetMapping("v1/organization/{organizationId}")
    ResponseEntity<Organization> getOrganization(@PathVariable("organizationId") String organizationId);
}
