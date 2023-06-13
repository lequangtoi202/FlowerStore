package com.quangtoi.flowerstore.service.impl;

import com.quangtoi.flowerstore.dto.ImportDto;
import com.quangtoi.flowerstore.exception.ResourceNotFoundException;
import com.quangtoi.flowerstore.model.Flower;
import com.quangtoi.flowerstore.model.Import;
import com.quangtoi.flowerstore.model.Supplier;
import com.quangtoi.flowerstore.repository.FlowerRepository;
import com.quangtoi.flowerstore.repository.ImportRepository;
import com.quangtoi.flowerstore.repository.SupplierRepository;
import com.quangtoi.flowerstore.service.ImportService;
import com.quangtoi.flowerstore.service.SupplierService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
@Transactional
public class ImportServiceImpl implements ImportService {
    private FlowerRepository flowerRepository;
    private SupplierRepository supplierRepository;
    private ImportRepository importRepository;
    private ModelMapper mapper;

    @Override
    public ImportDto save(ImportDto importInfo) {
        Flower flower = flowerRepository
                .findById(importInfo.getFlowerId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Flower", "id",
                                importInfo.getFlowerId()));
        int oldStockQuantity = flower.getStockQuantity();
        flower.setStockQuantity(oldStockQuantity + importInfo.getQuantity());
        flowerRepository.save(flower);
        Import importSaved = new Import();
        Supplier supplier = supplierRepository.findById(importInfo.getSupplierId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Supplier", "id",
                                importInfo.getSupplierId()));
        importSaved.setSupplier(supplier);
        importSaved.setFlower(flower);
        importSaved.setDateImport(importInfo.getDateImport());
        importSaved.setQuantity(importInfo.getQuantity());

        return mapper.map(importRepository.save(importSaved), ImportDto.class);
    }

    @Override
    public List<ImportDto> getAll() {
        return importRepository.findAll()
                .stream()
                .map((im) -> mapper.map(im, ImportDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public ImportDto getById(Long id) {
        Import importSaved = importRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Import", "id", id));

        return mapper.map(importSaved, ImportDto.class);
    }

    @Override
    public ImportDto update(ImportDto importInfo, Long id) {
        Import importSaved = importRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Import", "id", id));

        Flower flower = flowerRepository
                .findById(importInfo.getFlowerId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Flower", "id",
                                importInfo.getFlowerId()));

        int oldStockQuantity = flower.getStockQuantity();
        int balance = importSaved.getQuantity() - importInfo.getQuantity();

        if (balance < 0){
            flower.setStockQuantity(oldStockQuantity + Math.abs(balance));
        }else{
            flower.setStockQuantity(oldStockQuantity - Math.abs(balance));
        }
        Flower flowerSaved = flowerRepository.save(flower);


        importSaved.setDateImport(importInfo.getDateImport());
        importSaved.setQuantity(importInfo.getQuantity());
        importSaved.setFlower(flowerSaved);
        Supplier supplier = supplierRepository.findById(importInfo.getSupplierId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Supplier", "id",
                                importInfo.getSupplierId()));
        importSaved.setSupplier(supplier);

        return mapper.map(importRepository.save(importSaved), ImportDto.class);
    }

    @Override
    public void delete(Long id) {
        Import importSaved = importRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Import", "id", id));
        importRepository.delete(importSaved);
    }
}
