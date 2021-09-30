package com.matuageorge.technicalmaintenanceschedule.service;

import com.matuageorge.technicalmaintenanceschedule.dto.TerminalDto;
import com.matuageorge.technicalmaintenanceschedule.exception.NotFoundException;
import com.matuageorge.technicalmaintenanceschedule.exception.ResourceAlreadyExistsException;
import com.matuageorge.technicalmaintenanceschedule.exception.ValidationException;
import com.matuageorge.technicalmaintenanceschedule.model.Terminal;
import com.matuageorge.technicalmaintenanceschedule.repository.TerminalRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.*;

import java.util.List;
import java.util.Optional;

import static com.matuageorge.technicalmaintenanceschedule.prototype.TaskPrototype.aTask;
import static com.matuageorge.technicalmaintenanceschedule.prototype.TaskPrototype.bTask;
import static com.matuageorge.technicalmaintenanceschedule.prototype.TerminalPrototype.aTerminal;
import static com.matuageorge.technicalmaintenanceschedule.prototype.TerminalPrototype.bTerminal;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TerminalServiceImplTest {

    @Mock
    TerminalRepository terminalRepository;
    TerminalService terminalService;
    ModelMapper modelMapper;

    @BeforeEach
    void setUp() {
        modelMapper = new ModelMapper();
        terminalService = new TerminalServiceImpl(terminalRepository, modelMapper);
    }

    @Test
    void save() throws ValidationException, ResourceAlreadyExistsException {
        aTerminal().setId(1L);
        when(terminalRepository.findById(aTerminal().getId())).thenReturn(Optional.empty());
        when(terminalRepository.save(aTerminal())).thenReturn(aTerminal());
        Terminal savedTerminal = terminalService.save(modelMapper.map(aTerminal(), TerminalDto.class));
        assertThat(savedTerminal).isNotNull();
        assertThat(savedTerminal).isEqualTo(aTerminal());
        verify(terminalRepository).save(aTerminal());
    }

    @Test
    void saveThrowsResourceAlreadyExistsException() {
        aTerminal().setId(1L);
        when(terminalRepository.findById(aTask().getId())).thenReturn(Optional.ofNullable(aTerminal()));
        assertThrows(ResourceAlreadyExistsException.class,
                () -> terminalService.save(modelMapper.map(aTerminal(), TerminalDto.class)));
        verify(terminalRepository, never()).save(aTerminal());
    }

    @Test
    void update() throws ValidationException, NotFoundException {
        when(terminalRepository.findById(any())).thenReturn(Optional.ofNullable(aTerminal()));
        when(terminalRepository.save(bTerminal())).thenReturn(bTerminal());
        Terminal updatedTerminal = terminalService.update(modelMapper.map(bTerminal(), TerminalDto.class));
        assertThat(updatedTerminal).isNotNull();
        assertThat(updatedTerminal.getId()).isEqualTo(bTask().getId());
    }

    @Test
    void delete() throws ValidationException, NotFoundException {
        Long terminalToDelete = 1L;
        when(terminalRepository.findById(terminalToDelete)).thenReturn(Optional.ofNullable(aTerminal()));
        terminalService.delete(terminalToDelete);
        verify(terminalRepository).deleteById(1L);
    }

    @Test
    void findAll() throws NotFoundException {
        int page = 0;
        int pageSize = 1;
        Pageable pageable = PageRequest.of(page, pageSize, Sort.by("id").descending());
        List<Terminal> terminals = List.of(aTerminal(), bTerminal());
        Page<Terminal> pageOfTerminals = new PageImpl<>(terminals);
        when(terminalRepository.findAll(pageable)).thenReturn(pageOfTerminals);
        Page<Terminal> foundTerminals = terminalService.findAll(page, pageSize);
        assertThat(foundTerminals).isNotNull();
        assertThat(foundTerminals.getSize()).isEqualTo(terminals.size());
        verify(terminalRepository).findAll(pageable);
    }

    @Test
    void findById() throws ValidationException, NotFoundException {
        Long terminalToFind = 1L;
        when(terminalRepository.findById(terminalToFind)).thenReturn(Optional.ofNullable(aTerminal()));
        Terminal foundTask = terminalService.findById(terminalToFind);
        assertThat(foundTask).isNotNull();
        assertEquals("General service A", aTask().getDescription());
        assertEquals(30, aTask().getFrequency());
    }

    @Test
    void findByIdThrowsNotFoundExceptionWhenTerminalIsNotFound() {
        when(terminalRepository.findById(100L)).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> terminalService.findById(100L));
    }
}