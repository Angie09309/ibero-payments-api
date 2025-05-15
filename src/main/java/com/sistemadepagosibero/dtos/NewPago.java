package com.sistemadepagosibero.dtos;

import java.time.LocalDate;

import com.sistemadepagosibero.enums.TypePago;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NewPago {
    private double cantidad;
    private TypePago typePago;
    private LocalDate date;
    private String codigoEstudiante;
}
