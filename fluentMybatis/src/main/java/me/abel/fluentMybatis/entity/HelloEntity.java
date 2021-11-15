package me.abel.fluentMybatis.entity;

import cn.org.atool.fluent.mybatis.annotation.FluentMybatis;
import cn.org.atool.fluent.mybatis.base.IEntity;
import cn.org.atool.fluent.mybatis.base.RichEntity;
import lombok.Data;

@Data
@FluentMybatis
public class HelloEntity extends RichEntity {

    private Long id;

    @Override
    public Class<? extends IEntity> entityClass() {
        return HelloEntity.class;
    }
}
