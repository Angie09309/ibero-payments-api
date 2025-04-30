package com.sistemadepagosibero.entities;

import java.time.LocalDate;

import com.sistemadepagosibero.enums.PagoStatus;
import com.sistemadepagosibero.enums.TypePago;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

// Clase que representa un pago realizado por un estudiante
@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pago {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Genera ID auto-incremental
    private Long id;

    private LocalDate fechas;
    private double cantidad;
    private TypePago type;
    private PagoStatus status;
    private String file;

    @ManyToOne // Relación: muchos pagos pueden pertenecer a un mismo estudiante
    private Estudiante estudiante;
}
