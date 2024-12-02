package ru.vadim.finance.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.vadim.finance.entity.Category;
import ru.vadim.finance.entity.Operation;

import java.util.List;

public interface OperationRepository extends JpaRepository<Operation, Long> {

    @Query("select o from Operation o where o.category.categoryId = :category_id")
    List<Operation> findAllByCategoryId(@Param("category_id") Long categoryId);

    @Query("select o from Operation o where o.type = :type and o.category.categoryId = :category_id")
    List<Operation> findAllByTypeAndCategoryId(
            @Param("type") String type,
            @Param("category_id") Long categoryId);

    List<Operation> findAllByCategory(Category category);
}
