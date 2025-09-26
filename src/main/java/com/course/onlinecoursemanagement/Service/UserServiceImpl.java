package com.course.onlinecoursemanagement.Service;

import com.course.onlinecoursemanagement.Exception.MessageException;
import com.course.onlinecoursemanagement.Model.User;
import com.course.onlinecoursemanagement.Repository.UserRepository;
import com.course.onlinecoursemanagement.Response.UserResponseDTO;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public UserResponseDTO getRegisterUser(User userDetails){
       User userFromDB  = userRepository.findByEmail(userDetails.getEmail());

       if(userFromDB != null){
           throw new MessageException("User Already Exist");
       }
       userRepository.save(userDetails);
       return modelMapper.map(userDetails, UserResponseDTO.class);
    }
}
