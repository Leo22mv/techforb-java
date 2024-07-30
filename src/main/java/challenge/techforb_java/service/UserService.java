package challenge.techforb_java.service;

import java.util.List;

import challenge.techforb_java.entity.User;

public interface UserService {
    public User createUser(User user);
    public void editUser(Long id, User userDetails);
    public List<User> getUsers();
}