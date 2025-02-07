package com.src.train.track.User.model;

import com.src.train.track.general.helper.CustomQueryFilter;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UserFilter extends CustomQueryFilter {

    private static final long serialVersionUID = 6137157009051441625L;
    Long id;
    String username;
    String firstname;
    String lastname;
    String country;
}
