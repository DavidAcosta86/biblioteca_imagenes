package com.egg.biblioteca.entidades;

import java.util.UUID;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;

@Entity
public class Imagen {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Lob
    @Basic(fetch = FetchType.LAZY) // Carga diferida para optimizar el rendimiento
    @Column(columnDefinition = "LONGBLOB") // Tipo LONGBLOB en la base de datos
    private byte[] contenido;

    public Imagen() {
    }

    public byte[] getContenido() {
        return contenido;
    }

    public void setContenido(byte[] contenido) {
        this.contenido = contenido;
    }

}
