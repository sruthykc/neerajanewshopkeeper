package com.diviso.graeshoppe.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.diviso.graeshoppe.client.product.model.Address;
import com.diviso.graeshoppe.client.product.model.AuxilaryLineItem;
import com.diviso.graeshoppe.client.product.model.Category;
import com.diviso.graeshoppe.client.product.model.ComboLineItem;
import com.diviso.graeshoppe.client.product.model.Discount;
import com.diviso.graeshoppe.client.product.model.EntryLineItem;
import com.diviso.graeshoppe.client.product.model.Location;
import com.diviso.graeshoppe.client.product.model.Product;
import com.diviso.graeshoppe.client.product.model.Reason;
import com.diviso.graeshoppe.client.product.model.StockCurrent;
import com.diviso.graeshoppe.client.product.model.StockEntry;
import com.diviso.graeshoppe.client.product.model.UOM;

public interface ProductQueryService {

	public Page<Product> findProductByCategoryId(Long categoryId, String storeId, Pageable pageable);
	
	public Page<Product> findAllProductBySearchTerm(String searchTerm, String storeId, Pageable pageable);
	
	public Page<Product> findAllProducts(String storeId, Pageable pageable);
	
	public Page<Product> findProducts(Pageable pageable);
	
	/**
	 * @param iDPcode
	 * @param pageable
	 * @return
	 */
	public Page<Product> findNotAuxNotComboProductsByIDPcode(String iDPcode, Pageable pageable);
	
	/**
	 * @return
	 */
	public Page<Product> findAllAuxilaryProducts(String storeId);
	
	/**
	 * @param id
	 * @return
	 */
	public Product findProductById(Long id);
	
	public Page<Category> findAllCategories(Pageable pageable);
	
	public List<String> findAllUom(Pageable pageable);
	
	public Page<EntryLineItem> findAllEntryLineItems(String storeId, Pageable pageable);
	
	public Page<StockCurrent> findAllStockCurrents(String storeId, Pageable pageable);
	
	public Page<StockEntry> findAllStockEntries(String storeId, Pageable pageable);
	
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

	public StockEntry findStockEntryById(Long id);
	
	public Discount findDiscountByProductId(Long productId);
	
	public List<EntryLineItem> findAllEntryLineItemsByStockEntryId(Long id);
	
	public Reason findReasonByStockEntryId(Long id);
	
	public Location findLocationByStockEntryId(Long id);
	
	public Page<Location> findLocationByIdpcode(String idpcode, Pageable pageable);
	
	public Page<Reason> findReasonByIdpcode(String idpcode, Pageable pageable);
	
	public Page<EntryLineItem> findAllEntryLineItemsByStockEntryId(String id, Pageable pageable);

	public Address findAddressByStockEntryId(Long id);
	
	public Page<Category> findAllCategoryBySearchTerm(String searchTerm, String storeId, Pageable pageable);
	
	/**
	 * @param storeId
	 * @param pageable
	 * @return
	 */
	Page<Category> findAllCategories(String storeId, Pageable pageable);


}
