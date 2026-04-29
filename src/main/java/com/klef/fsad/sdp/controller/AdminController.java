package com.klef.fsad.sdp.controller;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.klef.fsad.sdp.SpringBootSdpBackendProjectApplication;
import com.klef.fsad.sdp.dto.EmailDTO;
import com.klef.fsad.sdp.entity.Admin;
import com.klef.fsad.sdp.entity.CertificateDetails;
import com.klef.fsad.sdp.entity.User;
import com.klef.fsad.sdp.service.AdminService;
import com.klef.fsad.sdp.service.CertificateService;
import com.klef.fsad.sdp.service.UserService;

@RestController
@RequestMapping("admin")
@CrossOrigin("*")
public class AdminController {

	
	@Autowired
	private AdminService adminService;
	
	
	@Autowired
	private CertificateService certificateservice;

    
	
	@GetMapping("/")
	public String slash()
	{
		return "Online Certificate Tracker";
	}
	
//	@PostMapping("/login")
//	public ResponseEntity<?> checkadminlogin(@RequestBody Admin admin)
//	{
//		try
//		{
//			Admin a = adminService.verifyAdminLogin(admin.getUsername(), admin.getPassword());
//		
//		    if(a!=null)
//		    {
//		    	return ResponseEntity.status(200).body(admin);
//		    	
//		    }
//		    else
//		    {
//		    	return ResponseEntity.status(401).body("Login Invalid");
//		    }
//		}
//		catch (Exception e) 
//		{
//			System.out.println(e.getMessage());
//			return ResponseEntity.status(500).body("Internal Server Error");
//		}
//	}
	@GetMapping("/viewallusers")
	public ResponseEntity<?> viewallusers() {
		try {
			List<User> user = adminService.ViewallUsers();
			
			
			if(user.size()>0)
			{
				return ResponseEntity.status(200).body(user);
			}
			else
			{
				return ResponseEntity.noContent().build();
			}
			
		} catch (Exception e) 
		{
			return ResponseEntity.status(500).body("Error While Fetching User Details");
			
		}
	}
	
	@DeleteMapping("/deleteuserbyid/{id}")
	public ResponseEntity<?> deleteUserById(@PathVariable int id )
	{
		try
		{
			boolean deleted= adminService.deleteUser(id);
			if(deleted)
			{
				return ResponseEntity.ok("User deleted successfully");
			}
			else
			{
				return ResponseEntity.status(404).body("User not found to delete");
			}
		}
		catch(Exception e)
		{
			return ResponseEntity.status(500).body("Internal Server Error");
		}
	}

	@GetMapping("/viewallcertificates")
	public ResponseEntity<?> viewAllCertificates()
	{
	   
	        List<CertificateDetails> list = certificateservice.viewAllCertificates();
	        return ResponseEntity.ok().body(list);

	}
	
	
	
	@GetMapping("/expiringcertificates/{date}")
	public ResponseEntity<?> viewExpiringCertificates(@PathVariable String date)
	{
	    try
	    {
	        List<CertificateDetails> list = certificateservice.viewExpiringCertificates(date);

	        if(list != null && list.size() > 0)
	        {
	            return ResponseEntity.ok(list);
	        }
	        else
	        {
	            return ResponseEntity.noContent().build();
	        }
	    }
	    catch(Exception e)
	    {
	        return ResponseEntity.status(500).body("Error Fetching Data");
	    }
	}
	
	
	@Autowired
	private JavaMailSender mailSender;

	@PostMapping("/sendemail")
	public ResponseEntity<String> sendEmail(@RequestBody EmailDTO emaildto) {
	    try {
	        SimpleMailMessage mail = new SimpleMailMessage();
	        mail.setTo(emaildto.getReceiveremail());
	        mail.setSubject(emaildto.getSubject());
	        mail.setText(emaildto.getMessage());
	        mailSender.send(mail);
	        return ResponseEntity.ok("Email Sent Successfully");
	    } catch (Exception e) {
	        return ResponseEntity.status(500).body("Email Sending Failed");
	    }
	}

}
