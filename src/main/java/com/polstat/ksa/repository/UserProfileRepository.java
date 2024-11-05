package com.polstat.ksa.repository;

import com.polstat.ksa.entity.UserProfile;
import com.polstat.ksa.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {
    UserProfile findByUser(User user);
    @Query("SELECT u FROM UserProfile u WHERE u.kabupaten.id = :idKab")
    List<UserProfile> findByKabupatenIdCustomQuery(@Param("idKab") Long idKab);
}

