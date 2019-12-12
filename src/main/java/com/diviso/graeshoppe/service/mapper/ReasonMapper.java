package com.diviso.graeshoppe.service.mapper;

import org.mapstruct.*;

import com.diviso.graeshoppe.client.product.model.Reason;
import com.diviso.graeshoppe.client.product.model.ReasonDTO;

/**
 * Mapper for the entity Reason and its DTO ReasonDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ReasonMapper extends EntityMapper<ReasonDTO, Reason> {



    default Reason fromId(Long id) {
        if (id == null) {
            return null;
        }
        Reason reason = new Reason();
        reason.setId(id);
        return reason;
    }
}