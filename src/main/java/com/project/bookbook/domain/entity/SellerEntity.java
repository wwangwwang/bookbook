package com.project.bookbook.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "Seller")
public class SellerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long sellerId;

    @Column(nullable = false)
    private String shopName;

    @Column(nullable = false)
    private String telNum;

    @Column(nullable = false, unique = true)
    private String businessNum;

    @Column(nullable = true)
    private String businessReg; 

    @Column(nullable = false)
    private String bank;

    @Column(nullable = false)
    private String account; 

    @Column(nullable = false)
    private String accountHolder;

    @Column(nullable = true)
    private String settlementAmount;

    private String businessRegCopy;

    // User fields
    @Column(nullable = false)
    private String userName;

    @Column(nullable = false)
    private String userRRN;

    @Column(nullable = false)
    private String gender;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String phoneNumber;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String birthDate;

    @Column(nullable = false)
    private String postcode;

    @Column(nullable = false)
    private String address;

    @Column(nullable = true)
    private String extraAddress;

    @Column(nullable = false)
    private String detailAddress;

    @Column(nullable = true)
    private Long status;
    
    //회원가입 승인 상태
    @Enumerated(EnumType.STRING)
    private ApprovalStatus approvalStatus;

  
    
    @OneToOne(mappedBy = "seller")
    private UserEntity user;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "business_reg_image_id", referencedColumnName = "id")
    private ImageEntity businessRegImage;
}