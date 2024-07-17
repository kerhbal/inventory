package domainapp.modules.simple.service;

import domainapp.modules.simple.dom.inventory.product.Product;
import domainapp.modules.simple.dom.inventory.product.ProductToCreate;
import domainapp.modules.simple.dom.inventory.product.ProductToUpdate;
import domainapp.modules.simple.dom.inventory.rule.Rule;
import domainapp.modules.simple.external.Record;
import domainapp.modules.simple.external.RecordToCreate;
import domainapp.modules.simple.external.RecordsCreateRequest;
import domainapp.modules.simple.external.RecordsResponse;
import domainapp.modules.simple.util.CommonUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;

import java.net.URISyntaxException;
import java.util.List;

@Component
public class ProductService {

    @Autowired
    APIAccessService apiAccessService;

    @Value("${airtable.products.path}")
    private String airtableProductsPath;

    public RecordsResponse<Record<Product>> getAllProducts() throws URISyntaxException {
        return apiAccessService.getRecords(airtableProductsPath, new ParameterizedTypeReference<>() {
        });
    }

    public List<String> getAllProductNames() throws URISyntaxException {
        RecordsResponse<Record<Product>> response = getAllProducts();
        return CommonUtil.getObjectFromResponse(response).stream().map(Product::getName).toList();
    }

    public void createProduct(String productName, int count) throws URISyntaxException {
        RecordsCreateRequest<RecordToCreate> productRecordRecords = new RecordsCreateRequest<>();
        productRecordRecords.setRecords(List.of(new RecordToCreate(new ProductToCreate(productName, count))));
        apiAccessService.createRecord(productRecordRecords, airtableProductsPath, new ParameterizedTypeReference<>() {});
    }

    public void updateProductCount(String productId, int count) throws URISyntaxException {
        RecordToCreate recordToUpdate = new RecordToCreate();
        recordToUpdate.setFields(new ProductToUpdate(count));
        apiAccessService.updateRecord(recordToUpdate, airtableProductsPath + "/" + productId, new ParameterizedTypeReference<>() {
        });
    }

    public void deleteProductRow(String id) throws URISyntaxException {
        apiAccessService.deleteRecord(airtableProductsPath + "/" + id, new ParameterizedTypeReference<>() {});
    }
}
