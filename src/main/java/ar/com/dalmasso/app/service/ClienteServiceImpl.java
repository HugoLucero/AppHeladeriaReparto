/*
 * Software creado y diseñado por Hugo Lucero.
* Derechos reservados para Hugo Lucero.
* Para más información contactarse a:
* hlucerodiaz@gmail.com
 */
package ar.com.dalmasso.app.service;

import ar.com.dalmasso.app.dao.ClienteDao;
import ar.com.dalmasso.app.dao.OrdenDao;
import ar.com.dalmasso.app.domain.Cliente;
import ar.com.dalmasso.app.domain.Orden;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 *
 * @author Hugo Lucero - Desarrollador Full - Stack
 */
@Service
public class ClienteServiceImpl implements IClienteService {

    @Autowired
    private ClienteDao clienteDao;
    
    @Autowired
    private OrdenDao ordenDao;
    
    @Override
    @Transactional(readOnly=true)
    public List<Cliente> listarClientes() {
        return (List<Cliente>)clienteDao.findAll();
    }

    @Override
    @Transactional
    public void guardar(Cliente cliente) {
        clienteDao.save(cliente);
    }

    @Override
    public void eliminar(Cliente cliente) {
        clienteDao.delete(cliente);
    }

    @Override
    @Transactional(readOnly=true)
    public Cliente encontrarCliente(Cliente cliente) {
        return clienteDao.findById(cliente.getIdCliente()).orElse(null);
    }

    @Override
    @Transactional(readOnly=true)
    public Cliente encontrarClienteZona(Cliente cliente) {
        return clienteDao.findByZona(cliente.getZona());
    }

    @Override
    @Transactional(readOnly=true)
    public Cliente encontrarClienteFreezer(Cliente cliente) {
        return clienteDao.findByFreezer(cliente.isFreezer());
    }

    @Override
    public List<Orden> listarOrdenes() {
        return (List<Orden>)ordenDao.findAll();
    }

    @Override
    public Cliente encontrarClienteNombre(Cliente cliente) {
        return clienteDao.findByNombreCliente(cliente.getNombreCliente());
    }
    
}
