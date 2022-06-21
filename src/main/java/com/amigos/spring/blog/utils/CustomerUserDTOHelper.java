package com.amigos.spring.blog.utils;

import com.amigos.spring.blog.dtos.CustomerUserDTO;
import com.amigos.spring.blog.dtos.RoleDTO;
import com.amigos.spring.blog.models.CustomerUser;

import java.util.ArrayList;
import java.util.List;

public class CustomerUserDTOHelper {

    public static CustomerUserDTO buildDTOFromCustomerUser(CustomerUser customerUser) {
        CustomerUserDTO customerUserDTO = new CustomerUserDTO();
        customerUserDTO.setName(customerUser.getName());
        customerUserDTO.setEmail(customerUser.getEmail());
        customerUserDTO.setIntro(customerUser.getIntro());
        customerUserDTO.setCreatedDate(customerUser.getCreatedDate());
        customerUserDTO.setProfileImageURL(customerUser.getProfileImageURL());

        List<RoleDTO> rolesDTO = new ArrayList<>();
        customerUser.getRoles().forEach((role) -> {
            RoleDTO roleDTO = RoleDTOHelper.buildDTOFromRole(role);
            rolesDTO.add(roleDTO);
        });
        customerUserDTO.setRoles(rolesDTO);
        return customerUserDTO;
    }
}
