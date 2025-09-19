package it.uniroma3.siw.siwshop.repository;

import it.uniroma3.siw.siwshop.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
