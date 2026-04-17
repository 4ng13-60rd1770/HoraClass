package co.edu.unbosque.horaclass.academy.service.impl;

import co.edu.unbosque.horaclass.academy.dto.EstadoGrupoRequestDto;
import co.edu.unbosque.horaclass.academy.dto.EstadoGrupoResponseDto;
import co.edu.unbosque.horaclass.academy.model.EstadoGrupo;
import co.edu.unbosque.horaclass.academy.repository.EstadoGrupoRepository;
import co.edu.unbosque.horaclass.academy.service.EstadoGrupoService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class EstadoGrupoServiceImpl implements EstadoGrupoService {

    private final EstadoGrupoRepository repository;
    private final ModelMapper modelMapper;

    public EstadoGrupoServiceImpl(EstadoGrupoRepository repository, ModelMapper modelMapper) {
        this.repository = repository;
        this.modelMapper = modelMapper;
    }

    @Override
    public EstadoGrupoResponseDto crear(EstadoGrupoRequestDto request) {
        if (repository.existsById(request.getIdEstadoGrupo())) {
            throw new RuntimeException("Ya existe un EstadoGrupo con ese ID");
        }
        EstadoGrupo entity = modelMapper.map(request, EstadoGrupo.class);
        repository.save(entity);
        return modelMapper.map(entity, EstadoGrupoResponseDto.class);
    }

    @Override
    public EstadoGrupoResponseDto actualizar(int id, EstadoGrupoRequestDto request) {
        EstadoGrupo entity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("EstadoGrupo no encontrado"));

        entity.setNombre(request.getNombre());
        repository.save(entity);

        return modelMapper.map(entity, EstadoGrupoResponseDto.class);
    }

    @Override
    public List<EstadoGrupoResponseDto> listarTodos() {
        return repository.findAll().stream()
                .map(entity -> modelMapper.map(entity, EstadoGrupoResponseDto.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public EstadoGrupoResponseDto obtenerPorId(int id) {
        EstadoGrupo entity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("EstadoGrupo no encontrado"));
        return modelMapper.map(entity, EstadoGrupoResponseDto.class);
    }

    @Override
    public void eliminar(int id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("EstadoGrupo no encontrado");
        }
        repository.deleteById(id);
    }
}