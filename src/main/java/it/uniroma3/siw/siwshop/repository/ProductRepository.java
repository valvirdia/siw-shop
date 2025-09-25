package it.uniroma3.siw.siwshop.repository;

import it.uniroma3.siw.siwshop.model.Product;
import it.uniroma3.siw.siwshop.model.ProductType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    public List<Product> findByNameContainingIgnoreCase(String keyword);

    Optional<Product> findByNameIgnoreCase(String name);

    List<Product> findByProductType(ProductType productType);
}
