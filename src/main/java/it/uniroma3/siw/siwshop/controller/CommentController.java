package it.uniroma3.siw.siwshop.controller;

import it.uniroma3.siw.siwshop.model.Comment;
import it.uniroma3.siw.siwshop.model.Product;
import it.uniroma3.siw.siwshop.model.User;
import it.uniroma3.siw.siwshop.service.CommentService;
import it.uniroma3.siw.siwshop.service.ProductService;
import it.uniroma3.siw.siwshop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CommentController {

    @Autowired
    private CommentService commentService;
    @Autowired
    private ProductService productService;
    @Autowired
    private UserService userService;

    @PostMapping("/comments/add/{productId}")
    public String addComment(@PathVariable("productId") Long productId,
                             @RequestParam("commentText") String commentText) {
        Product product = productService.findById(productId);
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User currentUser = userService.getUserByUsername(userDetails.getUsername());

        if (product != null && currentUser != null && !commentText.trim().isEmpty()) {
            Comment newComment = new Comment();
            newComment.setText(commentText);
            newComment.setAuthor(currentUser);
            newComment.setProduct(product);
            commentService.save(newComment);
        }
        return "redirect:/products/" + productId;
    }

    @PostMapping("/comments/edit/{commentId}")
    public String editComment(@PathVariable("commentId") Long commentId,
                              @RequestParam("commentText") String commentText) {
        Comment comment = commentService.findById(commentId);
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User currentUser = userService.getUserByUsername(userDetails.getUsername());

        // Controllo di sicurezza per modificare solo i tuoi commenti
        if (comment != null && comment.getAuthor().equals(currentUser) && !commentText.trim().isEmpty()) {
            comment.setText(commentText);
            commentService.save(comment);
        }
        return "redirect:/products/" + (comment != null ? comment.getProduct().getId() : "");
    }

    @PostMapping("/comments/delete/{commentId}")
    public String deleteComment(@PathVariable("commentId") Long commentId) {
        Comment comment = commentService.findById(commentId);
        if (comment == null) {
            return "redirect:/";
        }
        Long productId = comment.getProduct().getId();

        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User currentUser = userService.getUserByUsername(userDetails.getUsername());

        if (comment.getAuthor().equals(currentUser) || userDetails.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ADMIN"))) {
            commentService.deleteById(commentId);
        }
        return "redirect:/products/" + productId;
    }
}