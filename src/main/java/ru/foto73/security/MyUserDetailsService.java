package ru.foto73.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.foto73.model.User;
import ru.foto73.repository.UserRepository;

import java.util.Optional;

// авторизация пользователями, которые имеются в БД
@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepo.findByLogin(username);


        return user.map(MyUserDetails::new)
                .orElseThrow(()->new UsernameNotFoundException(username+"There is not such user in REPO"));
    }



































    //    private final UserRepository userRepository;
//    private final UserRoleRepository userRoleRepository;
//
//    public MyUserDetailService(UserRepository userRepository, UserRoleRepository userRoleRepository) {
//        this.userRepository = userRepository;
//        this.userRoleRepository = userRoleRepository;
//    }
//
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        User user = userRepository.findByLogin(username)
//                .orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден"));
//
//        List<SimpleGrantedAuthority> userRoles = userRoleRepository.findByUserId(user.getId()).stream()
//                .map(it -> new SimpleGrantedAuthority(it.getRoleName()))
//                .toList();
//        return new org.springframework.security.core.userdetails.User(
//                user.getLogin(),
//                user.getPassword(),
//                userRoles
//        );
//    }
//    @PostConstruct
//    private void init(){
//
//    }
}
