package com.zerobase.customer.model;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class SignInForm {
	private String name;
	private String phone;
	private String password;
}
