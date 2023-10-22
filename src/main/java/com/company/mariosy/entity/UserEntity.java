package com.company.mariosy.entity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity(name = "user_account")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "external_id", unique = true)
    private UUID externalId;

    @Column(name = "username", unique = true)
    private String username;
    @ManyToMany(mappedBy = "receivers")
    private Set<MariosEntity> received_marioses;
    @OneToMany(
            mappedBy = "creator",
            cascade = {CascadeType.REMOVE})
    private Set<MariosEntity> created_marioses;

    public UserEntity() {
    }

    public UserEntity(UUID externalId, String username) {
        this.username = username;
        this.externalId = externalId;
        this.created_marioses = new HashSet<>();
    }

    public UUID getExternalId() {
        return externalId;
    }

    public void setExternalId(UUID externalId) {
        this.externalId = externalId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Set<MariosEntity> getReceived_marioses() {
        return received_marioses;
    }

    public Set<MariosEntity> getCreated_marioses() {
        return created_marioses;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username +
                '}';
    }
}
