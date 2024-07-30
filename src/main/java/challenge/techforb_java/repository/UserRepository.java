package challenge.techforb_java.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

import challenge.techforb_java.entity.User;

public interface UserRepository extends JpaRepository<User, Long>{
    Optional<User> findByEmail(String email);
}
