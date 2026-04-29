package com.klef.fsad.sdp.service;

import java.util.List;

import com.klef.fsad.sdp.entity.Admin;
import com.klef.fsad.sdp.entity.CertificateDetails;
import com.klef.fsad.sdp.entity.User;

public interface AdminService {
	  public Admin verifyAdminLogin(String username,String password);
	  
	  public List<User> ViewallUsers();
	  
	  public boolean deleteUser(int id);
	  

}
