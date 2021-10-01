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

@CrossOrigin
@RestController
@Slf4j
@AllArgsConstructor
@RequestMapping("/terminals")
public class TerminalController {

    private final TerminalService terminalService;

    @PostMapping("/admin")
    public ResponseEntity<Terminal> save(@RequestBody TerminalDto terminalDto) throws ValidationException, ResourceAlreadyExistsException {

        log.info("Handling terminal {} to create", terminalDto);

        Terminal savedTerminal = terminalService.save(terminalDto);
        return ResponseEntity.ok(savedTerminal);
    }

    @PutMapping("/admin")
    public ResponseEntity<Terminal> update(@RequestBody TerminalDto terminalDto) throws ValidationException,
            ResourceAlreadyExistsException, NotFoundException {

        log.info("Handling terminal to update with terminal {}", terminalDto);

        Terminal updatedTerminal = terminalService.update(terminalDto);
        return ResponseEntity.ok(updatedTerminal);
    }

    @DeleteMapping("/admin/{terminalId}")
    public ResponseEntity<Void> delete(@PathVariable Long terminalId) throws ValidationException, NotFoundException {

        log.info("Handling delete terminal with terminalId: {}", terminalId);

        terminalService.delete(terminalId);
        return ResponseEntity.accepted().build();
    }

    @GetMapping("/{terminalId}")
    public ResponseEntity<Terminal> findById(@PathVariable Long terminalId) throws ValidationException,
            NotFoundException {

        log.info("Handling terminal to find by terminalId: {}", terminalId);

        Terminal terminalResponseEntity = terminalService.findById(terminalId);
        return ResponseEntity.ok().body(terminalResponseEntity);
    }

    @GetMapping("/{page}/{pageSize}")
    public ResponseEntity<Page<Terminal>> findAll(
            @PathVariable Integer page,
            @PathVariable Integer pageSize) throws NotFoundException, IllegalAccessException, NoSuchFieldException {

        log.info("Handling find all terminals page: {} with size: {}", page, pageSize);

        Page<Terminal> terminalsPageResponseBody = terminalService.findAll(page, pageSize);
        return ResponseEntity.ok().body(terminalsPageResponseBody);
    }
}