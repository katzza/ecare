package com.ecare.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "number", schema = "public", catalog = "eCare")
@NamedQueries({
        @NamedQuery(name = "FreeNumber.findUnbooked",
                query = "SELECT n.numberId, n.phoneNumber FROM NumberEntity n where n.isBooked = false"),

        @NamedQuery(name = "FreeNumber.findByPhone",
                query = "SELECT n FROM NumberEntity n where n.phoneNumber = :phoneNumber"),
})

public class NumberEntity implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int numberId;

    @Column(name = "phone_number", unique = true, length = 7)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String phoneNumber;

    @Column(name = "booked")
    private boolean isBooked = false;

    public NumberEntity(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
