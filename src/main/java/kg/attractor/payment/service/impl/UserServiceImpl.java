package kg.attractor.payment.service.impl;

import kg.attractor.payment.dao.UserDao;
import kg.attractor.payment.dto.UserDto;
import kg.attractor.payment.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserDao userDao;

    @Override
    public void createUser(UserDto user) {
        if (userDao.isUserExistsByPhone(user.getPhone())) {
            throw new NoSuchElementException("User with phone " + user.getPhone() + " already exists.");
        }
        if (userDao.isUserExistsByUsername(user.getUsername())) {
            throw new NoSuchElementException("User with username " + user.getUsername() + " already exists.");
        }

        userDao.createUser(user);
    }
}
