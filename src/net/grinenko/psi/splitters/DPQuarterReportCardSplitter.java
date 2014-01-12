package net.grinenko.psi.splitters;

import java.io.IOException;

import com.itextpdf.text.pdf.PRTokeniser;

public class DPQuarterReportCardSplitter extends ReportCardSplitter {

	@Override
	public String ExtractStudentName(byte[] streamBytes) throws IOException {
		String result = null;
		boolean catchNext = false;
		PRTokeniser tokenizer = new PRTokeniser(streamBytes);
        
        while ( tokenizer.nextToken() ) {        	
            if ( tokenizer.getTokenType() != PRTokeniser.TokenType.STRING ) {
            	continue;
            }
            
        	String currentString = tokenizer.getStringValue();
        	
        	if ( catchNext ) {
        		result = currentString.trim();            		
        		break;
        	}
            
        	if ( currentString.startsWith("First Quarter Progress Report") || 
        		 currentString.startsWith("Third Quarter Progress Report") ) {
        		catchNext = true;
            } else if ( currentString.startsWith("Student Name:") ) {
        		result = currentString.replace("Student", "").replace("Name", "").replace(":", "").trim();
        		break;
        	}
        }

        if ( result != null && result.length() > 0 ) {
			return result;
		}
        
        return null;
	}

}
