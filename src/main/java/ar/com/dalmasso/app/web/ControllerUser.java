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
import java.text.ParseException;
import java.util.Objects;

@Controller
@RequestMapping(path = "/usuario")
public class ControllerUser {

    private final UserManagerService userManagerService;

    @Autowired
    public ControllerUser(UserManagerService userManagerService) {
        this.userManagerService = userManagerService;
    }

    // MVC

    @GetMapping(value = "/")
    public String createOrEditUser(Model model, @RequestParam String username) {
        UsuarioDto usuarioDto;
        if(Objects.nonNull(username) && !username.isEmpty())
            usuarioDto = userManagerService.getUserDtoByUsername(username);
        else
            usuarioDto = new UsuarioDto();

        model.addAttribute("dto", usuarioDto);
        return "usuario/usuario";
    }



    @PostMapping(value = "/create")
    public String createOrEditUser(Model model, @ModelAttribute @Valid UsuarioDto dto) throws ParseException {
        UsuarioDto usuarioDto = new UsuarioDto();
        if(Objects.nonNull(dto))
            usuarioDto = userManagerService.createUser(dto);

        model.addAttribute("dto", usuarioDto);
        model.addAttribute("restablece", false);
        return "usuario/token";
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
