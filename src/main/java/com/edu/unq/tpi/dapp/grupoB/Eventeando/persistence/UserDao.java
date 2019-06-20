package com.edu.unq.tpi.dapp.grupoB.Eventeando.persistence;

import com.edu.unq.tpi.dapp.grupoB.Eventeando.dominio.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserDao extends JpaRepository<User, Long> {
    Optional<User> findByEmailEquals(String email);

    List<User> findAllByEmailIn(List<String> guestsEmails);

    @Query(value = "SELECT u.email FROM User u where u.email like %:email%")
    List<String> findAllByEmailContaining(@Param("email") String email);


}
