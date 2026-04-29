package com.klef.fsad.sdp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.klef.fsad.sdp.entity.CertificateDetails;
import com.klef.fsad.sdp.entity.User;

@Repository
public interface CertificateRepository extends JpaRepository<CertificateDetails, Integer>{

	List<CertificateDetails> findByUser(User user);
	
	public CertificateDetails findByCertName(String certName);
	public CertificateDetails findByCertNameAndUser_Id(String certName, int userid);
	
	@Query("select c from CertificateDetails c join fetch c.user where c.expiryDate < ?1")
	List<CertificateDetails> findExpiringCertificates(String date);

	List<CertificateDetails> findByExpiryDate(String date);

}
