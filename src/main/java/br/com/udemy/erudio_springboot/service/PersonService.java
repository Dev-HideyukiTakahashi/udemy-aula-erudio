package br.com.udemy.erudio_springboot.service;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.udemy.erudio_springboot.dto.v1.PersonDTO;
import br.com.udemy.erudio_springboot.dto.v2.PersonDTOV2;
import br.com.udemy.erudio_springboot.exception.ResourceNotFoundException;
import br.com.udemy.erudio_springboot.mapper.PersonMapper;
import br.com.udemy.erudio_springboot.model.Person;
import br.com.udemy.erudio_springboot.repository.PersonRepository;

import static br.com.udemy.erudio_springboot.mapper.ObjectMapper.*;

@Service
public class PersonService {

    private final Logger logger = Logger.getLogger(PersonService.class.getName());

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private PersonMapper mapper;

    public List<PersonDTO> findAll() {
        logger.info("Finding all people!");

        return parseListObjects(personRepository.findAll(), PersonDTO.class);
    }

    public PersonDTO findById(Long id) {
        logger.info("Finding one person!");

        var entity = personRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));

        return parseObjects(entity, PersonDTO.class);
    }

    public PersonDTO create(PersonDTO dto) {
        logger.info("Creating one person!");

        var entity = parseObjects(dto, Person.class);
        return parseObjects(personRepository.save(entity), PersonDTO.class);
    }

    public PersonDTOV2 createV2(PersonDTOV2 dto) {
        logger.info("Creating one person V2!");

        var entity = mapper.dtoToEntity(dto);
        return mapper.entityToDTO(personRepository.save(entity));
    }

    public PersonDTO update(PersonDTO dto) {
        logger.info("Updating one person!");

        Person entity = personRepository.findById(dto.getId())
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));

        entity.setFirstName(dto.getFirstName());
        entity.setLastName(dto.getLastName());
        entity.setAddress(dto.getAddress());
        entity.setGender(dto.getGender());

        return parseObjects(personRepository.save(entity), PersonDTO.class);
    }

    public void delete(Long id) {
        logger.info("Deleting one person!");

        Person entity = personRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));

        personRepository.delete(entity);
    }

}
