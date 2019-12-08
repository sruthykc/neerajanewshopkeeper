package com.diviso.graeshoppe.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.diviso.graeshoppe.client.product.api.AddressResourceApi;
import com.diviso.graeshoppe.client.product.api.AuxilaryLineItemResourceApi;
import com.diviso.graeshoppe.client.product.api.CategoryResourceApi;
import com.diviso.graeshoppe.client.product.api.ComboLineItemResourceApi;
import com.diviso.graeshoppe.client.product.api.DiscountResourceApi;
import com.diviso.graeshoppe.client.product.api.EntryLineItemResourceApi;
import com.diviso.graeshoppe.client.product.api.LocationResourceApi;
import com.diviso.graeshoppe.client.product.api.ProductResourceApi;
import com.diviso.graeshoppe.client.product.api.ReasonResourceApi;
import com.diviso.graeshoppe.client.product.api.StockCurrentResourceApi;
import com.diviso.graeshoppe.client.product.api.StockEntryResourceApi;
import com.diviso.graeshoppe.client.product.api.UomResourceApi;
import com.diviso.graeshoppe.client.product.model.AddressDTO;
import com.diviso.graeshoppe.client.product.model.AuxilaryLineItemDTO;
import com.diviso.graeshoppe.client.product.model.CategoryDTO;
import com.diviso.graeshoppe.client.product.model.ComboLineItemDTO;
import com.diviso.graeshoppe.client.product.model.DiscountDTO;
import com.diviso.graeshoppe.client.product.model.EntryLineItemDTO;
import com.diviso.graeshoppe.client.product.model.LocationDTO;
import com.diviso.graeshoppe.client.product.model.ProductDTO;
import com.diviso.graeshoppe.client.product.model.ReasonDTO;
import com.diviso.graeshoppe.client.product.model.StockCurrentDTO;
import com.diviso.graeshoppe.client.product.model.StockEntryDTO;
import com.diviso.graeshoppe.client.product.model.UOMDTO;
import com.diviso.graeshoppe.service.ProductCommandService;

@Service
public class ProductCommandServiceImpl implements ProductCommandService{
	
	private final Logger log = LoggerFactory.getLogger(ProductCommandServiceImpl.class);
	
	@Autowired
	private ProductResourceApi productResourceApi;
	
	@Autowired
	CategoryResourceApi categoryResourceApi;
	
	@Autowired
	private UomResourceApi uomResourceApi;

	@Autowired 
	private StockEntryResourceApi stockEntryResourceApi;
	
	@Autowired
	private StockCurrentResourceApi stockCurrentResourceApi;

	@Autowired
	AuxilaryLineItemResourceApi auxilaryLineItemResourceApi;

	@Autowired
	ComboLineItemResourceApi comboLineItemResourceApi;
	
	@Autowired
	private DiscountResourceApi discountResourceApi;
	
	@Autowired
    private EntryLineItemResourceApi entryLineItemResourceApi;
    
	@Autowired
	private ReasonResourceApi reasonResourceApi;
	
    @Autowired
    private LocationResourceApi locationResourceApi;
    
    @Autowired
    AddressResourceApi addressResourceApi; 
	    
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
	
	@Override
	public ResponseEntity<CategoryDTO> createProductCategory(CategoryDTO categoryDTO) {
		
		return categoryResourceApi.createCategoryUsingPOST(categoryDTO);
	}
	
	/**
	 * 
	 * @param categoryDTO
	 * @return category object
	 * 
	 * @description update category
	 */

	public ResponseEntity<CategoryDTO> updateCategory(CategoryDTO categoryDTO) {

		return categoryResourceApi.updateCategoryUsingPUT(categoryDTO);
	}

	@Override
	public void deleteCategory(Long id) {
		
		categoryResourceApi.deleteCategoryUsingDELETE(id);
	}

	@Override
	public ResponseEntity<UOMDTO> createUOM(UOMDTO uomDTO) {
		
		return uomResourceApi.createUOMUsingPOST(uomDTO);
	}

	@Override
	public ResponseEntity<UOMDTO> updateUOM(UOMDTO uomDTO) {
		
		return uomResourceApi.updateUOMUsingPUT(uomDTO);
	}

	@Override
	public void deleteUOM(Long id) {
		
		uomResourceApi.deleteUOMUsingDELETE(id);
	}

	@Override
	public ResponseEntity<StockEntryDTO> createStockEntry(StockEntryDTO stockEntryDTO) {
		
		return stockEntryResourceApi.createStockEntryUsingPOST(stockEntryDTO);
	}

	@Override
	public ResponseEntity<StockEntryDTO> updateStockEntry(StockEntryDTO stockEntryDTO) {
		
		return stockEntryResourceApi.updateStockEntryUsingPUT(stockEntryDTO);
	}

	@Override
	public ResponseEntity<Void> deleteStockEntry(Long id) {
		
		return stockEntryResourceApi.deleteStockEntryUsingDELETE(id);
	}

