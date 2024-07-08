package ar.com.dalmasso.app.service;

public interface PasswordManagerService {

    String changePassword(String username, String newPassword);

    String createNewPassword(String newPassword);

    void resetPassword(String username, String token);
}
