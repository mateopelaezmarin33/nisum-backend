package com.nisum.nisum.infrastructure.db.schema;

import com.nisum.nisum.entity.user.model.Phone;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "Telefono")
@Getter
@Setter
public class PhoneSchema {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column()
    private String number;
    @Column(name = "city_code")
    private String cityCode;
    @Column(name = "country_code")
    private String countryCode;
    @NotNull
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserSchema user;


    public PhoneSchema(Phone phone) {
        this.number = phone.getNumber();
        this.cityCode = phone.getCitycode();
        this.countryCode = phone.getContrycode();
    }

    public PhoneSchema() {
        super();
    }
}
