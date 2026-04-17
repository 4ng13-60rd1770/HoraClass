package co.edu.unbosque.horaclass.academy.service.impl;

import co.edu.unbosque.horaclass.academy.dto.TipoMatriculaRequestDto;
import co.edu.unbosque.horaclass.academy.dto.TipoMatriculaResponseDto;
import co.edu.unbosque.horaclass.academy.model.TipoMatricula;
import co.edu.unbosque.horaclass.academy.repository.TipoMatriculaRepository;
import co.edu.unbosque.horaclass.academy.service.TipoMatriculaService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class TipoMatriculaServiceImpl implements TipoMatriculaService {

    private final TipoMatriculaRepository repository;
    private final ModelMapper modelMapper;

    public TipoMatriculaServiceImpl(TipoMatriculaRepository repository, ModelMapper modelMapper) {
        this.repository = repository;
        this.modelMapper = modelMapper;
    }

    @Override
    public TipoMatriculaResponseDto crear(TipoMatriculaRequestDto request) {
        if (repository.existsById(request.getIdTipoMatricula())) {
            throw new RuntimeException("Ya existe TipoMatricula");
        }
        TipoMatricula entity = modelMapper.map(request, TipoMatricula.class);
        repository.save(entity);
        return modelMapper.map(entity, TipoMatriculaResponseDto.class);
    }

    @Override
    public TipoMatriculaResponseDto actualizar(int id, TipoMatriculaRequestDto request) {
        TipoMatricula entity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("TipoMatricula no encontrado"));

        entity.setNombre(request.getNombre());
        repository.save(entity);

        return modelMapper.map(entity, TipoMatriculaResponseDto.class);
    }

    @Override
    public List<TipoMatriculaResponseDto> listarTodos() {
        return repository.findAll().stream()
                .map(e -> modelMapper.map(e, TipoMatriculaResponseDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public TipoMatriculaResponseDto obtenerPorId(int id) {
        TipoMatricula entity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("TipoMatricula no encontrado"));
        return modelMapper.map(entity, TipoMatriculaResponseDto.class);
    }

    @Override
    public void eliminar(int id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("TipoMatricula no encontrado");
        }
        repository.deleteById(id);
    }
}