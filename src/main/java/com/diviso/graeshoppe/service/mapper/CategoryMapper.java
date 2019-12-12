package com.diviso.graeshoppe.service.mapper;

import org.mapstruct.*;

import com.diviso.graeshoppe.client.product.model.Category;
import com.diviso.graeshoppe.client.product.model.CategoryDTO;

/**
 * Mapper for the entity Category and its DTO CategoryDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CategoryMapper extends EntityMapper<CategoryDTO, Category> {
	 /* @Override
	 @Mapping(source = "id", target = "id")
	 @Mapping(source = "iDPcode", target = "idpCode")
	 @Mapping(source = "name", target = "name")
	 @Mapping(source = "image", target = "image")
	 @Mapping(source = "imageContentType", target = "imageContentType")
	 @Mapping(source = "imageLink", target = "imageLink")
	 @Mapping(source = "description", target = "description")
	CategoryDTO toDto(Category category);*/

    @Mapping(target = "products", ignore = true)
    Category toEntity(CategoryDTO categoryDTO);

    default Category fromId(Long id) {
        if (id == null) {
            return null;
        }
        Category category = new Category();
        category.setId(id);
        return category;
    }
}