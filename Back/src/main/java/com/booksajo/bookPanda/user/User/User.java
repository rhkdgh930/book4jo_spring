package com.booksajo.bookPanda.user.User;

import com.booksajo.bookPanda.user.JWT.Authority;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Table(name = "users")
public class User {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Column(name = "user_name")
    @NotEmpty(message = "이름을 입력해주세요.")
    private String userName;

    @Column(name = "user_email")
    @NotEmpty(message = "아이디를 입력해주세요.")
    @Email(message = "아이디는 이메일 형식이어야 합니다.")
    private String userEmail;

    @Column(name = "user_password")
    @NotEmpty(message = "비밀번호를 입력해주세요.")
    private String userPassword;

    @Column(name = "address")
    @NotEmpty(message = "주소를 입력해주세요.")
    private String address;

    @ManyToMany
    @JoinTable(
        name = "user_authority",
        joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")},
        inverseJoinColumns = {@JoinColumn(name = "authority_name", referencedColumnName = "authority_name")})
    private Set<Authority> authorities;
}
