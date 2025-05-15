package com.sistemadepagosibero.services;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.sistemadepagosibero.entities.Estudiante;
import com.sistemadepagosibero.entities.Pago;
import com.sistemadepagosibero.enums.PagoStatus;
import com.sistemadepagosibero.enums.TypePago;
import com.sistemadepagosibero.repository.EstudianteRepository;
import com.sistemadepagosibero.repository.PagoRepository;

import jakarta.transaction.Transactional;

@Service // Servicio encargado de manejar la lógica relacionada con los pagos.
@Transactional // para asegurar que los metodos de la clase se ejecuten dentro de una
               // transaccion
public class PagoService {

    // Inyeccion de dependencias de PagoReposotory para interactuar con la base de
    // pago
    @Autowired
    private PagoRepository pagoRepository;

    // Inyeccion de dependencias de EstudianteeRepository para obtener informacion
    // de los estudiantes desde la base de datos
    @Autowired
    private EstudianteRepository estudianteRepository;

    /**
     * metodo para guardar el pago en la base de datos almacenar un archivo pdf en
     * el servidor
     * 
     * @param file             archivo pdf que se subira al servidor
     * @param cantidad         monto del pago realizado
     * @param type             tipo de pago EFECTIVO, CHEQUE, TRANSFERENCIA,
     *                         DEPOSITO
     * @param date             fecha en la que se realiza el pago
     * @param codigoEStudiante codifgo del estudiante que realiza el pago
     * @return objeto del pago guardado en la base de datos
     * @throws IOException excepcion lanzada si ocurre un error al manejar archivo
     */

    public Pago savePago(MultipartFile file, double cantidad, TypePago type, LocalDate date, String codigoEstudiante)
            throws IOException {
        /**
         * construir la ruta donde se guardara el archivo dentro del sistema
         * System getProperty("user.home"); obtiene la ruta del directorio personal del
         * usuario del actual sistema operativo
         * Paths.get : construir una ruta dentro del directorio personal en la carpeta
         * "enset-data/pagos"
         */

        Path folderPath = Paths.get(System.getProperty("user.home"), "enset-data", "pagos");

        // verificar si la carpeta ya existe, si no la debe crear
        if (!Files.exists(folderPath)) {
            Files.createDirectories(folderPath);
        }

        // generamos un nombre unico para el archivo usando UUID(idenficador unico
        // universal)
        String fileName = UUID.randomUUID().toString();

        // Construimos la ruta completa del archivo agregando la extension .pdf
        Path filePath = Paths.get(System.getProperty("user.home"), "enset-data", "pagos", fileName + ".pdf");

        // Guardamos el archivo recibido en la ubicacion especificada dentro del sistema
        // de archivos
        Files.copy(file.getInputStream(), filePath);

        // Buscamos el estudiante que realiza el pago con su codigo
        Estudiante estudiante = estudianteRepository.findByCodigo(codigoEstudiante);

        // creamos un nuevo objeto Pago utilizando el patron de diseño builder
        Pago pago = Pago.builder()
                .type(type)
                .status(PagoStatus.CREADO) // Estado inicial del pago
                .fecha(date)
                .estudiante(estudiante)
                .cantidad(cantidad)
                .file(filePath.toUri().toString()) // ruta del archivo almacenado
                .build(); // construccion final del objeto Pago

        return pagoRepository.save(pago);

    }

    public byte[] getArchivoId(Long pagoId) throws IOException {

        // busca un objeto pago en la base datos por su ID
        Pago pago = pagoRepository.findById(pagoId).get();
        /**
         * pago gefFile:obtiene la URL del archivo guardado como una cadena de texto
         * URI.create: convierte la cadena de texto en un objeto URL
         * pathOF: convierte la URI en un path para poder acceder al archivo
         * Files.readAllBytes: lee el contenido del archivo y lo va a devolver en un
         * array vetor de bytes
         * esto va permitir obtener el contenidodel archivo para su posterior uso por
         * ejemplo descargar
         */
        return Files.readAllBytes(Path.of(URI.create(pago.getFile())));

    }

    public Pago actualizarPagoPorStatus(PagoStatus status, Long id) {
        // busca un objeto pago en la base datos por su ID
        Pago pago = pagoRepository.findById(id).get();

        // Actualiza el estado del pago(valido o rechazado)
        pago.setStatus(status);

        // Guarda el objeto pago actulizado en la base de datos y lo devuelve
        return pagoRepository.save(pago);
    }
}
