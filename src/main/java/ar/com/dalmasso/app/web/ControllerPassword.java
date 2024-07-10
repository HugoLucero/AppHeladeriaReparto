package ar.com.dalmasso.app.web;


import ar.com.dalmasso.app.dto.UsuarioDto;
import ar.com.dalmasso.app.service.PasswordManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.Objects;

@Controller
@RequestMapping(path = "/password")
public class ControllerPassword {

    private static final String RESTABLECE = "restablece";
    private static final String MENSAJE_ALERTA = "mensajeAlerta";
    private static final String TIPO_ALERTA = "tipoAlerta";
    private static final String RETURN_USUARIO = "usuario/usuario";

    private final PasswordManagerService passwordManagerService;

    @Autowired
    public ControllerPassword(PasswordManagerService passwordManagerService) {
        this.passwordManagerService = passwordManagerService;
    }

    @PostMapping(value = "/reset")
    public String restablece(Model model, @ModelAttribute @Valid UsuarioDto dto) {

        try {
            UsuarioDto usuarioDto = new UsuarioDto();
            if(Objects.nonNull(dto))
                passwordManagerService.resetPassword(dto.getUsername(), dto.getToken());

            model.addAttribute("dto", usuarioDto);
            model.addAttribute(RESTABLECE, true);
            model.addAttribute(MENSAJE_ALERTA, "Su contraseña se ha restablecido");
            model.addAttribute(TIPO_ALERTA, "success");
            return "usuario/token";
        } catch (Exception e) {
            UsuarioDto usuarioDto = new UsuarioDto();
            usuarioDto.setToken("Error de token");
            model.addAttribute("dto", usuarioDto);
            model.addAttribute(RESTABLECE, true);
            model.addAttribute(MENSAJE_ALERTA, e.getMessage());
            model.addAttribute(TIPO_ALERTA, "error");
            return RETURN_USUARIO;
        }
    }
}
