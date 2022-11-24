package com.project.devcat.service;

import com.project.devcat.domain.Member;
import com.project.devcat.domain.MemberRoleEnum;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.ArrayList;
import java.util.Collection;


public class UserDetailsImpl implements UserDetails {

    private final Member member;

    public UserDetailsImpl(Member member) {
        this.member = member;
    }

    public Member getMember() {
        return member;
    }

    public String getPassword() {
        return member.getPassword();
    }

    @Override
    public String getUsername() {
        return null;
    }

    /* 계정 만료 여부 (true : 만료되지 않음) */
    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    /* 계정 잠김 여부 */
    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    /* 패스워드 만료 여부 */
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /* 계정 활성화 여부 */
    public boolean isEnabled() {
        return true;
    }

    public Collection<? extends GrantedAuthority> getAuthorities() {
        MemberRoleEnum memberRoleEnum = member.getRole();

        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(memberRoleEnum.toString());
        Collection<GrantedAuthority> authorities = new ArrayList<>(); // 권한 여러개일 때
        authorities.add(authority);

        return authorities;
    }
}
