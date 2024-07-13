package ar.com.dalmasso.app.web;

import ar.com.dalmasso.app.dto.UsuarioDto;
import ar.com.dalmasso.app.service.UserManagerService;
import ar.com.dalmasso.app.util.CodeErrors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping(path = "/usuario")
public class ControllerUser {

    private static final String RESTABLECE = "restablece";
    private static final String MENSAJE_ALERTA = "mensajeAlerta";
    private static final String TIPO_ALERTA = "tipoAlerta";
    private static final String RETURN_USUARIO = "usuario/usuario";
    private static final String ALERTA_ROL = "alertaRol";
    private static final String ERROR = "error";
    private static final String USERNAME = "username";

    private final UserManagerService userManagerService;

    @Autowired
    public ControllerUser(UserManagerService userManagerService) {
        this.userManagerService = userManagerService;
    }

    // MVC

    @GetMapping(value = "/")
    public String createOrEditUserView(Model model, @RequestParam String username, @RequestParam boolean restablece,
                                       @RequestParam(required = false) Boolean edit,
                                       @RequestParam(required = false, defaultValue = "false") Boolean principal) {
        UsuarioDto usuarioDto;
        if (Objects.nonNull(username) && !username.isEmpty())
            usuarioDto = userManagerService.getUserDtoByUsername(username);
        else
            usuarioDto = new UsuarioDto();

        model.addAttribute("dto", usuarioDto);
        model.addAttribute("edit", edit);
        model.addAttribute("principal", principal);
        model.addAttribute(RESTABLECE, restablece);
        return RETURN_USUARIO;
    }


    @PostMapping(value = "/create")
    public String createOrEditUser(Model model, @ModelAttribute @Valid UsuarioDto dto,
                                   @RequestParam(required = false) Boolean edit,
                                   @RequestParam(required = false) Boolean principal) {

        try {
            UsuarioDto usuarioDto = new UsuarioDto();
            if (Objects.nonNull(dto)) {
                if (Boolean.TRUE.equals(edit))
                    usuarioDto = userManagerService.editUser(dto);
                else
                    usuarioDto = userManagerService.createUser(dto);
            }

            model.addAttribute("dto", usuarioDto);
            model.addAttribute(RESTABLECE, false);
            model.addAttribute(MENSAJE_ALERTA, Boolean.TRUE.equals(edit) ? "Usuario editado correctamente" : "Usuario creado correctamente");
            model.addAttribute(TIPO_ALERTA, "success");
            if (Boolean.TRUE.equals(edit)) {
                if (Boolean.FALSE.equals(principal)) {
                    return createOrEditUserView(model, usuarioDto.getUsername(), false, true, false);
                } else if (Boolean.TRUE.equals(principal)) {
                    return getUsers(model, false, 1, 20, USERNAME, Sort.Direction.ASC.name());
                }
            }
            return "usuario/token";
        } catch (Exception e) {
            UsuarioDto usuarioDto = new UsuarioDto();
            usuarioDto.setToken("Error de token");
            model.addAttribute("dto", usuarioDto);
            model.addAttribute(RESTABLECE, false);
            model.addAttribute(MENSAJE_ALERTA, e.getMessage());
            model.addAttribute(TIPO_ALERTA, ERROR);
            return Boolean.TRUE.equals(edit) ? createOrEditUserView(model, dto.getUsername(), false, true, false) : RETURN_USUARIO;
        }
    }

    @GetMapping("/getUsers")
    public String getUsers(Model model, @RequestParam(name = "noActive", required = false) Boolean noActive,
                           @RequestParam(name = "pageNo", required = false, defaultValue = "1") Integer pageNo,
                           @RequestParam(name = "pageSize", required = false, defaultValue = "20") Integer pageSize,
                           @RequestParam(name = "sortField", required = false, defaultValue = USERNAME) String sortField,
                           @RequestParam(name = "sortDirection", required = false, defaultValue = "asc") String sortDirection) {

        Page<UsuarioDto> list = userManagerService.getUsers(pageNo, pageSize, sortField, sortDirection);
        model.addAttribute("userList", list.getContent());
        model.addAttribute("totalUser", list.getTotalElements());
        model.addAttribute("totalPages", list.getTotalPages());
        model.addAttribute("actualPage", list.getNumber());
        model.addAttribute("roles", userManagerService.getRoles());

        return "usuario/listUsuarios";
    }

    @PostMapping("/changeRoles")
    public String changeRoles(Model model, @RequestParam(value = "rol", defaultValue = "") List<String> roles,
                              @RequestParam(value = USERNAME) String username,
                              @RequestParam(value = "token", defaultValue = "") String token,
                              @RequestParam(value = "delete", defaultValue = "false", required = false) Boolean delete) {
        try {
            if (Objects.isNull(token) || token.isEmpty()) {
                model.addAttribute(MENSAJE_ALERTA, CodeErrors.TOKEN_INCORRECT);
                model.addAttribute(TIPO_ALERTA, ERROR);
                return getUsers(model, false, 1, 20, USERNAME, Sort.Direction.ASC.name());
            }
            model.addAttribute(ALERTA_ROL, true);
            userManagerService.editRoles(username, token, roles);
            return getUsers(model, false, 1, 20, USERNAME, Sort.Direction.ASC.name());
        } catch (Exception e) {
            model.addAttribute(ALERTA_ROL, false);
            model.addAttribute(MENSAJE_ALERTA, e.getMessage());
            model.addAttribute(TIPO_ALERTA, ERROR);
            return getUsers(model, false, 1, 20, USERNAME, Sort.Direction.ASC.name());
        }
    }

    @PostMapping("/delete")
    public String deleteUser(Model model,
                             @RequestParam(value = USERNAME) String username,
                             @RequestParam(value = "token", defaultValue = "") String token ) {
            try {
                model.addAttribute(MENSAJE_ALERTA, "El usuario: " + username + ", se ha eliminado correctamente");
                model.addAttribute(TIPO_ALERTA, "success");
                userManagerService.deleteUser(username, token);
                return getUsers(model, false, 1, 20, USERNAME, Sort.Direction.ASC.name());
            } catch (Exception e) {
                model.addAttribute(ALERTA_ROL, false);
                model.addAttribute(MENSAJE_ALERTA, e.getMessage());
                model.addAttribute(TIPO_ALERTA, ERROR);
                return getUsers(model, false, 1, 20, USERNAME, Sort.Direction.ASC.name());
            }
    }
}
