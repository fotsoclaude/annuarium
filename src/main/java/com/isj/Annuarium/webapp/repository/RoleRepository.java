package com.isj.Annuarium.webapp.repository;

import com.isj.Annuarium.webapp.model.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("roleRepository")
public interface RoleRepository extends JpaRepository<Role, Integer> {
	Role findByRole(String role);

}
