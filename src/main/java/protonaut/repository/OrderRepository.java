package protonaut.repository;

import io.micronaut.data.annotation.Query;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.jpa.repository.JpaRepository;
import protonaut.entity.OrderEntity;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, Long> {

    @Query("update OrderEntity o set o.status = :status where o.id = :id")
    void updateStatus(Long id, String status);
}
