package io.github.shotoh.snippet.services;

import io.github.shotoh.snippet.exceptions.ResourceNotFoundException;
import io.github.shotoh.snippet.exceptions.UnauthorizedException;
import io.github.shotoh.snippet.models.SnippetModel;
import io.github.shotoh.snippet.models.auth.AuthDTO;
import io.github.shotoh.snippet.models.auth.TokenDTO;
import io.github.shotoh.snippet.models.users.User;
import io.github.shotoh.snippet.repositories.UserRepository;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
	private final AuthenticationManager authenticationManager;
	private final UserRepository repository;
	private final JwtEncoder jwtEncoder;

	@Autowired
	public AuthService(AuthenticationManager authenticationManager, UserRepository repository, JwtEncoder jwtEncoder) {
		this.authenticationManager = authenticationManager;
		this.repository = repository;
		this.jwtEncoder = jwtEncoder;
	}

	public TokenDTO login(AuthDTO authDTO) {
		Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authDTO.getUsername(), authDTO.getPassword()));
		User user = repository.findByUsername(authentication.getName());
		if (user == null) throw new ResourceNotFoundException("username", "User not found with this username"); // todo extend UserDetails to include id?
		long id = user.getId();
		return new TokenDTO(id, generateToken(id, authentication));
	}

	public String generateToken(long id, Authentication authentication) {
		Instant now = Instant.now();
		String scope = authentication.getAuthorities().stream()
				.map(GrantedAuthority::getAuthority)
				.collect(Collectors.joining(" "));
		JwtClaimsSet claims = JwtClaimsSet.builder()
				.issuer("self")
				.issuedAt(now)
				.expiresAt(now.plus(1, ChronoUnit.HOURS))
				.subject(String.valueOf(id))
				.claim("scope", scope)
				.build();
		JwtEncoderParameters encoderParameters = JwtEncoderParameters.from(JwsHeader.with(MacAlgorithm.HS256).build(), claims);
		return jwtEncoder.encode(encoderParameters).getTokenValue();
	}

	public void check(long objectId) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null) throw new UnauthorizedException();
		List<String> roles = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();
		if (roles.contains("ROLE_ADMIN")) return;
		if (!roles.contains("ROLE_USER")) throw new UnauthorizedException();
		long userId = Long.parseLong(((Jwt) authentication.getPrincipal()).getSubject());
		if (userId != objectId) throw new UnauthorizedException();
		if (objectId == 0) throw new UnauthorizedException();
	}

	public void check(SnippetModel object) {
		check(object.userId());
	}

	public long userId() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null) throw new UnauthorizedException();
		List<String> roles = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();
		if (!roles.contains("ROLE_USER")) throw new UnauthorizedException();
		return Long.parseLong(((Jwt) authentication.getPrincipal()).getSubject());
	}
}
