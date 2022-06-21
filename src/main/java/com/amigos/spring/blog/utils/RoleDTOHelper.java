package com.amigos.spring.blog.utils;

import com.amigos.spring.blog.dtos.RoleDTO;
import com.amigos.spring.blog.models.Role;

public class RoleDTOHelper {

    public static RoleDTO buildDTOFromRole(Role role) {
        RoleDTO roleDTO = new RoleDTO();
        roleDTO.setRoleName(role.getRoleName());
        return roleDTO;
    }
}
