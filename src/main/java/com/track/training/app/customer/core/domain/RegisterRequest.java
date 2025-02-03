package com.track.training.app.customer.core.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/** */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {
    String username;
    String password;
    String firstname;
    public String lastname;
    String country; 
}
