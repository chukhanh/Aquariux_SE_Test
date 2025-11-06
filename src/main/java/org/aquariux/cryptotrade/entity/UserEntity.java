package org.aquariux.cryptotrade.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "USERS")
@Data
@Getter
@Setter
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ID;

    @Column(nullable = false, name = "USERNAME")
    private String username;
    @Column(nullable = false, name = "PASSWORD")
    private String password;
    @Column(nullable = false, name = "ROLE")
    private String role;
}
