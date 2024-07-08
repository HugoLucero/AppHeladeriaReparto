/*
 * Software creado y diseñado por Hugo Lucero.
* Derechos reservados para Hugo Lucero.
* Para más información contactarse a:
* hlucerodiaz@gmail.com
 */
package ar.com.dalmasso.app.web;

import ar.com.dalmasso.app.domain.Cliente;
import ar.com.dalmasso.app.service.IClienteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.validation.Valid;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author Hugo Lucero - Desarrollador Full - Stack
 */
@Slf4j
@Controller
public class ControllerClientes {

    @Autowired
    private IClienteService clienteService;

    @GetMapping("/clientesInicio")
    public String inicio(Model model) {

        //Creamos una variable para manipular los clientes
        List<Cliente> clientes = clienteService.listarClientes();
        Collections.sort(clientes, (c1, c2) -> c1.getNombreCliente().compareToIgnoreCase(c2.getNombreCliente()));
        log.info("Ejecutamos el controlador de Clientes");
        model.addAttribute("clientes", clientes);
        model.addAttribute("totalClientes", clientes.size());
        return "clientesInicio";
    }

    //Creamos el método para crear un cliente
    @GetMapping("/agregarCliente")
    public String agregar(Cliente cliente) {
        return "modificarCliente";
    }

    //Creamos el método para guardar el cliente
    @PostMapping("/guardarCliente")
    public String guardar(@ModelAttribute @Valid Cliente cliente, Errors errores) {
        if (errores.hasErrors()) {
            return "modificarCliente";
        }
        clienteService.guardar(cliente);
        return "redirect:/clientesInicio";
    }
    
    //Creamos el método para editar el cliente
    @GetMapping("/editarCliente/{idCliente}")
    public String editar(Cliente cliente, Model model) {
        cliente = clienteService.encontrarCliente(cliente);
        model.addAttribute("cliente", cliente);
        return "modificarCliente";
    }

    //Creamos el método para eliminar el cliente
    @GetMapping("/eliminarCliente")
    public String eliminar(Cliente cliente) {
        clienteService.eliminar(cliente);
        return "redirect:/clientesInicio";
    }
}
