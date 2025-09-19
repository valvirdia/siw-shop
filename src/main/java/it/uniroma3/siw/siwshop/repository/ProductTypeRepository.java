package it.uniroma3.siw.siwshop.repository;

import it.uniroma3.siw.siwshop.model.ProductType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductTypeRepository extends JpaRepository<ProductType, Long> {
}
