package com.stomp.chat.common;


import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedDate;

@MappedSuperclass
@Getter
@Setter
@EntityListeners(AuditorAwareImpl.class)
public class BaseMember extends BaseEntity{

    @CreatedBy
    @Column(length = 65,updatable = false)
    private String createdBy; //생성한 사람(email)
    @LastModifiedDate
    @Column(length = 65,insertable = false)
    private String modifiedBy;// 수정한 사람




}
