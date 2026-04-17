package co.edu.unbosque.horaclass.academy.service.impl;

import co.edu.unbosque.horaclass.academy.dto.TipoDocumentoRequestDto;
import co.edu.unbosque.horaclass.academy.dto.TipoDocumentoResponseDto;
import co.edu.unbosque.horaclass.academy.model.TipoDocumento;
import co.edu.unbosque.horaclass.academy.repository.TipoDocumentoRepository;
import co.edu.unbosque.horaclass.academy.service.TipoDocumentoService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class TipoDocumentoServiceImpl implements TipoDocumentoService {

    private final TipoDocumentoRepository repository;
    private final ModelMapper modelMapper;

    public TipoDocumentoServiceImpl(TipoDocumentoRepository repository, ModelMapper modelMapper) {
        this.repository = repository;
        this.modelMapper = modelMapper;
    }

    @Override
    public TipoDocumentoResponseDto crear(TipoDocumentoRequestDto request) {
        if (repository.existsById(request.getIdTipoDocumento())) {
            throw new RuntimeException("Ya existe un TipoDocumento con ese ID");
        }
        TipoDocumento entity = modelMapper.map(request, TipoDocumento.class);
        repository.save(entity);
        return modelMapper.map(entity, TipoDocumentoResponseDto.class);
    }

    @Override
    public TipoDocumentoResponseDto actualizar(int id, TipoDocumentoRequestDto request) {
        TipoDocumento entity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("TipoDocumento no encontrado"));

        entity.setNombre(request.getNombre());
        repository.save(entity);

        return modelMapper.map(entity, TipoDocumentoResponseDto.class);
    }

    @Override
    public List<TipoDocumentoResponseDto> listarTodos() {
        return repository.findAll().stream()
                .map(entity -> modelMapper.map(entity, TipoDocumentoResponseDto.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public TipoDocumentoResponseDto obtenerPorId(int id) {
        TipoDocumento entity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("TipoDocumento no encontrado"));
        return modelMapper.map(entity, TipoDocumentoResponseDto.class);
    }

    @Override
    public void eliminar(int id) {
        if(!repository.existsById(id)){
            throw new RuntimeException("TipoDocumento no encontrado");
        }
        repository.deleteById(id);
    }
}
