/*
 * Software creado y diseñado por Hugo Lucero.
* Derechos reservados para Hugo Lucero.
* Para más información contactarse a:
* hlucerodiaz@gmail.com
 */
package ar.com.dalmasso.app.service;

import ar.com.dalmasso.app.dao.UsuarioDao;
import ar.com.dalmasso.app.domain.Rol;
import ar.com.dalmasso.app.domain.Usuario;
import ar.com.dalmasso.app.util.CodeErrors;
import ar.com.dalmasso.app.util.ErrorHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

/**
 *
 * @author Hugo Lucero - Desarrollador Full - Stack
 */
@Service("userDetailsService")
public class UsuarioService implements UserDetailsService {
    
    private final UsuarioDao usuarioDao;

    @Autowired
    public UsuarioService(UsuarioDao usuarioDao) {
        this.usuarioDao = usuarioDao;
    }

    @Override
    @Transactional(readOnly=true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = null;
        try {
            usuario = usuarioDao.findByUsername(username).orElseThrow(() -> new ErrorHandler(CodeErrors.USER_NOT_FOUND.name()));
        } catch (ErrorHandler e) {
            throw new UsernameNotFoundException(e.getMessage());
        }

        if(usuario == null){
            throw new UsernameNotFoundException(username);
        }
        
        ArrayList<GrantedAuthority> roles = new ArrayList<>();
        
        for(Rol rol : usuario.getRoles()){
            roles.add(new SimpleGrantedAuthority(rol.getNombre()));
        }
        
        return new User(usuario.getUsername(), usuario.getPassword(), roles);
    }
}
