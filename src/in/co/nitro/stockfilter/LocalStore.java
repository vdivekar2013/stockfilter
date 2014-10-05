package in.co.nitro.stockfilter;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.channels.FileChannel;
import java.util.List;

import android.content.Context;

public class LocalStore {
	private Context context = null;

	public LocalStore(Context context) {
		this.context = context;
	}

	public String getApplicationFolderName() {
		return context.getFilesDir().getAbsolutePath();
	}

	public File createDirectory(String dirName) {
		String dirPath = context.getFilesDir().getAbsolutePath() + File.separator + dirName;
		File projDir = new File(dirPath);
		if (!projDir.exists())
			projDir.mkdirs();
		return projDir;
	}

	public void getFileList(boolean depth, File root, List<String> fileList) {
		File[] fileArray = null == root ? context.getFilesDir().listFiles() : root.listFiles();
		for(File file :fileArray) {
			fileList.add(file.getAbsolutePath());
			if(depth && file.isDirectory())
				getFileList(depth,file,fileList);
		}
	}
	
	public File createFile(String dirName, String fileName) throws IOException {
		String dirPath = null == dirName ? context.getFilesDir().getAbsolutePath() + File.separator + fileName :
								 dirName + File.separator + fileName;
		File file = new File(dirPath);
		if(!file.exists())
			file.createNewFile();
		return file;
	}
	
	public void removeAllFiles(File root) {
		File[] fileArray = null == root ? context.getFilesDir().listFiles() : root.listFiles();
		for(File file :fileArray) {
			if(file.isDirectory())
				removeAllFiles(file);
			file.delete();
		}
	}
	
	public void copy(File src, File dst) throws IOException {
	    FileInputStream inStream = new FileInputStream(src);
	    FileOutputStream outStream = new FileOutputStream(dst);
	    FileChannel inChannel = inStream.getChannel();
	    FileChannel outChannel = outStream.getChannel();
	    inChannel.transferTo(0, inChannel.size(), outChannel);
	    inStream.close();
	    outStream.close();
	}
	
	public void copy(InputStream inputStream, File dst) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
	    FileOutputStream outStream = new FileOutputStream(dst);
	    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outStream));
	    // Transfer bytes from in to out
	    char[] buf = new char[1024];
	    int len;
	    while ((len = reader.read(buf,0,1024)) > 0) {
	    	writer.write(buf, 0, len);
	    }
	    inputStream.close();
	    outStream.close();
	}
}
