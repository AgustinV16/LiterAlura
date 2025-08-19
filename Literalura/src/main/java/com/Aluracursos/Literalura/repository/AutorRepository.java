package com.Aluracursos.Literalura.repository;

import com.Aluracursos.Literalura.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface AutorRepository extends JpaRepository<Autor, Long> {
    Optional<Autor> findByNombre(String nombre);

    @Query("SELECT a FROM Autor a WHERE a.anoNacimiento <= :ano AND (a.anoFallecimiento IS NULL OR a.anoFallecimiento >= :ano)")
    List<Autor> findAutoresVivosEnAno(Integer ano);
}