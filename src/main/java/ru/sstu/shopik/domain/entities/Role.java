package ru.sstu.shopik.domain.entities;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "role")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "roleId")
    private int id;

    @Column(name = "role")
    private String role;

/*    @ManyToMany(mappedBy = "roles")
    private Set<User> users;*/

    public Role() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
