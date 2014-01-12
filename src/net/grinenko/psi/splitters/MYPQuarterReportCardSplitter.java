package net.grinenko.psi.splitters;

import java.io.IOException;

import com.itextpdf.text.pdf.PRTokeniser;

public class MYPQuarterReportCardSplitter extends ReportCardSplitter {

	@Override
	public String ExtractStudentName(byte[] streamBytes) throws IOException {
		String result = null;
		String previousString = "";
		PRTokeniser tokenizer = new PRTokeniser(streamBytes);
		
        while ( tokenizer.nextToken() ) {        	
            if ( tokenizer.getTokenType() != PRTokeniser.TokenType.STRING ) {
            	continue;
            }
            
        	String currentString = tokenizer.getStringValue();
        	
        	if ( currentString.startsWith("Student Name :") ) {
        		result = previousString.trim();
        		break;
        	} else if ( currentString.startsWith("Student:") ) {
            	result = currentString.replace("Student", "").replace(":", "").trim();
        		break;
            }
            
            previousString = currentString;                            
        }
        
        if ( result != null && result.length() > 0 ) {
			return result;
		}
        
        return null;
	}

}
