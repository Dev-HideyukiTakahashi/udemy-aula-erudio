package br.com.udemy.erudio_springboot.repository;

import br.com.udemy.erudio_springboot.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT u FROM User u " +
           "WHERE u.username = :username")
    User findByUsername(String username);
}
