package com.ashhar.blogappapis.payloads;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserUpdateDto {
	
	 @Size(min=3,message = "name must be of min 3 chars!!")
	 private String name;
	 
	 @Email
	 private String email;
	 
	 @Size(min=6,max = 12,message = "Password must contain min 6 chars and max 12 chars!!")
	 private String password;
	 
	 private String about;
	 
	 @Size(min=10,max=10,message = "Please enter valid phone no!!")
	 private String phone;
	 
	 private String city;
	 
}
