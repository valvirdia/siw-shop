package it.uniroma3.siw.siwshop.repository;

import it.uniroma3.siw.siwshop.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
