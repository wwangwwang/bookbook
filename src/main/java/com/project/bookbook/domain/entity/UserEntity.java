package com.project.bookbook.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.hibernate.annotations.DynamicUpdate;


import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@DynamicUpdate
@Entity
@Table(name = "user")
public class UserEntity {
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long userId; // 사용자ID

    @Column(nullable = false)
    private String userName; // 사용자이름

    @Column(nullable = false)
    private String userRRN; // 주민등록번호

    @Column(nullable = false)
    private String gender; // 성별

    @Column(nullable = false,unique = true)
    private String email; // 이메일

    @Column(nullable = false)
    private String phoneNumber; // 핸드폰번호

    @Column(nullable = false)
    private String password; // 비밀번호

    @Column(nullable = false)
    private String birthDate; // 생년월일

    @Column(nullable = false)
    private String postcode; // 우편번호

    @Column(nullable = false)
    private String address; // 주소
    
    @Column(nullable = true)
    private String entrance; // 공동현관 비밀번호

    @Column(nullable = false)
    private String extraAddress; // 참고항목

    @Column(nullable = false)
    private String detailAddress; // 상세주소
    
    @Column(nullable = true)
    private long status; // 회원상태 
    
    @ManyToOne
    @JoinColumn(name = "sellerId", nullable = true)
    private SellerEntity seller;

    @Enumerated(EnumType.STRING)
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "role", joinColumns = @JoinColumn(name = "userId"))
    @Builder.Default
    @Column(name = "role")
    private Set<Role> roles = new HashSet<Role>(); // 'Role' Enum 타입을 별도로 정의
    
	public UserEntity addRole(Role role) {
		roles.add(role);
		return this;
	}
    
	public UserEntity addRoleByRange(String role) {
		for(int i=0; i<=Role.valueOf(role).ordinal(); i++) { //.ordinal() == 범위
			roles.add(Role.values()[i]); //addRole 메서드가 없는 경우
		}

		return this;
	}
    public void setSeller(SellerEntity seller) {
        this.seller = seller;
    }
}