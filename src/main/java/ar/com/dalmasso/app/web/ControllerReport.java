/*
 * Software creado y diseñado por Hugo Lucero.
* Derechos reservados para Hugo Lucero.
* Para más información contactarse a:
* hlucerodiaz@gmail.com
 */
package ar.com.dalmasso.app.web;

import ar.com.dalmasso.app.domain.ProductoVendido;
import ar.com.dalmasso.app.domain.Reporte;
import ar.com.dalmasso.app.service.IReporteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 *
 * @author Hugo Lucero - Desarrollador Full - Stack
 */
@Controller
public class ControllerReport {

    @Autowired
    private IReporteService reporteService;

    //Metodos necesarios para las acciones del Reporte.
    @GetMapping(value = "/reportesInicio")
    public String inicio(Model model) {
        var reportes = reporteService.listarReportes();
        model.addAttribute("reportes", reportes);
        return "reportesInicio";
    }

    @GetMapping(value = "/eliminarReporte")
    public String delete(Reporte reporte) {
        reporteService.eliminar(reporte);
        return "redirect:/reportesInicio";
    }

    @GetMapping(value = "/verReporte/{idReporte}")
    public String verReporte(@ModelAttribute Reporte reporte, Model model) {
        reporte = reporteService.encontrarReporte(reporte);
        model.addAttribute("reporte", reporte);

        List<ProductoVendido> prod = reporte.getProductos();
        Map<Object, Double> totalProductos = prod.stream()
        .collect(Collectors.groupingBy(p -> p.getNombre(), Collectors.summingDouble(p -> p.getCantidad())));
        model.addAttribute("pp", totalProductos);
        
        return "visualizarReporte";
    }

}
