package com.klef.fsad.sdp.controller;

import java.sql.Blob;
import java.util.List;

import javax.sql.rowset.serial.SerialBlob;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import com.klef.fsad.sdp.entity.CertificateDetails;
import com.klef.fsad.sdp.entity.User;
import com.klef.fsad.sdp.service.CertificateService;

@RestController
@RequestMapping("cert")
@CrossOrigin("*")
public class CertificateController 
{
    @Autowired
    private CertificateService certificateService;

    
    @PostMapping("/add")
    public ResponseEntity<String> addCertificate(@RequestBody CertificateDetails cert)
    {
        try
        {
            String output = certificateService.addCertificate(cert);
            return ResponseEntity.status(201).body(output);
        }
        catch(Exception e)
        {
            return ResponseEntity.status(500).body("Internal Server Error");
        }
    }

    
    @GetMapping("/viewcertsbyuser/{userid}")
    public ResponseEntity<?> viewCertificates(@PathVariable int userid)
    {
        try
        {
            List<CertificateDetails> list = certificateService.viewCertificatesByUser(userid);

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
            return ResponseEntity.status(500).body("Error Fetching Certificates");
        }
    }

    
    @PutMapping("/updatecertificate")
    public ResponseEntity<String> updateCertificate(
            @RequestParam int id,
            @RequestParam String certName,
            @RequestParam String orgName,
            @RequestParam String issueDate,
            @RequestParam String expiryDate,
            @RequestParam int userid,
            @RequestParam(value = "file", required = false) MultipartFile file)
    {
        try 
        {
           
            CertificateDetails cert = certificateService.getCertificateById(id);

         
            if(cert == null)
            {
                return ResponseEntity.status(404).body("Certificate not found");
            }

 
            cert.setCertName(certName);
            cert.setOrgName(orgName);
            cert.setIssueDate(issueDate);
            cert.setExpiryDate(expiryDate);

           
            if(file != null && !file.isEmpty())
            {
                byte[] bytes = file.getBytes();
                Blob blob = new SerialBlob(bytes);

                cert.setFileName(file.getOriginalFilename());
                cert.setFileData(blob);
            }

            
            certificateService.addCertificate(cert);

            return ResponseEntity.ok("Certificate Updated Successfully");
        } 
        catch (Exception e) 
        {
            return ResponseEntity.status(500).body("Error: " + e.getMessage());
        }
    }
   
    @DeleteMapping("/delete/{certName}/{userid}")
    public ResponseEntity<String> deleteCertificate(
            @PathVariable String certName,
            @PathVariable int userid)
    {
        try
        {
            String output = certificateService.deleteByCertNameAndUserId(certName, userid);
            return ResponseEntity.ok(output);
        }
        catch(Exception e)
        {
            return ResponseEntity.status(500).body("Error Deleting Certificate");
        }
    }

    // ---------------- FILE UPLOAD ----------------
    @PostMapping("/addcertificate")
    public ResponseEntity<String> addCertificateWithFile(
            @RequestParam String certName,
            @RequestParam String orgName,
            @RequestParam String issueDate,
            @RequestParam String expiryDate,
            @RequestParam int userid,
            @RequestParam("file") MultipartFile file)
    {
        try 
        {
            byte[] bytes = file.getBytes();
            Blob blob = new SerialBlob(bytes);

            CertificateDetails cert = new CertificateDetails();
            cert.setCertName(certName);
            cert.setOrgName(orgName);
            cert.setIssueDate(issueDate);
            cert.setExpiryDate(expiryDate);
            cert.setFileName(file.getOriginalFilename());
            cert.setFileData(blob);

            User user = new User();
            user.setId(userid);
            cert.setUser(user);

            // ✅ FIXED HERE
            String output = certificateService.addCertificate(cert);

            return ResponseEntity.ok(output);
        } 
        catch (Exception e) 
        {
            return ResponseEntity.status(500).body("Error: " + e.getMessage());
        }
    }

    
    @GetMapping("/displaycertificate")
    public ResponseEntity<byte[]> displayCertificate(@RequestParam int id) throws Exception
    {
       
        CertificateDetails cert = certificateService.getCertificateById(id);

        byte[] fileBytes = cert.getFileData().getBytes(1, (int) cert.getFileData().length());

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .body(fileBytes);
    }
}