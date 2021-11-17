package ug.payway.technicalmaintenanceschedule.service.security;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ug.payway.technicalmaintenanceschedule.model.User;
import ug.payway.technicalmaintenanceschedule.service.UserService;

import java.util.Collection;
import java.util.Collections;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomUserDetailsService implements UserDetailsService {
    private final UserService userService;

    @SneakyThrows
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User foundUser = userService.findByEmail(username);
        if (foundUser == null) {
            throw new UsernameNotFoundException(
                    String.format("User with username: %s not found", username));
        } else {
            return new UserDetails() {
                @Override
                public Collection<? extends GrantedAuthority> getAuthorities() {
                    return Collections.singletonList(
                            new SimpleGrantedAuthority(foundUser.getRole().name()));
                }

                @Override
                public String getPassword() {
                    return foundUser.getEncryptedPassword();
                }

                @Override
                public String getUsername() {
                    return foundUser.getEmail();
                }

                @Override
                public boolean isAccountNonExpired() {
                    return true;
                }

                @Override
                public boolean isAccountNonLocked() {
                    return foundUser.getActive();
                }

                @Override
                public boolean isCredentialsNonExpired() {
                    return true;
                }

                @Override
                public boolean isEnabled() {
                    return foundUser.getActive();
                }
            };
        }
    }
}
