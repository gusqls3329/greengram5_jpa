package com.green.greengram4.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

//나를 상속받으면 나를 가지고있는 매핑정보를 자식들에게 전달하는 객체
@Data
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class) //엔터티를 데이터베이스에 저장하기전에 콜백으로 class가가지고있는 기능들을 가져와 공통적으로 ...??
public class BaseEntity {
    @CreatedDate
    @Column(updatable = false)//수정 불가능함
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;
}
