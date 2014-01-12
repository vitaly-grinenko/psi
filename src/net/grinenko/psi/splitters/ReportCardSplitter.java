package net.grinenko.psi.splitters;

import java.io.FileOutputStream;
import java.io.IOException;

import net.grinenko.psi.splitters.exceptions.SplitterBadContextException;
import net.grinenko.psi.splitters.exceptions.SplitterIOException;
import net.grinenko.psi.splitters.exceptions.SplitterProcessingException;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfCopy;
import com.itextpdf.text.pdf.PdfImportedPage;
import com.itextpdf.text.pdf.PdfReader;

public abstract class ReportCardSplitter {
	public static final String FILE_EXTENSION = ".pdf";
	
	public static final String MESSAGE_DONE = "Done";
	public static final String MESSAGE_TOTAL_PAGES = "There are %s pages in total.";
	public static final String MESSAGE_NEW_STUDENT_FOUND = "\nNew student name  [%s] found at page [%d]\nThe previous one is [%s]";
	public static final String MESSAGE_DOCUMENT_CLOSING = "Saving and closing the output document [%s]... ";
	public static final String MESSAGE_DOCUMENT_CREATING = "Creating document [%s]... ";
	public static final String MESSAGE_PAGE_ADDING = "Adding page %d... ";
	public static final String MESSAGE_COMPLETED_SUCCESSFULLY = "\nReport cards splitting completed successfully!\n";
	
	
	public abstract String ExtractStudentName(byte[] streamBytes) throws IOException;
	
	
	public void split(String inFileName) throws SplitterProcessingException {
		PdfReader reader = null;
		Document document = null;
        PdfCopy writer = null;
        String outFileName = null;
        
    	try {	
    		reader = new PdfReader(inFileName);
    		int totalPages = reader.getNumberOfPages();
    		
            System.out.println(String.format(MESSAGE_TOTAL_PAGES, totalPages));
            
            int offset = 0;
            String currentStudentName = null;
            
            for ( int pageNumber = 1; pageNumber <= totalPages; pageNumber++ ) {        	    
            	String studentName = ExtractStudentName(reader.getPageContent(pageNumber));
            	
            	if ( pageNumber == 1 && studentName == null ) {
            		//The first page should contain student name. Or maybe wrong period/type was given.
            		throw new SplitterBadContextException();
            	}
            	
            	if ( studentName != null && !studentName.equalsIgnoreCase(currentStudentName) ) {
            		System.out.println(String.format(MESSAGE_NEW_STUDENT_FOUND, studentName, pageNumber, currentStudentName));
            		
            		currentStudentName = studentName;
            		
            		if ( offset > 0 ) {
            			System.out.print(String.format(MESSAGE_DOCUMENT_CLOSING, outFileName));
                    	
            			offset = 0;
            			document.close();
                    	writer.close();
                    	
                    	System.out.println(MESSAGE_DONE);
            		}
            		
            		if ( offset == 0 ) {
            			outFileName = currentStudentName + FILE_EXTENSION;
            			
            			System.out.print(String.format(MESSAGE_DOCUMENT_CREATING, outFileName));
            			
            			document = new Document(reader.getPageSizeWithRotation(1));
            			writer = new PdfCopy(document, new FileOutputStream(outFileName));
            			document.open();
            			
            			System.out.println(MESSAGE_DONE);
            		}
            		
            	}
            	
            	offset += 1;
            	
            	System.out.print(String.format(MESSAGE_PAGE_ADDING, offset));
            	
            	PdfImportedPage page = writer.getImportedPage(reader, pageNumber);
    			writer.addPage(page);
    			
    			System.out.println(MESSAGE_DONE);
            }
            
    	} catch (IOException e) {
    		throw new SplitterIOException();
    		
    	} catch (DocumentException e) {
    		throw new SplitterProcessingException();
    		
    	} catch (SplitterBadContextException e) {
    		throw e;
    		
		} finally {
    		if ( document != null && document.isOpen()) {
    			System.out.print(String.format(MESSAGE_DOCUMENT_CLOSING, outFileName));
    			
    			document.close();
    			
    			System.out.println(MESSAGE_DONE);
    		}
    		if ( writer != null ) {  //Check this out
            	writer.close();
            }
    		if ( reader != null) {
    			reader.close();
            }
    	}
    	
    	System.out.println(MESSAGE_COMPLETED_SUCCESSFULLY);
    }
	
}
