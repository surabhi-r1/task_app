package com.surabhi.taskapp.entity;

import lombok.*;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user_entity", schema = "public")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Column(name = "reg_no")
    private Integer regNo;
    private String gender;
    @Column(name = "mob_no")
    private Long mobileNumber;
    private Integer age;

    @OneToMany(orphanRemoval = true, mappedBy = "userEntity", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @LazyCollection(LazyCollectionOption.TRUE)
    private Set<AddressEntity> addressEntityList = new HashSet<>();


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserEntity that = (UserEntity) o;
        return Objects.equals(name, that.name) && Objects.equals(regNo, that.regNo) && Objects.equals(gender, that.gender) && Objects.equals(mobileNumber, that.mobileNumber) && Objects.equals(age, that.age);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, regNo, gender, mobileNumber, age);
    }

    @Override
    public String toString() {
        return "UserEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", regNo=" + regNo +
                ", gender='" + gender + '\'' +
                ", mobileNumber=" + mobileNumber +
                ", age=" + age +
                '}';
    }


}
