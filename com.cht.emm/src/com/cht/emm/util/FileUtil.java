package com.cht.emm.util;

/*
 * 版权声明 国家电网, 版权所有 违者必究
 *
 * 系统名称：国家电网公司统一应用开发平台
 * 模块名称：移动应用组件
 * 版本信息：
 * 版本		日期				作者			备注
 * 1.0		2012-8-6		wangjun		新建
 */

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import nariis.pi3000.framework.exception.MWRuntimeException;

import org.springframework.http.MediaType;

/**
 * <b>概述</b>：<br>
 * 文件工具类。
 * <p>
 * <b>功能</b>：<br>
 * 文件工具类。
 * 
 * @author wangjun
 * @since 1.0
 */
public class FileUtil {

	/**
	 * 读取指定文件。
	 * 
	 * @param p_fileName
	 *            指定文件。
	 * @return String
	 */
	// public static String readFile(String p_fileName) {
	// File file = new File(p_fileName);
	// StringBuffer buf = new StringBuffer();
	// BufferedReader fr = null;
	// FileReader inFileReader = null;
	// try {
	// inFileReader = new FileReader(file);
	// fr = new BufferedReader(inFileReader);
	// while (true) {
	// String line = fr.readLine();
	// if (line == null) {
	// break;
	// }
	// buf.append(line).append("\t\n");
	// }
	// return buf.toString();
	// } catch (IOException e) {
	// throw new MWRuntimeException(e);
	// } finally {
	// if (fr != null) {
	// try {
	// fr.close();
	// } catch (Exception e) {
	// }
	// }
	// if (inFileReader != null) {
	// try {
	// inFileReader.close();
	// } catch (Exception e) {
	// }
	// }
	// }
	// }
	//
	// /**
	// * 读取指定文件。
	// *
	// * @param p_fullFileName
	// * 指定文件。
	// * @return byte[]
	// */
	// public static byte[] readFileForByte(String p_fullFileName) {
	// InputStream is = null;
	// ByteArrayOutputStream out = null;
	// try {
	// out = new ByteArrayOutputStream();
	// is = new FileInputStream(p_fullFileName);
	// byte[] b = new byte[1024];
	// int n;
	// while ((n = is.read(b)) != -1) {
	// out.write(b, 0, n);
	// }
	// return out.toByteArray();
	// } catch (Exception e) {
	// throw new MWRuntimeException(e);
	// } finally {
	// if (out != null) {
	// try {
	// out.close();
	// } catch (Exception e2) {
	// }
	// out = null;
	// }
	// if (is != null) {
	// try {
	// is.close();
	// } catch (Exception e2) {
	// }
	// is = null;
	// }
	// }
	// }
	//
	// /**
	// * 创建指定文件目录。
	// *
	// * @param p_file
	// * 指定文件目录。
	// * @return 是否创建成功。
	// */
	// public static boolean mkdirs(File p_file) {
	// if (!p_file.exists()) {
	// return p_file.mkdirs();
	// }
	// return true;
	// }
	//
	// /**
	// * 创建文件。
	// *
	// * @param p_file
	// * 指定文件。
	// * @return 是否创建成功。
	// * @throws IOException
	// */
	// public static boolean mkfile(File p_file) throws IOException {
	// if (p_file == null) {
	// return false;
	// }
	// if (p_file.exists()) {
	// return true;
	// }
	// mkdirs(p_file.getParentFile());
	// p_file.createNewFile();
	// return true;
	// }
	//
	// /**
	// * 删除目录及其内容，不区分目录是否存在文件。
	// *
	// * @param p_dir
	// * 待删除的目录对象。
	// * @return 删除成功则返回 true， 否则为 false。
	// */
	// public static boolean deleteDir(File p_dir) {
	// if (p_dir.isFile()) {
	// return p_dir.delete();
	// }
	// String[] fileNames = p_dir.list();
	// if (fileNames == null) {
	// return true;
	// }
	// for (String fileName : fileNames) {
	// File file = null;
	// try {
	// file = new File(p_dir.getCanonicalPath() + File.separator
	// + fileName);
	// } catch (IOException e) {
	// return false;
	// }
	// if (file.isFile()) {
	// if (!file.delete()) {
	// return false;
	// }
	// } else {
	// if (!deleteDir(file)) {
	// return false;
	// }
	// }
	// }
	// return p_dir.delete();
	// }
	//
	/**
	 * 缓存是 4KB 的字节数组。
	 */
	private final static int BUFFER_LENGTH = 4 * 1024;

