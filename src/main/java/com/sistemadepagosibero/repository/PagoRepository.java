package com.sistemadepagosibero.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sistemadepagosibero.entities.Pago;
import com.sistemadepagosibero.enums.PagoStatus;
import com.sistemadepagosibero.enums.TypePago;

@Repository
public interface PagoRepository extends JpaRepository<Pago, Long> {

    // Busca todos los pagos realizados por un estudiante específico usando su
    // código
    List<Pago> findByEstudianteCodigo(String codigo);

    // Busca todos los pagos con un estado específico (CREADO, VALIDADO, RECHAZADO)
    List<Pago> findByStatus(PagoStatus status);

    // Busca todos los pagos según su tipo (EFECTIVO, CHEQUE, TRANSFERENCIA,
    // DEPÓSITO)
    List<Pago> findByType(TypePago type);
}
