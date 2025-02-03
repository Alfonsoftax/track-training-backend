package com.track.training.app.customer.core.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    public Long id;
    public String username;
    public String firstname;
    public String lastname;
    public String country;
}
