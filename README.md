# Optima-Growth:-
Optima Growth is a software development company whose core product  O-stock, provides an enterprise-grade asset management application. It furnishes coverage for all the critical elements: inventory, software delivery, license management, compliance, cost, and resource management. Its primary goal is to enable organizations to gain an accurate point-in-time picture of their software assets.

This uses Heroku’s best practice guide- : called the twelve-factor app (https://12factor.net/). It includes the 12 best practices to build a microservice architecture cloud application. 
 
This cutting-edge project was meticulously crafted using an extensive tech stack, including: 
Spring Boot 3.0, Spring Security 6, Spring Cloud Project, Spring JPA Spring Micrometer and OpenFeign.

# ARCHITECTURE:-
![image](https://github.com/Alphabtw00/Optima-Growth/assets/124119017/0b774d6c-1576-4491-b3ae-688a6929ddb1)


# Installation:-

Follow these steps to get your development environment set up: (Before Run Start the Docker Desktop)
1. Clone the repository
2. Install Docker Dekstop and start it.
3. At the root directory of the parent project which is OPTIMA GROWTH, it includes docker-compose.yml file, run it in the root directory iteself via below command:
```
docker compose up
```
4. To stop the file use:
```
docker compose down
```

## Includes tools like: 
**Config Server**: server used to store configuration of all services used in this project.  
**Admin Server**: provides ui to monitor and manage our application and services.  
**Micrometer**: used Spring Micrometer which is a distributed tracing and observation tool.
**KeyCloak**: security is handled using oauth2 protocol. it uses keycloack to generate authentication bearer token to get access to the services, which is passed to all downstream services automatically.  
**Api Gateway**: Requests are channeled and fitlered through spring cloud api gateway which passes the requests forward to  downstream services.  
**Netflix Eureka**: enables services to locate and communicate with each other without hard-coded, static configurations.  
**Kafka**: Messaging is handled via Spring annotation integration of Kafka using Spring Cloud Stream Kafka.  
**Redis**: my choice of in-memory data structure store used to cache the database entities to improve call times, induce high performance, low latency and quicker retrieval of data.  
**ELK(elasticsearch, logstash, kibana)**: used for centralized logging and logs management.  
      -*Elasticsearch* stores and indexes logs.  
      -*Logstash* ingests and transforms our logs and sends them to elasticsearch.  
      -*Kibana* provides an UI to manage and query log data from elasticsearch.  
**Zipkin**: tracing system to monitor all calls and traces, also provides an UI to do so.  
**PostgreSQL**: Chosen as the relational database management system.  
**Resilience4j**: Implementing resilience patterns like circuit breaker, bulkhead and retry to fortify the system against failures and unexpected conditions.  
**Postman**: Utilized for API development, testing, and documentation.  

Moreover, I orchestrated the deployment by containerizing all services and established a centralized repository on Docker Hub for seamless deployment and version control. 


