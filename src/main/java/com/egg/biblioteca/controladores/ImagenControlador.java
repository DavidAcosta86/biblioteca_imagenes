// package com.egg.biblioteca.controladores;

// import java.util.UUID;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.http.HttpHeaders;
// import org.springframework.http.HttpStatus;
// import org.springframework.http.MediaType;
// import org.springframework.http.ResponseEntity;
// import org.springframework.stereotype.Controller;
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.PathVariable;
// import org.springframework.web.bind.annotation.RequestMapping;

// import com.egg.biblioteca.entidades.Usuario;
// import com.egg.biblioteca.servicios.UsuarioServicio;

// @Controller
// @RequestMapping("/imagen")
// public class ImagenControlador {
//     @Autowired
//     UsuarioServicio usuarioServicio;

//     @GetMapping("/perfil/{id}")
//     public ResponseEntity<byte[]> imagenUsuario(@PathVariable UUID id) {
//         Usuario usuario = usuarioServicio.getOne(id);

//         byte[] imagen = usuario.getImagen().getContenido();
//         HttpHeaders headers = new HttpHeaders();

//         headers.setContentType(MediaType.IMAGE_JPEG);

//         return new ResponseEntity<>(imagen, headers, HttpStatus.OK);
//     }

// }

package com.egg.biblioteca.controladores;

import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.egg.biblioteca.entidades.Usuario;
import com.egg.biblioteca.servicios.UsuarioServicio;

import jakarta.transaction.Transactional;

@Controller
@RequestMapping("/imagen")
public class ImagenControlador {

    private final Logger logger = LoggerFactory.getLogger(ImagenControlador.class);

    @Autowired
    UsuarioServicio usuarioServicio;

    @GetMapping("/perfil/{id}")
    @Transactional
    public ResponseEntity<byte[]> imagenUsuario(@PathVariable UUID id) {
        try {
            logger.info("Buscando imagen para usuario con ID: {}", id);

            Usuario usuario = usuarioServicio.getOne(id);
            if (usuario == null) {
                logger.error("Usuario no encontrado con ID: {}", id);
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            if (usuario.getImagen() == null) {
                logger.error("Usuario {} no tiene imagen asociada", id);
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            byte[] imagen = usuario.getImagen().getContenido();
            if (imagen == null) {
                logger.error("Contenido de imagen nulo para usuario {}", id);
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_JPEG);

            return new ResponseEntity<>(imagen, headers, HttpStatus.OK);

        } catch (Exception e) {
            logger.error("Error al procesar imagen para usuario {}: {}", id, e.getMessage(), e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
