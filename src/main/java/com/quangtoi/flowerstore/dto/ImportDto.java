package com.quangtoi.flowerstore.dto;

import com.quangtoi.flowerstore.model.Flower;
import com.quangtoi.flowerstore.model.Supplier;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ImportDto {
    private Long id;
    private int quantity;
    private Date dateImport;
    private Long flowerId;
    private Long supplierId;

}
