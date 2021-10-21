package com.matuageorge.technicalmaintenanceschedule.service;

import com.matuageorge.technicalmaintenanceschedule.dto.TerminalDto;
import com.matuageorge.technicalmaintenanceschedule.exception.NotFoundException;
import com.matuageorge.technicalmaintenanceschedule.exception.ResourceAlreadyExistsException;
import com.matuageorge.technicalmaintenanceschedule.exception.ValidationException;
import com.matuageorge.technicalmaintenanceschedule.model.Terminal;
import com.matuageorge.technicalmaintenanceschedule.model.TerminalType;
import com.matuageorge.technicalmaintenanceschedule.repository.TerminalRepository;
import com.matuageorge.technicalmaintenanceschedule.service.api.payway.PayWayApiService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@AllArgsConstructor
public class TerminalServiceImpl implements TerminalService {

    private final PayWayApiService payWayApiService;
    private final TerminalRepository terminalRepository;
    private final ModelMapper modelMapper;

    @Override
    public Terminal save(TerminalDto terminalDto) throws ValidationException, ResourceAlreadyExistsException {
        Optional<Terminal> terminalToSave = terminalRepository.findByName(terminalDto.getName());
        if (terminalToSave.isPresent()) {
            log.info("Terminal to save with such name already exists");
            throw new ResourceAlreadyExistsException("Terminal with such name already exists");
        } else {
            try {
                return terminalRepository.save(modelMapper.map(terminalDto, Terminal.class));
            } catch (IllegalArgumentException e) {
                throw new ValidationException("Terminal cannot be null");
            }
        }
    }

    @Override
    public Terminal update(TerminalDto terminalDto) throws NotFoundException {
        Optional<Terminal> terminalToUpdate = terminalRepository.findByName(terminalDto.getName());
        Terminal savedTerminal;
        if (terminalToUpdate.isPresent()) {
            Terminal terminal = terminalToUpdate.get();
            log.info("Terminal to update is found");
            terminal.setName(terminalDto.getName());
            terminal.setLocation(terminalDto.getLocation());
            terminal.setDisabled(terminalDto.getDisabled());
            terminal.setType(terminalDto.getType());
            savedTerminal = terminalRepository.save(modelMapper.map(terminal, Terminal.class));
        } else {
            log.info("Terminal to update is NOT found");
            throw new NotFoundException("Terminal to update is NOT found");
        }
        return savedTerminal;
    }

    @Override
    public void delete(Long terminalId) throws NotFoundException {
        findById(terminalId);
        terminalRepository.deleteById(terminalId);
    }

    @Override
    public Page<Terminal> findAllByPage(Integer page, Integer pageSize) throws NotFoundException {
        Pageable pageable = PageRequest.of(page, pageSize, Sort.by("id").descending());
        Page<Terminal> terminalsPage = terminalRepository.findAll(pageable);

        if (terminalsPage.hasContent()) {
            return terminalsPage;
        } else {
            throw new NotFoundException("No terminals found");
        }
    }


    @Override
    public Terminal findById(Long id) throws NotFoundException {
        return terminalRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Terminal is not found")
        );
    }

    @Override
    public Optional<Terminal> findByName(String terminalName) {
        return terminalRepository.findByName(terminalName);
    }

    @Override
    public void deleteByName(String terminalName) throws NotFoundException {
        Terminal task = findByName(terminalName).orElseThrow(
                () -> new NotFoundException("Task to delete is not found"));
        delete(task.getId());
    }

    @Override
    public List<Terminal> updateListOfTerminalsInDb(TerminalType terminalType) throws NotFoundException {
        List<Terminal> currentListOfTerminals = payWayApiService.getCurrentListOfTerminals();

        List<Terminal> filteredByTypeListOfTerminals = filterTerminalsByType(currentListOfTerminals, terminalType);

        for (Terminal terminal : filteredByTypeListOfTerminals) {
            Optional<Terminal> terminalInDb = terminalRepository.findByName(terminal.getName());
            if (terminalInDb.isEmpty()) {
                terminalRepository.save(terminal);
            } else {
                if (!terminalInDb.get().equals(terminal)) {
                    update(modelMapper.map(terminal, TerminalDto.class));
                }
            }
        }
        log.info("The terminals list has been updated.");
        return terminalRepository.findAll();
    }

    private List<Terminal> filterTerminalsByType(List<Terminal> currentListOfTerminals, TerminalType terminalType) {
        return currentListOfTerminals.stream()
                .filter(
                        terminal -> terminal.getType() == terminalType)
                .filter(
                        terminal -> !terminal.getDeleted())
                .toList();
    }
}
