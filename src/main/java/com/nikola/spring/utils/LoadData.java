package com.nikola.spring.utils;

import com.nikola.spring.entities.RoleEntity;
import com.nikola.spring.entities.UserEntity;
import com.nikola.spring.repositories.RoleRepository;
import com.nikola.spring.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Component
public class LoadData {

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private BCryptPasswordEncoder passwordEncoder;


    @Autowired
    public void injectAll(UserRepository userRepository, RoleRepository roleRepository, BCryptPasswordEncoder passwordEncoder){
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        addRolesData();
        addAdminData();
    }

    public void addAdminData(){
        RoleEntity role = roleRepository.findByRole("ADMIN").orElse(null);
        if(role != null){
            List<RoleEntity> roles = new ArrayList<>();
            roles.add(role);
            UserEntity userEntity = new UserEntity("Nikola","Nikolic","nikola.nikolic.it@gmail.com", "nikola123",(byte)1);
            userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
            userEntity.setRoles(roles);

            try{
                UserEntity storedUser = userRepository.save(userEntity);
                role.setUsers(List.of(storedUser));
                roleRepository.saveAndFlush(role);
            }catch (Exception e){
                System.err.println("Admin already added!!!");
            }
        }else{
            System.err.println("Admin already added");
        }

    }

    public void addRolesData(){
        RoleEntity role1 = new RoleEntity("ADMIN");
        RoleEntity role2 = new RoleEntity("USER");
        if(roleRepository.findByRole("ADMIN").isEmpty()){
            roleRepository.save(role1);
        }
        if(roleRepository.findByRole("USER").isEmpty()){
            roleRepository.save(role2);
        }
    }
}
