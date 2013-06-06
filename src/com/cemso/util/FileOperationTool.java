package com.cemso.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipOutputStream;

import com.cemso.dto.SystemInfoDTO;

public class FileOperationTool {

	private static final int BUFFER_SIZE = 16 * 1024; // 16M
	public static String DEFAULT_IMG_DES_PATH = "D:/CPSolution/Resource/Images/";
	public static String DEFAULT_VIDEO_DES_PATH = "D:/CPSolution/Resource/Videos/";
	public static String DEFAULT_AUDIO_DES_PATH = "D:/CPSolution/Resource/Audios/";
	public static String DEFAULT_TEXT_DES_PATH = "D:/CPSolution/Resource/Texts/";
	public static String DEFAULT_XML_DES_PATH = "D:/CPSolution/XmlDB/";

	public static String DEFAULT_ZIP_PATH = "D:/CPSolution/Resource/Zips/";
	public static String DEFAULT_FLV_PATH = "D:/CPSolution/Resource/Flvs/";

	public static final String IMAGETYPE = "IMAGETYPE";
	public static final String VIDEOTYPE = "VIDEOTYPE";
	public static final String AUDIOTYPE = "AUDIOTYPE";
	public static final String TEXTTYPE = "TEXTTYPE";
	public static final String XML_TYPE = "XMLTYPE";
	public static final String OTHERTYPES = "OTHERTYPES";

	public static final String ALL_IMAGE_TYPES = "BMP,JPG,JPEG,PNG,GIF";
	public static final String ALL_VIDEO_TYPES = "AVI,WMV,FLV,MKV,MOV,3GP,MP4,MPG,MPEG,RM,RMVB,TS,SWF";
	public static final String ALL_AUDIO_TYPES = "MP3,WAV,WMA,MID,MKA,WV";
	public static final String ALL_TEXT_TYPES = "TXT";

	private static Log log = LogFactory
			.getLog(com.cemso.util.FileOperationTool.class);

	// 资源类型
	enum FileTypes {
		IMAGETYPE, VIDEOTYPE, AUDIOTYPE, TEXTTYPE, OTHERTYPES
	};

	// 删除Resource下的资源
	public static boolean del(String filename, String type) {
		String absolutePath = null;
		if (XML_TYPE.equals(type)) {
			absolutePath = DEFAULT_XML_DES_PATH + filename;
		}
		if (TEXTTYPE.equals(type)) {
			absolutePath = DEFAULT_TEXT_DES_PATH + filename;
		}
		if (VIDEOTYPE.equals(type)) {
			File file = new File(DEFAULT_FLV_PATH
					+ filename.substring(0, filename.lastIndexOf(".")) + ".flv");
			boolean flag = file.delete();
			if (flag) {
				if (log.isDebugEnabled()) {
					log.debug("delete flv file successfully:"
							+ DEFAULT_FLV_PATH
							+ filename.substring(0, filename.lastIndexOf("."))
							+ ".flv");
				}
			}
			absolutePath = DEFAULT_VIDEO_DES_PATH + filename;
		}
		if (IMAGETYPE.equals(type)) {
			absolutePath = DEFAULT_IMG_DES_PATH + filename;
		}
		if (AUDIOTYPE.equals(type)) {
			absolutePath = DEFAULT_AUDIO_DES_PATH + filename;
		}

		File file = new File(absolutePath);
		return file.delete();
	}

	// 删除指定path的文件
	public static boolean del(String path) {
		File file = new File(path);
		return file.delete();
	}

