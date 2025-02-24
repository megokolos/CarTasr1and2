package hiber.service;

import hiber.model.User;

import java.util.List;

public interface UserService {
    void add(User user);

    List<User> listUsers();

    List<User> getUserByCar(String model, int series);

    User getUserById(Long id);
}
