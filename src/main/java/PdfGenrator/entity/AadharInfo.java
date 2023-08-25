package PdfGenrator.entity;

import java.math.BigInteger;
import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AadharInfo {
	
	 private BigInteger addharNumber;
	 private String enrolmentNo;
	 private BigInteger VID;
	 private String name;
	 private String nameOdia;
	 private String address;
	 private LocalDate dateOfBirth; // Add date of birth field
	 private String gender;

}
