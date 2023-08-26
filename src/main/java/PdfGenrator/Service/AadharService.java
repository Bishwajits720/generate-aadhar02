package PdfGenrator.Service;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.PDPageContentStream.AppendMode;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.itextpdf.io.source.ByteArrayOutputStream;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;

import PdfGenrator.entity.AadharInfo;

@Service
public class AadharService {
	
	private final ResourceLoader resourceLoader;
	
	    @Value("${pdf.output.directory}")
    	private String pdfOutputDirectory;
	
//	    @Value("classpath:ODIA-BLANK.jpg")
//	    private Resource templateImage;
	    
	    
	    public AadharService(ResourceLoader resourceLoader) {
	        this.resourceLoader = resourceLoader;
	    }

	 public Resource  generateAadharPDF(AadharInfo aadharInfo ,MultipartFile photo,String filename) throws IOException {
	        // Load the template image from the resources folder
		 
		 
		 
		 Resource templateImageResource = resourceLoader.getResource("classpath:HINDI_NEW (1).jpg");
		 
		 //--File templateFile = templateImageResource.getFile();
	      
	     // Resource templateImageResource = resourceLoader.getResource("classpath:ODIA-BLANK.jpg");
	      InputStream templateImageInputStream = templateImageResource.getInputStream();
	      
	  
	      
	      
	 //       BufferedImage templateImage = ImageIO.read(templateFile);

	        // Create a new PDF document
	        
	     //   Translate translate = TranslateOptions.getDefaultInstance().getService();
	        
	        
	        int dpi = 72;
	        
	        float widthInInches =   660 / (float) dpi;
	        float heightInInches =    1600 / (float) dpi;
	        float widthInPoints = widthInInches * dpi;
	        float heightInPoints = heightInInches * dpi;
	      
	      
//	      int dpi = 72;
//	      float widthInCentimeters = 7;  // Desired width in centimeters
//	      float heightInCentimeters = 21; // Desired height in centimeters
//	      float widthInInches = widthInCentimeters / 2.54f; // Convert cm to inches
//	      float heightInInches = heightInCentimeters / 2.54f; // Convert cm to inches
//	      float widthInPoints = widthInInches * dpi;
//	      float heightInPoints = heightInInches * dpi;
	        
 
            
            try (PDDocument document = new PDDocument()) {
            	 PDRectangle customPageSize = new PDRectangle(widthInPoints, heightInPoints);
            	    PDPage page = new PDPage(customPageSize);
            	    document.addPage(page);

            	    PDImageXObject pdImage = PDImageXObject.createFromByteArray(document, IOUtils.toByteArray(templateImageInputStream), "template");   
               //-- PDImageXObject pdImage = PDImageXObject.createFromFileByContent(templateFile, document);
                try (PDPageContentStream contentStream = new PDPageContentStream(document, page, AppendMode.APPEND, true, true)) {
                    contentStream.drawImage(pdImage, 0, 0, widthInPoints, heightInPoints);
                    
                    String aadharNumber = aadharInfo.getAddharNumber().toString();
                    String formattedAadharNumber = aadharNumber.replaceAll("(\\d{4})(?=\\d)", "$1 ");

                    contentStream.beginText();
                    contentStream.setFont(PDType1Font.HELVETICA_BOLD, 32);
                    contentStream.newLineAtOffset(217, 57);
                    contentStream.showText(formattedAadharNumber);
                    contentStream.endText();
                    
                    
                    float xStart1 = 200;
                    float xEnd1 = 460;
                    float y1 = 53; // Adjust this value to position the line below the text

                    // Draw the line
                    contentStream.setLineWidth(1.0f); // Adjust line width as needed
                    contentStream.moveTo(xStart1, y1);
                    contentStream.lineTo(xEnd1, y1);
                    contentStream.stroke();
                    

                    
                    
//                    contentStream.beginText();
//                    contentStream.setFont(PDType1Font.HELVETICA_BOLD, 21);
//                    contentStream.newLineAtOffset(210, 47); // Adjust the coordinates as needed
//                    contentStream.showText("VID: " +aadharInfo.getVID() );
//                    contentStream.endText();

                    

                    contentStream.beginText();
                    contentStream.setFont(PDType1Font.HELVETICA_BOLD, 40);
                    contentStream.newLineAtOffset(173, 497);
                    contentStream.showText(formattedAadharNumber);
                    contentStream.endText();
                    
                    
                 // Calculate the line coordinates
                    float xStart = 160;
                    float xEnd = 480;
                    float y = 493; // Adjust this value to position the line below the text

                    // Draw the line
                    contentStream.setLineWidth(1.0f); // Adjust line width as needed
                    contentStream.moveTo(xStart, y);
                    contentStream.lineTo(xEnd, y);
                    contentStream.stroke();
                    
    

                    
                    String enrolmentNumber = aadharInfo.getEnrolmentNo();
                    String formattedEnrolmentNumber = enrolmentNumber.replaceAll("(\\d{4})(\\d{5})(\\d{5})", "$1/$2/$3");
           
                    contentStream.beginText();
                    contentStream.setFont(PDType1Font.HELVETICA, 22);
                    contentStream.newLineAtOffset(370, 1160); // Adjust the coordinates as needed
                    contentStream.showText(formattedEnrolmentNumber);
                    contentStream.endText();
                    
                    
//                    contentStream.beginText();
//                    contentStream.setFont(PDType1Font.HELVETICA_BOLD, 21);
//                    contentStream.newLineAtOffset(200, 485); // Adjust the coordinates as needed
//                    contentStream.showText("VID: " +aadharInfo.getVID() );
//                    contentStream.endText();
       
                    
                    
                    //odia :
                    
                    
      
                    InputStream odiaFontStream = getClass().getResourceAsStream("/NotoSansDevanagari_Condensed-Regular.TTF");
                    PDFont odiaFont = PDType0Font.load(document, odiaFontStream);
                    
                    String odiaText = "" + aadharInfo.getNameOdia(); // Replace with Odia text
                    contentStream.beginText();
                    contentStream.setFont(odiaFont, 22);
                    contentStream.newLineAtOffset(235, 300); // Adjust the coordinates
                    contentStream.showText(odiaText);
                    contentStream.endText();
                    

                    
                    contentStream.beginText();
                    contentStream.setFont(PDType1Font.HELVETICA, 22);
                    contentStream.newLineAtOffset(235, 275);
                    contentStream.showText("" + aadharInfo.getName());
                    contentStream.endText();
                    
                    
                    InputStream odiaFontStream1 = getClass().getResourceAsStream("/NotoSansDevanagari_Condensed-Regular.TTF");
                    PDFont odiaFont1 = PDType0Font.load(document, odiaFontStream1);
                    
                    
                    DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                    DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

                    String dob =  aadharInfo.getDateOfBirth().toString();
                    
                    LocalDate date = LocalDate.parse(dob, inputFormatter);
                    String formattedDob = "/DOB: " + date.format(outputFormatter);
                    
                    String odiaText1 = "जन्म की तारीख " +  formattedDob;  // aadharInfo.getDateOfBirth().toString();
                    
                    
                    contentStream.beginText();
                    contentStream.setFont(odiaFont1, 23);
                    contentStream.newLineAtOffset(235, 250);
                    contentStream.showText(odiaText1);
                    contentStream.endText();
                    
                    
                    
                    InputStream odiaFontStream2 = getClass().getResourceAsStream("/NotoSansDevanagari_Condensed-Regular.TTF");
                    PDFont odiaFont2 = PDType0Font.load(document, odiaFontStream2);
                    
                    
                    String genderText;
                    if ("Male".equals(aadharInfo.getGender())) {
                        genderText = "पुरुष / MALE";
                    } else  {
                        genderText = "महिला / FEMALE";
                    } 
                    
   
//                    String odiaText2 = "ପୁରୁଷ / Male"; 
//                    
//                    String odiatext2= "ମହିଳା /female";

               
                 contentStream.beginText();
                 contentStream.setFont(odiaFont2, 22);
                 contentStream.newLineAtOffset(235, 225);
                 contentStream.showText(genderText);
                 contentStream.endText();
                 
    
                 
                 String textToPrint = "To";

                 contentStream.beginText();
                 contentStream.setFont(PDType1Font.HELVETICA, 21);
                 contentStream.newLineAtOffset(120, 1106); // Replace x and y with the desired coordinates
                 contentStream.showText(textToPrint);
                 contentStream.endText();
                 
                 
                 
                 contentStream.beginText();
                 contentStream.setFont(odiaFont, 23);
                 contentStream.newLineAtOffset(120, 1079); // Adjust the coordinates
                 contentStream.showText(odiaText);
                 contentStream.endText();

                    // Split and format the address
                    String[] addressParts = aadharInfo.getAddress().split(",");
                    float yCoordinate = 1052;
                    for (String part : addressParts) {
                        contentStream.beginText();
                        contentStream.setFont(PDType1Font.HELVETICA, 21);
                        contentStream.newLineAtOffset(120, yCoordinate);
                        contentStream.showText(part.trim());
                        contentStream.endText();
                        yCoordinate -= 23;
                    }
                    
                    
                    // Insert uploaded photo
                    // Note: Replace these variables with appropriate values
                    int photoX = 73;
                    int photoY = 160;
                    int photoWidth = 137;
                    int photoHeight = 157;

                    // Load and insert the uploaded photo
                    PDImageXObject photoImage = PDImageXObject.createFromByteArray(document, photo.getBytes(), "photo");
                    contentStream.drawImage(photoImage, photoX, photoY, photoWidth, photoHeight);
                }
                
                
                // Generate and return the PDF as a Resource
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                document.save(byteArrayOutputStream);
                document.close();
                
                byte[] pdfBytes = byteArrayOutputStream.toByteArray();
              
                
                
//  --           // Save the PDF
//                File outputFile = new File(pdfOutputDirectory, filename);
//                FileUtils.writeByteArrayToFile(outputFile, pdfBytes);
//                
//                Resource pdfResource = new ByteArrayResource(pdfBytes);
//
//  --              return pdfResource;

                

                
                // Save the PDF
                File outputFile = new File(pdfOutputDirectory, filename);
                FileUtils.writeByteArrayToFile(outputFile, pdfBytes);

                Resource pdfResource = new FileSystemResource(outputFile);

                return pdfResource;
         
                
            } catch (IOException e) {
                // Handle the exception
                e.printStackTrace();
                throw e;
            }
            
	
            
 }      
            
       
	        

//                // Load the template image onto the PDF
//                PDImageXObject pdImage = PDImageXObject.createFromFileByContent(templateFile, document);
//                try (PDPageContentStream contentStream = new PDPageContentStream(document, page, AppendMode.APPEND, true, true)) {
//                    contentStream.drawImage(pdImage, 0, 0, widthInPoints, heightInPoints);
//
//                    contentStream.beginText();
//                    contentStream.setFont(PDType1Font.HELVETICA_BOLD, 45);  // Font for Aadhar Number
//                    contentStream.newLineAtOffset(180, 60);  // Coordinates for Aadhar Number
//                    contentStream.showText("" + aadharInfo.getAddharNumber());
//
//                    contentStream.setFont(PDType1Font.HELVETICA, 25);  // Font for Name
//                    contentStream.newLineAtOffset(65, 220);  // Coordinates for Name
//                    contentStream.showText("" + aadharInfo.getName());
//                    contentStream.endText();
//
//                    // Split and format the address
//                    String[] addressParts = aadharInfo.getAddress().split(",");
//                    float yCoordinate = 1050; // Initial Y-coordinate for the address
//                    int maxLineLength = 30;
//                    for (String part : addressParts) {
//                        String formattedPart = formatText(part.trim(), maxLineLength); // Use the formatText method
//                        contentStream.beginText();
//                        contentStream.setFont(PDType1Font.HELVETICA, 25); // Font for Address
//                        contentStream.newLineAtOffset(150, yCoordinate); // Coordinates for Address
//                        contentStream.showText(formattedPart); // Print each line of the address
//                        contentStream.endText();
//                        yCoordinate -= 40; // Move to the next line
//                    }
//
//                    // Save the PDF
//                    File outputFile = new File(pdfOutputDirectory, filename);
//                    document.save(outputFile);
//                } catch (IOException e) {
//                    // Handle the exception
//                    e.printStackTrace();
//                } finally {
//                    document.close(); // Close the document after all content is added
//                }  
//	        
//	 }
	        

	 private String formatText(String text, int maxLineLength) {
		    StringBuilder formattedText = new StringBuilder();
		    String[] words = text.split("\\s+");
		    int lineLength = 0;

		    for (String word : words) {
		        if (lineLength + word.length() + 1 <= maxLineLength) {
		            if (formattedText.length() > 0) {
		                formattedText.append(" ");
		                lineLength++;
		            }
		            formattedText.append(word);
		            lineLength += word.length();
		        } else {
		            formattedText.append("\n").append(word);
		            lineLength = word.length();
		        }
		    }

		    return formattedText.toString();
		}
	

	

//	  public void createPdf() {
//		  
//		  String filePath = "C:/Users/BISHWAJIT SAHU/Desktop/PDFs/pdfSampleFile.pdf";
//		  try {
//			PdfWriter writer = new PdfWriter(filePath);
//			
//			PdfDocument pdf =  new PdfDocument(writer);
//			
//			Document document = new Document(pdf);
//			
//			Paragraph p1 = new Paragraph("Welcome to user !! ");
//			
//			document.add(p1);
//			
//			document.close();
//			
//		} catch (FileNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		  
//		  
//	  }
	
}
