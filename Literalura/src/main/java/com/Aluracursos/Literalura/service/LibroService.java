package com.Aluracursos.Literalura.service;

import com.Aluracursos.Literalura.model.Autor;
import com.Aluracursos.Literalura.model.Libro;
import com.Aluracursos.Literalura.model.LibroData;
import com.Aluracursos.Literalura.model.Result;
import com.Aluracursos.Literalura.repository.AutorRepository;
import com.Aluracursos.Literalura.repository.LibroRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional; // Importante para la consistencia

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LibroService {
    private final ConsultaApi consultaApi;
    private final LibroRepository libroRepository;
    private final AutorRepository autorRepository;
    private final ObjectMapper objectMapper = new ObjectMapper(); // Puedes instanciarlo aquí


    public LibroService(ConsultaApi consultaApi, LibroRepository libroRepository, AutorRepository autorRepository) {
        this.consultaApi = consultaApi;
        this.libroRepository = libroRepository;
        this.autorRepository = autorRepository;
    }

    @Transactional
    public void buscarYGuardarLibro(String titulo) {
      
        Optional<Libro> libroExistente = libroRepository.findByTituloContainsIgnoreCase(titulo);
        if (libroExistente.isPresent()) {
            System.out.println("El libro ya existe en la base de datos.");
            return;
        }

    
        String json = consultaApi.buscarLibroPorTitulo(titulo);
        try {
            LibroData libroData = objectMapper.readValue(json, LibroData.class);

            if (libroData != null && libroData.results() != null && !libroData.results().isEmpty()) {
                Result primerResultado = libroData.results().get(0);

                guardarLibroConAutores(primerResultado);

            } else {
                System.out.println("No se encontró ningún libro con ese título en la API.");
            }
        } catch (JsonProcessingException e) {
            System.err.println("Error al procesar la respuesta JSON: " + e.getMessage());
        }
    }

    private void guardarLibroConAutores(Result result) {
    
        List<Autor> autores = result.authors().stream()
                .map(autorData -> {
               
                    Optional<Autor> autorExistente = autorRepository.findByNombre(autorData.name());

            
                    return autorExistente.orElseGet(() -> {
                        Autor autorNuevo = new Autor(autorData.name(), autorData.birth_year(), autorData.death_year());
                        return autorRepository.save(autorNuevo);
                    });
                })
                .collect(Collectors.toList());

        String idioma = result.languages().isEmpty() ? "Desconocido" : result.languages().get(0);

        Libro libro = new Libro(
                result.title(),
                idioma,
                result.download_count()
        );

       
        libro.setAutores(autores);

      
        libroRepository.save(libro);
        System.out.println("Libro guardado exitosamente: " + libro.getTitulo());
    }

    @Transactional(readOnly = true)
    public void listarLibrosRegistrados() {
        List<Libro> libros = libroRepository.findAll();
        if (libros.isEmpty()){
            System.out.println("No hay libros registrados.");
            return;
        }
        libros.forEach(System.out::println); 
    }

    @Transactional(readOnly = true)
    public void listarAutoresRegistrados() {
        List<Autor> autores = autorRepository.findAll();
        if (autores.isEmpty()){
            System.out.println("No hay autores registrados.");
            return;
        }
        autores.forEach(System.out::println); 
    @Transactional(readOnly = true)
    public void listarLibrosPorIdioma(String idioma) {
        List<Libro> libros = libroRepository.findByIdioma(idioma);
        if (libros.isEmpty()) {
            System.out.println("No se encontraron libros en el idioma '" + idioma + "'.");
        } else {
            System.out.println("Libros encontrados en '" + idioma + "':");
            libros.forEach(System.out::println); 
        }
    }

    @Transactional(readOnly = true)
    public void listarAutoresVivosEnAno(int ano) {
        List<Autor> autores = autorRepository.findAutoresVivosEnAno(ano);
        if (autores.isEmpty()) {
            System.out.println("No se encontraron autores vivos en el año " + ano + ".");
        } else {
            System.out.println("Autores vivos en el año " + ano + ":");
            autores.forEach(System.out::println);
        }
    }
}
