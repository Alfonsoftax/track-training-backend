package com.src.train.track.User.proxy.impl;
import org.springframework.stereotype.Component;

import com.src.train.track.User.mapper.UserMapper;
import com.src.train.track.User.model.UserDTO;
import com.src.train.track.User.model.UserFilter;
import com.src.train.track.User.model.Usuario;
import com.src.train.track.User.proxy.UserProxy;
import com.src.train.track.User.service.UserService;
import com.src.train.track.general.helper.DefaultProxyImpl;

@Component
public class UserProxyImpl
        extends DefaultProxyImpl<UserDTO, Usuario, Long, UserFilter, UserService>
        implements UserProxy {

    public UserProxyImpl(final UserService service, final UserMapper mapper) {
        super(service, mapper);
    }

	@Override
	public UserDTO findByUsername(String username) {
		return this.mapper.convertToDto(this.service.findByUsername(username));
	}
}
