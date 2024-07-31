package org.programing.microservice.repositories;

import org.programing.microservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    public User findByEmail(String username);
    public User findByFullName(String username);
}
