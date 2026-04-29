package com.klef.fsad.sdp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.klef.fsad.sdp.entity.Admin;
import com.klef.fsad.sdp.entity.CertificateDetails;
import com.klef.fsad.sdp.entity.User;
import com.klef.fsad.sdp.repository.AdminRepository;
import com.klef.fsad.sdp.repository.CertificateRepository;
import com.klef.fsad.sdp.repository.UserRepository;

@Service
public class AdminServiceImpl implements AdminService {
	
	@Autowired
	private AdminRepository adminRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	private CertificateRepository certificateRepository;

	@Override
	public Admin verifyAdminLogin(String username, String password) {
		return adminRepository.findByUsernameAndPassword(username, password);
	}

	@Override
	public List<User> ViewallUsers() 
	{
		return userRepository.findAll();
		
		
	}

	@Override
	public boolean deleteUser(int id) {
		if(userRepository.existsById(id))
		{
			userRepository.deleteById(id);
			return true;
		}
		else
		{
			return false;
		}
	}

	

}
