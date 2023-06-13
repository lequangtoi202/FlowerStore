package com.quangtoi.flowerstore.repository;

import com.quangtoi.flowerstore.model.Import;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImportRepository extends JpaRepository<Import, Long> {
}
