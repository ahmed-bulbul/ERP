package com.bulbul.erp.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface StorageService {


    public void uploadFile(MultipartFile file, String fileNewName);

    /** Multiple File upload
     * this section include the multiple file uploading.....
     * */
    public void uploadMultipleFile(MultipartFile file, List<String> fileNewName);


}