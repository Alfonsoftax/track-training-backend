package com.src.train.track.User.service;
import com.src.train.track.User.model.UserFilter;
import com.src.train.track.User.model.Usuario;
import com.src.train.track.interfaz.DefaultService;

public interface UserService extends DefaultService<Usuario, Long, UserFilter> {

	Usuario findByUsername(String username);


}
