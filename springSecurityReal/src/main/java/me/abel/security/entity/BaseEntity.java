package me.abel.security.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import java.io.Serializable;
import java.util.Date;

import static javax.persistence.TemporalType.TIMESTAMP;

@Data
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseEntity implements Serializable {

    @CreatedDate
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @Temporal(TIMESTAMP)
    private Date createdTime;

    @LastModifiedDate
    @Temporal(TIMESTAMP)
    private Date lastUpdatedTime;

    @Column(columnDefinition = "varchar(32) default '' comment '创建人Id' ")
    private String createUser;

    @Column(columnDefinition = "varchar(64) default '' comment '创建人名称' ")
    private String createName;

    @Column(columnDefinition = "varchar(32) default '' comment '最后更新人Id' ")
    private String updateUser;

    @Column(columnDefinition = "varchar(64) default '' comment '最后更新人姓名' ")
    private String updateName;

}
