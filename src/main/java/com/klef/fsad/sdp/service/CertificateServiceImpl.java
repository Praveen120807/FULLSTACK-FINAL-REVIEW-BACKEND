package com.klef.fsad.sdp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.klef.fsad.sdp.entity.CertificateDetails;
import com.klef.fsad.sdp.entity.User;
import com.klef.fsad.sdp.repository.CertificateRepository;
import com.klef.fsad.sdp.repository.UserRepository;

@Service
public class CertificateServiceImpl implements CertificateService
{
    @Autowired
    private CertificateRepository certificateRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public String addCertificate(CertificateDetails cert) 
    {
        certificateRepository.save(cert);
        return "Certificate Added Successfully";
    }

    @Override
    public List<CertificateDetails> viewCertificatesByUser(int userid) 
    {
        User user = userRepository.findById(userid).orElse(null);

        if(user != null)
        {
            return certificateRepository.findByUser(user);
        }
        return null;
    }

    @Override
    public String updateCertificateByName(CertificateDetails cert) 
    {
        certificateRepository.save(cert);
        return "Certificate Updated Successfully";
    }

    @Override
    public String deleteByCertNameAndUserId(String certName, int userid) 
    {
        User user = userRepository.findById(userid).orElse(null);

        if(user != null)
        {
            List<CertificateDetails> list = certificateRepository.findByUser(user);

            for(CertificateDetails c : list)
            {
                if(c.getCertName().equals(certName))
                {
                    certificateRepository.delete(c);
                    return "Certificate Deleted Successfully";
                }
            }
        }
        return "Certificate Not Found";
    }

    @Override
    public List<CertificateDetails> viewAllCertificates() 
    {
        return certificateRepository.findAll();
    }

    @Override
    public List<CertificateDetails> viewExpiringCertificates(String date) 
    {
        return certificateRepository.findByExpiryDate(date);
    }

    @Override
    public CertificateDetails getCertificateById(int id) 
    {
        return certificateRepository.findById(id).orElse(null);
    }
}