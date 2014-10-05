package in.co.nitro.stockfilter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Formatter;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import android.app.Activity;

public class InstrumentReader {
	private final static int MAX_HOLIDAYS_IN_ROW = 10; // Number of days to go back for file
	private final static String FILE_PREFIX = "EQ";
	private final static String FILE_POSTFIX = "_CSV.ZIP";
	private final static String REMOTE_PATH = "http://www.bseindia.com/download/BhavCopy/Equity/";
	private final static String LOCAL_PATH = "data/data/in.co.nitro.stockfilter/files/";

	private static List<Instrument> readRemote(Calendar cal)  throws Exception {
		URL url = new URL(getFullPath(cal,Location.REMOTE));
		ZipInputStream zipStream = new ZipInputStream(url.openStream());
		ZipEntry zipEntry = zipStream.getNextEntry();
		if(zipEntry == null) {
			zipStream.close();
			throw new Exception("File not found");
		}
		List<Instrument> instrumentList = null;
		if(null != zipEntry) 
			instrumentList = readStream(zipStream);
		return instrumentList;
	}

	private static List<Instrument> readLocal(Calendar cal)  throws Exception {
		FileInputStream fileStream = new FileInputStream(getFullPath(cal,Location.LOCAL));
		ZipInputStream zipStream = new ZipInputStream(fileStream);
		ZipEntry zipEntry = zipStream.getNextEntry();
		if(zipEntry == null) {
			zipStream.close();
			throw new Exception("File not found");
		}
		List<Instrument> instrumentList = null;
		if(null != zipEntry) {
			instrumentList = readStream(zipStream);
		}
		return instrumentList;
	}

	private static List<Instrument> readStream(InputStream inputStream)  throws Exception {
		List<Instrument> instrumentList = new ArrayList<Instrument>();
		BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));
		String inputLine;
		boolean isFirstRow = true;
		while ((inputLine = in.readLine()) != null) {
			if(isFirstRow) {
				isFirstRow = false;
				continue;
			}
			String fields[] = inputLine.split(",");
			Instrument instrument = new Instrument();
			instrument.setCode(fields[0]);
			instrument.setName(fields[1]);
			instrument.setGroup(fields[2].charAt(0));
			instrument.setType(fields[3].charAt(0));
			instrument.setOpen(Float.parseFloat(fields[4]));
			instrument.setHigh(Float.parseFloat(fields[5]));
			instrument.setLow(Float.parseFloat(fields[6]));
			instrument.setClose(Float.parseFloat(fields[7]));
			instrument.setLastTrade(Float.parseFloat(fields[8]));
			instrument.setPreviousclose(Float.parseFloat(fields[9]));
			instrument.setNumberTrades(Long.parseLong(fields[10]));
			instrument.setNumberShares(Long.parseLong(fields[11]));
			instrument.setTurnover(Float.parseFloat(fields[12]));
			instrumentList.add(instrument);
		}
		in.close();
		return instrumentList;
	}

	private static String getFileName(Calendar cal) {
		StringBuilder fileName = new StringBuilder(FILE_PREFIX);
		Formatter formatter = new Formatter();
		fileName.append(formatter.format("%02d%02d%02d", cal.get(Calendar.DAY_OF_MONTH),cal.get(Calendar.MONTH)+1,cal.get(Calendar.YEAR)-2000));
		formatter.close();
		fileName.append(FILE_POSTFIX);
		return fileName.toString();
	}

	private static String getFullPath(Calendar cal,Location location) {
		StringBuilder path = new StringBuilder(location.equals(Location.LOCAL) ? LOCAL_PATH : REMOTE_PATH);
		path.append(getFileName(cal));
		return path.toString();
	}

	public static List<Instrument> read(Activity activity) throws Exception{
		Calendar cal = Calendar.getInstance();
		List<Instrument> instrumentList = null;
		int numberOfTries = 0;
		while(numberOfTries < MAX_HOLIDAYS_IN_ROW) {
			try {
				instrumentList = readLocal(cal);
				break;
			} catch(Exception ex1) {
				try {
					instrumentList = readRemote(cal);
					copyRemoteToLocal(activity,cal);
					break;
				} catch(Exception ex2) {
					cal.add(Calendar.DAY_OF_YEAR, -1);
				}
			}
			numberOfTries++;
		}
		if(numberOfTries == MAX_HOLIDAYS_IN_ROW)
			throw new Exception("Error reading Bhavcopy");
		return instrumentList;
	}

	private static void copyRemoteToLocal(Activity activity, Calendar cal) throws Exception {
		URL url = new URL(getFullPath(cal,Location.REMOTE));
		InputStream inputStream = url.openStream();
		LocalStore localStore = new LocalStore(activity);
		localStore.removeAllFiles(null);
		File outputFile = localStore.createFile(LOCAL_PATH, getFileName(cal));
		localStore.copy(inputStream, outputFile);
	}

	public static void main(String[] args) {
		try {
			List<Instrument> instrumentList = read(null);
			System.out.println("Number of instruments = " + instrumentList.size());
			List<Instrument> filteredInstrumentList = InstrumentFilter.filterOnName(instrumentList, "*");
			filteredInstrumentList = InstrumentFilter.filterOnLastTrade(filteredInstrumentList, '>', 5000.0F);
			for(Instrument instrument : filteredInstrumentList)
				System.out.println("Instrument name is " + instrument.getName() + " Last Traded at = " + instrument.getLastTrade());
			System.out.println("Number of filtered instruments = " + filteredInstrumentList.size());
		} catch(Exception ex) {
			ex.printStackTrace();
		}
	}

	private enum Location {
		LOCAL,
		REMOTE
	}
}
	
