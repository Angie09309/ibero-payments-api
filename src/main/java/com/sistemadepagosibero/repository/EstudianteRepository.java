package com.sistemadepagosibero.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sistemadepagosibero.entities.Estudiante;

@Repository
public interface EstudianteRepository extends JpaRepository<Estudiante, String> {
    // metodo personalizado que busque un estudiante por su codigo unico
    Estudiante findByCodigo(String codigo);

    // metodo personalizado que me muestre una lista de estudiantes que pertenecen a
    // un programa especifico
    List<Estudiante> findByProgramaId(String programaId);
}
