package protonaut.repository;

import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.PageableRepository;
import protonaut.entity.UserEntity;

import java.util.Optional;

@Repository
public interface UserRepository extends PageableRepository<UserEntity, Long> {

    Optional<UserEntity> findById(Long id);

    Optional<UserEntity> findByUsername(String username);

}
