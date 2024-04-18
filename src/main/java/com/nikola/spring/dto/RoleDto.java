package com.nikola.spring.dto;

import com.nikola.spring.entities.UserEntity;
import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.io.Serializable;
import java.util.List;

public class RoleDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;
    @NotEmpty
    @Size(min = 3,max = 40)
    private String role;
    private List<Integer> usersIds;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public List<Integer> getUsersIds() {
        return usersIds;
    }

    public void setUsersIds(List<Integer> usersIds) {
        this.usersIds = usersIds;
    }
}
