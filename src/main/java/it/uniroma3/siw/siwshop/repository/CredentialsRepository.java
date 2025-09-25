package it.uniroma3.siw.siwshop.repository;

import it.uniroma3.siw.siwshop.model.Credentials;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;


public interface CredentialsRepository extends JpaRepository<Credentials, Long> {
    Optional<Credentials> findByUsername(String username);
}
