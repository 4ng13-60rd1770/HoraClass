package co.edu.unbosque.horaclass.academy.service.impl;

import co.edu.unbosque.horaclass.academy.dto.EscalafonRequestDto;
import co.edu.unbosque.horaclass.academy.dto.EscalafonResponseDto;
import co.edu.unbosque.horaclass.academy.model.Escalafon;
import co.edu.unbosque.horaclass.academy.repository.EscalafonRepository;
import co.edu.unbosque.horaclass.academy.service.EscalafonService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class EscalafonServiceImpl implements EscalafonService {

    private final EscalafonRepository repository;
    private final ModelMapper modelMapper;

    public EscalafonServiceImpl(EscalafonRepository repository, ModelMapper modelMapper) {
        this.repository = repository;
        this.modelMapper = modelMapper;
    }

    @Override
    public EscalafonResponseDto crear(EscalafonRequestDto request) {
        if (repository.existsById(request.getIdEscalafon())) {
            throw new RuntimeException("Ya existe un Escalafon con ese ID");
        }
        Escalafon entity = modelMapper.map(request, Escalafon.class);
        repository.save(entity);
        return modelMapper.map(entity, EscalafonResponseDto.class);
    }

    @Override
    public EscalafonResponseDto actualizar(int id, EscalafonRequestDto request) {
        Escalafon entity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Escalafon no encontrado"));

        entity.setNombre(request.getNombre());
        repository.save(entity);

        return modelMapper.map(entity, EscalafonResponseDto.class);
    }

    @Override
    public List<EscalafonResponseDto> listarTodos() {
        return repository.findAll().stream()
                .map(entity -> modelMapper.map(entity, EscalafonResponseDto.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public EscalafonResponseDto obtenerPorId(int id) {
        Escalafon entity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Escalafon no encontrado"));
        return modelMapper.map(entity, EscalafonResponseDto.class);
    }

    @Override
    public void eliminar(int id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Escalafon no encontrado");
        }
        repository.deleteById(id);
    }
}