package ar.com.dalmasso.app.service;

import ar.com.dalmasso.app.domain.Usuario;
import ar.com.dalmasso.app.dto.UsuarioDto;
import ar.com.dalmasso.app.util.ErrorHandler;
import org.springframework.data.domain.Page;

import java.text.ParseException;
import java.util.List;

public interface UserManagerService {

    Usuario getUserByUsername(String userName);

    UsuarioDto getUserDtoByUsername(String username);

    void setPassword(String username, String password);

    UsuarioDto createUser(UsuarioDto usuarioDto) throws ParseException, ErrorHandler;

    UsuarioDto editUser(UsuarioDto usuarioDto) throws ParseException, ErrorHandler;

    void setToken2User(String username, String token);

    UsuarioDto editRoles(String userName, String token, List<String> roles) throws ErrorHandler;

    Page<UsuarioDto> getUsers(int pageNo, int pageSize, String sortField, String sortDirection);

    List<String> getRoles();

    void deleteUser(String usernameToDelete, String tokenUserLogged) throws ErrorHandler;
}
