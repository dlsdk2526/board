package org.ina.common.page;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Getter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PageDTO {

	private int page = 1;
	private int perSheet = 10;
	
	private String keyword,type;
	
	public int getSkip() { 
		return (page -1) * perSheet; 

	}
	
	public String[] getArr(){

		if(keyword == null || keyword.trim().length() == 0 ) {
			return null;
		}
		if(type == null) {
			return null;
		}
		return type.split("");
	}


	
}
