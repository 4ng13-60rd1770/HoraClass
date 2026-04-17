package co.edu.unbosque.horaclass.academy.repository;

import co.edu.unbosque.horaclass.academy.model.Directivo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DirectivoRepository extends JpaRepository<Directivo, Integer> {
}