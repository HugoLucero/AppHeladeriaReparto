package ar.com.dalmasso.app.service;

import ar.com.dalmasso.app.dao.UsuarioDao;
import ar.com.dalmasso.app.domain.Rol;
import ar.com.dalmasso.app.domain.Usuario;
import ar.com.dalmasso.app.dto.UsuarioDto;
import ar.com.dalmasso.app.util.CodeErrors;
import ar.com.dalmasso.app.util.EncriptPass;
import ar.com.dalmasso.app.util.ErrorHandler;
import ar.com.dalmasso.app.util.RolesEnum;
import ar.com.dalmasso.app.util.Utiles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class UserManagerServiceImpl implements UserManagerService {


    private final UsuarioDao usuarioDao;

    @Autowired
    public UserManagerServiceImpl(UsuarioDao usuarioDao) {
        this.usuarioDao = usuarioDao;
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
    public UsuarioDto createUser(UsuarioDto dto) throws ErrorHandler {
        try {
            List<Rol> roles = new ArrayList<>();
            if (usuarioDao.count() > 0)
                roles.add(new Rol(null, "ROLE_USER"));
            else {
                roles.add(new Rol(null, "ROLE_USER"));
                roles.add(new Rol(null, "ROLE_ADMIN"));
            }

            if (usuarioDao.existsByUsernameIgnoreCase(dto.getUsername()))
                throw new ErrorHandler(CodeErrors.USER_REPEATED.name());

            validatePassword(dto.getPassword().trim());

            int token = Utiles.getFiveDigitsNumber();
            Usuario user = dto2Entity(dto);
            user.setPassword(EncriptPass.encriptarPassword(dto.getPassword()));
            user.setToken(Utiles.encodeB64(Integer.toString(token)));
            user.setRoles(roles);

            usuarioDao.saveAndFlush(user);

            return entity2Dto(user);
        } catch (Exception e) {
            throw new ErrorHandler(e.getMessage());
        }
    }

    @Override
    public UsuarioDto editUser(UsuarioDto usuarioDto) throws ParseException, ErrorHandler {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date parsed = format.parse(usuarioDto.getFechaNacimiento());
        Usuario usuario = usuarioDao.findByUsername(usuarioDto.getUsername().trim()).orElseThrow(() -> new UsernameNotFoundException(CodeErrors.USER_INVALID.name()));

        if(Objects.nonNull(usuarioDto.getUsername()) && !usuario.getUsername().equals(usuarioDto.getUsername()))
            usuario.setUsername(usuarioDto.getUsername());
        if(Objects.nonNull(usuarioDto.getMail()) && !usuario.getMail().equals(usuarioDto.getMail()))
            usuario.setMail(usuarioDto.getMail());
        if(Objects.nonNull(usuarioDto.getFechaNacimiento()) && !usuario.getFechaNacimiento().equals(parsed))
            usuario.setFechaNacimiento(parsed);
        if(Objects.nonNull(usuarioDto.getPassword()) && !usuarioDto.getPassword().equals("null")) {
            validatePassword(usuarioDto.getPassword());
            validateSetPassword(usuarioDto.getPassword(), usuario.getPassword());
            usuario.setPassword(EncriptPass.encriptarPassword(usuarioDto.getPassword()));
        }

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


        validateUserLogged(token);

        Usuario usuario = getUserByUsername(userName);

        List<Rol> rolList = usuario.getRoles();
        for (String rol : roles) {
            if (rolList.stream().noneMatch(rol1 -> rol1.getNombre().equals(rol)))
                rolList.add(new Rol(null, rol));
        }
        usuarioDao.saveAndFlush(usuario);

        return entity2Dto(usuario);
    }

    private void validateUserLogged(String token) throws ErrorHandler {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = null;
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            username = userDetails.getUsername();
        }
        Usuario loggedUser = getUserByUsername(username);
        String tokenDecoded = Utiles.decodeB64(loggedUser.getToken());
        if (!tokenDecoded.equals(token))
            throw new ErrorHandler(CodeErrors.TOKEN_NOT_EQUAL.name());
    }

    @Override
    public Page<UsuarioDto> getUsers(int pageNo, int pageSize, String sortField, String sortDirection) {
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() :
                Sort.by(sortField).descending();
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
        return usuarioDao.findAll(pageable).map(this::entity2Dto);
    }

    @Override
    public List<String> getRoles() {
        return Arrays.stream(RolesEnum.values()).map(Enum::name).collect(Collectors.toList());
    }

    @Override
    public void deleteUser(String usernameToDelete, String tokenUserLogged) throws ErrorHandler {
        validateUserLogged(tokenUserLogged);
        if (!usuarioDao.existsByUsernameIgnoreCase(usernameToDelete))
            throw new ErrorHandler(CodeErrors.USER_NOT_FOUND.name());
        usuarioDao.delete(getUserByUsername(usernameToDelete));
    }

    private Usuario dto2Entity(UsuarioDto dto) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date parsed = format.parse(dto.getFechaNacimiento());
        Usuario user = new Usuario();
        user.setUsername(dto.getUsername());
        user.setMail(dto.getMail());
        user.setFechaNacimiento(parsed);
        return user;
    }

    private UsuarioDto entity2Dto(Usuario user){
        return new UsuarioDto(user.getUsername(), "", user.getMail(), user.getFechaNacimiento().toString(),
                Utiles.decodeB64(user.getToken()), user.getRoles().stream().map(Rol::getNombre).collect(Collectors.toList()));
    }

    private void validatePassword(String password) throws ErrorHandler {
        String regex = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$";
        List<String> list = new ArrayList<>();
        list.add("1234");
        list.add("1111");
        list.add("0000");
        list.add("1234567890");

        if (list.contains(password)) {
            throw new ErrorHandler(CodeErrors.PASSWORD_NOT_1234.name());
        } else if (password.length() < 8) {
            throw new ErrorHandler(CodeErrors.PASSWORD_NOT_LENGTH.name());
        }else if (!password.matches(regex)) {
            throw new ErrorHandler(CodeErrors.PASSWORD_NOT_REGEX.name());
        }
    }

    private void validateSetPassword(String newPassword, String oldPassword) throws ErrorHandler {
        if (newPassword.isEmpty() || newPassword.equals("null"))
            throw new ErrorHandler(CodeErrors.PASSWORD_EMPTY.name());
        if (Boolean.FALSE.equals(EncriptPass.matchPassword(oldPassword, newPassword)))
            throw new ErrorHandler(CodeErrors.PASSWORD_EQUAL.name());

    }
}
