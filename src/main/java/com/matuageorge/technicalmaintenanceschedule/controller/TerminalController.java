package com.matuageorge.technicalmaintenanceschedule.controller;

import com.matuageorge.technicalmaintenanceschedule.dto.TerminalDto;
import com.matuageorge.technicalmaintenanceschedule.exception.NotFoundException;
import com.matuageorge.technicalmaintenanceschedule.exception.ResourceAlreadyExistsException;
import com.matuageorge.technicalmaintenanceschedule.exception.ValidationException;
import com.matuageorge.technicalmaintenanceschedule.model.Terminal;
import com.matuageorge.technicalmaintenanceschedule.service.TerminalService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.matuageorge.technicalmaintenanceschedule.config.Utility.ADMIN;
import static com.matuageorge.technicalmaintenanceschedule.config.Utility.TERMINALS;

@CrossOrigin
@RestController
@Slf4j
@AllArgsConstructor
public class TerminalController {

    private final TerminalService terminalService;

    @PostMapping(ADMIN + TERMINALS)
    public ResponseEntity<Terminal> save(@RequestBody TerminalDto terminalDto) throws ValidationException, ResourceAlreadyExistsException {

        log.info("Handling task {} to create", terminalDto);

        return ResponseEntity.ok(terminalService.save(terminalDto));
    }

    @PutMapping(ADMIN + TERMINALS)
    public ResponseEntity<Terminal> update(@RequestBody TerminalDto terminalDto) throws ValidationException,
            ResourceAlreadyExistsException, NotFoundException {

        log.info("Handling terminal to update with task {}", terminalDto);

        Terminal updatedTerminal = terminalService.update(terminalDto);
        return ResponseEntity.ok(updatedTerminal);
    }

    @DeleteMapping(ADMIN + TERMINALS + "/{terminalName}")
    public ResponseEntity<Void> delete(@PathVariable String terminalName) throws ValidationException,
            NotFoundException {

        log.info("Handling delete task with task description: {}", terminalName);

        terminalService.deleteByName(terminalName);
        return ResponseEntity.accepted().build();
    }

    @GetMapping(TERMINALS + "/{terminalId}")
    public ResponseEntity<Terminal> findById(@PathVariable Long terminalId) throws ValidationException,
            NotFoundException {

        log.info("Handling task to find by terminalId: {}", terminalId);

        Terminal terminalResponseEntity = terminalService.findById(terminalId);
        return ResponseEntity.ok().body(terminalResponseEntity);
    }

    @GetMapping(TERMINALS + "/{page}/{pageSize}")
    public ResponseEntity<Page<Terminal>> findAll(
            @PathVariable Integer page,
            @PathVariable Integer pageSize) throws NotFoundException, IllegalAccessException, NoSuchFieldException {

        log.info("Handling find all temrinals page: {} with size: {}", page, pageSize);

        Page<Terminal> terminalsPageResponseBody = terminalService.findAllByPage(page, pageSize);
        return ResponseEntity.ok().body(terminalsPageResponseBody);
    }
}