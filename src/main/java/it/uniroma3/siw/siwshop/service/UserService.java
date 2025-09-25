package it.uniroma3.siw.siwshop.service;

import it.uniroma3.siw.siwshop.model.Credentials;
import it.uniroma3.siw.siwshop.model.User;
import it.uniroma3.siw.siwshop.repository.CredentialsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private CredentialsRepository credentialsRepository;

    public User getUserByUsername(String username) {
        Credentials credentials = credentialsRepository.findByUsername(username).orElse(null);
        return (credentials != null) ? credentials.getUser() : null;
    }
}