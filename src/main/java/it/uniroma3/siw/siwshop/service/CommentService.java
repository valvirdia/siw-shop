package it.uniroma3.siw.siwshop.service;

import it.uniroma3.siw.siwshop.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;
}
