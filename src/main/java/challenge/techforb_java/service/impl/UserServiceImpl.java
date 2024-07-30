package challenge.techforb_java.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import challenge.techforb_java.entity.User;
import challenge.techforb_java.repository.UserRepository;
import challenge.techforb_java.service.UserService;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public User createUser(User user) {
    Optional<User> existentUser = userRepository.findByEmail(user.getEmail());
        if(existentUser.isPresent()) {
            throw new RuntimeException("Usuario con email " + user.getEmail() + " ya existe.");
        }
        if(user.getEmail()==null){
            throw new RuntimeException("No se ingresó un email");
        }
        return userRepository.save(user);
    }

    @Override
    public void editUser(Long id, User userDetails) {
        Optional<User> optionalUser = userRepository.findById(id);
        Optional<User> optionalEmail;
        if (optionalUser.isPresent()) {
            User userExistente = optionalUser.get();
            
            if (userDetails.getName() != null) {
                userExistente.setName(userDetails.getName());
            }
            if (userDetails.getSurname() != null) {
                userExistente.setSurname(userDetails.getSurname());
            }
            if (userDetails.getEmail() != null) {
                optionalEmail=userRepository.findByEmail(userDetails.getEmail());
                if(optionalEmail.isPresent() && optionalEmail.get().getEmail().equals(userDetails.getEmail()) && !(optionalEmail.get().getId().equals(id)))
                {
                    throw new RuntimeException("Email registrado");
                }
                else
                {
                    userExistente.setEmail(userDetails.getEmail());
                }
            }
            userRepository.save(userExistente);
        } else {
            throw new RuntimeException("Usuario no encontrado");
        }
    }

    @Override
    public List<User> getUsers() {
        return userRepository.findAll();
    }
}
