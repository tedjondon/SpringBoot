package com.zabello.springboot.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.security.core.GrantedAuthority;
import javax.persistence.*;
import java.util.Set;

@JsonIgnoreProperties
@Entity
@Table(name = "roles")
public class Role implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String role;

    @Transient
    @ManyToMany(mappedBy = "roles", fetch = FetchType.EAGER)
    private Set<User> users;

    public Role() {
    }

    public Role(String role) {
        this.role = role;
    }

    public int getId() {
        return id;
    }

    public String getRole() {
        return role;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setRole(String name) {
        this.role = name;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    public String getName() {
        return role.substring(5);
    }

    @Override
    public String getAuthority() {
        return role;
    }

    @Override
    public String toString() {
        return getAuthority();
    }
}