	// 文件拷贝
	public static void copy(File src, File dst) {
		InputStream in = null;
		OutputStream out = null;
		try {
			in = new BufferedInputStream(new FileInputStream(src), BUFFER_SIZE);
			out = new BufferedOutputStream(new FileOutputStream(dst),
					BUFFER_SIZE);
			byte[] buffer = new byte[BUFFER_SIZE];
			while (in.read(buffer) > 0)
				out.write(buffer);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (null != in)
					in.close();
				if (null != out)
					out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	// file copy 2
	public static void ChannelCopy(File f1, File f2) throws Exception {
		int length = 2097152;
		FileInputStream in = new FileInputStream(f1);
		FileOutputStream out = new FileOutputStream(f2);
		FileChannel inC = in.getChannel();
		FileChannel outC = out.getChannel();
		ByteBuffer b = null;
		boolean flag = true;
		while (flag) {
			if (inC.position() == inC.size()) {
				inC.close();
				outC.close();
				flag = false;
			}else{
				if ((inC.size() - inC.position()) < length) {
					length = (int) (inC.size() - inC.position());
				} else
					length = 2097152;
				b = ByteBuffer.allocateDirect(length);
				inC.read(b);
				b.flip();
				outC.write(b);
				outC.force(false);
			}
		}
	}

	// 获取文件后缀名
	public static String getSuffix(String fileName) {
		return fileName.substring(fileName.lastIndexOf("."));
	}

	// 获取上传资源类型
	public static String getResourceType(String fileContentType) {
		// image,text,audio,video

		String ext = fileContentType.substring(
				fileContentType.lastIndexOf(".") + 1, fileContentType.length());

		if (ALL_IMAGE_TYPES.contains(ext.toUpperCase())) {
			return IMAGETYPE;
		}
		if (ALL_TEXT_TYPES.contains(ext.toUpperCase())) {
			return TEXTTYPE;
		}
		if (ALL_AUDIO_TYPES.contains(ext.toUpperCase())) {
			return AUDIOTYPE;
		}
		if (ALL_VIDEO_TYPES.contains(ext.toUpperCase())) {
			return VIDEOTYPE;
		}

		return OTHERTYPES;
	}

	/**
	 * 多个文件
	 * 
	 * @param files
	 *            所要打包的文件列表
	 * @param outputStream
	 * @throws IOException
	 */
	public static void zipFile(List<File> files, ZipOutputStream outputStream)
			throws IOException {
		int size = files.size();
		for (int i = 0; i < size; i++) {
			File file = (File) files.get(i);
			zipFile(file, outputStream);
		}
	}

	/**
	 * 根据输入的文件与流对文件进行打包
	 * 
	 * @throws IOException
	 */
	private static void zipFile(File inputFile, ZipOutputStream ouputStream)
			throws IOException {
		if (inputFile.exists()) {
			if (inputFile.isFile()) {
				FileInputStream in = new FileInputStream(inputFile);
				BufferedInputStream bins = new BufferedInputStream(in, 512);
				ZipEntry entry = new ZipEntry(inputFile.getName());
				// 向压缩文件中输出数据
				ouputStream.putNextEntry(entry);
				int nNumber;
				byte[] buffer = new byte[512];
				while ((nNumber = bins.read(buffer)) != -1) {
					ouputStream.write(buffer, 0, nNumber);
				}
				// 关闭创建的流对象
				bins.close();
				in.close();
			} else {
				File[] files = inputFile.listFiles();
				for (int i = 0; i < files.length; i++) {
					zipFile(files[i], ouputStream);
				}
			}
		}
	}

	// get content of txt file
	public static String getContentOfTxt(String txtPath) throws IOException {
		InputStreamReader isr = new InputStreamReader(new FileInputStream(
				txtPath), "GBK");
		BufferedReader br = new BufferedReader(isr);
		StringBuffer sb = new StringBuffer();
		String data = br.readLine();
		while (data != null) {
			sb.append(data.trim());
			data = br.readLine();
		}
		return sb.toString();
	}

	/**
	 * parse the ini file and return a ini file object
	 * 
	 * @param filePath
	 * @return
	 * @throws IOException
	 */
	public static SystemInfoDTO parseIni(String filePath) throws IOException {
		SystemInfoDTO info = new SystemInfoDTO();
		Map<String, String> infoMap = new HashMap<String, String>();
		BufferedReader reader = new BufferedReader(new FileReader(filePath));
		String line;
		boolean firstLine = true;
		while ((line = reader.readLine()) != null) {
			if (firstLine) {
				firstLine = false;
			} else {
				line = line.trim();
				if (line.matches(".*=.*")) {
					int i = line.indexOf('=');
					String name = line.substring(0, i);
					String value = line.substring(i + 1);
					infoMap.put(name, value);
				}
			}
		}

		info = infoMapToObj(infoMap);
		return info;
	}

	@SuppressWarnings("unchecked")
	protected static SystemInfoDTO infoMapToObj(Map infoMap) {
		SystemInfoDTO infoDTO = new SystemInfoDTO();
		Iterator it = infoMap.keySet().iterator();
		while (it.hasNext()) {
			String key = (String) it.next();
			String value = (String) infoMap.get(key);

			if (key.equals("PNO")) {
				infoDTO.setpNo(value);
			}
			if (key.equals("VERSION")) {
				infoDTO.setVersion(value);
			}
			if (key.equals("WIDTH")) {
				infoDTO.setWidth(value);
			}
			if (key.equals("HEIGHT")) {
				infoDTO.setHeight(value);
			}
			if (key.equals("STORAGE")) {
				infoDTO.setStorage(value);
			}
			if (key.equals("IP")) {
				infoDTO.setIp(value);
			}
			if (key.equals("GATEWAY")) {
				infoDTO.setGateway(value);
			}
			if (key.equals("SUBNETMASK")) {
				infoDTO.setSubnetMask(value);
			}
			if (key.equals("MACADDRESS")) {
				infoDTO.setMacAddress(value);
			}
			if (key.equals("STATUS")) {
				infoDTO.setStatus(value);
			}
			if (key.equals("TIME")) {
				infoDTO.setTime(value);
			}
			if (key.equals("FREEDISK")) {
				Long byteValue = Long.parseLong(value);
				Long mValue = byteValue / 1024;
				String mValueStr = String.valueOf(mValue);
				infoDTO.setFreedisk(mValueStr);
			}
			if (key.equals("PRGTOTAL")) {
				infoDTO.setPrgTotal(value);
			}
			if (key.equals("PRGINDEX")) {
				infoDTO.setPrgIndex(value);
			}
			if (key.equals("BOOT_TIME")) {
				infoDTO.setBootTime(value);
			}
		}
		return infoDTO;
	}
}