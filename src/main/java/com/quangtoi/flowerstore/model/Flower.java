package com.quangtoi.flowerstore.model;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "flowers")
public class Flower implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;

    @Column(name = "unit_price")
    private double unitPrice;

    @Column(name = "stock_quantity")
    private int stockQuantity;

    @Column(name = "url_image")
    private String urlImage;

    @OneToMany(mappedBy = "flower", cascade = CascadeType.ALL)
    private List<CartItem> cartItems;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "flowers_colors",
            joinColumns = @JoinColumn(name = "flower_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "color_id", referencedColumnName = "id")
    )
    private Set<Color> colors;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "flower")
    private List<Previews> previews;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "flower")
    private List<Import> imports;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
