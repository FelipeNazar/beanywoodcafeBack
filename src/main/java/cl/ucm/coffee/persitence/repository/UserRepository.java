package cl.ucm.coffee.persitence.repository;

import cl.ucm.coffee.persitence.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, String> {
    Optional<UserEntity> findByUsername(String username);
    Boolean existsByUsername(String username);
}

