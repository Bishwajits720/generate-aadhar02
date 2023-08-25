package PdfGenrator.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import PdfGenrator.Service.AadharService;
import PdfGenrator.entity.AadharInfo;

@Controller
public class AadharController {
	
	
    @Autowired
    private AadharService aadharService;

    public AadharController(AadharService aadharService) {
		super();
		this.aadharService = aadharService;
	}

	@GetMapping("/generate-aadhar")
    public String generateAadharForm(Model model) {
        model.addAttribute("aadharInfo", new AadharInfo());
        return "generate-aadhar";
    }

//    @PostMapping("/generate-aadhar")
//    public String generateAadhar(@ModelAttribute AadharInfo aadharInfo,
//    		@RequestParam("photo") MultipartFile photo) {
//        // Generate PDF using AadharService
//        try {
//        	 String filename = aadharInfo.getName() + "-" + aadharInfo.getAddharNumber() + ".pdf";
//			aadharService.generateAadharPDF(aadharInfo,photo,filename);
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//        return "redirect:/generate-success";
//    }
	
	
	
	@PostMapping("/generate-aadhar")
	public ResponseEntity<Resource> generateAadhar(@ModelAttribute AadharInfo aadharInfo,
	        @RequestParam("photo") MultipartFile photo) {
	    // Generate PDF using AadharService
	    try {
	    	
	        String filename = aadharInfo.getName() + "-" + aadharInfo.getAddharNumber() + ".pdf";
	        Resource pdfResource = aadharService.generateAadharPDF(aadharInfo, photo, filename);

	        HttpHeaders headers = new HttpHeaders();
	        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename);

	        String resetScript = "<script>" +
                    "resetFormAndShowSuccess();" +
                    "</script>";
	        
	        return ResponseEntity.ok()
	                .headers(headers)
	                .contentType(MediaType.APPLICATION_PDF)
	                .body(pdfResource);
	    
	    	
	        
	    } catch (IOException e) {
	        // Handle the exception
	        e.printStackTrace();
	        // You might want to return an error page or response here
	        return ResponseEntity.badRequest().build();
	    }
	}
	

    
    @GetMapping("/generate-success")
    public String generateSuccess() {
        return "generate-success";
    }
	
	
//	public GetPdfController(CreatePdfFileService createPdfFileService) {
//		super();
//		this.createPdfFileService = createPdfFileService;
//	}
//
//
//	@Autowired
//	private CreatePdfFileService createPdfFileService;
//	
//	    
//	@GetMapping("/pdfFile")
//	    public String getPdf() {
//	    	
//	    	return "getPdfFile";
//	    }
//	
//	
//	@GetMapping("/createPdf")
//	public String pdfFile() {
//		
//		createPdfFileService.createPdf();
//		return "redirect:/pdfFile";
//		
//	}
	

}
