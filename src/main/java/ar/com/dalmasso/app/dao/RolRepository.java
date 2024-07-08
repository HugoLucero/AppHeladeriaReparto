package ar.com.dalmasso.app.dao;

import ar.com.dalmasso.app.domain.Rol;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RolRepository extends JpaRepository<Rol, Long> {
    Rol findByNombreIgnoreCase(String nombre);
}