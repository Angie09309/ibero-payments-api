// Paquete donde se encuentra la clase
package com.sistemadepagosibero.entities;

// Importaciones necesarias para anotaciones y mapeo de datos
import org.springframework.data.annotation.Id; // Anotación para marcar el identificador del documento (MongoDB, por ejemplo)
import jakarta.persistence.Column; // Para definir detalles de columnas en JPA
import jakarta.persistence.Entity; // Marca la clase como una entidad JPA

// Lombok es una biblioteca que reduce código repetitivo (como getters, setters, constructores)
import lombok.AllArgsConstructor; // Genera constructor con todos los campos
import lombok.Builder; // Permite usar el patrón builder para crear objetos
import lombok.Data; // Genera automáticamente getters, setters, toString, equals y hashCode
import lombok.NoArgsConstructor; // Genera constructor sin argumentos

// Marca esta clase como una entidad JPA, que será mapeada a una tabla en la base de datos
@Entity
@Builder // Permite crear instancias usando el patrón builder
@Data // Genera automáticamente métodos comunes como getters/setters
@NoArgsConstructor // Constructor sin argumentos (requerido por JPA)
@AllArgsConstructor // Constructor con todos los campos

public class Estudiante {
    @Id // indicar que este campo es la primary key en la base de datos
    private String id;
    private String nombre;
    private String apellido;

    // Campo único que representa el código del estudiante
    @Column(unique = true)
    private String codigo;

    private String programaId;

}
