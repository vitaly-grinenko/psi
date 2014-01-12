package net.grinenko.psi.splitters;

import java.io.IOException;

import com.itextpdf.text.pdf.PRTokeniser;

public class PYPSemesterReportCardSplitter extends ReportCardSplitter {
	
	@Override
    public String ExtractStudentName(byte[] streamBytes) throws IOException {        
        PRTokeniser tokenizer = new PRTokeniser(streamBytes);
        boolean bCatchNext = false;
        
        while ( tokenizer.nextToken() ) {        	
            if ( tokenizer.getTokenType() == PRTokeniser.TokenType.STRING ) {
            	String currentString = tokenizer.getStringValue();
            	
            	if ( bCatchNext ) {
            		String tmpString = currentString.trim();
            		
            		if ( tmpString.length() > 0 ) {
            			return tmpString;
            		}
            	}
                
            	if ( currentString.startsWith("Homeroom Teacher") ) {
                	bCatchNext = true;
                }
            }
        }
        
        return null;
    }
    
}
