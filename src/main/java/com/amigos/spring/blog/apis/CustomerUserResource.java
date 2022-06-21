package com.amigos.spring.blog.apis;

import com.amigos.spring.blog.dtos.CustomerUserDTO;
import com.amigos.spring.blog.models.CustomerUser;
import com.amigos.spring.blog.services.interfaces.CustomerUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/users")
public class CustomerUserResource {

    @Autowired
    private CustomerUserService customerUserService;

    @GetMapping("/list")
    public ResponseEntity<List<CustomerUserDTO>> getCustomerUsers() {
        List<CustomerUserDTO> customerUsers = customerUserService.getCustomerUsers();
        return ResponseEntity.ok(customerUsers);
    }

    @GetMapping("/get/{userId}")
    public ResponseEntity<CustomerUserDTO> getCustomerUserDetails(@PathVariable("userId") Long userId) {
        CustomerUserDTO customerUserDetails = customerUserService.getCustomerUserDetails(userId);
        return ResponseEntity.ok(customerUserDetails);
    }

    @PutMapping("/update/{userId}")
    public ResponseEntity<CustomerUserDTO> updateCustomerUser(@Valid @RequestBody CustomerUser customerUser,
                                                              @PathVariable("userId") Long userId) {
        CustomerUserDTO customerUserDTO = customerUserService.updateCustomerUser(customerUser, userId);
        return new ResponseEntity<>(customerUserDTO, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{userId}")
     ResponseEntity deleteCustomerUser(@PathVariable("userId") Long userId) {
        boolean isUserDeleted = customerUserService.deleteCustomerUser(userId);
        return new ResponseEntity(Map.of("isUserDeleted", isUserDeleted), HttpStatus.OK);
    }

    @PostMapping("/image/upload/{customerUserId}")
    public ResponseEntity uploadFeaturedImage(@RequestParam("profileImage") MultipartFile file,
                                              @PathVariable("customerUserId") Long customerUserId) {
        CustomerUserDTO customerUserDTO = customerUserService.uploadProfileImageForCustomerUser(customerUserId, file);
        return new ResponseEntity(customerUserDTO, HttpStatus.OK);
    }

    @GetMapping("/image/get/{imageName}")
    public ResponseEntity retrieveImageURLByImageName(@PathVariable("imageName") String imageName)  {
        String fileDownloadUri = customerUserService.getImageURLByImageName(imageName);
        return ResponseEntity.ok(Map.of("blogFeaturedImageDownloadURL", fileDownloadUri));
    }
}
