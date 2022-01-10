package com.surabhi.taskapp.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "address", schema = "public")

public class AddressEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", insertable = false, updatable = false)
    private Long userId;

    private String place;

    private Integer pincode;

    private String state;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity userEntity;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AddressEntity that = (AddressEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(userId, that.userId) && Objects.equals(place, that.place) && Objects.equals(pincode, that.pincode) && Objects.equals(state, that.state);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userId, place, pincode, state);
    }

    @Override
    public String toString() {
        return "AddressEntity{" +
                "id=" + id +
                ", userId=" + userId +
                ", place='" + place + '\'' +
                ", pincode=" + pincode +
                ", state='" + state + '\'' +
                '}';
    }


}