	@Override
	public ResponseEntity<StockCurrentDTO> createStockCurrent(StockCurrentDTO stockCurrent) {
		
		return stockCurrentResourceApi.createStockCurrentUsingPOST(stockCurrent);
	}

	@Override
	public ResponseEntity<StockCurrentDTO> updateStockCurrent(StockCurrentDTO StockCurrent) {
		
		return stockCurrentResourceApi.updateStockCurrentUsingPUT(StockCurrent);

	}

	@Override
	public ResponseEntity<AuxilaryLineItemDTO> createAuxilaryLineItem(AuxilaryLineItemDTO auxilaryLineItemDTO) {
		
		return auxilaryLineItemResourceApi.createAuxilaryLineItemUsingPOST(auxilaryLineItemDTO);
	}

	@Override
	public ResponseEntity<AuxilaryLineItemDTO> updateAuxilaryLineItem(AuxilaryLineItemDTO auxilaryLineItemDTO) {
		
		return auxilaryLineItemResourceApi.updateAuxilaryLineItemUsingPUT(auxilaryLineItemDTO);
	}

	@Override
	public ResponseEntity<Void> deleteAuxilaryLineIteam(Long id) {
		
		return auxilaryLineItemResourceApi.deleteAuxilaryLineItemUsingDELETE(id);
	}

	@Override
	public ResponseEntity<ComboLineItemDTO> createComboLineItem(ComboLineItemDTO comboLineItemDTO) {
		
		return comboLineItemResourceApi.createComboLineItemUsingPOST(comboLineItemDTO);
	}

	@Override
	public ResponseEntity<ComboLineItemDTO> updateComboLineItem(ComboLineItemDTO comboLineItemDTO) {
		
		return comboLineItemResourceApi.updateComboLineItemUsingPUT(comboLineItemDTO);
	}

	@Override
	public ResponseEntity<Void> deleteComboLineItem(Long id) {
		
		return comboLineItemResourceApi.deleteComboLineItemUsingDELETE(id);
	}

	@Override
	public ResponseEntity<DiscountDTO> createDiscount(DiscountDTO discountDTO) {
		
		return discountResourceApi.createDiscountUsingPOST(discountDTO);
	}

	@Override
	public ResponseEntity<DiscountDTO> updateDiscount(DiscountDTO discountDTO) {
		
		return  discountResourceApi.updateDiscountUsingPUT(discountDTO);
	}

	@Override
	public ResponseEntity<Void> deleteDiscount(Long id) {
		
		return discountResourceApi.deleteDiscountUsingDELETE(id);
	}

	@Override
	public ResponseEntity<EntryLineItemDTO> createEntryLineItem(EntryLineItemDTO entrylineitemDTO) {
		
		log.info("////////////////////////////////////////////////////"+entrylineitemDTO);
		return entryLineItemResourceApi.createEntryLineItemUsingPOST(entrylineitemDTO);
	}

	@Override
	public ResponseEntity<EntryLineItemDTO> updateEntryLineItem(EntryLineItemDTO entrylineitemDTO) {
		
		return entryLineItemResourceApi.updateEntryLineItemUsingPUT(entrylineitemDTO);
	}

	@Override
	public ResponseEntity<Void> deleteEntryLineItem(Long id) {
		
		return entryLineItemResourceApi.deleteEntryLineItemUsingDELETE(id);
	}

	@Override
	public ResponseEntity<ReasonDTO> createReason(ReasonDTO reasonDTO) {
		
		return reasonResourceApi.createReasonUsingPOST(reasonDTO);
	}

	@Override
	public ResponseEntity<ReasonDTO> updateReason(ReasonDTO reasonDTO) {
		
		return reasonResourceApi.updateReasonUsingPUT(reasonDTO);
	}

	@Override
	public ResponseEntity<Void> deleteReason(Long id) {
		
		return reasonResourceApi.deleteReasonUsingDELETE(id);
	}

	@Override
	public ResponseEntity<LocationDTO> createLocation(LocationDTO locationDTO) {
		
		return locationResourceApi.createLocationUsingPOST(locationDTO);
	}

	@Override
	public ResponseEntity<LocationDTO> updateLocation(LocationDTO locationDTO) {
		
		return locationResourceApi.updateLocationUsingPUT(locationDTO);
	}

	@Override
	public ResponseEntity<Void> deleteLocation(Long id) {
		
		return locationResourceApi.deleteLocationUsingDELETE(id);
	}

	@Override
	public ResponseEntity<AddressDTO> createProductAddress(AddressDTO addressDTO) {
		
		return addressResourceApi.createAddressUsingPOST(addressDTO);
	}

	@Override
	public ResponseEntity<AddressDTO> updateProductAddress(AddressDTO addressDTO) {
		
		return addressResourceApi.updateAddressUsingPUT(addressDTO);
	}

	@Override
	public ResponseEntity<Void> deleteProductAddress(Long id) {
		
		return addressResourceApi.deleteAddressUsingDELETE(id);
	}

	
	
	
	
}
