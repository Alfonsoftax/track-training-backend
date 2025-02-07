package com.src.train.track.User.proxy;
import com.src.train.track.User.model.UserDTO;
import com.src.train.track.User.model.UserFilter;
import com.src.train.track.interfaz.DefaultProxy;

public interface UserProxy extends DefaultProxy<UserDTO, Long, UserFilter> {

	UserDTO findByUsername(String username);

}
