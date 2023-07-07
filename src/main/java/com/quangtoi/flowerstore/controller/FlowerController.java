package com.quangtoi.flowerstore.controller;

import com.quangtoi.flowerstore.dto.FlowerDto;
import com.quangtoi.flowerstore.service.FlowerService;
import com.quangtoi.flowerstore.service.PreviewService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/flowers")
@CrossOrigin
public class FlowerController {
    private FlowerService flowerService;
    private PreviewService previewService;

    //test ok
    @Operation(summary = "Get all flowers REST API")
    @GetMapping
    public ResponseEntity<List<FlowerDto>> getAllFlowers(@RequestParam(value = "kw", required = false, defaultValue = "") String kw){
        return ResponseEntity.ok().body(flowerService.getFlowersByKeyword(kw));
    }

    //test ok
    @Operation(summary = "Get flower by id REST API")
    @GetMapping("/{id}")
    public ResponseEntity<FlowerDto> getFlowerById(@PathVariable("id") Long id){
        return ResponseEntity.ok().body(flowerService.getFlowerById(id));
    }

    //test ok
    @Operation(summary = "Get all flowers best seller REST API")
    @GetMapping("/bestSeller")
    public ResponseEntity<List<FlowerDto>> getFlowerByBestSeller(){
        return ResponseEntity.ok().body(flowerService.getFlowerBestSeller());
    }

    //test ok
    @Operation(summary = "Get all flowers favorites REST API")
    @GetMapping("/favorites")
    public ResponseEntity<List<FlowerDto>> getFlowerByFavorites(){
        return ResponseEntity.ok().body(flowerService.getFlowerFavorites());
    }

    //test ok
    @Operation(summary = "Get flowers by category id REST API")
    @GetMapping("/categories/{id}")
    public ResponseEntity<List<FlowerDto>> getFlowerByCategoryId(@PathVariable("id") Long id){
        return ResponseEntity.ok().body(flowerService.getFlowerByCategoryId(id));
    }

    //tạm ok - chưa test lại
    @Operation(summary = "Get all flowers by supplier id REST API")
    @GetMapping("/supplier/{id}")
    public ResponseEntity<List<FlowerDto>> getFlowerBySupplierId(@PathVariable("id") Long id){
        return ResponseEntity.ok().body(flowerService.getFlowerBySupplierId(id));
    }

    //test ok
    @Operation(summary = "Get all total previews REST API")
    @GetMapping("/{id}/previews/total-previews")
    public ResponseEntity<FlowerDto> getTotalPreviewsAndScoreById(@PathVariable("id") Long id){
        return ResponseEntity.ok().body(previewService.countPreviewsById(id));
    }

    //test ok
    @Operation(summary = "Post preview  to flower REST API")
    @PostMapping("/{id}/previews")
    public ResponseEntity<FlowerDto> postPreviewScore(@PathVariable("id") Long id, @RequestParam("score") double score){
        return new ResponseEntity<>(previewService.postPreviewScoreByFlowerId(id, score), HttpStatus.CREATED);
    }

    //test ok
    @Operation(summary = "Create flower REST API")
    @SecurityRequirement(
            name = "Bearer Authentication"
    )
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<FlowerDto> createFlower(@RequestPart("flower") FlowerDto flowerDto,
                                                  @RequestPart("image") MultipartFile imageFlower){
        FlowerDto flowerDtoResponse = flowerService.saveFlower(flowerDto, imageFlower);
        return new ResponseEntity<>(flowerDtoResponse, HttpStatus.CREATED);
    }

    //test ok
    @Operation(summary = "Update flower REST API")
    @SecurityRequirement(
            name = "Bearer Authentication"
    )
    @PutMapping(value = "/{id}", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE,
                                                MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<FlowerDto> updateFlower(@RequestPart("flower") FlowerDto flowerDto,
                                                  @PathVariable("id") Long id,
                                                  @RequestPart("image") MultipartFile imageFlower){
        FlowerDto flowerDtoRes = flowerService.updateFlowerById(flowerDto, id, imageFlower);

        return new ResponseEntity<>(flowerDtoRes, HttpStatus.OK);
    }

    //test ok
    @Operation(summary = "Delete flower REST API")
    @SecurityRequirement(
            name = "Bearer Authentication"
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteFlower(@PathVariable("id") Long id){
        flowerService.deleteFlowerById(id);
        return new ResponseEntity<>("Delete flower successfully!", HttpStatus.OK);
    }


    @Operation(
            summary = "get  amount of sold flowers REST API"
    )
    @GetMapping("/{flowerId}/sold")
    public ResponseEntity<Integer> getAmountOfSoldFlowers(@PathVariable("flowerId")Long flowerId){
        Integer amount = flowerService.getAmountOfSoldFlowers(flowerId);
        if (amount == null)
            amount = 0;
        return ResponseEntity.ok().body(amount);
    }
}
