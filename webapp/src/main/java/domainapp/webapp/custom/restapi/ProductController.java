package domainapp.webapp.custom.restapi;

import java.net.URISyntaxException;
import java.util.List;


import domainapp.modules.simple.dom.inventory.product.Product;
import domainapp.modules.simple.external.Record;

import domainapp.modules.simple.external.RecordsResponse;
import domainapp.modules.simple.service.APIAccessService;

import domainapp.modules.simple.service.ProductService;
import domainapp.modules.simple.util.CommonUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductController {

    @Autowired
    ProductService productService;

	@GetMapping("/products")
	public List<Product> showAllProducts() throws URISyntaxException {
		RecordsResponse<Record<Product>> response = productService.getAllProducts();
		return CommonUtil.getObjectFromResponse(response);
	}

	@PutMapping("/products")
	public void addProduct(@RequestBody Product product) throws URISyntaxException {
		List<Record<Product>> productRecords = productService.getAllProducts().getRecords();
		int currentQuantity = 0;
		String productId = null;
		String name = product.getName();
		for (Record<Product> p : productRecords) {
            if (p.getFields().getName().equals(name)) {
                currentQuantity = currentQuantity + p.getFields().getQuantity();
				productId = p.getId();
            }
		}
		int quantity = product.getQuantity();
		if (currentQuantity == 0) {
            productService.createProduct(name, quantity);
		} else {
            productService.updateProductCount(productId,  quantity + currentQuantity);
		}


	}
}
