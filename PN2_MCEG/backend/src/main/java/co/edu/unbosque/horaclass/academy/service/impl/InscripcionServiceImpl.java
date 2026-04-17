package co.edu.unbosque.horaclass.academy.service.impl;

import co.edu.unbosque.horaclass.academy.dto.InscripcionRequestDto;
import co.edu.unbosque.horaclass.academy.dto.InscripcionResponseDto;
import co.edu.unbosque.horaclass.academy.model.Inscripcion;
import co.edu.unbosque.horaclass.academy.model.InscripcionId;
import co.edu.unbosque.horaclass.academy.repository.InscripcionRepository;
import co.edu.unbosque.horaclass.academy.service.InscripcionService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class InscripcionServiceImpl implements InscripcionService {

    private final InscripcionRepository repository;
    private final ModelMapper modelMapper;

    public InscripcionServiceImpl(InscripcionRepository repository, ModelMapper modelMapper) {
        this.repository = repository;
        this.modelMapper = modelMapper;
    }

    private InscripcionId buildId(InscripcionRequestDto request) {
        return new InscripcionId(request.getIdEstudiante(), request.getIdGrupo());
    }

    @Override
    public InscripcionResponseDto crear(InscripcionRequestDto request) {
        InscripcionId id = buildId(request);

        if (repository.existsById(id)) {
            throw new RuntimeException("Ya existe una Inscripcion con ese ID");
        }

        Inscripcion entity = modelMapper.map(request, Inscripcion.class);
        repository.save(entity);

        return modelMapper.map(entity, InscripcionResponseDto.class);
    }

    @Override
    public InscripcionResponseDto actualizar(InscripcionRequestDto request) {
        InscripcionId id = buildId(request);

        Inscripcion entity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Inscripcion no encontrada"));

        modelMapper.map(request, entity);
        repository.save(entity);

        return modelMapper.map(entity, InscripcionResponseDto.class);
    }

    @Override
    public List<InscripcionResponseDto> listarTodos() {
        return repository.findAll().stream()
                .map(entity -> modelMapper.map(entity, InscripcionResponseDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public InscripcionResponseDto obtenerPorId(int idEstudiante, int idGrupo) {
        InscripcionId id = new InscripcionId(idEstudiante, idGrupo);

        Inscripcion entity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Inscripcion no encontrada"));

        return modelMapper.map(entity, InscripcionResponseDto.class);
    }

    @Override
    public void eliminar(int idEstudiante, int idGrupo) {
        InscripcionId id = new InscripcionId(idEstudiante, idGrupo);

        if (!repository.existsById(id)) {
            throw new RuntimeException("Inscripcion no encontrada");
        }
        repository.deleteById(id);
    }
}