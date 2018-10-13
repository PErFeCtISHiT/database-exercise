package project.service;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

/**
 * @author: pis
 * @description: 泛型service，有着所有实体的公共的方法,有一个泛型jpaRepository，每个继承该类的方法都有自己的jpaRepository
 * @date: create in 15:52 2018/10/12
 */
@Service
public abstract class PublicService {
    JpaRepository repository;

    /**
     * @param id entity的id
     * @return 找到的实体类
     */
    public Object findByID(Integer id) {
        return repository.findOne(id);
    }


    /**
     * @param o 新增的实体类对象
     * @return 新增结果
     */
    public boolean add(Object o) {
        try {
            repository.saveAndFlush(o);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * @param o 修改的实体类对象
     * @return 修改结果
     */
    public boolean modify(Object o) {
        return add(o);
    }

    /**
     * @param o 删除的实体类对象
     * @return 删除结果
     */
    public boolean delete(Object o) {
        try {
            repository.delete(o);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    /**
     * @return 对象个数
     */
    public Integer count() {
        return Math.toIntExact(repository.count());
    }


    /**
     * @param integer id
     * @return 判断该实体是否存在
     */
    public boolean has(Integer integer) {
        return repository.exists(integer);
    }


    /**
     * @return 清空结果
     */
    public boolean deleteAll() {
        try {
            repository.deleteAll();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
