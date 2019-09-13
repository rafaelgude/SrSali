package br.com.srsali.srsali.security;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import br.com.srsali.srsali.enums.Funcao;

public class UserSS implements UserDetails {
    private static final long serialVersionUID = 1L;
    
    private int id;
    private String email;
    private String senha;
    private Collection<? extends GrantedAuthority> authorities;
    
    public UserSS() {
    }
    
    public UserSS(int id, String email, String senha, Set<Funcao> funcoes) {
        super();
        this.id = id;
        this.email = email;
        this.senha = senha;
        this.authorities = funcoes.stream().map(x -> new SimpleGrantedAuthority(x.getRole())).collect(Collectors.toList());
    }

    public int getId() {
        return this.id;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return this.senha;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
