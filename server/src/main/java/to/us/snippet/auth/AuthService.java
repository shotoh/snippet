package to.us.snippet.auth;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;
import to.us.snippet.SnippetModel;
import to.us.snippet.exceptions.ResourceNotFoundException;
import to.us.snippet.exceptions.UnauthorizedException;
import to.us.snippet.users.User;
import to.us.snippet.users.UserRepository;

@Service
public class AuthService {
	private final AuthenticationManager authenticationManager;
	private final UserRepository repository;
	private final JwtEncoder jwtEncoder;
	private final JwtDecoder jwtDecoder;
	private final PasswordEncoder passwordEncoder;

	@Autowired
	public AuthService(AuthenticationManager authenticationManager, UserRepository repository, JwtEncoder jwtEncoder, JwtDecoder jwtDecoder, PasswordEncoder passwordEncoder) {
		this.authenticationManager = authenticationManager;
		this.repository = repository;
		this.jwtEncoder = jwtEncoder;
		this.jwtDecoder = jwtDecoder;
		this.passwordEncoder = passwordEncoder;
	}

	public TokenDTO login(AuthDTO authDTO) {
		Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authDTO.getUsername(), authDTO.getPassword()));
		User user = repository.findByUsername(authentication.getName());
		if (user == null) throw new ResourceNotFoundException("username", "User not found with this username"); // todo extend UserDetails to include id?
		long id = user.getId();
		return new TokenDTO(id, generateToken(id, authentication));
	}

	public void changePassword(PasswordDTO passwordDTO) {
		User user = repository.findById(userId()).orElseThrow(() -> new ResourceNotFoundException("username", "User not found with this id"));
		if (!passwordEncoder.matches(passwordDTO.getOldPassword(), user.getEncryptedPassword())) throw new UnauthorizedException();
		user.setEncryptedPassword(passwordEncoder.encode(passwordDTO.getNewPassword()));
		repository.save(user);
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

	public void setTestAuth(String token) {
		Authentication authentication = new JwtAuthenticationToken(jwtDecoder.decode(token.substring(7)), List.of(new SimpleGrantedAuthority("ROLE_USER")));
		SecurityContextHolder.getContext().setAuthentication(authentication);
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

	public String encryptPassword(String password) {
		return passwordEncoder.encode(password);
	}
}
