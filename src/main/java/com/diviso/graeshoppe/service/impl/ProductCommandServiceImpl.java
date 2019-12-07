package com.diviso.graeshoppe.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.diviso.graeshoppe.client.product.api.ProductResourceApi;
import com.diviso.graeshoppe.client.product.model.ProductDTO;
import com.diviso.graeshoppe.service.ProductCommandService;

@Service
public class ProductCommandServiceImpl implements ProductCommandService{
	
	@Autowired
	private ProductResourceApi productResourceApi;

	@Override
	public ResponseEntity<ProductDTO> createProduct(ProductDTO productDTO) {
		
		return productResourceApi.createProductUsingPOST(productDTO);
	}

	@Override
	public ResponseEntity<ProductDTO> updateProduct(ProductDTO productDTO) {
		
		return productResourceApi.updateProductUsingPUT(productDTO);
		
	}

	@Override
	public void deleteProduct(Long id) {
		
		productResourceApi.deleteProductUsingDELETE(id);
		
	}

}
