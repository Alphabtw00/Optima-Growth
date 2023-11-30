package com.optimagrowth.organization.message;

import io.micrometer.tracing.Tracer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Component;


@Component
@Slf4j
public class MessageOutputStream {
    private StreamBridge streamBridge;
    private Tracer tracer;



    public void publishOrganizationChange(ActionEnum action, String organizationId){
        log.info("Sending Kafka message {} for Organization Id: {}", action, organizationId);
        OrganizationChangeModel orgChange = new OrganizationChangeModel(OrganizationChangeModel.class.getTypeName(),
                action.toString(),organizationId, tracer.currentSpan().context().traceId());
        streamBridge.send("producer", orgChange); //adds the model object to "producer" binding source
    }

}
