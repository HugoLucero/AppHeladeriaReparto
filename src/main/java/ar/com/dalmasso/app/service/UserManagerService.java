package ar.com.dalmasso.app.service;

import ar.com.dalmasso.app.domain.Usuario;
import ar.com.dalmasso.app.domain.UsuarioDto;

import java.util.List;

public interface UserManagerService {

    Usuario getUserByUsername(String userName);

    void setPassword(String username, String password);

    UsuarioDto createUser(UsuarioDto usuarioDto);

    UsuarioDto editUser(UsuarioDto usuarioDto);

    void setToken2User(String username, String token);

    UsuarioDto editRoles(String userName, String token, List<String> roles);
}
