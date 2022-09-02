package com.justclick.authentication.models;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ResponseData<T> {
	private int code;
	private String message;
	private T data;
	private Page page;
	private List<String> errors = new ArrayList<>();
}
