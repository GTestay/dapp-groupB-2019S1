package com.edu.unq.tpi.dapp.grupoB.Eventeando.persistence;

import com.edu.unq.tpi.dapp.grupoB.Eventeando.dominio.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserDao extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    List<User> findAllByEmailIn(List<String> guestsEmails);

}
