package co.edu.unbosque.horaclass.academy.service.impl;

import co.edu.unbosque.horaclass.academy.dto.GrupoRequestDto;
import co.edu.unbosque.horaclass.academy.dto.GrupoResponseDto;
import co.edu.unbosque.horaclass.academy.model.Grupo;
import co.edu.unbosque.horaclass.academy.repository.GrupoRepository;
import co.edu.unbosque.horaclass.academy.service.GrupoService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class GrupoServiceImpl implements GrupoService {

    private final GrupoRepository repository;
    private final ModelMapper modelMapper;

    public GrupoServiceImpl(GrupoRepository repository, ModelMapper modelMapper) {
        this.repository = repository;
        this.modelMapper = modelMapper;
    }

    @Override
    public GrupoResponseDto crear(GrupoRequestDto request) {
        if (repository.existsById(request.getIdGrupo())) {
            throw new RuntimeException("Ya existe un Grupo con ese ID");
        }
        Grupo entity = modelMapper.map(request, Grupo.class);
        repository.save(entity);
        return modelMapper.map(entity, GrupoResponseDto.class);
    }

    @Override
    public GrupoResponseDto actualizar(int id, GrupoRequestDto request) {
        Grupo entity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Grupo no encontrado"));

        modelMapper.map(request, entity);
        repository.save(entity);

        return modelMapper.map(entity, GrupoResponseDto.class);
    }

    @Override
    public List<GrupoResponseDto> listarTodos() {
        return repository.findAll().stream()
                .map(entity -> modelMapper.map(entity, GrupoResponseDto.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public GrupoResponseDto obtenerPorId(int id) {
        Grupo entity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Grupo no encontrado"));
        return modelMapper.map(entity, GrupoResponseDto.class);
    }

    @Override
    public void eliminar(int id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Grupo no encontrado");
        }
        repository.deleteById(id);
    }
}