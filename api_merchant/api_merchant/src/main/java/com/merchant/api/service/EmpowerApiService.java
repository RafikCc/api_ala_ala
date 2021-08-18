package com.merchant.api.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Service
@NoArgsConstructor
public class EmpowerApiService {
	
	@Value("${api.empower.login}")
	@Setter(AccessLevel.NONE)
    @Getter(AccessLevel.NONE)
	private String empowerLogin;
	
	public String getEmpowerToken(String username, String password) {
		RestTemplate restTemplate = new RestTemplate();
		
		return null;
	}
}
