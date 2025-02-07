package com.src.train.track.User.service.impl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.querydsl.core.types.Predicate;
import com.src.train.track.User.model.UserFilter;
import com.src.train.track.User.model.Usuario;
import com.src.train.track.User.predicates.UserPredicates;
import com.src.train.track.User.repository.UserRepository;
import com.src.train.track.User.service.UserService;
import com.src.train.track.general.helper.DefaultServiceImpl;
import com.src.train.track.interfaz.LoaderProcessService;

@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class UserServiceImpl
        extends DefaultServiceImpl<Usuario, Long, UserFilter, UserRepository>
        implements UserService, LoaderProcessService {



    // -------------------------------------------------------------------------

    public UserServiceImpl(final UserRepository repository) {
        super(repository);
    }

    @Override
    protected Predicate getSearchPredicate(final UserFilter filter) {
    	return UserPredicates.searchPredicate(filter);
    }

	@Override
	public Usuario findByUsername(String username) {
		return this.repository.findByUsername(username).get();
	}

    

}
