package com.ashhar.blogappapis.payloads;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import com.ashhar.blogappapis.entities.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;


@NoArgsConstructor
@Getter
@Setter
public class UserDto {
    private UUID id;
    @NotEmpty
    @Size(min=3,message = "name must be of min 3 chars!!")
    private String name;
    @Email
    @NotEmpty
    private String email;
    @NotEmpty
    @Size(min=6,max = 12,message = "Password must contain min 6 chars and max 12 chars!!")
    private String password;
    @NotEmpty
    private String about;
    @NotEmpty
    @Size(min=10,max=10,message = "Please enter valid phone no!!")
    private String phone;
    @NotEmpty(message = "city shouldn't be blank!!")
    private String city;
    
    private Set<Role> roles=new HashSet<>();
    
    @JsonIgnore
    public String getPassword() {
    	return this.password;
    }
}
