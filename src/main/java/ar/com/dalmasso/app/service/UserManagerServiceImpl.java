package ar.com.dalmasso.app.service;

import ar.com.dalmasso.app.dao.RolRepository;
import ar.com.dalmasso.app.dao.UsuarioDao;
import ar.com.dalmasso.app.domain.Rol;
import ar.com.dalmasso.app.domain.Usuario;
import ar.com.dalmasso.app.dto.UsuarioDto;
import ar.com.dalmasso.app.util.CodeErrors;
import ar.com.dalmasso.app.util.EncriptPass;
import ar.com.dalmasso.app.util.ErrorHandler;
import ar.com.dalmasso.app.util.Utiles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
public class UserManagerServiceImpl implements UserManagerService {


    private final UsuarioDao usuarioDao;
    private final RolRepository rolDao;

    @Autowired
    public UserManagerServiceImpl(UsuarioDao usuarioDao, RolRepository rolDao) {
        this.usuarioDao = usuarioDao;
        this.rolDao = rolDao;
    }

    @Override
    public Usuario getUserByUsername(String username) {
        return usuarioDao.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(CodeErrors.USER_NOT_FOUND.name()));
    }

    @Override
    public UsuarioDto getUserDtoByUsername(String username) {
        return entity2Dto(getUserByUsername(username));
    }

    @Override
    public void setPassword(String username, String password) {
        Usuario usuario = getUserByUsername(username);
        usuario.setPassword(password);
        usuarioDao.save(usuario);
    }

    @Override
    public UsuarioDto createUser(UsuarioDto dto) throws ParseException {
        List<Rol> roles = new ArrayList<>();
        roles.add(new Rol(null, "ROLE_USER"));
        int token = Utiles.getFiveDigitsNumber();
        Usuario user = dto2Entity(dto);
        user.setPassword(EncriptPass.encriptarPassword(dto.getPassword()));
        user.setToken(Utiles.encodeB64(Integer.toString(token)));
        user.setRoles(roles);

        usuarioDao.saveAndFlush(user);

        return entity2Dto(user);
    }

    @Override
    public UsuarioDto editUser(UsuarioDto usuarioDto) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        Date parsed = format.parse(usuarioDto.getFechaNacimiento());
        Usuario usuario = usuarioDao.findByToken(usuarioDto.getToken()).orElseThrow(() -> new UsernameNotFoundException(CodeErrors.TOKEN_INCORRECT.name()));

        if(Objects.nonNull(usuarioDto.getUsername()) && !usuario.getUsername().equals(usuarioDto.getUsername()))
            usuario.setUsername(usuarioDto.getUsername());
        if(Objects.nonNull(usuarioDto.getMail()) && !usuario.getMail().equals(usuarioDto.getMail()))
            usuario.setMail(usuarioDto.getMail());
        if(Objects.nonNull(usuarioDto.getFechaNacimiento()) && !usuario.getFechaNacimiento().equals(parsed))
            usuario.setFechaNacimiento(parsed);

        usuarioDao.saveAndFlush(usuario);

        return entity2Dto(usuario);
    }

    @Override
    public void setToken2User(String username, String token) {
        Usuario usuario = getUserByUsername(username);
        usuario.setToken(Utiles.encodeB64(token));
        usuarioDao.save(usuario);
    }

    @Override
    public UsuarioDto editRoles(String userName, String token, List<String> roles) throws ErrorHandler {
        if(Objects.isNull(roles) || roles.isEmpty())
            throw new ErrorHandler(CodeErrors.ROLES_NULL_EMPTY.name());


        Usuario usuario = getUserByUsername(userName);
        String tokenDecoded = Utiles.decodeB64(usuario.getToken());
        if (!tokenDecoded.equals(token))
            throw new ErrorHandler(CodeErrors.TOKEN_NOT_EQUAL.name());


        List<Rol> rolList = usuario.getRoles();
        for (String rol : roles) {
            Rol r = rolDao.findByNombreIgnoreCase(rol);
            rolList.add(Objects.requireNonNull(r, CodeErrors.ROL_NOT_FOUND.name()));
        }
        usuarioDao.saveAndFlush(usuario);

        return entity2Dto(usuario);
    }

    private Usuario dto2Entity(UsuarioDto dto) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        Date parsed = format.parse(dto.getFechaNacimiento());
        Usuario user = new Usuario();
        user.setUsername(dto.getUsername());
        user.setMail(dto.getMail());
        user.setFechaNacimiento(parsed);
        return user;
    }

    private UsuarioDto entity2Dto(Usuario user){
        return new UsuarioDto(user.getUsername(), null, user.getMail(), user.getFechaNacimiento().toString(), Utiles.decodeB64(user.getToken()), null);
    }
}
