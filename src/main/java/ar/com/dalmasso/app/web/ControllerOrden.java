/*
 * Software creado y diseñado por Hugo Lucero.
* Derechos reservados para Hugo Lucero.
* Para más información contactarse a:
* hlucerodiaz@gmail.com
 */
package ar.com.dalmasso.app.web;

import ar.com.dalmasso.app.domain.Orden;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
       return findPaginated(1, "idOrden", "asc", model);
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

    @GetMapping("/page/{pageNo}")
    public String findPaginated(@PathVariable (value = "pageNo")int pageNo,
        @RequestParam("sortField") String sortField,
        @RequestParam("sortDir") String sortDir,
        Model model){
        int pageSize = 20;
        Page<Orden> page = ordenService.findPaginated(pageNo, pageSize, sortField, sortDir); 
        List<Orden> ordenes = page.getContent();
        
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");

        model.addAttribute("ordenes", ordenes);
        
        return "ordenesInicio";
        
    }
       
}
 