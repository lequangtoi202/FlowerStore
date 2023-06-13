package com.quangtoi.flowerstore.controller;

import com.quangtoi.flowerstore.model.Supplier;
import com.quangtoi.flowerstore.service.SupplierService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/suppliers")
@CrossOrigin
public class SupplierController {
    private SupplierService supplierService;

    //test ok
    @Operation(
            summary = "Create supplier REST API"
    )
    @SecurityRequirement(
            name = "Bearer Authentication"
    )
    @PostMapping()
    public ResponseEntity<Supplier> postSupplier(@RequestBody Supplier supplier){
        return new ResponseEntity<>(supplierService.save(supplier), HttpStatus.CREATED);
    }


    //test ok
    @Operation(
            summary = "Create supplier REST API"
    )
    @SecurityRequirement(
            name = "Bearer Authentication"
    )
    @PutMapping("/{id}")
    public ResponseEntity<Supplier> updateSupplier(@RequestBody Supplier supplier, @PathVariable("id") Long id){
        return new ResponseEntity<>(supplierService.update(supplier, id), HttpStatus.OK);
    }

    //test ok
    @Operation(
            summary = "Create supplier REST API"
    )
    @SecurityRequirement(
            name = "Bearer Authentication"
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteSupplier(@PathVariable("id") Long id){
        supplierService.delete(id);
        return new ResponseEntity<>("Delete Supplier successfully!", HttpStatus.OK);
    }

    //test ok
    @Operation(
            summary = "Get supplier by id REST API"
    )
    @GetMapping("/{id}")
    public ResponseEntity<Supplier> getSupplierById(@PathVariable("id") Long id){
        return new ResponseEntity<>(supplierService.getById(id), HttpStatus.OK);
    }

    //test ok
    @Operation(
            summary = "Get all suppliers REST API"
    )
    @GetMapping
    public ResponseEntity<List<Supplier>> getSuppliers(){
        return new ResponseEntity<>(supplierService.getAll(), HttpStatus.OK);
    }

}
