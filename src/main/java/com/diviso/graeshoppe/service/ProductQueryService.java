package com.diviso.graeshoppe.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

import com.diviso.graeshoppe.client.product.model.Address;
import com.diviso.graeshoppe.client.product.model.AuxilaryLineItem;
import com.diviso.graeshoppe.client.product.model.AuxilaryLineItemDTO;
import com.diviso.graeshoppe.client.product.model.Category;
import com.diviso.graeshoppe.client.product.model.CategoryDTO;
import com.diviso.graeshoppe.client.product.model.ComboLineItem;
import com.diviso.graeshoppe.client.product.model.ComboLineItemDTO;
import com.diviso.graeshoppe.client.product.model.Discount;
import com.diviso.graeshoppe.client.product.model.EntryLineItem;
import com.diviso.graeshoppe.client.product.model.Location;
import com.diviso.graeshoppe.client.product.model.Product;
import com.diviso.graeshoppe.client.product.model.ProductBundle;
import com.diviso.graeshoppe.client.product.model.ProductDTO;
import com.diviso.graeshoppe.client.product.model.Reason;
import com.diviso.graeshoppe.client.product.model.StockCurrent;
import com.diviso.graeshoppe.client.product.model.StockCurrentDTO;
import com.diviso.graeshoppe.client.product.model.StockEntry;
import com.diviso.graeshoppe.client.product.model.StockEntryBundle;
import com.diviso.graeshoppe.client.product.model.StockEntryDTO;
import com.diviso.graeshoppe.client.product.model.UOM;
import com.diviso.graeshoppe.client.product.model.UOMDTO;

public interface ProductQueryService {

	

	public Page<Product> findAllProductByNameAndStoreId(String name, String storeId, Pageable pageable);

	public Page<Product> findAllProductsByIdpCode(String idpCode, Pageable pageable);
	
	public ProductDTO findProductDTOById(Long id);
	Page<Category> findAllCategoriesByIdpCode(String idpCode, Pageable pageable);
	// findAllCategoriesByNameAndStoreId
		public Page<Category> findAllCategoriesByNameAndIdpCode(String name, String idpCode, Pageable pageable);
		// findAllCategoriesWithOutImage
		public Page<CategoryDTO> findAllCategoryDTOsByIdpCode(String iDPcode, Pageable pageable);
		public Page<StockEntry> findAllStockEntriesbyIdpCode(String idpCode, Pageable pageable);
		public StockEntryDTO findStockEntryDTOById(Long id);
		public Page<UOM> findUOMByIDPcode(String iDPcode, Pageable pageable);
		public UOMDTO findUOMDTOById(Long id);
		public CategoryDTO findCategoryDTOById(Long id);
		public ComboLineItemDTO findCombolineItemById(Long id);

		public AuxilaryLineItemDTO findAuxilaryLineItemById(Long id);
		public Page<Product> findNotAuxNotComboProductsByIDPcode(String iDPcode, Pageable pageable);
		public Page<Product> findAllAuxilaryProducts(String storeId, Pageable pageable);
		public Product findProductById(Long id);
		public Category findCategoryById(Long id);
		public Page<Location> findLocationByIdpcode(String idpcode, Pageable pageable);
		public Page<Reason> findReasonByIdpcode(String idpcode, Pageable pageable);
		public Page<EntryLineItem> findAllEntryLineItemsByStockEntryId(String id, Pageable pageable);
		public UOM findUOMById(Long id);
		public Page<AuxilaryLineItem> findAuxilaryLineItemsByIDPcode(String iDPcode, Pageable pageable);
		public StockEntryBundle getStockEntryBundleById(Long id);
		public ProductBundle getProductBundleById( Long id);
		public Page<EntryLineItem> findAllEntryLineItemsByIdpCode(String idpCode, Pageable pageable);

		
	

}
