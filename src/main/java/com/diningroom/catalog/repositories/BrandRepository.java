package com.diningroom.catalog.repositories;

import com.diningroom.catalog.entitties.Brand;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BrandRepository extends JpaRepository<Brand, Long> {
}
