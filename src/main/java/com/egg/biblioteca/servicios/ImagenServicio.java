package com.egg.biblioteca.servicios;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.egg.biblioteca.entidades.Imagen;
import com.egg.biblioteca.excepciones.MiException;
import com.egg.biblioteca.repositorios.ImagenRepositorio;

@Service
public class ImagenServicio {

    // Inyección del repositorio
    @Autowired
    private ImagenRepositorio imagenRepositorio;

    // Método para guardar una nueva imagen
    public Imagen guardar(MultipartFile archivo) throws MiException {
        if (archivo != null && !archivo.isEmpty()) {
            // Crear un objeto Imagen
            Imagen imagen = new Imagen();
            try {
                imagen.setContenido(archivo.getBytes());
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } // Convertir archivo a byte[]

            // Guardar en la base de datos
            return imagenRepositorio.save(imagen);
        } else {
            // Manejar el caso en que el archivo es nulo o vacío
            throw new IllegalArgumentException("El archivo no puede ser nulo o vacío.");
        }
    }

    // Método para actualizar una imagen existente
    public Imagen actualizar(UUID id, MultipartFile archivo) throws MiException {
        Optional<Imagen> imagenOpt = imagenRepositorio.findById(id);

        if (imagenOpt.isPresent()) {
            Imagen imagen = imagenOpt.get();

            // Verificar si el archivo no es nulo y no está vacío
            if (archivo != null && !archivo.isEmpty()) {
                try {
                    imagen.setContenido(archivo.getBytes()); // Actualizar el contenido con el nuevo archivo
                } catch (IOException e) {
                    throw new MiException("Error al procesar el archivo");
                }
            }

            // Guardar la imagen actualizada en la base de datos
            return imagenRepositorio.save(imagen);
        } else {
            // Manejar el caso en que no se encuentra la imagen
            throw new IllegalArgumentException("No se encontró una imagen con el id proporcionado.");
        }
    }
}
