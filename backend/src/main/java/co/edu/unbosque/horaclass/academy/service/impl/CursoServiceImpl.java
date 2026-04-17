package co.edu.unbosque.horaclass.academy.service.impl;

import co.edu.unbosque.horaclass.academy.dto.CursoRequestDto;
import co.edu.unbosque.horaclass.academy.dto.CursoResponseDto;
import co.edu.unbosque.horaclass.academy.model.Curso;
import co.edu.unbosque.horaclass.academy.repository.CursoRepository;
import co.edu.unbosque.horaclass.academy.service.CursoService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class CursoServiceImpl implements CursoService {

    private final CursoRepository repository;
    private final ModelMapper modelMapper;

    public CursoServiceImpl(CursoRepository repository, ModelMapper modelMapper) {
        this.repository = repository;
        this.modelMapper = modelMapper;
    }

    @Override
    public CursoResponseDto crear(CursoRequestDto request) {
        if (repository.existsById(request.getIdCurso())) {
            throw new RuntimeException("Ya existe un Curso con ese ID");
        }
        Curso entity = modelMapper.map(request, Curso.class);
        repository.save(entity);
        return modelMapper.map(entity, CursoResponseDto.class);
    }

    @Override
    public CursoResponseDto actualizar(int id, CursoRequestDto request) {
        Curso entity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Curso no encontrado"));

        modelMapper.map(request, entity);
        repository.save(entity);

        return modelMapper.map(entity, CursoResponseDto.class);
    }

    @Override
    public List<CursoResponseDto> listarTodos() {
        return repository.findAll().stream()
                .map(entity -> modelMapper.map(entity, CursoResponseDto.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public CursoResponseDto obtenerPorId(int id) {
        Curso entity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Curso no encontrado"));
        return modelMapper.map(entity, CursoResponseDto.class);
    }

    @Override
    public void eliminar(int id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Curso no encontrado");
        }
        repository.deleteById(id);
    }
}