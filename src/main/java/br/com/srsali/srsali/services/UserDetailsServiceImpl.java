package br.com.srsali.srsali.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.srsali.srsali.repositories.UsuarioRepository;
import br.com.srsali.srsali.security.UserSS;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    
    @Autowired private UsuarioRepository usuarioRepo;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        var usuario = usuarioRepo.findByEmail(email);
        if (usuario == null)
            throw new UsernameNotFoundException(email);
            
        return new UserSS(usuario.getId(), usuario.getEmail(), usuario.getSenha(), usuario.getFuncoes());
    }

}
