package com.maids.salesmanagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.maids.salesmanagement.auth.JwtUtil;
import com.maids.salesmanagement.model.Client;
import com.maids.salesmanagement.repository.ClientRepository;
import com.maids.salesmanagement.request.LoginReq;
import com.maids.salesmanagement.response.LoginRes;

@RestController
@RequestMapping("/auth")
@CrossOrigin(value = "http://localhost:4200")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final ClientRepository userDao;
    private final PasswordEncoder passwordEncoder;

    public AuthController(AuthenticationManager authenticationManager, JwtUtil jwtUtil, ClientRepository userDao, PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.userDao = userDao;
        this.passwordEncoder = passwordEncoder;
    }
	
	@PostMapping("/login")
    public ResponseEntity login(@RequestBody LoginReq loginReq)  {
        try {
        	System.out.println(loginReq.getEmail());
            System.out.println(loginReq.getPassword());
            Authentication authentication =
                    authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginReq.getEmail(), loginReq.getPassword()));
			
            String email = authentication.getName();
            Client user = userDao.findByEmail(email);
            
            System.out.println(email);
            
            if (!passwordEncoder.matches(loginReq.getPassword(), user.getPassword())) {
                throw new BadCredentialsException("Invalid username or password");
            }
            
            String token = jwtUtil.createToken(user);
            LoginRes loginRes = new LoginRes(token, "Welcome Back!");

            return ResponseEntity.ok(loginRes);

        }catch (BadCredentialsException e){
            LoginRes loginRes = new LoginRes(null, "Invalid username or password");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(loginRes);
        }catch (Exception e){
            LoginRes loginRes = new LoginRes(null, "Invalid username or password");
        	return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(loginRes);
        }
    }
    
	@PostMapping("/register")
    public ResponseEntity register(@RequestBody Client user)  {
        if (userDao.findByEmail(user.getEmail()) != null) {
        	LoginRes loginRes = new LoginRes(null, "User with this email already exists");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(loginRes);
        }
		
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        
		System.out.println(user);
        user.getRoles().add("ROLE_USER");
        user = userDao.save(user);
    	return ResponseEntity.ok(user);
    }
	
//	@PostMapping("/admin-register")
//    public ResponseEntity adminRegister(@RequestBody User user)  {
//        if (userDao.findByEmail(user.getEmail()) != null) {
//        	LoginRes loginRes = new LoginRes(null, "Admin with this email already exists");
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(loginRes);
//        }
//        
//        user.setPassword(passwordEncoder.encode(user.getPassword()));
//		
//		System.out.println(user);
//        user.getRoles().add("ROLE_ADMIN");
//        user = userDao.save(user);
//    	return ResponseEntity.ok(user);
//    }

}