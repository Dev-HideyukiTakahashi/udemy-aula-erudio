package br.com.udemy.erudio_springboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.udemy.erudio_springboot.model.Person;

public interface PersonRepository extends JpaRepository<Person, Long> {

}
