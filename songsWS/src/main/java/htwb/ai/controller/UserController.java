package htwb.ai.controller;

import htwb.ai.dao.IUserDAO;
import htwb.ai.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping(value="/auth")
public class UserController {
    private IUserDAO userDAO;
    public UserController(IUserDAO dao){
        this.userDAO = dao;
    }
    //GET http://localhost:8080/authSpring/rest/auth/1
    @GetMapping(value="/{id}")
    public ResponseEntity<User> getUser(
            @PathVariable(value="id") Integer id) throws IOException {
        User user = userDAO.getUserById(id);
        if (user != null) {
            return new ResponseEntity<User>(user, HttpStatus.OK);
        }
        return new ResponseEntity<User>(user, HttpStatus.NOT_FOUND);
    }


}
