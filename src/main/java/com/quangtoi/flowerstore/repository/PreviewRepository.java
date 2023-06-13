package com.quangtoi.flowerstore.repository;

import com.quangtoi.flowerstore.model.Flower;
import com.quangtoi.flowerstore.model.Previews;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PreviewRepository extends JpaRepository<Previews, Long> {
    List<Previews> findAllByFlower(Flower flower);

    @Query("select coalesce(AVG(p.preview), 0) as avgScore from Previews p join Flower f on f.id=p.flower.id where f.id = :flowerId")
    double getAvgScoreByFlowerId(@Param("flowerId")Long flowerId);
}