	//
	// /**
	// * 拷贝文件。使用NIO中的管道到管道传输。
	// *
	// * @param p_srcFile
	// * 源文件。
	// * @param p_destFile
	// * 目标文件。
	// */
	// public static boolean copyFileChannel(File p_srcFile, File p_destFile) {
	// if (!p_srcFile.exists()) {
	// throw new MWRuntimeException("源文件[" + p_srcFile.getAbsolutePath()
	// + "]不存在。");
	// }
	// FileChannel inChannel = null;
	// FileChannel outChannel = null;
	// int length = BUFFER_LENGTH;
	// try {
	// inChannel = new FileInputStream(p_srcFile).getChannel();
	// outChannel = new FileOutputStream(p_destFile).getChannel();
	// while (true) {
	// if (inChannel.position() == inChannel.size()) {
	// break;
	// }
	// if ((inChannel.size() - inChannel.position()) < BUFFER_LENGTH) {
	// length = (int) (inChannel.size() - inChannel.position());
	// }
	// inChannel.transferTo(inChannel.position(), length, outChannel);
	// inChannel.position(inChannel.position() + length);
	// }
	// } catch (IOException e) {
	// throw new MWRuntimeException(e);
	// } finally {
	// if (outChannel != null) {
	// try {
	// outChannel.close();
	// } catch (Exception e2) {
	// }
	// }
	// if (inChannel != null) {
	// try {
	// inChannel.close();
	// } catch (Exception e2) {
	// }
	// inChannel = null;
	// }
	// }
	// return true;
	// }
	//
	// /**
	// * 拷贝文件。
	// *
	// * @param p_srcFileStream
	// * @param p_destFile
	// * @return
	// */
	// public static boolean copyFile(byte[] p_srcFileStream, File p_destFile) {
	// ByteArrayInputStream in = null;
	// OutputStream out = null;
	// try {
	// in = new ByteArrayInputStream(p_srcFileStream);
	// out = new FileOutputStream(p_destFile);
	//
	// byte[] buf = new byte[1024];
	// int len;
	// while ((len = in.read(buf)) > 0) {
	// out.write(buf, 0, len);
	// }
	// } catch (Exception e) {
	// throw new MWRuntimeException(e);
	// } finally {
	// closeStream(in, out);
	// }
	// return true;
	// }
	//
	// /**
	// * 拷贝文件。
	// *
	// * @param p_srcStream
	// * @param p_destFile
	// * @return
	// */
	// public static boolean copyFile(InputStream p_srcStream, File p_destFile)
	// {
	// ByteArrayInputStream in = null;
	// OutputStream out = null;
	// try {
	// out = new FileOutputStream(p_destFile);
	//
	// byte[] buf = new byte[1024];
	// int len;
	// while ((len = p_srcStream.read(buf)) > 0) {
	// out.write(buf, 0, len);
	// }
	// } catch (Exception e) {
	// throw new MWRuntimeException(e);
	// } finally {
	// closeStream(in, out);
	// }
	// return true;
	// }
	//
	/**
	 * 拷贝文件。
	 * 
	 * @param p_srcFile
	 * @param p_destFile
	 * @return
	 */
	public static boolean copyFile(File p_srcFile, File p_destFile) {
		if (!p_srcFile.exists()) {
			throw new MWRuntimeException("源文件[" + p_srcFile.getAbsolutePath()
					+ "]不存在。");
		}
		InputStream in = null;
		OutputStream out = null;
		try {
			in = new FileInputStream(p_srcFile);
			out = new FileOutputStream(p_destFile,false);

			byte[] buf = new byte[BUFFER_LENGTH];
			int len;
			while ((len = in.read(buf)) > 0) {
				out.write(buf, 0, len);
			}
		} catch (Exception e) {
			throw new MWRuntimeException(e);
		} finally {
			closeStream(in, out);
		}
		return true;
	}

