package net.grinenko.psi.splitters;

import java.io.IOException;

import com.itextpdf.text.pdf.PRTokeniser;

public class DPSemesterReportCardSplitter extends ReportCardSplitter {

	@Override
	public String ExtractStudentName(byte[] streamBytes) throws IOException {
		PRTokeniser tokenizer = new PRTokeniser(streamBytes);
		
        while ( tokenizer.nextToken() ) {        	
            if ( tokenizer.getTokenType() == PRTokeniser.TokenType.STRING ) {
            	String currentString = tokenizer.getStringValue();
            	//StringBuilder currentString = new StringBuilder(tokenizer.getStringValue()); <--- use SB ?!?!
            	
            	if ( currentString.startsWith("Student Name:") ) {
            		String tmpString = currentString.replace("Student", "").replace("Name", "").replace(":", "").trim();
            		
            		if ( tmpString.length() > 0 ) {
            			return tmpString;
            		}
            	}         
            }
        }
        
        return null;
	}

}
