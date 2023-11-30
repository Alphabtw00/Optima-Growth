package com.optimagrowth.organization.service;

import com.optimagrowth.organization.message.ActionEnum;
import com.optimagrowth.organization.message.MessageOutputStream;
import com.optimagrowth.organization.model.Organization;
import com.optimagrowth.organization.repository.OrganizationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class OrganizationService {
    private OrganizationRepository organizationRepository;
    private MessageOutputStream messageOutputStream;

    @Autowired
    public OrganizationService(OrganizationRepository organizationRepository, MessageOutputStream messageOutputStream) {
        this.organizationRepository = organizationRepository;
        this.messageOutputStream = messageOutputStream;
    }

    public Organization findById(String organizationId) {
        Optional<Organization> organization = organizationRepository.findById(organizationId);
        return organization.orElse(null);
    }

    public Organization create(Organization organization){
        organization.setId( UUID.randomUUID().toString());
        organization = organizationRepository.save(organization);
        messageOutputStream.publishOrganizationChange(ActionEnum.CREATED,organization.getId());
        return organization;

    }

    public void update(Organization organization){
        organizationRepository.save(organization);
        messageOutputStream.publishOrganizationChange(ActionEnum.UPDATED,organization.getId());
    }

    public void delete(Organization organization){
        organizationRepository.deleteById(organization.getId());
        messageOutputStream.publishOrganizationChange(ActionEnum.DELETED,organization.getId());
    }
}
