package com.quangtoi.flowerstore.repository;

import com.quangtoi.flowerstore.model.Category;
import com.quangtoi.flowerstore.model.Flower;
import com.quangtoi.flowerstore.model.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FlowerRepository extends JpaRepository<Flower, Long> {
    List<Flower> findByCategory(Category category);

    @Query(value = "SELECT f.id, f.name, f.description, f.category_id, f.created_at, f.stock_quantity, f.unit_price, f.updated_at\n" +
            "FROM flowers f\n" +
            "JOIN imports i ON f.id = i.flower_id\n" +
            "GROUP BY f.id, f.name\n" +
            "ORDER BY SUM(i.quantity) DESC\n" +
            "LIMIT 10;",nativeQuery = true)
    List<Flower> findByBestSeller();

    @Query(value = "SELECT f.id, f.name, f.description, f.category_id, f.created_at, f.stock_quantity, f.unit_price, f.updated_at\n" +
            "    FROM flowers f\n" +
            "    JOIN previews p ON f.id = p.flower_id\n" +
            "    GROUP BY f.id, f.name\n" +
            "    HAVING AVG(p.preview) >= 4.0\n" +
            "    ORDER BY AVG(p.preview) DESC;",nativeQuery = true)
    List<Flower> findByFavorites();

    @Query(value = "select f.id, f.name, f.description, f.stock_quantity, f.unit_price, f.updated_at, f.category_id, f.created_at " +
            "from (flowers f inner join imports i on i.flower_id = f.id) " +
            "inner join suppliers s on s.id = i.supplier_id where s.id = :id", nativeQuery = true)
    List<Flower> getAllBySuppliers(@Param("id") Long id);

    @Query(value = "select f.id,f.category_id,f.created_at,f.description,f.name,f.stock_quantity,f.unit_price,f.updated_at " +
            "from flowers f where f.name like %:kw%", nativeQuery = true)
    List<Flower> getFlowersByKeyword(@Param("kw") String kw);

}
