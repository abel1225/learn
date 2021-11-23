package me.abel.security.entity;

import lombok.Data;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;

@Entity
@Table(name = "tbl_user")
@Data
@DynamicInsert
public class UserEntity extends BaseEntity{

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(length = 32)
    private String loginId;

    @Column(length = 128)
    private String passwd;

    @Column(length = 128)
    private String salt;
}