	// /**
	// * 保存文件到本地。
	// *
	// * @param p_inputStream
	// * @param p_fileLocation
	// */
	// public void writeToFile(InputStream p_inputStream, String p_fileLocation)
	// {
	// OutputStream out = null;
	// try {
	// out = new FileOutputStream(new File(p_fileLocation));
	// int read = 0;
	// byte[] bytes = new byte[1024];
	//
	// out = new FileOutputStream(new File(p_fileLocation));
	// while ((read = p_inputStream.read(bytes)) != -1) {
	// out.write(bytes, 0, read);
	// }
	// out.flush();
	// out.close();
	// p_inputStream.close();
	// } catch (IOException e) {
	// throw new MWRuntimeException(e);
	// } finally {
	// closeStream(p_inputStream, out);
	// }
	// }

	/**
	 * 关闭 {@link InputStream} 流。
	 * 
	 * @param p_inStream
	 */
	public static void closeInputStream(InputStream p_inStream) {
		if (p_inStream != null) {
			try {
				p_inStream.close();
			} catch (Exception e2) {
			}
			p_inStream = null;
		}
	}

	/**
	 * 关闭 {@link OutputStream} 流。
	 * 
	 * @param p_outStream
	 */
	public static void closeOutputStream(OutputStream p_outStream) {
		if (p_outStream != null) {
			try {
				p_outStream.close();
			} catch (Exception e2) {
			}
			p_outStream = null;
		}
	}

	public static void closeStream(InputStream p_in, OutputStream p_out) {
		closeOutputStream(p_out);
		closeInputStream(p_in);
	}

	// //////////////////////////////////////////////////////////////////////////

	/**
	 * 抓取部分文件内容，支持断点续传。
	 * 
	 * @param p_file
	 *            指定下载的文件
	 * @param p_request
	 * @param p_response
	 * @return 是否从文件头部下载
	 */
	public static boolean splitterFetch(File p_file,
			HttpServletRequest p_request, HttpServletResponse p_response) {
		return splitterFetch(p_file, p_request.getHeader("Range"), p_response);
	}

	/**
	 * 抓取文件，支持断点续传。
	 * 
	 * @param p_file
	 *            指定下载的文件。
	 * @param p_range
	 *            指定下载的文件块的开始字节和结束位置。
	 * @param p_response
	 * @return 是否从文件首部下载。
	 */
	public static boolean splitterFetch(File p_file, String p_range,
			HttpServletResponse p_response) {
		// 记录文件大小
		long fileLength = p_file.length();
		// 记录已下载文件大小
		long pastLength = 0;
		// 0：从头开始的全文下载；
		// 1：从某字节开始的下载（bytes=27000-）；
		// 2：从某字节开始到某字节结束的下载（bytes=27000-39000）
		int rangeSwitch = 0;
		// 记录客户端需要下载的字节段的最后一个字节偏移量（比如bytes=27000-39000，则这个值是为39000）
		long toLength = 0;
		// 客户端请求的字节总量
		long contentLength = 0;
		// 记录客户端传来的形如“bytes=27000-”或者“bytes=27000-39000”的内容
		String rangeBytes = "";

		if (p_range != null && !p_range.isEmpty()) {
			p_response.setStatus(HttpServletResponse.SC_PARTIAL_CONTENT);
			rangeBytes = p_range.replaceAll("bytes=", "");
			if (rangeBytes.indexOf('-') == rangeBytes.length() - 1) {
				// bytes=969998336- ：表示客户端请求的是 969998336之后的字节
				rangeSwitch = 1;
				rangeBytes = rangeBytes.substring(0, rangeBytes.indexOf('-'));
				pastLength = Long.parseLong(rangeBytes.trim());
				pastLength = pastLength < 0 ? 0 : pastLength;
				contentLength = fileLength - pastLength + 1;
			} else {
				// bytes=1275856879-1275877358
				rangeSwitch = 2;
				String startPoint = rangeBytes.substring(0,
						rangeBytes.indexOf('-'));
				String endPoint = rangeBytes.substring(
						rangeBytes.indexOf('-') + 1, rangeBytes.length());
				// bytes=1275856879-1275877358，从第 1275856879 个字节开始下载
				pastLength = Long.parseLong(startPoint.trim());
				pastLength = pastLength < 0 ? 0 : pastLength;
				// bytes=1275856879-1275877358，到第1275877358 个字节结束
				toLength = Long.parseLong(endPoint);
				// 客户端请求的是 1275856879-1275877358 之间的字节
				toLength = toLength >= fileLength ? fileLength - 1 : toLength;
				contentLength = toLength - pastLength + 1;
			}
		} else {
			// 从开始进行下载，客户端要求全文下载
			contentLength = fileLength;
		}

		setContentRange(p_response, fileLength, pastLength, rangeSwitch,
				rangeBytes);

		writeOutputStream(p_file, p_response, pastLength, rangeSwitch,
				contentLength);
		return pastLength <= 0 ? true : false;
	}

