package ug.payway.technicalmaintenanceschedule.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ug.payway.technicalmaintenanceschedule.config.Utility;
import ug.payway.technicalmaintenanceschedule.dto.TerminalDto;
import ug.payway.technicalmaintenanceschedule.exception.NotFoundException;
import ug.payway.technicalmaintenanceschedule.exception.ResourceAlreadyExistsException;
import ug.payway.technicalmaintenanceschedule.exception.ValidationException;
import ug.payway.technicalmaintenanceschedule.model.Terminal;
import ug.payway.technicalmaintenanceschedule.service.TerminalService;

@CrossOrigin
@RestController
@Slf4j
@AllArgsConstructor
public class TerminalController {

  private final TerminalService terminalService;

  @PostMapping(Utility.ADMIN + Utility.TERMINALS)
  public ResponseEntity<Terminal> save(@RequestBody TerminalDto terminalDto)
      throws ValidationException, ResourceAlreadyExistsException {

    log.info("Handling task {} to create", terminalDto);

    return ResponseEntity.ok(terminalService.save(terminalDto));
  }

  @PutMapping(Utility.ADMIN + Utility.TERMINALS)
  public ResponseEntity<Terminal> update(@RequestBody TerminalDto terminalDto)
      throws ValidationException, ResourceAlreadyExistsException, NotFoundException {

    log.info("Handling terminal to update with task {}", terminalDto);

    Terminal updatedTerminal = terminalService.update(terminalDto);
    return ResponseEntity.ok(updatedTerminal);
  }

  @DeleteMapping(Utility.ADMIN + Utility.TERMINALS + "/{terminalName}")
  public ResponseEntity<Void> delete(@PathVariable String terminalName)
      throws ValidationException, NotFoundException {

    log.info("Handling delete task with task description: {}", terminalName);

    terminalService.deleteByName(terminalName);
    return ResponseEntity.accepted().build();
  }

  @GetMapping(Utility.TERMINALS + "/{terminalId}")
  public ResponseEntity<Terminal> findById(@PathVariable Long terminalId)
      throws ValidationException, NotFoundException {

    log.info("Handling task to find by terminalId: {}", terminalId);

    Terminal terminalResponseEntity = terminalService.findById(terminalId);
    return ResponseEntity.ok().body(terminalResponseEntity);
  }

  @GetMapping(Utility.TERMINALS + "/{page}/{pageSize}")
  public ResponseEntity<Page<Terminal>> findAll(
      @PathVariable Integer page, @PathVariable Integer pageSize)
      throws NotFoundException, IllegalAccessException, NoSuchFieldException {

    log.info("Handling find all temrinals page: {} with size: {}", page, pageSize);

    Page<Terminal> terminalsPageResponseBody = terminalService.findAllByPage(page, pageSize);
    return ResponseEntity.ok().body(terminalsPageResponseBody);
  }
}
