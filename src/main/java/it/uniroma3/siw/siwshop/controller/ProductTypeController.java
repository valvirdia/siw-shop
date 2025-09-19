package it.uniroma3.siw.siwshop.controller;

import it.uniroma3.siw.siwshop.service.ProductTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class ProductTypeController {

@Autowired
    private ProductTypeService productTypeService;

}
