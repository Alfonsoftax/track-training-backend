package com.src.train.track.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.src.train.track.User.model.UserDTO;
import com.src.train.track.User.proxy.UserProxy;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(value = "/user")
@RequiredArgsConstructor
public class UserController {
	
	@Autowired
    private final UserProxy proxy;
    
    @GetMapping(value = "{username}")
    public ResponseEntity<UserDTO> getUser(@PathVariable String username)
    {
        UserDTO userDTO = proxy.findByUsername(username);
        if (userDTO==null)
        {
           return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(userDTO);
    }

//    @PutMapping()
//    public ResponseEntity<UserResponse> updateUser(@RequestBody UserRequest userRequest)
//    {
//        return ResponseEntity.ok(userService.updateUser(userRequest));
//    }
}
