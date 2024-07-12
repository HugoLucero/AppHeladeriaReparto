package ar.com.dalmasso.app.web;

import ar.com.dalmasso.app.dto.UsuarioDto;
import ar.com.dalmasso.app.service.UserManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Objects;

@Controller
@RequestMapping(path = "/usuario")
public class ControllerUser {

    private static final String RESTABLECE = "restablece";
    private static final String MENSAJE_ALERTA = "mensajeAlerta";
    private static final String TIPO_ALERTA = "tipoAlerta";
    private static final String RETURN_USUARIO = "usuario/usuario";

    private final UserManagerService userManagerService;

    @Autowired
    public ControllerUser(UserManagerService userManagerService) {
        this.userManagerService = userManagerService;
    }

    // MVC

    @GetMapping(value = "/")
    public String createOrEditUserView(Model model, @RequestParam String username, @RequestParam boolean restablece,
                                   @RequestParam(required = false) Boolean edit) {
        UsuarioDto usuarioDto;
        if(Objects.nonNull(username) && !username.isEmpty())
            usuarioDto = userManagerService.getUserDtoByUsername(username);
        else
            usuarioDto = new UsuarioDto();

        model.addAttribute("dto", usuarioDto);
        model.addAttribute("edit", edit);
        model.addAttribute(RESTABLECE, restablece);
        return RETURN_USUARIO;
    }



    @PostMapping(value = "/create")
    public String createOrEditUser(Model model, @ModelAttribute @Valid UsuarioDto dto,
                                   @RequestParam(required = false) Boolean edit) {

       try {
           UsuarioDto usuarioDto = new UsuarioDto();
           if(Objects.nonNull(dto)) {
               if (Boolean.TRUE.equals(edit))
                   usuarioDto = userManagerService.editUser(dto);
               else
                   usuarioDto = userManagerService.createUser(dto);
           }

           model.addAttribute("dto", usuarioDto);
           model.addAttribute(RESTABLECE, false);
           model.addAttribute(MENSAJE_ALERTA, edit ? "Usuario editado correctamente" : "Usuario creado correctamente");
           model.addAttribute(TIPO_ALERTA, "success");
           return edit ? createOrEditUserView(model, usuarioDto.getUsername(), false, true) : "usuario/token";
       } catch (Exception e) {
           UsuarioDto usuarioDto = new UsuarioDto();
            usuarioDto.setToken("Error de token");
           model.addAttribute("dto", usuarioDto);
           model.addAttribute(RESTABLECE, false);
           model.addAttribute(MENSAJE_ALERTA, e.getMessage());
           model.addAttribute(TIPO_ALERTA, "error");

           return edit ? createOrEditUserView(model, dto.getUsername(), false, true) : RETURN_USUARIO;
       }
    }

    // REST

    @PostMapping(value = "/rest/createUser")
    public ResponseEntity<?> createUser(@RequestBody UsuarioDto dto) {
        try {
            UsuarioDto response = userManagerService.createUser(dto);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PutMapping("/rest/editUser")
    public ResponseEntity<?> updateUser(@RequestBody UsuarioDto dto) {
        try {
            UsuarioDto response = userManagerService.editUser(dto);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PutMapping("/rest/editRoles")
    public ResponseEntity<?> editRoles(@RequestBody UsuarioDto dto) {
        try {
            UsuarioDto response = userManagerService.editRoles(dto.getUsername(), dto.getToken(), dto.getRoles());
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PutMapping("/rest/setToken")
    public ResponseEntity<String> setToken(@RequestBody UsuarioDto dto) {
        try {
            userManagerService.setToken2User(dto.getUsername(), dto.getToken());
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

}
