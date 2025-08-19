package com.Aluracursos.Literalura.model;

import jakarta.persistence.*;
import java.util.List;
import java.util.stream.Collectors;


@Entity
@Table(name = "libros")
public class Libro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;
    private String idioma;
    private Integer descargas;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinTable(
            name = "libro_autor",
            joinColumns = @JoinColumn(name = "libro_id"),
            inverseJoinColumns = @JoinColumn(name = "autor_id")
    )
    private List<Autor> autores;

    public Libro() {}


    public Libro(String titulo, String idioma, Integer descargas) {
        this.titulo = titulo;
        this.idioma = idioma;
        this.descargas = descargas;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    public Integer getDescargas() {
        return descargas;
    }

    public void setDescargas(Integer descargas) {
        this.descargas = descargas;
    }

    public List<Autor> getAutores() {
        return autores;
    }

    public void setAutores(List<Autor> autores) {
        this.autores = autores;
    }
    @Override
    public String toString() {
        String nombresAutores = autores.stream()
                .map(Autor::getNombre)
                .collect(Collectors.joining(", "));
        return String.format(
                "--- LIBRO ---\n" +
                        "Título: %s\n" +
                        "Autor(es): %s\n" +
                        "Idioma: %s\n" +
                        "Descargas: %d\n" +
                        "-------------\n",
                titulo, nombresAutores, idioma, descargas
        );
    }
}