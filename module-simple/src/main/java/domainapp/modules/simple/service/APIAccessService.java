package domainapp.modules.simple.service;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import domainapp.modules.simple.dom.inventory.product.Product;
import domainapp.modules.simple.dom.inventory.product.ProductToCreate;
import domainapp.modules.simple.dom.inventory.rule.RuleToCreate;
import domainapp.modules.simple.dom.inventory.rule.RuleToUpdate;
import domainapp.modules.simple.external.Record;

import domainapp.modules.simple.dom.inventory.product.ProductToUpdate;
import domainapp.modules.simple.external.RecordToCreate;
import domainapp.modules.simple.external.RecordsCreateRequest;
import domainapp.modules.simple.external.RecordsResponse;
import domainapp.modules.simple.dom.inventory.rule.Rule;

import domainapp.modules.simple.external.RecordsResponseSingle;

import domainapp.modules.simple.util.CommonUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class APIAccessService {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${airtable.token}")
    private String airtableToken;

    @Value("${airtable.base.url}")
    private String airtableBaseUrl;

    private HttpHeaders generateHeaders(boolean json) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + airtableToken);
        if (json) {
            headers.add("Content-Type", "application/json");
        }
        return headers;
    }

    public <T> RecordsResponse<Record<T>> getRecords(String apiPath, ParameterizedTypeReference<RecordsResponse<Record<T>>> responseType) throws URISyntaxException {
        HttpEntity<Object> request = new HttpEntity<>(null, generateHeaders(false));
        RecordsResponse<Record<T>> body = restTemplate.exchange(new URI(airtableBaseUrl + apiPath), HttpMethod.GET, request, responseType).getBody();
        return body;
    }

    public  <T> Record<T> getSingleRecord(String apiPath, ParameterizedTypeReference<Record<T>> responseType) throws URISyntaxException {
        HttpEntity<Object> request = new HttpEntity<>(null, generateHeaders(false));
        Record<T> body = restTemplate.exchange(new URI(airtableBaseUrl + apiPath), HttpMethod.GET, request, responseType).getBody();
        return body;
    }

    public <T> HttpStatusCode createRecord(RecordsCreateRequest<T> records, String apiPath, ParameterizedTypeReference<RecordsResponse<Record<T>>> responseType) throws URISyntaxException {
        HttpEntity<Object> request = new HttpEntity<>(records, generateHeaders(true));
        return restTemplate.exchange(new URI(airtableBaseUrl + apiPath), HttpMethod.POST, request, responseType).getStatusCode();
    }

    public <T> HttpStatusCode updateRecord(RecordToCreate<T> records, String apiPath, ParameterizedTypeReference<RecordsResponse<Record<T>>> responseType) throws URISyntaxException {
        HttpEntity<Object> request = new HttpEntity<>(records, generateHeaders(true));
        return restTemplate.exchange(new URI(airtableBaseUrl + apiPath), HttpMethod.PATCH, request, responseType).getStatusCode();
    }

    public <T> HttpStatusCode deleteRecord(String apiPath, ParameterizedTypeReference<RecordsResponse<Record<T>>> responseType) throws URISyntaxException {
        HttpEntity<Object> request = new HttpEntity<>(null, generateHeaders(true));
        return restTemplate.exchange(new URI(airtableBaseUrl + apiPath), HttpMethod.DELETE, request, responseType).getStatusCode();
    }
}
