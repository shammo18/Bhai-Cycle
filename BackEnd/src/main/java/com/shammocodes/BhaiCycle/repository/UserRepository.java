package com.shammocodes.BhaiCycle.repository;

import com.shammocodes.BhaiCycle.entity.User;
import org.hibernate.sql.Select;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {
      User findByUsername(String username);
      int countByUsername(String username);
      User findByVerificationcode(String code);
      User findByMessengerid(String code);
      @Query(value = "SELECT * FROM user u WHERE u.cycle_status = '0'" , nativeQuery = true)
      public List<User> getAvailableCycles();
      public List<User> findTop10ByOrderByContributionDesc();


}
