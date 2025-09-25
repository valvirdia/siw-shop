package it.uniroma3.siw.siwshop.service;

import it.uniroma3.siw.siwshop.model.Product;
import it.uniroma3.siw.siwshop.repository.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public Iterable<Product> findAll() {
        return productRepository.findAll();
    }

    public Product findById(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    @Transactional
    public Product save(Product product) {
        return productRepository.save(product);
    }

    public List<Product> searchByName(String keyword) {
        return productRepository.findByNameContainingIgnoreCase(keyword);
    }

    public Optional<Product> findProductByName(String name) {
        return productRepository.findByNameIgnoreCase(name);
    }

    @Transactional
    public List<Product> findSimilarProducts(Product product) {
        if (product.getProductType() == null) {
            return new ArrayList<>();
        }
        return productRepository.findByProductType(product.getProductType());
    }

    @Transactional
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

}
