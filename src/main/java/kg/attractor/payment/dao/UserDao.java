package kg.attractor.payment.dao;

import kg.attractor.payment.dto.UserDto;
import kg.attractor.payment.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserDao {
    private final JdbcTemplate template;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public boolean isUserExistsByPhone(String phone) {
        String sql = "select count(*) from users where phone = ?";
        Integer count = template.queryForObject(sql, Integer.class, phone);
        return count != null && count > 0;
    }

    public boolean isUserExistsByUsername(String username) {
        String sql = "select count(*) from users where username = ?";
        Integer count = template.queryForObject(sql, Integer.class, username);
        return count != null && count > 0;
    }

    public void createUser(UserDto user) {
        Optional<User> existingUserPhone = findByPhone(user.getPhone());
        Optional<User> existingUserUsername = findByUsername(user.getUsername());
        if (existingUserPhone.isPresent() && existingUserUsername.isPresent()) {
            throw new IllegalArgumentException("User with phone " + user.getPhone() + " already exists.");
        }
        String sql = "insert into users (phone, username, password, ENABLED) " +
                "values (:phone, :username, :password, :enabled);";
        namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource()
                .addValue("phone", user.getPhone())
                .addValue("username", user.getUsername())
                .addValue("password", user.getPassword())
                .addValue("enabled", true));

    }

    public Optional<User> findByPhone(String phone) {
        String sql = "select * from users where phone = ?";
        List<User> users = template.query(sql, new BeanPropertyRowMapper<>(User.class), phone);
        return users.stream().findFirst();
    }

    public Optional<User> findByUsername(String username) {
        String sql = "select * from users where username = ?";
        List<User> users = template.query(sql, new BeanPropertyRowMapper<>(User.class), username);
        return users.stream().findFirst();
    }
}
