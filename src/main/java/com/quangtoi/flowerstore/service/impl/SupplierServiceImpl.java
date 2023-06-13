package com.quangtoi.flowerstore.service.impl;

import com.quangtoi.flowerstore.exception.ResourceNotFoundException;
import com.quangtoi.flowerstore.model.Supplier;
import com.quangtoi.flowerstore.repository.SupplierRepository;
import com.quangtoi.flowerstore.service.SupplierService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class SupplierServiceImpl implements SupplierService {
    private SupplierRepository supplierRepository;
    @Override
    public Supplier save(Supplier supplier) {
        return supplierRepository.save(supplier);
    }

    @Override
    public Supplier update(Supplier supplier, Long id) {
        Supplier supplierSaved = supplierRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Supplier", "id", id));
        supplierSaved.setAddress(supplier.getAddress());
        supplierSaved.setContactPerson(supplier.getContactPerson());
        supplierSaved.setPhoneNumber(supplier.getPhoneNumber());
        supplierSaved.setName(supplier.getName());
        supplierSaved.setEmail(supplier.getEmail());

        return supplierRepository.save(supplierSaved);
    }

    @Override
    public void delete(Long id) {
        Supplier supplierSaved = supplierRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Supplier", "id", id));
        supplierRepository.delete(supplierSaved);
    }

    @Override
    public List<Supplier> getAll() {
        return supplierRepository.findAll();
    }

    @Override
    public Supplier getById(Long id) {
        Supplier supplierSaved = supplierRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Supplier", "id", id));
        return supplierSaved;
    }
}
