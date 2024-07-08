package ar.com.dalmasso.app.service;

import ar.com.dalmasso.app.domain.Usuario;
import ar.com.dalmasso.app.util.EncriptPass;
import ar.com.dalmasso.app.util.ResponseUtil;
import ar.com.dalmasso.app.util.Utiles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class PasswordManagerServiceImpl implements PasswordManagerService {


    private final UserManagerService userManagerService;

    @Autowired
    public PasswordManagerServiceImpl(UserManagerService userManagerService) {
        this.userManagerService = userManagerService;
    }

    @Override
    public String changePassword(String userName, String newPassword) {
        userManagerService.setPassword(userName, EncriptPass.encriptarPassword(newPassword.trim()));
        return ResponseUtil.OK.toString();
    }

    @Override
    public String createNewPassword(String newPassword) throws RuntimeException{
        return EncriptPass.encriptarPassword(newPassword.trim());
    }

    @Override
    public void resetPassword(String username, String token) {
        Usuario usuario = userManagerService.getUserByUsername(username);
        if(!usuario.getToken().equals(Utiles.decodeB64(token))) {
            throw new UsernameNotFoundException("Token is incorrect");
        } else {
            userManagerService.setPassword(usuario.getUsername(), token);
        }
    }
}
