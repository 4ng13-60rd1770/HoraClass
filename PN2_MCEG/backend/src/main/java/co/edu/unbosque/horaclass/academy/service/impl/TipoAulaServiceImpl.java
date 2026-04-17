package co.edu.unbosque.horaclass.academy.service.impl;

import co.edu.unbosque.horaclass.academy.dto.TipoAulaRequestDto;
import co.edu.unbosque.horaclass.academy.dto.TipoAulaResponseDto;
import co.edu.unbosque.horaclass.academy.model.TipoAula;
import co.edu.unbosque.horaclass.academy.repository.TipoAulaRepository;
import co.edu.unbosque.horaclass.academy.service.TipoAulaService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class TipoAulaServiceImpl implements TipoAulaService {

    private final TipoAulaRepository repository;
    private final ModelMapper modelMapper;

    public TipoAulaServiceImpl(TipoAulaRepository repository, ModelMapper modelMapper) {
        this.repository = repository;
        this.modelMapper = modelMapper;
    }

    @Override
    public TipoAulaResponseDto crear(TipoAulaRequestDto request) {
        if (repository.existsById(request.getIdTipoAula())) {
            throw new RuntimeException("Ya existe TipoAula");
        }
        TipoAula entity = modelMapper.map(request, TipoAula.class);
        repository.save(entity);
        return modelMapper.map(entity, TipoAulaResponseDto.class);
    }

    @Override
    public TipoAulaResponseDto actualizar(int id, TipoAulaRequestDto request) {
        TipoAula entity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("TipoAula no encontrado"));

        entity.setNombre(request.getNombre());
        repository.save(entity);

        return modelMapper.map(entity, TipoAulaResponseDto.class);
    }

    @Override
    public List<TipoAulaResponseDto> listarTodos() {
        return repository.findAll().stream()
                .map(e -> modelMapper.map(e, TipoAulaResponseDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public TipoAulaResponseDto obtenerPorId(int id) {
        TipoAula entity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("TipoAula no encontrado"));
        return modelMapper.map(entity, TipoAulaResponseDto.class);
    }

    @Override
    public void eliminar(int id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("TipoAula no encontrado");
        }
        repository.deleteById(id);
    }
}