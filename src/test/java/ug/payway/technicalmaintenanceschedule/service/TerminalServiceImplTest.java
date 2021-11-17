package ug.payway.technicalmaintenanceschedule.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.*;
import ug.payway.technicalmaintenanceschedule.dto.TerminalDto;
import ug.payway.technicalmaintenanceschedule.exception.NotFoundException;
import ug.payway.technicalmaintenanceschedule.exception.ResourceAlreadyExistsException;
import ug.payway.technicalmaintenanceschedule.exception.ValidationException;
import ug.payway.technicalmaintenanceschedule.model.Terminal;
import ug.payway.technicalmaintenanceschedule.prototype.TaskPrototype;
import ug.payway.technicalmaintenanceschedule.prototype.TerminalPrototype;
import ug.payway.technicalmaintenanceschedule.repository.TerminalRepository;
import ug.payway.technicalmaintenanceschedule.service.api.payway.PayWayApiService;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TerminalServiceImplTest {

    @Mock
    PayWayApiService payWayApiService;
    TerminalRepository terminalRepository;
    TerminalService terminalService;
    ModelMapper modelMapper;

    @BeforeEach
    void setUp() {
        modelMapper = new ModelMapper();
        terminalService = new TerminalServiceImpl(payWayApiService, terminalRepository, modelMapper);
    }

    @Test
    void save() throws ValidationException, ResourceAlreadyExistsException {
        TerminalPrototype.aTerminal().setId(1L);
        when(terminalRepository.findById(TerminalPrototype.aTerminal().getId())).thenReturn(Optional.empty());
        when(terminalRepository.save(TerminalPrototype.aTerminal())).thenReturn(TerminalPrototype.aTerminal());
        Terminal savedTerminal = terminalService.save(modelMapper.map(TerminalPrototype.aTerminal(), TerminalDto.class));
        assertThat(savedTerminal).isNotNull();
        assertThat(savedTerminal).isEqualTo(TerminalPrototype.aTerminal());
        verify(terminalRepository).save(TerminalPrototype.aTerminal());
    }

    @Test
    void saveThrowsResourceAlreadyExistsException() {
        TerminalPrototype.aTerminal().setId(1L);
        when(terminalRepository.findById(TaskPrototype.aTask().getId())).thenReturn(Optional.ofNullable(TerminalPrototype.aTerminal()));
        assertThrows(ResourceAlreadyExistsException.class,
                () -> terminalService.save(modelMapper.map(TerminalPrototype.aTerminal(), TerminalDto.class)));
        verify(terminalRepository, never()).save(TerminalPrototype.aTerminal());
    }

    @Test
    void update() throws ValidationException, NotFoundException {
        when(terminalRepository.findById(any())).thenReturn(Optional.ofNullable(TerminalPrototype.aTerminal()));
        when(terminalRepository.save(TerminalPrototype.bTerminal())).thenReturn(TerminalPrototype.bTerminal());
        Terminal updatedTerminal = terminalService.update(modelMapper.map(TerminalPrototype.bTerminal(), TerminalDto.class));
        assertThat(updatedTerminal).isNotNull();
        assertThat(updatedTerminal.getId()).isEqualTo(TaskPrototype.bTask().getId());
    }

    @Test
    void delete() throws ValidationException, NotFoundException {
        Long terminalToDelete = 1L;
        when(terminalRepository.findById(terminalToDelete)).thenReturn(Optional.ofNullable(TerminalPrototype.aTerminal()));
        terminalService.delete(terminalToDelete);
        verify(terminalRepository).deleteById(1L);
    }

    @Test
    void findAll() throws NotFoundException {
        int page = 0;
        int pageSize = 1;
        Pageable pageable = PageRequest.of(page, pageSize, Sort.by("id").descending());
        List<Terminal> terminals = List.of(TerminalPrototype.aTerminal(), TerminalPrototype.bTerminal());
        Page<Terminal> pageOfTerminals = new PageImpl<>(terminals);
        when(terminalRepository.findAll(pageable)).thenReturn(pageOfTerminals);
        Page<Terminal> foundTerminals = terminalService.findAllByPage(page, pageSize);
        assertThat(foundTerminals).isNotNull();
        assertThat(foundTerminals.getSize()).isEqualTo(terminals.size());
        verify(terminalRepository).findAll(pageable);
    }

    @Test
    void findById() throws ValidationException, NotFoundException {
        Long terminalToFind = 1L;
        when(terminalRepository.findById(terminalToFind)).thenReturn(Optional.ofNullable(TerminalPrototype.aTerminal()));
        Terminal foundTask = terminalService.findById(terminalToFind);
        assertThat(foundTask).isNotNull();
        Assertions.assertEquals("General service A", TaskPrototype.aTask().getDescription());
        Assertions.assertEquals(30, TaskPrototype.aTask().getFrequency());
    }

    @Test
    void findByIdThrowsNotFoundExceptionWhenTerminalIsNotFound() {
        when(terminalRepository.findById(100L)).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> terminalService.findById(100L));
    }
}