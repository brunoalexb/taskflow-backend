package com.brunodev.taskflow.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.brunodev.taskflow.model.User;
import com.brunodev.taskflow.model.UserLogin;
import com.brunodev.taskflow.repository.UserRepository;
import com.brunodev.taskflow.security.JwtService;



@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private JwtService jwtService;
	
    @Autowired
    private AuthenticationManager authenticationManager;

	public Optional<User> cadastrarUsuario(User user) {

		if (userRepository.findByEmail(user.getEmail()).isPresent())
			return Optional.empty();

		user.setPassword(criptografarSenha(user.getPassword()));

		return Optional.of(userRepository.save(user));
	
	}

	public Optional<User> atualizarUsuario(User user) {
		
		if(userRepository.findById(user.getId()).isPresent()) {

			Optional<User> buscaUsuario = userRepository.findByEmail(user.getEmail());

			if ( (buscaUsuario.isPresent()) && ( buscaUsuario.get().getId() != user.getId()))
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Usuário já existe!", null);

			user.setPassword(criptografarSenha(user.getPassword()));

			return Optional.ofNullable(userRepository.save(user));
			
		}

		return Optional.empty();
	
	}	

	public Optional<UserLogin> autenticarUsuario(Optional<UserLogin> userLogin) {
        
        // Gera o Objeto de autenticação
		var credenciais = new UsernamePasswordAuthenticationToken(userLogin.get().getEmail(), userLogin.get().getPassword());
		
        // Autentica o Usuario
		Authentication authentication = authenticationManager.authenticate(credenciais);
        
        // Se a autenticação foi efetuada com sucesso
		if (authentication.isAuthenticated()) {

            // Busca os dados do usuário
			Optional<User> usuario = userRepository.findByEmail(userLogin.get().getEmail());

            // Se o usuário foi encontrado
			if (usuario.isPresent()) {

                // Preenche o Objeto usuarioLogin com os dados encontrados 
			   userLogin.get().setId(usuario.get().getId());
			   userLogin.get().setUsername(usuario.get().getUsername());
			   userLogin.get().setToken(gerarToken(userLogin.get().getEmail()));
			   userLogin.get().setPassword("");
				
                 // Retorna o Objeto preenchido
			   return userLogin;
			
			}

        } 
            
		return Optional.empty();

    }

	private String criptografarSenha(String senha) {

		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		
		return encoder.encode(senha);

	}

	private String gerarToken(String usuario) {
		return "Bearer " + jwtService.generateToken(usuario);
	}
}
