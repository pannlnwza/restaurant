package ku.restaurant.service;


import jakarta.persistence.EntityExistsException;
import ku.restaurant.dto.SignupRequest;
import ku.restaurant.entity.User;
import ku.restaurant.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.time.Instant;


@Service
public class UserService {
    private UserRepository userRepository;
    private PasswordEncoder encoder;


    @Autowired
    public UserService(UserRepository userRepository,
                       PasswordEncoder encoder) {


        this.userRepository = userRepository;
        this.encoder = encoder;
    }

    public boolean userExists(String username) {
        return userRepository.existsByUsername(username);
    }

    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public void createUser(SignupRequest request) {
        if (userExists(request.getUsername())) {
            throw new EntityExistsException("Username '" + request.getUsername() + "' is already taken");
        }
        
        User dao = new User();
        dao.setUsername(request.getUsername());
        dao.setPassword(encoder.encode(request.getPassword()));
        dao.setName(request.getName());
        dao.setRole("ROLE_USER");
        dao.setCreatedAt(Instant.now());


        userRepository.save(dao);
    }
}

