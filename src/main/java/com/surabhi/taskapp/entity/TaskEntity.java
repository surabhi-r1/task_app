package com.surabhi.taskapp.entity;

import lombok.*;
import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "task_entity", schema = "public")
public class TaskEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name")
    private String name;
    private String description;

    @CreationTimestamp
    @Column(name = "created_date")
    private Date createdDate;
    @Column(name="user_id")

    private Integer userId;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TaskEntity that = (TaskEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(description, that.description) && Objects.equals(createdDate, that.createdDate) ;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, createdDate);
    }


    @OneToMany(orphanRemoval = true, mappedBy = "taskEntity", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @LazyCollection(LazyCollectionOption.FALSE)
    private Set<SubTaskEntity> subTaskEntityList = new HashSet<>();

//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        TaskEntity that = (TaskEntity) o;
//        return id == that.id && Objects.equals(name, that.name) && Objects.equals(description, that.description) && Objects.equals(createdDate, that.createdDate);
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(id, name, description, createdDate);
//    }

    public void setSubTaskEntityList(Set<SubTaskEntity> subTaskEntityList) {
        this.subTaskEntityList.clear();
        if (!subTaskEntityList.isEmpty()) {
            subTaskEntityList.forEach(subTaskEntity -> {
                subTaskEntity.setTaskEntity(this);
                this.subTaskEntityList.add(subTaskEntity);
            });
        }
    }

    @Override
    public String toString() {
        return "TaskEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", createdDate=" + createdDate +
                '}';
    }
}
