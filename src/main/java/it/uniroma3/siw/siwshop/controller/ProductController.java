package it.uniroma3.siw.siwshop.controller;

import org.springframework.stereotype.Controller;
import it.uniroma3.siw.siwshop.model.Product;
import it.uniroma3.siw.siwshop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class ProductController {



@Autowired
    private ProductService productService;

    @GetMapping("/products")
    public String getProducts(Model model) {
        model.addAttribute("products", productService.findAll());
        return "products";
    }

    @GetMapping("/products/{id}")
    public String getProduct(@PathVariable("id") Long id, Model model) {
        Product product = productService.findById(id);
        model.addAttribute("product", product);
        return "product";
    }

    @GetMapping("/addPrduct")
    public String getAddProduct(Model model) {
        model.addAttribute("product", new Product());
        return "formAddProduct";
    }

    @GetMapping("/home")
    public String home(Model model){
        return "home";
    }

}
