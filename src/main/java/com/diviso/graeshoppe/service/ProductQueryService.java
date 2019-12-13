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
import com.diviso.graeshoppe.client.product.model.ProductDTO;
import com.diviso.graeshoppe.client.product.model.Reason;
import com.diviso.graeshoppe.client.product.model.StockCurrent;
import com.diviso.graeshoppe.client.product.model.StockCurrentDTO;
import com.diviso.graeshoppe.client.product.model.StockEntry;
import com.diviso.graeshoppe.client.product.model.StockEntryDTO;
import com.diviso.graeshoppe.client.product.model.UOM;
import com.diviso.graeshoppe.client.product.model.UOMDTO;

public interface ProductQueryService {

	public Page<Product> findProductByCategoryId(Long categoryId, String storeId, Pageable pageable);

	public Page<Product> findAllProductByNameAndStoreId(String name, String storeId, Pageable pageable);

	public Page<Product> findAllProductsByIdpCode(String idpCode, Pageable pageable);

	public Page<Product> findProducts(Pageable pageable);

	/**
	 * @param iDPcode
	 * @param pageable
	 * @return
	 */
	public Page<Product> findNotAuxNotComboProductsByIDPcode(String iDPcode, Pageable pageable);

	/**
	 * @param pageable
	 * @return
	 */
	public Page<Product> findAllAuxilaryProducts(String storeId, Pageable pageable);

	/**
	 * @param id
	 * @return
	 */
	public Product findProductById(Long id);

	public Page<Category> findAllCategories(Pageable pageable);

	public List<String> findAllUom(Pageable pageable);

	public Page<EntryLineItem> findAllEntryLineItemsByIdpCode(String idpCode, Pageable pageable);

	public Page<StockCurrent> findAllStockCurrents(String storeId, Pageable pageable);

	public Page<StockEntry> findAllStockEntriesbyIdpCode(String idpCode, Pageable pageable);

	public Page<StockCurrent> findAllStockCurrentByCategoryId(Long categoryId, String storeId, Pageable pageable);

	public StockCurrent findStockCurrentByProductId(Long productId, String storeId);

	public StockEntry findStockEntryByProductId(Long productId, String storeId);

	public Page<StockCurrent> findStockCurrentByProductName(String name, String storeId, Pageable pageable);

	/**
	 * @param storeId
	 * @param pageable
	 * @return
	 */
	public Page<AuxilaryLineItem> findAuxilaryLineItemsByIDPcode(String iDPcode, Pageable pageable);

	/**
	 * @param storeId
	 * @param pageable
	 * @return
	 */
	public Page<UOM> findUOMByIDPcode(String iDPcode, Pageable pageable);

	/**
	 * @param id
	 * @return
	 */
	public Category findCategoryById(Long id);

	/**
	 * @param id
	 * @return
	 */
	public UOM findUOMById(Long id);

	/**
	 * @param id
	 */
	public List<ComboLineItem> finAllComboLineItemsByProductId(Long id);

	/**
	 * @param productId
	 * 
	 */
	public List<AuxilaryLineItem> findAllAuxilaryProductsByProductId(Long productId);

	/**
	 * 
	 * @param id
	 * @return
	 */
	public StockEntry findStockEntryById(Long id);

	/**
	 * 
	 * @param productId
	 * @return
	 */
	public Discount findDiscountByProductId(Long productId);

	/**
	 * 
	 * @param id
	 * @return
	 */
	public List<EntryLineItem> findAllEntryLineItemsByStockEntryId(Long id);

	/**
	 * 
	 * @param id
	 * @return
	 */
	public Reason findReasonByStockEntryId(Long id);

	/**
	 * 
	 * @param id
	 * @return
	 */
	public Location findLocationByStockEntryId(Long id);

	/**
	 * 
	 * @param idpcode
	 * @param pageable
	 * @return
	 */
	public Page<Location> findLocationByIdpcode(String idpcode, Pageable pageable);

	/**
	 * 
	 * @param idpcode
	 * @param pageable
	 * @return
	 */
	public Page<Reason> findReasonByIdpcode(String idpcode, Pageable pageable);

	/**
	 * 
	 * @param id
	 * @param pageable
	 * @return
	 */
	public Page<EntryLineItem> findAllEntryLineItemsByStockEntryId(String id, Pageable pageable);

	/**
	 * 
	 * @param id
	 * @return
	 */
	public Address findAddressByStockEntryId(Long id);

	// public Page<Category> findAllCategoryBySearchTerm(String searchTerm, String
	// storeId, Pageable pageable);

	/**
	 * @param storeId
	 * @param pageable
	 * @return
	 */
	Page<Category> findAllCategoriesByIdpCode(String idpCode, Pageable pageable);

	/**
	 * 
	 * @param searchTerm
	 * @param storeId
	 * @param pageable
	 * @return
	 */
	// findAllCategoriesByNameAndStoreId
	public Page<Category> findAllCategoriesByNameAndIdpCode(String name, String idpCode, Pageable pageable);

	public UOMDTO findUOMDTOById(Long id);

	// findAllCategoriesWithOutImage
	public Page<CategoryDTO> findAllCategoryDTOsByIdpCode(String iDPcode, Pageable pageable);

	public CategoryDTO findCategoryDTOById(Long id);

	public ProductDTO findProductDTOById(Long id);
	/*
	 * public ResponseEntity<List<StockCurrentDTO>>
	 * searchStockCurrents(@PathVariable String searchTerm, Integer page, Integer
	 * size, ArrayList<String> sort);
	 */

	// public ResponseEntity<StockEntryDTO> findOneStockEntry(Long id) ;
	public StockEntryDTO findStockEntryDTOById(Long id);

	public ComboLineItemDTO findCombolineItemById(Long id);

	public AuxilaryLineItemDTO findAuxilaryLineItemById(Long id);

}
