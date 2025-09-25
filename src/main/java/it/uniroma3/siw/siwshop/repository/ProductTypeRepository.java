package it.uniroma3.siw.siwshop.repository;

import it.uniroma3.siw.siwshop.model.Product;
import it.uniroma3.siw.siwshop.model.ProductType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductTypeRepository extends JpaRepository<ProductType, Long> {
    Optional<ProductType> findByNameIgnoreCase(String name);
    List<ProductType> findByNameContainingIgnoreCase(String name);
}
