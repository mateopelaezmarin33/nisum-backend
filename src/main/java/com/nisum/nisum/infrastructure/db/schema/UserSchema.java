package com.nisum.nisum.infrastructure.db.schema;

import com.nisum.nisum.entity.user.model.Phone;
import com.nisum.nisum.entity.user.model.User;
import com.nisum.nisum.infrastructure.user.dto.UserRegistrationData;
import jakarta.persistence.*;
import lombok.Getter;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Entity
@Table(name = "Usuario")
@Getter
public class UserSchema {

    public UserSchema(UserRegistrationData user, String token) {
        this.email = user.getEmail();
        this.name = user.getName();
        this.password = user.getPassword();
        this.created = LocalDateTime.now();
        this.lastLogin = LocalDateTime.now();
        this.modified = LocalDateTime.now();
        this.token = token;
        this.phones = user.getPhones().stream()
                .map(phone -> {
                    PhoneSchema phoneSchema = new PhoneSchema(phone);
                    phoneSchema.setUser(this);
                    return phoneSchema;
                })
                .collect(Collectors.toList());
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "user_id")
    private Long id;

    @NotBlank
    @Column(unique = true)
    private String email;

    @NotBlank
    @Column()
    private String name;

    @NotBlank
    @Column()
    private String password;

    @Column()
    private LocalDateTime created;

    @Column()
    private LocalDateTime modified;

    @Column(name = "last_login")
    private LocalDateTime lastLogin;

    @Column(name = "token")
    private String token;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private List<PhoneSchema> phones;


    public UserSchema() {
       super();
    }

    public User toUser() {
        return User.builder()
                .name(this.name)
                .email(this.email)
                .password(this.password)
                .phones(
                        phones.stream()
                                .map(phone -> new Phone(
                                        phone.getNumber(),
                                        phone.getCityCode(),
                                        phone.getCountryCode()
                                ))
                                .collect(Collectors.toList())
                )
                .token(this.token)
                .created(this.created)
                .modified(this.modified)
                .lastLogin(this.lastLogin)
                .isActive(true)
                .id(UUID.nameUUIDFromBytes(this.id.toString().getBytes()))
                .build();
    }
}
