package com.quangtoi.flowerstore.service;

import com.quangtoi.flowerstore.model.Supplier;

import java.util.List;

public interface SupplierService {
    Supplier save(Supplier supplier);
    Supplier update(Supplier supplier, Long id);
    void delete(Long id);
    List<Supplier> getAll();
    Supplier getById(Long id);
}
