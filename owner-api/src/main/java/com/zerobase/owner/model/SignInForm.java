package com.zerobase.owner.model;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class SignInForm {
	private String name;
	private String password;
}
