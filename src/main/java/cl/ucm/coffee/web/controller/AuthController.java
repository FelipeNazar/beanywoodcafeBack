package cl.ucm.coffee.web.controller;

import cl.ucm.coffee.service.dto.LoginDto;
import cl.ucm.coffee.web.config.JwtUtil;
import cl.ucm.coffee.persitence.entity.UserRoleEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import cl.ucm.coffee.persitence.entity.UserEntity;
import cl.ucm.coffee.persitence.repository.UserRepository;


import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.List;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private  AuthenticationManager authenticationManager;
    @Autowired
    private  JwtUtil jwtUtil;
    @Autowired
    private UserRepository userRepository;

//    @Autowired
//    public AuthController(AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
//        this.authenticationManager = authenticationManager;
//        this.jwtUtil = jwtUtil;
//    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDto loginDto) {
        UsernamePasswordAuthenticationToken login = new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword());
        Authentication authentication = this.authenticationManager.authenticate(login);
        String username = loginDto.getUsername();

        Optional<UserEntity> userEntityOptional = userRepository.findByUsername(username);

        if (!userEntityOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuario no encontrado");
        }

        UserEntity userEntity = userEntityOptional.get();

        if (userEntity.getDisabled()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("El usuario está deshabilitado");
        }

        List<String> roles = userEntity.getRoles().stream().map(UserRoleEntity::getRole).toList();

       // System.out.println(authentication.isAuthenticated());
       // System.out.println(authentication.getPrincipal());

        String jwt = this.jwtUtil.create(loginDto.getUsername(), roles.get(0));
        Map map = new HashMap<>();
        map.put("token",jwt);
        return ResponseEntity.ok(map);
        //return ResponseEntity.ok().header(HttpHeaders.AUTHORIZATION, jwt).build();
    }
}
