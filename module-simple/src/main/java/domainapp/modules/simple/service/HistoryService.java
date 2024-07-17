package domainapp.modules.simple.service;

import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.core.JsonProcessingException;

import domainapp.modules.simple.dom.inventory.history.History;

import domainapp.modules.simple.dom.inventory.history.HistoryToCreate;
import domainapp.modules.simple.dom.inventory.product.Product;

import domainapp.modules.simple.dom.inventory.product.ProductToCreate;

import org.apache.causeway.applib.services.user.UserMemento;
import org.apache.causeway.applib.services.user.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;

import domainapp.modules.simple.dom.inventory.rule.Rule;
import domainapp.modules.simple.dom.inventory.rule.RuleDto;
import domainapp.modules.simple.dom.inventory.rule.RuleToCreate;
import domainapp.modules.simple.dom.inventory.rule.RuleToUpdate;
import domainapp.modules.simple.external.Record;
import domainapp.modules.simple.external.RecordToCreate;
import domainapp.modules.simple.external.RecordsCreateRequest;
import domainapp.modules.simple.external.RecordsResponse;
import domainapp.modules.simple.util.CommonUtil;

@Component
public class HistoryService {

    @Autowired
    APIAccessService apiAccessService;

    @Autowired
    UserService userService;

    @Value("${airtable.histories.path}")
    private String airtableHistoriesPath;

    public void createHistory(String action, String detail) throws URISyntaxException {
        UserMemento user = userService.getUser();
        String userName = user.getName();
        String uuid = UUID.randomUUID().toString();
        HistoryToCreate history = new HistoryToCreate(uuid, userName, action, detail);
        RecordsCreateRequest<RecordToCreate> historyRecords = new RecordsCreateRequest<>();
        historyRecords.setRecords(List.of(new RecordToCreate(history)));
        apiAccessService.createRecord(historyRecords, airtableHistoriesPath, new ParameterizedTypeReference<>() {
        });
    }

    public RecordsResponse<Record<History>> getAllHistories() throws URISyntaxException {
        return apiAccessService.getRecords(airtableHistoriesPath, new ParameterizedTypeReference<>() {
        });
    }}
