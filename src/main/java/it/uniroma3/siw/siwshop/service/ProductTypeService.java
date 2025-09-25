package it.uniroma3.siw.siwshop.service;

import it.uniroma3.siw.siwshop.model.Product;
import it.uniroma3.siw.siwshop.model.ProductType;
import it.uniroma3.siw.siwshop.repository.ProductTypeRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductTypeService {

    @Autowired
    private ProductTypeRepository productTypeRepository;

    @Transactional
    public List<ProductType> findAll() {
        return (List<ProductType>) productTypeRepository.findAll();
    }

    @Transactional
    public ProductType findOrCreateByName(String name) {
        String formattedName = name.trim();
        if (formattedName.isEmpty()) {
            return null;
        }
        formattedName = formattedName.substring(0, 1).toUpperCase() + formattedName.substring(1).toLowerCase();
        Optional<ProductType> existingType = this.productTypeRepository.findByNameIgnoreCase(formattedName);

        if (existingType.isPresent()) {
            return existingType.get();
        } else {
            ProductType newType = new ProductType();
            newType.setName(formattedName);
            return this.productTypeRepository.save(newType);
        }
    }

    @Transactional
    public List<ProductType> searchByName(String name) {
         return productTypeRepository.findByNameContainingIgnoreCase(name);
    }
}
