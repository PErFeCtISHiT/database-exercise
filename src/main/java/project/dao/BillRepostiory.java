package project.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import project.entities.BillEntity;

import javax.persistence.Table;

/**
 * @author: pis
 * @description: good good study
 * @date: create in 13:59 2018/10/13
 */
@Repository
@Transactional
@Table(name = "bill")
public interface BillRepostiory extends JpaRepository<BillEntity,Integer> {
}
