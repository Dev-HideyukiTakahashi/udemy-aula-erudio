package br.com.udemy.erudio_springboot.service;

import java.util.List;
import java.util.logging.Logger;

import br.com.udemy.erudio_springboot.controller.PersonController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.udemy.erudio_springboot.dto.v1.PersonDTO;
import br.com.udemy.erudio_springboot.dto.v2.PersonDTOV2;
import br.com.udemy.erudio_springboot.exception.ResourceNotFoundException;
import br.com.udemy.erudio_springboot.mapper.PersonMapper;
import br.com.udemy.erudio_springboot.model.Person;
import br.com.udemy.erudio_springboot.repository.PersonRepository;

import static br.com.udemy.erudio_springboot.mapper.ObjectMapper.*;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class PersonService {

    private final Logger logger = Logger.getLogger(PersonService.class.getName());

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private PersonMapper mapper;

    public List<PersonDTO> findAll() {
        logger.info("Finding all people!");

        var dto = parseListObjects(personRepository.findAll(), PersonDTO.class);
        dto.forEach(this::addHateoasLinks); ;
        return dto;
    }

    public PersonDTO findById(Long id) {
        logger.info("Finding one person!");

        var entity = personRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));

        var dto = parseObjects(entity, PersonDTO.class);
        addHateoasLinks(dto);
        return dto;
    }



    public PersonDTO create(PersonDTO dto) {
        logger.info("Creating one person!");

        var entity = parseObjects(dto, Person.class);
        dto = parseObjects(personRepository.save(entity), PersonDTO.class);
        addHateoasLinks(dto);
        return dto;
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

        dto = parseObjects(personRepository.save(entity), PersonDTO.class);
        addHateoasLinks(dto);
        return dto;
    }

    public void delete(Long id) {
        logger.info("Deleting one person!");

        Person entity = personRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));

        personRepository.delete(entity);
    }

    private void addHateoasLinks(PersonDTO dto) {
        dto.add(linkTo(methodOn(PersonController.class)
                .findById(dto.getId())).withSelfRel().withType("GET"));
        dto.add(linkTo(methodOn(PersonController.class)
                .delete(dto.getId())).withRel("delete").withType("DELETE"));
        dto.add(linkTo(methodOn(PersonController.class)
                .findAll()).withRel("findAll").withType("GET"));
        dto.add(linkTo(methodOn(PersonController.class)
                .create(dto)).withRel("create").withType("POST"));
        dto.add(linkTo(methodOn(PersonController.class)
                .update(dto)).withRel("update").withType("PUT"));
    }
}
