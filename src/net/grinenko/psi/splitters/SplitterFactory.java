package net.grinenko.psi.splitters;

import net.grinenko.psi.Period;
import net.grinenko.psi.ReportType;
import net.grinenko.psi.splitters.exceptions.SplitterCreateException;

public class SplitterFactory {

	public static ReportCardSplitter createSplitter(ReportType reportType, Period period) throws SplitterCreateException {
		ReportCardSplitter splitter = null;
		
		if ( reportType == ReportType.PYP && period == Period.SEMESTER ) {
			splitter = new PYPSemesterReportCardSplitter();
		} else if ( reportType == ReportType.MYP && period == Period.SEMESTER ) {
			splitter = new MYPSemesterReportCardSplitter();
		} else if ( reportType == ReportType.MYP && period == Period.QUARTER ) {
			splitter = new MYPQuarterReportCardSplitter();
		} else if ( reportType == ReportType.DP && period == Period.SEMESTER ) {
			splitter = new DPSemesterReportCardSplitter();
		} else if ( reportType == ReportType.DP && period == Period.QUARTER ) {
			splitter = new DPQuarterReportCardSplitter();
		} else throw new SplitterCreateException();
		
		return splitter;
	}

}
