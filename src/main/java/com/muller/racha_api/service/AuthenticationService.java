package com.muller.racha_api.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.muller.racha_api.dto.RegisterDTO;
import com.muller.racha_api.model.User;
import com.muller.racha_api.repository.UserRepository;
import com.muller.racha_api.security.PasswordConfiguration;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationService implements UserDetailsService {
    private final UserRepository userRepository;
    private final PasswordConfiguration passwordConfig;

    public void register(RegisterDTO dto) {
        User user = new User();
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());

        String encryptedPassword = passwordConfig.passwordEncoder().encode(dto.getPassword());
        user.setPassword(encryptedPassword);

        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new IllegalArgumentException("Este e-mail já está registrado");
        }

        userRepository.save(user);
    }

    public User loginOrRegisterOAuth2User(OAuth2User oAuth2User) {
        String email = oAuth2User.getAttribute("email");
        String name = oAuth2User.getAttribute("name");
        User user = userRepository.findByEmail(email);

        if (user == null) {
            user = new User();
            user.setEmail(email);
            user.setName(name);
            user.setPassword("||"); // oAuth2User vai garantir o login.
            user.setOAuth2User(true);
            user = userRepository.save(user);
        }

        return user;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username);

        if (user == null) {
            throw new UsernameNotFoundException("Usuário não encontrado");
        }

        if (user.isOAuth2User()) {
            throw new UsernameNotFoundException("Este usuário pode logar apenas com a conta google");
        }

        return user;
    }
}
