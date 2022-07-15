package com.amigos.spring.blog.repositories;

import com.amigos.spring.blog.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
