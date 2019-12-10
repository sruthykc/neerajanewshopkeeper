package com.diviso.graeshoppe.service.mapper;

import org.mapstruct.*;

import com.diviso.graeshoppe.client.product.model.Manufacturer;
import com.diviso.graeshoppe.client.product.model.ManufacturerDTO;

/**
 * Mapper for the entity Manufacturer and its DTO ManufacturerDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ManufacturerMapper extends EntityMapper<ManufacturerDTO, Manufacturer> {



    default Manufacturer fromId(Long id) {
        if (id == null) {
            return null;
        }
        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setId(id);
        return manufacturer;
    }
}