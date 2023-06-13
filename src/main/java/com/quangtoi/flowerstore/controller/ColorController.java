package com.quangtoi.flowerstore.controller;

import com.quangtoi.flowerstore.model.Color;
import com.quangtoi.flowerstore.service.ColorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/colors")
@CrossOrigin
public class ColorController {
    private ColorService colorService;

    //test ok
    @Operation(summary = "Get all colors")
    @GetMapping
    public ResponseEntity<List<Color>> getAllColors(){
        return ResponseEntity.ok().body(colorService.getAllColors());
    }

    //test ok
    @Operation(summary = "Get color by id")
    @GetMapping("/{id}")
    public ResponseEntity<Color> getColorById(@PathVariable("id")Long id){
        return ResponseEntity.ok().body(colorService.getColorById(id));
    }

    //test ok
    @Operation(summary = "Update color by id")
    @SecurityRequirement(
            name = "Bearer Authentication"
    )
    @PutMapping("/{id}")
    public ResponseEntity<Color> updateColor(@PathVariable("id")Long id, @RequestBody Color color){
        return ResponseEntity.ok().body(colorService.update(color, id));
    }

    //test ok
    @SecurityRequirement(
            name = "Bearer Authentication"
    )
    @Operation(summary = "Create color")
    @PostMapping
    public ResponseEntity<Color> saveColor(@RequestBody Color color){
        return new ResponseEntity<>(colorService.save(color), HttpStatus.CREATED);
    }

    //test ok
    @SecurityRequirement(
            name = "Bearer Authentication"
    )
    @Operation(summary = "Delete color by id")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteColor(@PathVariable("id") Long id){
        colorService.delete(id);
        return new ResponseEntity<>("Delete color successfully!", HttpStatus.OK);
    }
}
