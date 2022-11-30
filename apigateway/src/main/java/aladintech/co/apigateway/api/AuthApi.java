package aladintech.co.apigateway.api;

import aladintech.co.apigateway.config.jwt.JWTFilter;
import aladintech.co.apigateway.config.jwt.TokenProvider;
import aladintech.co.apigateway.dto.JwtResponse;
import aladintech.co.apigateway.dto.LoginRequestDto;
import aladintech.co.apigateway.dto.SignUpDto;
import aladintech.co.apigateway.exception.NotFoundException;
import aladintech.co.apigateway.model.Role;
import aladintech.co.apigateway.model.RoleName;
import aladintech.co.apigateway.model.User;
import aladintech.co.apigateway.repository.RoleRepository;
import aladintech.co.apigateway.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/auth")
public class AuthApi {
	private final Logger log = LoggerFactory.getLogger(AuthApi.class);
	@Autowired
	private ReactiveAuthenticationManager authenticationManager;
	@Autowired
	private TokenProvider tokenProvider;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;

	@PostMapping("/login")
	public Mono<ResponseEntity<?>> login(@Valid @RequestBody Mono<LoginRequestDto> loginRequestDto) {
		log.debug("REST request to login with User : {}", loginRequestDto);
		return loginRequestDto
				.flatMap(l -> authenticationManager
							.authenticate(new UsernamePasswordAuthenticationToken(l.getUsername(), l.getPassword()))
							.flatMap(auth -> Mono.fromCallable(() -> tokenProvider.createToken(auth)))
				)
				.map(jwt -> {
					HttpHeaders httpHeaders = new HttpHeaders();
					httpHeaders.add(JWTFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);
					return new ResponseEntity<>(
							new JwtResponse(jwt),
							httpHeaders,
							HttpStatus.OK
					);
				});
	}

	@PostMapping("/signup")
	public ResponseEntity<User> createUser(@Valid @RequestBody SignUpDto signUpDto) throws Exception {
		log.debug("REST request to save User : {}", signUpDto);

		Optional<User> userOptional = userRepository.findByUsername(signUpDto.getUsername());
		if(userOptional.isPresent()) {
			throw new Exception("Login name already used!");
		}
		User user = new User();
		user.setFullname(signUpDto.getFullname());
		user.setUsername(signUpDto.getUsername());
		user.setPassword(passwordEncoder.encode(signUpDto.getPassword()));
		user.setRoles(signUpDto.getRoles().stream().map(r -> {
			Role role = roleRepository.findByName(RoleName.valueOf(r));
			if(role == null) {
				role = new Role();
				role.setName(RoleName.valueOf(r));
				role = roleRepository.save(role);
			}
			return role;
		}).collect(Collectors.toSet()));

		User res = userRepository.save(user);

		return ResponseEntity.ok().body(res);
	}
}

