package br.com.udemy.erudio_springboot.mapper;

import org.springframework.stereotype.Service;

import br.com.udemy.erudio_springboot.dto.v2.PersonDTOV2;
import br.com.udemy.erudio_springboot.model.Person;

@Service
public class PersonMapper {

  public PersonDTOV2 entityToDTO(Person person) {
    return new PersonDTOV2(
        person.getId(),
        person.getFirstName(),
        person.getLastName(),
        null, // adicionaria no banco de dados esse atributo
        person.getAddress(),
        person.getGender());
  }

  public Person dtoToEntity(PersonDTOV2 dto) {
    return new Person(
        dto.getId(),
        dto.getFirstName(),
        dto.getLastName(),
        dto.getAddress(),
        dto.getGender());
  }

}
