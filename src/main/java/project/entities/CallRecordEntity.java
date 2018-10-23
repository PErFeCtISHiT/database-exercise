package project.entities;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * @author: pis
 * @description: 通话记录
 * @date: create in 10:40 2018/10/23
 */
@Entity
@Table(name = "call_record", schema = "businessDB")
public class CallRecordEntity {
    private Integer id;//通话id
    private UserEntity userEntity;//所属用户
    private Timestamp call_date;//账单日期
    private Integer call_length;//通话时长
    private Double call_cost;//通话费用
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @ManyToOne()
    @JoinColumn(name = "user_id")
    public UserEntity getUserEntity() {
        return userEntity;
    }

    public void setUserEntity(UserEntity userEntity) {
        this.userEntity = userEntity;
    }
    @Basic
    @Column(name = "call_date", nullable = false)
    public Timestamp getCall_date() {
        return call_date;
    }

    public void setCall_date(Timestamp call_date) {
        this.call_date = call_date;
    }

    @Column(name = "call_month", nullable = false)
    public Integer getCall_length() {
        return call_length;
    }

    public void setCall_length(Integer call_length) {
        this.call_length = call_length;
    }

    @Column(name = "call_cost", nullable = false)
    public Double getCall_cost() {
        return call_cost;
    }

    public void setCall_cost(Double call_cost) {
        this.call_cost = call_cost;
    }
}
