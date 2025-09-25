package it.uniroma3.siw.siwshop.controller;

import it.uniroma3.siw.siwshop.model.Comment;
import it.uniroma3.siw.siwshop.model.ProductType;
import it.uniroma3.siw.siwshop.model.User;
import it.uniroma3.siw.siwshop.service.ProductTypeService;
import it.uniroma3.siw.siwshop.service.UserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import it.uniroma3.siw.siwshop.model.Product;
import it.uniroma3.siw.siwshop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@Controller
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private UserService userService;

    @Autowired
    private ProductTypeService productTypeService;


    public static final String UPLOAD_DIRECTORY = System.getProperty("user.home") + "/Desktop/uploads2/product-photo/";

    @GetMapping("/")
    public String home(@RequestParam(value = "keyword", required = false) String keyword, Model model) {

        List<Product> products = null;

        if (keyword != null && !keyword.trim().isEmpty()) {
            products = productService.searchByName(keyword);

            if (products.isEmpty()) {
                model.addAttribute("searchError", "Nessun prodotto trovato per '" + keyword + "'.");
            }
        }
        model.addAttribute("products", products);
        model.addAttribute("keyword", keyword);
        return "home";
    }


    @GetMapping("/products/{id}")
    public String getProduct(@PathVariable("id") Long id, Model model) {
        Product product = productService.findById(id);
        if (product != null) {
            model.addAttribute("product", product);

            try {
                UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
                User currentUser = userService.getUserByUsername(userDetails.getUsername());

                Comment currentUserComment = product.getComments().stream()
                        .filter(comment -> comment.getAuthor() != null && comment.getAuthor().equals(currentUser))
                        .findFirst()
                        .orElse(null);

                model.addAttribute("currentUserComment", currentUserComment);
            } catch (ClassCastException e) {
                // L'utente non è loggato (è anonimo), non facciamo nulla
                model.addAttribute("currentUserComment", null);
            }

            return "product";
        }
        return "redirect:/";
    }


    @GetMapping("/formAddProduct")
    public String showAddProductForm(Model model) {
        model.addAttribute("product", new Product());
        model.addAttribute("productTypes", productTypeService.findAll());
        return "formAddProduct";
    }


    @PostMapping("/products/add")
    public String addProduct(@ModelAttribute("product") Product product,
                             @RequestParam("productImage") MultipartFile productImage,
                             @RequestParam("categoryName") String categoryName, // <-- Nuovo parametro
                             Model model) {

        if (categoryName != null && !categoryName.trim().isEmpty()) {
            ProductType productType = productTypeService.findOrCreateByName(categoryName);
            product.setProductType(productType);
        }

        if (!productImage.isEmpty()) {
            try {
                String fileName = System.currentTimeMillis() + "_" + productImage.getOriginalFilename();
                Path filePath = Paths.get(UPLOAD_DIRECTORY, fileName);

                Files.createDirectories(filePath.getParent());
                Files.write(filePath, productImage.getBytes());
                product.setImageUrl(fileName);

            } catch (IOException e) {
                e.printStackTrace();
                model.addAttribute("error", "Errore nel salvataggio dell'immagine. Riprova.");
                model.addAttribute("productTypes", productTypeService.findAll());
                return "formAddProduct";
            }
        }
        productService.save(product);
        return "redirect:/products/" + product.getId();
    }

    @PostMapping("/products/edit/{id}")
    public String editProductDescription(@PathVariable("id") Long id,
                                         @RequestParam("description") String newDescription) {
        Product product = productService.findById(id);

        if (product != null && newDescription != null && !newDescription.trim().isEmpty()) {
            product.setDescription(newDescription);
            productService.save(product);
        }
        return "redirect:/products/" + id;
    }

    @GetMapping("/products/{id}/similar")
    public String showSimilarProducts(@PathVariable("id") Long id, Model model) {
        Product originalProduct = productService.findById(id);

        if (originalProduct != null) {
            List<Product> similarProducts = productService.findSimilarProducts(originalProduct);
            model.addAttribute("originalProduct", originalProduct);
            model.addAttribute("similarProducts", similarProducts);
            return "similarProducts";
        }
        return "redirect:/";
    }

    @PostMapping("/products/delete/{id}")
    public String deleteProduct(@PathVariable("id") Long id,
                                @RequestParam("originalProductId") Long originalProductId) {
        productService.deleteProduct(id);
        return "redirect:/products/" + originalProductId + "/similar";
    }
}