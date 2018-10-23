package project.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import project.entities.FlowRecordEntity;

import javax.persistence.Table;

/**
 * @author: pis
 * @description: good good study
 * @date: create in 11:25 2018/10/23
 */
@Repository
@Transactional
@Table(name = "flow_record")
public interface FlowRecordRepository extends JpaRepository<FlowRecordEntity,Integer> {
}
