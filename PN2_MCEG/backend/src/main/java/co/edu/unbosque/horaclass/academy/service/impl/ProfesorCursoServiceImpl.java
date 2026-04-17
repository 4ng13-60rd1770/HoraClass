package co.edu.unbosque.horaclass.academy.service.impl;

import co.edu.unbosque.horaclass.academy.dto.ProfesorCursoRequestDto;
import co.edu.unbosque.horaclass.academy.dto.ProfesorCursoResponseDto;
import co.edu.unbosque.horaclass.academy.model.ProfesorCurso;
import co.edu.unbosque.horaclass.academy.model.ProfesorCursoId;
import co.edu.unbosque.horaclass.academy.repository.ProfesorCursoRepository;
import co.edu.unbosque.horaclass.academy.service.ProfesorCursoService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ProfesorCursoServiceImpl implements ProfesorCursoService {

    private final ProfesorCursoRepository repository;

    public ProfesorCursoServiceImpl(ProfesorCursoRepository repository) {
        this.repository = repository;
    }

    private ProfesorCursoId buildId(int idEmpleado, int idCurso) {
        return new ProfesorCursoId(idEmpleado, idCurso);
    }

    @Override
    public ProfesorCursoResponseDto crear(ProfesorCursoRequestDto request) {

        ProfesorCursoId id = buildId(request.getIdEmpleado(), request.getIdCurso());

        if (repository.existsById(id)) {
            throw new RuntimeException("Ya existe ProfesorCurso");
        }

        ProfesorCurso entity = new ProfesorCurso();
        entity.setId(id);

        repository.save(entity);

        return new ProfesorCursoResponseDto(id.getIdEmpleado(), id.getIdCurso());
    }

    @Override
    public ProfesorCursoResponseDto actualizar(int idEmpleado, int idCurso, ProfesorCursoRequestDto request) {

        ProfesorCursoId id = buildId(idEmpleado, idCurso);

        if (!repository.existsById(id)) {
            throw new RuntimeException("ProfesorCurso no encontrado");
        }

        // ⚠️ NO se actualiza PK → se elimina y se crea uno nuevo
        repository.deleteById(id);

        ProfesorCursoId newId = buildId(request.getIdEmpleado(), request.getIdCurso());

        ProfesorCurso newEntity = new ProfesorCurso();
        newEntity.setId(newId);

        repository.save(newEntity);

        return new ProfesorCursoResponseDto(newId.getIdEmpleado(), newId.getIdCurso());
    }

    @Override
    public List<ProfesorCursoResponseDto> listarTodos() {
        return repository.findAll().stream()
                .map(entity -> new ProfesorCursoResponseDto(
                        entity.getId().getIdEmpleado(),
                        entity.getId().getIdCurso()
                ))
                .collect(Collectors.toList());
    }

    @Override
    public ProfesorCursoResponseDto obtenerPorId(int idEmpleado, int idCurso) {

        ProfesorCursoId id = buildId(idEmpleado, idCurso);

        ProfesorCurso entity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("ProfesorCurso no encontrado"));

        return new ProfesorCursoResponseDto(
                entity.getId().getIdEmpleado(),
                entity.getId().getIdCurso()
        );
    }

    @Override
    public void eliminar(int idEmpleado, int idCurso) {

        ProfesorCursoId id = buildId(idEmpleado, idCurso);

        if (!repository.existsById(id)) {
            throw new RuntimeException("ProfesorCurso no encontrado");
        }

        repository.deleteById(id);
    }
}