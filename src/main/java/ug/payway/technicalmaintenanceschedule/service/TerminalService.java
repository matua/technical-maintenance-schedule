package ug.payway.technicalmaintenanceschedule.service;

import org.springframework.data.domain.Page;
import ug.payway.technicalmaintenanceschedule.dto.TerminalDto;
import ug.payway.technicalmaintenanceschedule.exception.NotFoundException;
import ug.payway.technicalmaintenanceschedule.exception.ResourceAlreadyExistsException;
import ug.payway.technicalmaintenanceschedule.exception.ValidationException;
import ug.payway.technicalmaintenanceschedule.model.Terminal;
import ug.payway.technicalmaintenanceschedule.model.TerminalType;

import java.util.List;
import java.util.Optional;

public interface TerminalService {
  Terminal save(TerminalDto terminalDto) throws ValidationException, ResourceAlreadyExistsException;

  Terminal update(TerminalDto terminalDto) throws ValidationException, NotFoundException;

  void delete(Long terminalId) throws ValidationException, NotFoundException;

  Page<Terminal> findAllByPage(Integer page, Integer pageSize) throws NotFoundException;

  Terminal findById(Long id) throws ValidationException, NotFoundException;

  Optional<Terminal> findByName(String terminalName);

  void deleteByName(String terminalName) throws NotFoundException;

  List<Terminal> updateListOfTerminalsInDb(TerminalType terminalType) throws NotFoundException;

  List<Terminal> findAll();
}
