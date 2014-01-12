import java.io.File;

import net.grinenko.psi.Period;
import net.grinenko.psi.ReportType;
import net.grinenko.psi.splitters.ReportCardSplitter;
import net.grinenko.psi.splitters.exceptions.SplitterBadContextException;
import net.grinenko.psi.splitters.exceptions.SplitterCreateException;
import net.grinenko.psi.splitters.exceptions.SplitterProcessingException;
import net.grinenko.psi.splitters.SplitterFactory;


public class RCSplitter {
	
	public static final int ARGUMENTS_NUMBER = 3;
	
	public static final int ARGUMENT_NUMBER_REPORT_TYPE = 0;
	public static final int ARGUMENT_NUMBER_PERIOD = 1;
	public static final int ARGUMENT_NUMBER_FILENAME = 2;	
	
	public static final String MESSAGE_USAGE = "Usage: ReportCardSplitter {pyp|myp|dp} {quarter|semester} filename";
	
	public static final String MESSAGE_WRONG_REPORT_TYPE_ARGUMENT = "First argument should be \"pyp\", \"myp\" or \"dp\".";
	public static final String MESSAGE_WRONG_PERIOD_ARGUMENT = "Second argument should be either \"quarter\" or \"semester\".";
	public static final String MESSAGE_WRONG_FILENAME = "File should be a PDF file with \"" + 
			ReportCardSplitter.FILE_EXTENSION + "\" extension.";
	public static final String MESSAGE_FILE_DOESNT_EXIST = "File \"%s\" does not exist.";
	
	public static final String MESSAGE_PROCESSING = "Passing file to %s %s splitter:\n";
	public static final String MESSAGE_FAREWELL = "Bye!";
	
	public static final String MESSAGE_SPLITTER_NOT_AVAILABLE = "Cannot create a splitter for the given arguments.";
	public static final String MESSAGE_SPLITTER_PROCESSING_ERROR = "Error processing the given PDF file.";
	public static final String MESSAGE_ERROR_EXTRACTING_NAME = "Cannot extract student name at the first page.\n" +
			"Please make sure that the combination of the arguments is specified correctly for the given file.\n" + 
			MESSAGE_USAGE;

	
	
	public static void performSplitting(ReportType type, Period period, String fileName) {
		ReportCardSplitter splitter = null;
		
		try {
			splitter = SplitterFactory.createSplitter(type, period);
			splitter.split(fileName);
			
		} catch (SplitterCreateException e) {
			System.out.println(MESSAGE_SPLITTER_NOT_AVAILABLE);
			
		} catch (SplitterBadContextException e) {
			System.out.println(MESSAGE_ERROR_EXTRACTING_NAME);
			
		} catch (SplitterProcessingException e) {
			System.out.println(MESSAGE_SPLITTER_PROCESSING_ERROR);
		}
	}
	
	
	public static void main(String[] args) {
		if ( args.length != ARGUMENTS_NUMBER ) {
			System.out.println(MESSAGE_USAGE);
			return;
		}
		
		ReportType argType = ReportType.checkValueOf(args[ARGUMENT_NUMBER_REPORT_TYPE]);		
		
		if ( argType == null ) {
			System.out.println(MESSAGE_WRONG_REPORT_TYPE_ARGUMENT);
			return;
		}
		
		Period argPeriod = Period.checkValueOf(args[ARGUMENT_NUMBER_PERIOD]);
		
		if ( argPeriod == null) {
			System.out.println(MESSAGE_WRONG_PERIOD_ARGUMENT);
			return;
		}
		
		String argFilename = args[ARGUMENT_NUMBER_FILENAME];
		
		if ( !argFilename.toLowerCase().endsWith(ReportCardSplitter.FILE_EXTENSION) ) {
			System.out.println(MESSAGE_WRONG_FILENAME);
			return;
		}
		
		if ( !(new File(argFilename)).isFile() ) {
			System.out.println(String.format(MESSAGE_FILE_DOESNT_EXIST, argFilename));
			return;
		}
		
		System.out.println(String.format(MESSAGE_PROCESSING, argType, argPeriod));
		
		performSplitting(argType, argPeriod, argFilename);
		
		System.out.println(MESSAGE_FAREWELL);
	}

}