	/**
	 * 设置HTTP头部信息，支持断点续传。<br>
	 * 如果设设置了Content-Length，则客户端会自动进行多线程下载。<br>
	 * 如果不希望支持多线程，则不要设置这个参数。 响应的格式是: Content-Length: [文件的总大小] -
	 * [客户端请求的下载的文件块的开始字节]
	 * 
	 * @param p_response
	 * @param p_fileLength
	 * @param p_pastLength
	 * @param p_rangeSwitch
	 * @param p_rangeBytes
	 */
	private static void setContentRange(HttpServletResponse p_response,
			long p_fileLength, long p_pastLength, int p_rangeSwitch,
			String p_rangeBytes) {
		p_response.reset();
		// 告诉客户端允许断点续传多线程连接下载,响应的格式是:Accept-Ranges: bytes
		p_response.setHeader("Accept-Ranges", "bytes");
		p_response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
		// 设置文件总大小
		p_response.setHeader("Content-Length", String.valueOf(p_fileLength));
		// p_response.setContentLength((int) p_fileLength);
		// 如果是第一次下,还没有断点续传,状态是默认的200,无需显式设置;响应的格式是:HTTP/1.1 200 OK
		if (p_pastLength != 0) {
			// 不是从最开始下载
			// 响应的格式是:Content-Range: bytes [文件块的开始字节]-[文件的总大小 - 1]/[文件的总大小]
			// 不是从开始进行下载,服务器即将开始断点续传"
			switch (p_rangeSwitch) {
			case 1: {
				// 针对 bytes=27000- 的请求
				String contentRange = new StringBuffer("bytes ")
						.append(new Long(p_pastLength).toString()).append("-")
						.append(new Long(p_fileLength - 1).toString())
						.append("/").append(new Long(p_fileLength)).toString();
				p_response.setHeader("Content-Range", contentRange);
				break;
			}
			case 2: {
				// 针对 bytes=27000-39000 的请求
				String contentRange = p_rangeBytes + "/" + p_fileLength;
				p_response.setHeader("Content-Range", contentRange);
				break;
			}
			default: {
				break;
			}
			}
		}
	}

