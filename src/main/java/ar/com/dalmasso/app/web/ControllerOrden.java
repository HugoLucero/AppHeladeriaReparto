/*
 * Software creado y diseñado por Hugo Lucero.
* Derechos reservados para Hugo Lucero.
* Para más información contactarse a:
* hlucerodiaz@gmail.com
 */
package ar.com.dalmasso.app.web;

import ar.com.dalmasso.app.domain.Orden;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import ar.com.dalmasso.app.service.IOrdenService;

/**
 *
 * @author Hugo Lucero - Desarrollador Full - Stack
 */
@Controller
public class ControllerOrden {
    
    @Autowired
    private IOrdenService ordenService;
    
    //Métodos correspondientes para cada acción.
    @GetMapping("/ordenesInicio")
    public String Inicio(Model model){
        
        List<Orden> ordenes = ordenService.listarOrdenes();
        Collections.sort(ordenes, new Comparator<Orden>() {

            @Override
            public int compare(Orden o1, Orden o2) {
                return o1.getFechaYHora().compareToIgnoreCase(o2.getFechaYHora());
            }

        }.reversed());
        model.addAttribute("ordenes", ordenes);
        return "ordenesInicio";
    }
    
    @PostMapping("guardarOrden")
    public String guardarOrden(@ModelAttribute Orden orden, Errors errores){
        if(errores.hasErrors()){
            return "modificarOrden";
        }
        ordenService.guardar(orden);
        return "redirect:/ordenesInicio";
    }
    
    @GetMapping("/verOrden/{idOrden}")
    public String verOrden(@ModelAttribute Orden orden, Model model){
        orden = ordenService.encontrarOrden(orden);
        model.addAttribute("orden", orden);
        model.addAttribute("ordenVer", orden);
        return "visualizarOrden";
    }
    
    @GetMapping("/eliminarOrden")
    public String eliminarOrden(Orden orden){
        ordenService.eliminar(orden);
        return "redirect:/ordenesInicio";
    }
       
}
