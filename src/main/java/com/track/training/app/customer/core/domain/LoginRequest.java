package com.track.training.app.customer.core.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/** */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {
   public String username;
   public String password; 
}
