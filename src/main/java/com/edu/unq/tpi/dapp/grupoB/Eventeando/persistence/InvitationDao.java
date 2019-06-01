package com.edu.unq.tpi.dapp.grupoB.Eventeando.persistence;

import com.edu.unq.tpi.dapp.grupoB.Eventeando.dominio.Invitation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InvitationDao extends JpaRepository<Invitation, Long> {

}
