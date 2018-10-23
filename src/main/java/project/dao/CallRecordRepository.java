package project.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import project.entities.CallRecordEntity;

import javax.persistence.Table;

/**
 * @author: pis
 * @description: good good study
 * @date: create in 11:24 2018/10/23
 */
@Repository
@Transactional
@Table(name = "call_record")
public interface CallRecordRepository extends JpaRepository<CallRecordEntity,Integer> {
}
