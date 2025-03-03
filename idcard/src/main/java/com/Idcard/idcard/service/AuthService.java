package com.Idcard.idcard.service;

// import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.Idcard.idcard.entity.User;
import com.Idcard.idcard.entity.UserToken;
import com.Idcard.idcard.repository.UserRepository;
import com.Idcard.idcard.repository.UserTokenRepository;
// import java.util.Optional;
// import java.util.Optional;
import java.util.UUID;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserTokenRepository userTokenRepository;

    @Transactional
    public String authenticate(String username, String password) {
        User user = userRepository.findByUsername(username).orElseThrow();

        if (user != null && user.getPassword().equals(password)) {
            // Generate Token
            String token = UUID.randomUUID().toString();

            userTokenRepository.findByUserId(user.getId()).ifPresent(
                    existingToken -> userTokenRepository.deleteByToken(existingToken.getToken()));
            // Save new token in database
            UserToken userToken = new UserToken();
            userToken.setUser(user);
            userToken.setToken(token);
            userTokenRepository.save(userToken);

            return token;
        } else {
            return null;
        }
    }

    public boolean validateToken(String token) {
        return userTokenRepository.findByToken(token).isPresent();
    }

    @Transactional
    public void logout(String token) {
        userTokenRepository.deleteByToken(token);
    }

    @Transactional
    public void logout1(String token) {
        int deletedRows = userTokenRepository.deleteByToken(token);

        if (deletedRows > 0) {
            System.out.println("Token Deleted Successfully: " + token);
        } else {
            System.out.println("Token Not Found: " + token);
        }
    }

}
