package co.edu.unbosque.horaclass.academy.repository;

import co.edu.unbosque.horaclass.academy.model.Grupo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GrupoRepository extends JpaRepository<Grupo, Integer> {
}