	private static void writeOutputStream(File p_file,
			HttpServletResponse p_response, long p_pastLength,
			int p_rangeSwitch, long p_contentLength) {
		// 负责读取数据
		RandomAccessFile raf = null;
		// 写出数据
		OutputStream os = null;
		// 缓冲
		OutputStream out = null;
		// 暂存容器
		byte b[] = new byte[1024];
		try {
			p_response.addHeader("Content-Disposition",
					"attachment; filename=\"" + p_file.getName() + "\"");
			os = p_response.getOutputStream();
			out = new BufferedOutputStream(os);
			raf = new RandomAccessFile(p_file, "r");
			try {
				switch (p_rangeSwitch) {
				case 0: {
					// 普通下载，或者从头开始的下载,同1
				}
				case 1: {
					// 针对 bytes=27000- 的请求,跳过 27000 个字节
					raf.seek(p_pastLength);
					int n = 0;
					while ((n = raf.read(b, 0, 1024)) != -1) {
						out.write(b, 0, n);
					}
					break;
				}
				case 2: {
					// 针对 bytes=27000-39000 的请求
					// 形如 bytes=1275856879-1275877358 的客户端请求，找到第 1275856879 个字节
					if (p_contentLength > 0) {
						// raf.seek(p_pastLength - 1);
						raf.seek(p_pastLength);
						int n = 0;
						// 记录已读字节数
						long readLength = 0;
						while (readLength <= p_contentLength - 1024) {
							n = raf.read(b, 0, 1024);
							readLength += 1024;
							out.write(b, 0, n);
						}
						// 余下的不足 1024 个字节在这里读取
						if (readLength <= p_contentLength) {
							n = raf.read(b, 0,
									(int) (p_contentLength - readLength));
							out.write(b, 0, n);
						}
					}
					break;
				}
				default: {
					break;
				}
				}
				out.flush();
			} catch (IOException ie) {
				/**
				 * 在写数据的时候， 对于 ClientAbortException 之类的异常，
				 * 是因为客户端取消了下载，而服务器端继续向浏览器写入数据时， 抛出这个异常，这个是正常的。 尤其是对于迅雷这种客户端软件，
				 * 明明已经有一个线程在读取 bytes=1275856879-1275877358，
				 * 如果短时间内没有读取完毕，迅雷会再启第二个、第三个。。。线程来读取相同的字节段， 直到有一个线程读取完毕，迅雷会 KILL
				 * 掉其他正在下载同一字节段的线程， 强行中止字节读出，造成服务器抛 ClientAbortException。
				 * 所以，忽略这种异常
				 */
				// ignore
			}
		} catch (Exception e) {
			// throw new MobileRuntimeException(ErrorCode.INVALIDATE_DOWNLOAD,
			// "下载文件失败，请参考：" + e.getMessage());
		} finally {
			closeOutputStream(out);
			closeOutputStream(os);
			if (raf != null) {
				try {
					raf.close();
				} catch (IOException e) {
				}
			}
		}
	}

	/**
	 * 抓取整个文件内容，不支持断点续传。
	 * 
	 * @param p_response
	 * @param p_file
	 *            指定下载的文件。
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static void siteFetch(HttpServletResponse p_response, File p_file)
			throws FileNotFoundException, IOException {
		FileInputStream fin = new FileInputStream(p_file);
		p_response.setHeader("Content-Length", Long.toString(p_file.length()));
		p_response.setContentType("application/octet-stream");
		// 不支持断点续传
		p_response.setHeader("Accept-Ranges", "none");
		try {
			if (fin != null && fin.available() > 0) {
				byte[] buff = new byte[1024];
				int readed = -1;
				while ((readed = fin.read(buff)) > 0) {
					p_response.getOutputStream().write(buff, 0, readed);
				}
			}
		} finally {
			if (fin != null) {
				try {
					fin.close();
				} catch (Exception e) {
				}
				fin = null;
			}
		}
	}

	public static final byte[] inputStreamToByteArray(InputStream p_inStream)
			throws IOException {
		ByteArrayOutputStream swapStream = null;
		try {
			swapStream = new ByteArrayOutputStream();
			byte[] buff = new byte[1024];
			int rc = 0;
			while ((rc = p_inStream.read(buff, 0, 1024)) > 0) {
				swapStream.write(buff, 0, rc);
			}
			swapStream.flush();
			return swapStream.toByteArray();
		} finally {
			closeOutputStream(swapStream);
			closeInputStream(p_inStream);
		}
	}
	
	public static String getWebRootPath(){
		String path = FileUtil.class.getClassLoader().getResource("").getPath();
		return path.substring(0,path.indexOf("WEB-INF"));
	}
	public  static String changeSeperator(String  path){
		if(File.separator.equals("/")){
			return path.replace("\\", "/");
		}else {
			return path.replace("/", "\\");
		}
		
	}
}
