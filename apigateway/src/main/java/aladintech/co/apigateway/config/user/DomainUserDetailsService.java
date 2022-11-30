package aladintech.co.apigateway.config.user;

import aladintech.co.apigateway.exception.NotFoundException;
import aladintech.co.apigateway.model.User;
import aladintech.co.apigateway.repository.UserRepository;
import org.hibernate.validator.internal.constraintvalidators.hv.EmailValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Authenticate a user from the database.
 */
@Component("userDetailsService")
public class DomainUserDetailsService implements ReactiveUserDetailsService {

    private final Logger log = LoggerFactory.getLogger(DomainUserDetailsService.class);

    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public Mono<UserDetails> findByUsername(final String login) {
        log.debug("Authenticating {}", login);

        Optional<User> userOptional = userRepository.findByUsername(login);
        if(userOptional.isEmpty()) {
            throw new NotFoundException("User with username " + login + " was not found in the database");
        }
        User user = userOptional.get();
        List<GrantedAuthority> grantedAuthorities = user
                .getRoles()
                .stream()
                .map(r -> r.getName().toString())
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
        return Mono.just(new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), grantedAuthorities));
    }
}
