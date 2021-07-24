package com.bulbul.erp.controller;


import com.bulbul.erp.repository.UserRepository;
import com.bulbul.erp.entity.User;
import com.bulbul.erp.error.UserNotFoundException;
import com.bulbul.erp.service.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/multimedia")
public class MultimediaController {


    @Autowired
    private StorageService storageService;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/profile/{id}")
    public ResponseEntity<?> uploadProfilePhoto(@PathVariable Long id, @RequestParam("file") MultipartFile file)
            throws UserNotFoundException {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()){
            User editEntity = user.get();

            String saveFileName="";
            String savePathFileName="";

            String fileName = file.getOriginalFilename();

            System.out.println("File Name "+fileName);
            if (fileName !=null){
                int index = fileName.lastIndexOf('.');
                System.out.println("=======After dot======== = "+index);
                if (index>0){
                    String extension = fileName.substring(index+ 1);
                    extension=extension.toLowerCase();
                    saveFileName=editEntity.getUsername() + "_img"+System.currentTimeMillis()+"." + extension;
                    savePathFileName="/multimedia/profile-pic/" + saveFileName;
                }
            }
            // store file in disk
            storageService.uploadFile(file,saveFileName);
            //save file path in db
            editEntity.setProfile(savePathFileName);
            return ResponseEntity.ok(savePathFileName);
        }else {
            throw new UserNotFoundException("User not Found !! ");
        }
    }

    //upload multiple file
    @PostMapping("/multiple")
    public ResponseEntity<?> uploadMultipleFiles(@RequestParam("files") MultipartFile[] files){

        Arrays.asList(files).stream().forEach(file -> {

            String saveFileName = "";
            String savePathFileName = "";
            String fileName = file.getOriginalFilename();
            System.out.println("File Name " + fileName);
            if (fileName != null) {
                int index = fileName.lastIndexOf('.');
                System.out.println("=======After dot======== = " + index);
                if (index > 0) {
                    String extension = fileName.substring(index + 1);
                    extension = extension.toLowerCase();
                    saveFileName = "unique" + "_img" + System.currentTimeMillis() + "." + extension;
                    savePathFileName = "/multimedia/multiple-pic/" + saveFileName;
                }
            }
            // store file in disk
            storageService.uploadFile(file, saveFileName);
    });
    return ResponseEntity.ok("");
}

}