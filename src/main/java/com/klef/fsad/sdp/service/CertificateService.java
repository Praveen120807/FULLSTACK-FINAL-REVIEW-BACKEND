package com.klef.fsad.sdp.service;

import java.util.List;
import com.klef.fsad.sdp.entity.CertificateDetails;

public interface CertificateService 
{
    String addCertificate(CertificateDetails cert);

    List<CertificateDetails> viewCertificatesByUser(int userid);

    String updateCertificateByName(CertificateDetails cert);

    String deleteByCertNameAndUserId(String certName, int userid);

    List<CertificateDetails> viewAllCertificates();

    List<CertificateDetails> viewExpiringCertificates(String date);

    CertificateDetails getCertificateById(int id);   
}