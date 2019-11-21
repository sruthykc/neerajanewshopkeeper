 /*
 * Copyright 2002-2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.diviso.graeshoppe.client.product.model;

import java.util.List;

/**
 * TODO Provide a detailed description here 
 * @author MayaSanjeev
 * mayabytatech, maya.k.k@lxisoft.com
 */
public class ProductBundle {

	private Product product;
	
	private Discount discount;
	
	
	private List<ComboLineItem> comboLineItems;
	
    private List<AuxilaryLineItem> auxilaryLineItems;

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public List<ComboLineItem> getComboLineItems() {
		return comboLineItems;
	}

	public void setComboLineItems(List<ComboLineItem> comboLineItems) {
		this.comboLineItems = comboLineItems;
	}

	public List<AuxilaryLineItem> getAuxilaryLineItems() {
		return auxilaryLineItems;
	}

	public void setAuxilaryLineItems(List<AuxilaryLineItem> auxilaryLineItems) {
		this.auxilaryLineItems = auxilaryLineItems;
	}

	@Override
	public String toString() {
		return "ProductBundle [product=" + product + ", comboLineItems=" + comboLineItems + ", auxilaryLineItems="
				+ auxilaryLineItems + "]";
	}

	public Discount getDiscount() {
		return discount;
	}

	public void setDiscount(Discount discount) {
		this.discount = discount;
	}

    
    
    
	
}
