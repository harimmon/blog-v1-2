package com.example.loginapp.user;

import com.example.loginapp._core.erorr.ex.Exception400;
import com.example.loginapp._core.erorr.ex.Exception401;
import com.example.loginapp._core.erorr.ex.Exception404;
import lombok.RequiredArgsConstructor;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;

    @Transactional
    public void 회원가입(UserRequest.JoinDTO joinDTO) {
        try {
            // 비밀번호를 해시값으로 변환
            String encodedPassword = BCrypt.hashpw(joinDTO.getPassword(), BCrypt.gensalt());
            joinDTO.setPassword(encodedPassword);
            userRepository.save(joinDTO.toEntity());
        } catch (Exception e) {
            throw new Exception400("이미 사용 중인 유저네임입니다.");
        }
    }

    public User 로그인(UserRequest.LoginDTO loginDTO) {
        User user = userRepository.findByUsername(loginDTO.getUsername());
        if (user == null) throw new Exception401("유저네임 혹은 비밀번호가 틀렸습니다.");

        Boolean isSame = BCrypt.checkpw(loginDTO.getPassword(), user.getPassword());
        if (!isSame) throw new Exception401("유저네임 혹은 비밀번호가 틀렸습니다.");

        return user;
    }

    public Map<String, Object> 유저네임중복체크(String username) {
        User user = userRepository.findByUsername(username);
        Map<String, Object> dto = new HashMap<>();

        if (user == null) {
            dto.put("available", true);
        } else {
            dto.put("available", false);
        }
        return dto;
    }

    @Transactional
    public User 회원정보수정(UserRequest.UpdateDTO updateDTO, Integer userId) {

        User userPS = userRepository.findById(userId);

        // TODO Exception404
        if (userPS == null) throw new Exception404("회원을 찾을 수 없습니다.");
        userPS.update(updateDTO.getPassword(), updateDTO.getEmail());
        return userPS;
    }
} 