package ar.com.dalmasso.app.service;

import ar.com.dalmasso.app.dao.RolRepository;
import ar.com.dalmasso.app.dao.UsuarioDao;
import ar.com.dalmasso.app.domain.Rol;
import ar.com.dalmasso.app.domain.Usuario;
import ar.com.dalmasso.app.domain.UsuarioDto;
import ar.com.dalmasso.app.util.Utiles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserManagerServiceImpl implements UserManagerService {


    private final UsuarioDao usuarioDao;
    private final PasswordManagerService passwordManagerService;
    private final RolRepository rolDao;

    @Autowired
    public UserManagerServiceImpl(UsuarioDao usuarioDao, PasswordManagerService passwordManagerService, RolRepository rolDao) {
        this.usuarioDao = usuarioDao;
        this.passwordManagerService = passwordManagerService;
        this.rolDao = rolDao;
    }

    @Override
    public Usuario getUserByUsername(String username) {
        return usuarioDao.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not exist"));
    }

    @Override
    public void setPassword(String username, String password) {
        Usuario usuario = getUserByUsername(username);
        usuario.setPassword(password);
        usuarioDao.save(usuario);
    }

    @Override
    public UsuarioDto createUser(UsuarioDto dto) {
        List<Rol> roles = new ArrayList<>();
        roles.add(rolDao.findByNombreIgnoreCase("USER"));
        int token = Utiles.getFiveDigitsNumber();
        Usuario user = dto2Entity(dto);
        user.setPassword(passwordManagerService.createNewPassword(dto.getPassword()));
        user.setToken(Utiles.encodeB64(Integer.toString(token)));
        user.setRoles(roles);

        usuarioDao.saveAndFlush(user);

        return entity2Dto(user);
    }

    @Override
    public UsuarioDto editUser(UsuarioDto usuarioDto) {
        return null;
    }

    @Override
    public void setToken2User(String username, String token) {
        Usuario usuario = getUserByUsername(username);
        usuario.setToken(Utiles.encodeB64(token));
    }

    @Override
    public UsuarioDto editRoles(String userName, String token, List<String> roles) {
        return null;
    }

    private Usuario dto2Entity(UsuarioDto dto) {
        Usuario user = new Usuario();
        user.setUsername(dto.getUsername());
        user.setMail(dto.getMail());
        user.setFechaNacimiento(dto.getFechaNacimiento());
        return user;
    }

    private UsuarioDto entity2Dto(Usuario user){
        return new UsuarioDto(user.getUsername(), null, user.getMail(), user.getFechaNacimiento(), Utiles.decodeB64(user.getToken()));
    }
}
