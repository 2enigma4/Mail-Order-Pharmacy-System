package com.cognizant.auth.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.cognizant.auth.dao.UserDAO;
import com.cognizant.auth.entity.UserData;

@Service
public class CustomerDetailsService implements UserDetailsService {
	@Autowired
	private UserDAO userdao;

	/**
	 * @param String
	 * @return User 
	 * @throws UsernameNotFoundException
	 */
	@Override
	public UserDetails loadUserByUsername(String uid) {
		
		try
		{
			UserData custuser = userdao.findById(uid).orElse(null);//NOSONAR
			if(custuser != null) {
				custuser.getUserid();
			}
			return new User(custuser.getUserid(), custuser.getUpassword(), new ArrayList<>());//NOSONAR
		}
		catch (Exception e) {
			throw new UsernameNotFoundException("UsernameNotFoundException");
		}
			
		
		
	}

}

