package com.seguros.demo.model;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class SessionData {
	private String nuu;
	private String nuc;
	private List<ApplicationData> appData = new ArrayList<ApplicationData>();
}
