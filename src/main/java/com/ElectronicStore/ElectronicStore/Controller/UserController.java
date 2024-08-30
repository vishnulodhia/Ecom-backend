package com.ElectronicStore.ElectronicStore.Controller;


import com.ElectronicStore.ElectronicStore.DTO.APiResponseMessage;
import com.ElectronicStore.ElectronicStore.DTO.PageableResponse;
import com.ElectronicStore.ElectronicStore.DTO.UserDto;
import com.ElectronicStore.ElectronicStore.Exception.GlobalExceptionHandler;
import com.ElectronicStore.ElectronicStore.Service.FileServiceImpl;
import com.ElectronicStore.ElectronicStore.Service.Interface.FileService;
import com.ElectronicStore.ElectronicStore.Service.Interface.UserService;
import jakarta.validation.Valid;
import org.aspectj.lang.annotation.DeclareWarning;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RequestMapping("/user")
@RestController
public class UserController {


    @Autowired
    private UserService userService;

   @Autowired
   private FileService fileService;




    private Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);


    @GetMapping("/findAllUser")
    public ResponseEntity<PageableResponse<UserDto>> findAllUser(@RequestParam(value = "PageNumber" , defaultValue = "0" , required = false) int PageNumber, @RequestParam(value = "PageSize" , defaultValue = "10" , required = false) int PageSize, @RequestParam(value = "SortBy" , defaultValue = "name" , required = false) String SortBy, @RequestParam(value = "SortDir" , defaultValue = "asc" , required = false) String SortDir ){
       System.out.println("PageNumber " + PageNumber+"PageSize"+PageSize);
        return new ResponseEntity<>(userService.getAllUser(PageNumber,PageSize,SortBy,SortDir),HttpStatus.OK);
    }


    @PostMapping("/createUser")
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto){
         UserDto user = userService.createUser(userDto);
         return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @PutMapping("/updateUser/{id}")
    public ResponseEntity<UserDto> updateUser(@PathVariable long id,@Valid @RequestBody UserDto userDto){

        try{
            UserDto user = userService.updateUser(userDto,id);
            return new ResponseEntity<>(user, HttpStatus.OK);
        }
        catch(Exception e){
            logger.error(e.toString());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping("/SearchUser/{username}")
    public ResponseEntity<List<UserDto>> SearchUser(@PathVariable String username){

        try{
            List<UserDto> users = userService.searchUser(username);
            return new ResponseEntity<>(users, HttpStatus.OK);
        }
        catch (Exception e){
            logger.error(e.toString());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/SearchUserByEmail/{email}")
    public ResponseEntity<UserDto> SearchUserByEmail(@PathVariable String email){

            UserDto users = userService.getUserByEmail(email);
            return new ResponseEntity<>(users, HttpStatus.OK);

    }

    @DeleteMapping("/deleteUser/{id}")
    public ResponseEntity<APiResponseMessage> deleteUser(@PathVariable long id){

        try{
              userService.deleteUser(id);
            APiResponseMessage message = APiResponseMessage.builder().message("User deleted successfully").status(HttpStatus.OK).success(true).build();
            return new ResponseEntity<>(message,HttpStatus.OK);
        }
        catch (Exception e){
            APiResponseMessage message = APiResponseMessage.builder().message("User not deleted").status(HttpStatus.INTERNAL_SERVER_ERROR).success(false).build();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

//    @PostMapping("/uploadfile")
//    public ResponseEntity<String> testUpload(@RequestParam("file")MultipartFile file) throws IOException {
//
//
//       String originalFilename = file.getOriginalFilename();
//       logger.info("Filename:{}",originalFilename);
//       String filename  = UUID.randomUUID().toString();
//       String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
//       String fileNameWithExtension= filename+extension; // store to database
//      return new ResponseEntity<>(fileService.uploadFileFirebase(file,fileNameWithExtension,"Testimg"), HttpStatus.OK);
//        // url store to database
//    }


//    @PostMapping("/updatefile")
//    public ResponseEntity<String> testUpdate(@RequestParam("file")MultipartFile file) throws IOException {
//
//
//        String originalFilename = file.getOriginalFilename();
//        logger.info("Filename:{}",originalFilename);
//        String filename  = UUID.randomUUID().toString();
//        String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
//        String fileNameWithExtension= filename+extension; // store to database
////        return new ResponseEntity<>(fileService.updateFileFirebase(file,fileNameWithExtension,"Testimg","https://firebasestorage.googleapis.com/v0/b/electronic-sto.appspot.com/o/Testimg20ed6935-7146-474e-ba6c-ae50f462ce19.jpeg?alt=media"), HttpStatus.OK);
//        // url store to database
//    }








}
