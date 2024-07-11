package ar.com.dalmasso.app.web;

import ar.com.dalmasso.app.util.CodeErrors;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ControllerLogin {

    @GetMapping("/login")
    public String login(@RequestParam(value = "error", required = false) String error,
                        @RequestParam(value = "logout", required = false) String logout,
                        Model model) {
        if (error != null) {
            model.addAttribute("mensajeAlerta", CodeErrors.USER_INVALID);
            model.addAttribute("tipoAlerta", "warning");
        }
        if (logout != null) {
            model.addAttribute("mensajeAlerta", "Has sido desconectado exitosamente.");
            model.addAttribute("tipoAlerta", "success");
        }
        return "login";
    }

}
