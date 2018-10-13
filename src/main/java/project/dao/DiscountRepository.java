package project.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import project.entities.DiscountEntity;

import javax.persistence.Table;

/**
 * @author: pis
 * @description: repository
 * @date: create in 15:44 2018/10/12
 */
@Repository
@Transactional
@Table(name = "discount")
public interface DiscountRepository extends JpaRepository<DiscountEntity, Integer> {
}
