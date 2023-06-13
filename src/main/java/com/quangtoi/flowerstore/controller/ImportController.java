package com.quangtoi.flowerstore.controller;

import com.quangtoi.flowerstore.dto.ImportDto;
import com.quangtoi.flowerstore.model.Import;
import com.quangtoi.flowerstore.service.ImportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/imports")
@CrossOrigin
public class ImportController {
    private ImportService importService;

    //test ok
    @Operation(
            summary = "Get all imports REST API"
    )
    @GetMapping
    public ResponseEntity<List<ImportDto>> getAllImports(){
        return new ResponseEntity<>(importService.getAll(), HttpStatus.CREATED);
    }

    //test ok
    @Operation(
            summary = "Get import by id REST API"
    )
    @GetMapping("/{id}")
    public ResponseEntity<ImportDto> getImportById(@PathVariable("id") Long id){
        return new ResponseEntity<>(importService.getById(id), HttpStatus.CREATED);
    }

    //test ok
    @Operation(
            summary = "Create import REST API"
    )
    @SecurityRequirement(
            name = "Bearer Authentication"
    )
    @PostMapping
    public ResponseEntity<ImportDto> saveImport(@RequestBody ImportDto importInfo){
        return new ResponseEntity<>(importService.save(importInfo), HttpStatus.CREATED);
    }

    //test ok
    @Operation(
            summary = "Update import REST API"
    )
    @SecurityRequirement(
            name = "Bearer Authentication"
    )
    @PutMapping("/{id}")
    public ResponseEntity<ImportDto> updateImport(@RequestBody ImportDto importInfo, @PathVariable("id") Long id){
        return new ResponseEntity<>(importService.update(importInfo, id), HttpStatus.OK);
    }


    //test ok
    @Operation(
            summary = "Delete import REST API"
    )
    @SecurityRequirement(
            name = "Bearer Authentication"
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteImport(@PathVariable("id") Long id){
        importService.delete(id);
        return new ResponseEntity<>("Delete successfully!", HttpStatus.OK);
    }

}
