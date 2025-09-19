package it.uniroma3.siw.siwshop.service;

import it.uniroma3.siw.siwshop.repository.ProductTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductTypeService {

    @Autowired
    private ProductTypeRepository productTypeRepository;

}
