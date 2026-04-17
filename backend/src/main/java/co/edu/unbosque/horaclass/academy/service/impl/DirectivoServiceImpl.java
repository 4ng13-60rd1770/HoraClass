package co.edu.unbosque.horaclass.academy.service.impl;

import co.edu.unbosque.horaclass.academy.dto.DirectivoRequestDto;
import co.edu.unbosque.horaclass.academy.dto.DirectivoResponseDto;
import co.edu.unbosque.horaclass.academy.model.Directivo;
import co.edu.unbosque.horaclass.academy.repository.DirectivoRepository;
import co.edu.unbosque.horaclass.academy.service.DirectivoService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class DirectivoServiceImpl implements DirectivoService {

    private final DirectivoRepository repository;
    private final ModelMapper modelMapper;

    public DirectivoServiceImpl(DirectivoRepository repository, ModelMapper modelMapper) {
        this.repository = repository;
        this.modelMapper = modelMapper;
    }

    @Override
    public DirectivoResponseDto crear(DirectivoRequestDto request) {
        if (repository.existsById(request.getIdEmpleado())) {
            throw new RuntimeException("Ya existe un Directivo con ese ID");
        }
        Directivo entity = modelMapper.map(request, Directivo.class);
        repository.save(entity);
        return modelMapper.map(entity, DirectivoResponseDto.class);
    }

    @Override
    public DirectivoResponseDto actualizar(int id, DirectivoRequestDto request) {
        Directivo entity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Directivo no encontrado"));

        modelMapper.map(request, entity);
        repository.save(entity);

        return modelMapper.map(entity, DirectivoResponseDto.class);
    }

    @Override
    public List<DirectivoResponseDto> listarTodos() {
        return repository.findAll().stream()
                .map(entity -> modelMapper.map(entity, DirectivoResponseDto.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public DirectivoResponseDto obtenerPorId(int id) {
        Directivo entity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Directivo no encontrado"));
        return modelMapper.map(entity, DirectivoResponseDto.class);
    }

    @Override
    public void eliminar(int id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Directivo no encontrado");
        }
        repository.deleteById(id);
    }
}