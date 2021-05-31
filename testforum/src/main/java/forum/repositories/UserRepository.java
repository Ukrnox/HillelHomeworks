package forum.repositories;

import forum.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Long>
{
    @Modifying
    @Query(value = "UPDATE users u SET user_login = :newLogin WHERE u.id = :userId", nativeQuery = true)
    void changeUserLogin(@Param("newLogin") String newLogin, @Param("userId") Long userId);

    @Modifying
    @Query(value = "UPDATE users u SET password = :newPassword WHERE u.id = :userId", nativeQuery = true)
    void changeUserPassword(@Param("newPassword") String newPassword, @Param("userId") Long userId);
}