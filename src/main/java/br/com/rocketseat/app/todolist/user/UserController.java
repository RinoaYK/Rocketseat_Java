package br.com.rocketseat.app.todolist.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import at.favre.lib.crypto.bcrypt.BCrypt;

@RestController
@RequestMapping("/users")
public class UserController {
    // String
    // Integer
    // Doublle 0.0000
    // Float 0.00
    // char
    // Date
    // void

    @Autowired
    private IUserRepository userRepository;

    @PostMapping("/")
    public ResponseEntity<?> create(@RequestBody UserModel userModel) {    
        var user = this.userRepository.findByUsername(userModel.getUsername());   
        
        if(user != null) {
            System.out.println("Usuário já existe!!");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Usuário já existe!!");
        }

        var passwordHashed = BCrypt.withDefaults().hashToString(12, userModel.getPassword().toCharArray());

        userModel.setPassword(passwordHashed);
        
        var userCreated = this.userRepository.save(userModel);
        return ResponseEntity.status(HttpStatus.OK).body(userCreated);
    }

    //     public void create(@RequestBody UserModel userModel) {
    //     System.out.println(userModel.getName());     
    // }
}
