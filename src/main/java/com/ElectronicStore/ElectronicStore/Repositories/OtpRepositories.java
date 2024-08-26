package com.ElectronicStore.ElectronicStore.Repositories;

import com.ElectronicStore.ElectronicStore.Model.OTP;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface OtpRepositories extends JpaRepository<OTP,Long> {


//       @Transactional
//       @Query(value = "SELECT * FROM leave_requests lr WHERE lr.user_id = :userId AND lr.status = 'Pending'", nativeQuery = true)
//       LeaveRequest findByUserIdAndStatusNative(Long userId);
//
//       @Transactional
//       @Query(value = "SELECT * FROM otp ot WHERE ot.otp = :otp AND ot.User_email = :email", nativeQuery = true)
       public OTP findByOtpAndEmail(String otp, String email);

       boolean existsByotp(String otp);

       OTP findByotp(String otp);
}
