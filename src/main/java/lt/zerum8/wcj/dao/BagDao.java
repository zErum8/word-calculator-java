package lt.zerum8.wcj.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import lt.zerum8.wcj.entity.Bag;

public interface BagDao extends JpaRepository<Bag, Long> {

}
