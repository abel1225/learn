package me.abel.fluentMybatis.entity;

import cn.org.atool.fluent.mybatis.annotation.FluentMybatis;
import cn.org.atool.fluent.mybatis.base.IEntity;
import cn.org.atool.fluent.mybatis.base.RichEntity;
import lombok.Data;

@Data
@FluentMybatis(table = "tbl_increment")
public class HelloEntity extends RichEntity {

    private String ssnName;
    private Long ssn;

    @Override
    public Class<? extends IEntity> entityClass() {
        return HelloEntity.class;
    }
}
