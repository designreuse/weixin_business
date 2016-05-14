package com.cht.emm.servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cht.emm.util.ApkParser;
import com.cht.emm.util.UUIDGen;

public class UploadReceiver extends HttpServlet {
	// private static final File UPLOAD_DIR = new File("test/uploads");
	// private static File TEMP_DIR = new File("test/uploadsTemp");

	/**
	 * 
	 */
	private static final long serialVersionUID = -2643713382052105479L;
	private static String CONTENT_LENGTH = "Content-Length";
	private static int SUCCESS_RESPONSE_CODE = 200;
	private String callback;

	final Logger log = LoggerFactory.getLogger(UploadReceiver.class);

	@Override
	public void init() throws ServletException {
		// UPLOAD_DIR.mkdirs();
	}

	@Override
	public void doDelete(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		// String uuid = req.getPathInfo().replaceAll("/", "");
		//
		// FileUtils.deleteDirectory(new File(UPLOAD_DIR, uuid));
		//
		// if (new File(UPLOAD_DIR, uuid).exists()) {
		// log.warn("couldn't find or delete " + uuid);
		// } else {
		// log.info("deleted " + uuid);
		// }
		//
		// resp.setStatus(SUCCESS_RESPONSE_CODE);
		// // resp.addHeader("Access-Control-Allow-Origin", "*");
	}

	@Override
	public void doOptions(HttpServletRequest req, HttpServletResponse resp) {
		resp.setStatus(SUCCESS_RESPONSE_CODE);
		// resp.addHeader("Access-Control-Allow-Origin",
		// "http://192.168.130.118:8080");
		// resp.addHeader("Access-Control-Allow-Credentials", "true");
		resp.addHeader("Access-Control-Allow-Origin", "*");
		resp.addHeader("Access-Control-Allow-Methods", "POST, DELETE");
		resp.addHeader("Access-Control-Allow-Headers",
				"x-requested-with, cache-control, content-type");
	}

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		RequestParser requestParser = null;
		File TEMP_DIR = new File(req.getSession().getServletContext()
				.getRealPath("/"), "uploads/temp");
		callback = req.getParameter("CKEditorFuncNum");

		boolean isIframe = req.getHeader("X-Requested-With") == null
				|| !req.getHeader("X-Requested-With").equals("XMLHttpRequest");
		try {
			resp.setContentType(isIframe ? "text/html" : "text/plain");
			resp.setStatus(SUCCESS_RESPONSE_CODE);
			resp.setContentType("text/html; charset=UTF-8");

			// resp.addHeader("Access-Control-Allow-Origin",
			// "http://192.168.130.118:8080");
			// resp.addHeader("Access-Control-Allow-Credentials", "true");
			// resp.addHeader("Access-Control-Allow-Origin", "*");

			if (ServletFileUpload.isMultipartContent(req)) {
				MultipartUploadParser multipartUploadParser = new MultipartUploadParser(
						req, TEMP_DIR, getServletContext());
				requestParser = RequestParser.getInstance(req,
						multipartUploadParser);
				if (callback != null) {
					String fileName = requestParser.getFilename();
					if (fileName == null) {
						resp.getWriter()
								.print("<font color=\"red\" size=\"2\">*请选择上传文件</font>");
						return;
					} else if (fileName.endsWith(".jpg")
							|| fileName.endsWith(".jpeg")
							|| fileName.endsWith(".gif")
							|| fileName.endsWith(".png")
							|| fileName.endsWith(".bmp")) {

					} else {
						resp.getWriter()
								.print("<font color=\"red\" size=\"2\">*文件格式不正确（必须为.jpeg/.jpg/.gif/.bmp/.png文件）</font>");
						return;
					}
				}
				writeFileForMultipartRequest(req, requestParser);
				writeResponse(resp.getWriter(),
						requestParser.generateError() ? "Generated error"
								: null, isIframe, false, requestParser);
			} else {
				requestParser = RequestParser.getInstance(req, null);
				if (callback != null) {
					String fileName = requestParser.getFilename();
					if (fileName == null) {
						resp.getWriter()
								.print("<font color=\"red\" size=\"2\">*请选择上传文件</font>");
						return;
					} else if (fileName.endsWith(".jpg")
							|| fileName.endsWith(".jpeg")
							|| fileName.endsWith(".gif")
							|| fileName.endsWith(".png")
							|| fileName.endsWith(".bmp")) {

					} else {
						resp.getWriter()
								.print("<font color=\"red\" size=\"2\">*文件格式不正确（必须为.jpeg/.jpg/.gif/.bmp/.png文件）</font>");
						return;
					}
				}
				writeFileForNonMultipartRequest(req, requestParser);
				writeResponse(resp.getWriter(),
						requestParser.generateError() ? "Generated error"
								: null, isIframe, false, requestParser);
			}

		} catch (Exception e) {
			log.error("Problem handling upload request", e);
			if (e instanceof MergePartsException) {
				writeResponse(resp.getWriter(), e.getMessage(), isIframe, true,
						requestParser);
			} else {
				writeResponse(resp.getWriter(), e.getMessage(), isIframe,
						false, requestParser);
			}
		}
	}

	private void writeFileForNonMultipartRequest(HttpServletRequest req,
			RequestParser requestParser) throws Exception {
		File UPLOAD_DIR = new File(req.getSession().getServletContext()
				.getRealPath("/"), "uploads");
		if (requestParser.getUuid() == null) {
			requestParser.setUuid(UUIDGen.getUUID());
		}
		File dir = new File(UPLOAD_DIR, requestParser.getUuid());
		dir.mkdirs();

		File outputFile = null;

		String contentLengthHeader = req.getHeader(CONTENT_LENGTH);
		long expectedFileSize = Long.parseLong(contentLengthHeader);

		if (requestParser.getPartIndex() >= 0) {
			writeFile(
					req.getInputStream(),
					new File(dir, requestParser.getUuid()
							+ "_"
							+ String.format("%05d",
									requestParser.getPartIndex())), null);

			if (requestParser.getTotalParts() - 1 == requestParser
					.getPartIndex()) {
				File[] parts = getPartitionFiles(dir, requestParser.getUuid());
				outputFile = new File(dir, requestParser.getFilename());
				for (File part : parts) {
					mergeFiles(outputFile, part);
				}

				assertCombinedFileIsVaid(UPLOAD_DIR,
						requestParser.getTotalFileSize(), outputFile,
						requestParser.getUuid());
				deletePartitionFiles(dir, requestParser.getUuid());

			}
		} else {
			outputFile = new File(dir, requestParser.getFilename());
			writeFile(req.getInputStream(), outputFile, expectedFileSize);
		}
		if (outputFile != null) {

			try {
				String url = req.getRequestURL().toString();
				String pre = url.substring(0, url.indexOf("upload/receiver"));
				String path = outputFile.getAbsolutePath();
				String suffix = path.substring(path.indexOf("uploads"))
						.replace("\\", "/");
				requestParser.setAbsoluteFilePath(path);
				requestParser.setAbsoluteUrl(pre + suffix);
			} catch (Exception e) {

			}

		}
	}

	private void writeFileForMultipartRequest(HttpServletRequest req,
			RequestParser requestParser) throws Exception {
		File UPLOAD_DIR = new File(req.getSession().getServletContext()
				.getRealPath("/"), "uploads");
		if (requestParser.getUuid() == null) {
			requestParser.setUuid(UUIDGen.getUUID());
		}
		File dir = new File(UPLOAD_DIR, requestParser.getUuid());
		dir.mkdirs();
		File outputFile = null;
		if (requestParser.getPartIndex() >= 0) {
			writeFile(
					requestParser.getUploadItem().getInputStream(),
					new File(dir, requestParser.getUuid()
							+ "_"
							+ String.format("%05d",
									requestParser.getPartIndex())), null);

			if (requestParser.getTotalParts() - 1 == requestParser
					.getPartIndex()) {
				File[] parts = getPartitionFiles(dir, requestParser.getUuid());
				outputFile = new File(dir, requestParser.getOriginalFilename());
				for (File part : parts) {
					mergeFiles(outputFile, part);
				}

				assertCombinedFileIsVaid(UPLOAD_DIR,
						requestParser.getTotalFileSize(), outputFile,
						requestParser.getUuid());
				deletePartitionFiles(dir, requestParser.getUuid());
			}
		} else {
			outputFile = new File(dir, requestParser.getFilename());
			writeFile(requestParser.getUploadItem().getInputStream(),
					outputFile, null);
		}
		if (outputFile != null) {
			try {
				String url = req.getRequestURL().toString();
				String pre = url.substring(0, url.indexOf("upload/receiver"));
				String path = outputFile.getAbsolutePath();
				String suffix = path.substring(path.indexOf("uploads"))
						.replace("\\", "/");
				requestParser.setAbsoluteFilePath(path);
				requestParser.setAbsoluteUrl(pre + suffix);
			} catch (Exception e) {

			}
		}
	}

	private void assertCombinedFileIsVaid(File UPLOAD_DIR, int totalFileSize,
			File outputFile, String uuid) throws MergePartsException {
		if (totalFileSize != outputFile.length()) {
			deletePartitionFiles(UPLOAD_DIR, uuid);
			outputFile.delete();
			throw new MergePartsException("Incorrect combined file size!");
		}

	}

	private static class PartitionFilesFilter implements FilenameFilter {
		private String filename;

		PartitionFilesFilter(String filename) {
			this.filename = filename;
		}

		@Override
		public boolean accept(File file, String s) {
			return s.matches(Pattern.quote(filename) + "_\\d+");
		}
	}

	private static File[] getPartitionFiles(File directory, String filename) {
		File[] files = directory.listFiles(new PartitionFilesFilter(filename));
		Arrays.sort(files);
		return files;
	}

	private static void deletePartitionFiles(File directory, String filename) {
		File[] partFiles = getPartitionFiles(directory, filename);
		for (File partFile : partFiles) {
			partFile.delete();
		}
	}

	private File mergeFiles(File outputFile, File partFile) throws Exception {
		FileOutputStream fos;
		FileInputStream fis;
		byte[] fileBytes;
		int bytesRead = 0;
		fos = new FileOutputStream(outputFile, true);
		fis = new FileInputStream(partFile);
		fileBytes = new byte[(int) partFile.length()];
		bytesRead = fis.read(fileBytes, 0, (int) partFile.length());
		assert (bytesRead == fileBytes.length);
		assert (bytesRead == (int) partFile.length());
		fos.write(fileBytes);
		fos.flush();
		fis.close();
		fos.close();

		return outputFile;
	}

	private File writeFile(InputStream in, File out, Long expectedFileSize)
			throws IOException {
		FileOutputStream fos = null;

		try {
			fos = new FileOutputStream(out);

			IOUtils.copy(in, fos);

			if (expectedFileSize != null) {
				Long bytesWrittenToDisk = out.length();
				if (!expectedFileSize.equals(bytesWrittenToDisk)) {
					log.warn(
							"Expected file {} to be {} bytes; file on disk is {} bytes",
							new Object[] { out.getAbsolutePath(),
									expectedFileSize, 1 });
					out.delete();
					throw new IOException(
							String.format(
									"Unexpected file size mismatch. Actual bytes %s. Expected bytes %s.",
									bytesWrittenToDisk, expectedFileSize));
				}
			}

			return out;
		} catch (Exception e) {
			throw new IOException(e);
		} finally {
			IOUtils.closeQuietly(fos);
		}
	}

	private void writeResponse(PrintWriter writer, String failureReason,
			boolean isIframe, boolean restartChunking,
			RequestParser requestParser) {
		if (failureReason == null) {
			// if (isIframe)
			// {
			// writer.print("{\"success\": true, \"uuid\": \"" +
			// requestParser.getUuid() +
			// "\"}<script src=\"http://192.168.130.118:8080/client/js/iframe.xss.response.js\"></script>");
			// }
			// else
			// {
			if (requestParser.getAbsoluteFilePath().endsWith(".apk")
					|| requestParser.getAbsoluteFilePath().endsWith(".APK")) {
				String[] str = null;
				try {
					str = ApkParser.unZip(requestParser.getAbsoluteFilePath(),
							"");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if (str != null) {
					writer.print("{\"success\": true,\"url\":\""
							+ requestParser.getAbsoluteUrl()
							+ "\",\"file\":\""
							+ requestParser.getAbsoluteFilePath().replace("\\",
									"\\\\") + "\",\"versionName\":\"" + str[0]
							+ "\",\"versionCode\":\"" + str[1]
							+ "\",\"pkgName\":\"" + str[2] + "\"}");
					return;
				}
			}
			if (requestParser.getAbsoluteFilePath().endsWith(".zip")
					|| requestParser.getAbsoluteFilePath().endsWith(".ZIP")) {
				writer.print("{\"success\": true,\"url\":\""
						+ requestParser.getAbsoluteUrl()
						+ "\",\"file\":\""
						+ requestParser.getAbsoluteFilePath().replace("\\",
								"\\\\")
						+ "\",\"versionName\":\"\",\"versionCode\":\"\",\"pkgName\":\"\"}");
				return;
			}
			if (callback != null) {
				writer.println("<script type=\"text/javascript\">");
				writer.println("window.parent.CKEDITOR.tools.callFunction("
						+ callback + ",'" + requestParser.getAbsoluteUrl()
						+ "','')");
				writer.println("</script>");
				return;
			}
			writer.print("{\"success\": true,\"url\":\""
					+ requestParser.getAbsoluteUrl() + "\",\"file\":\""
					+ requestParser.getAbsoluteFilePath().replace("\\", "\\\\")
					+ "\"}");
			// }
		} else {
			if (restartChunking) {
				writer.print("{\"error\": \"" + failureReason
						+ "\", \"reset\": true}");
			} else {
				// if (isIframe)
				// {
				// writer.print("{\"error\": \"" + failureReason +
				// "\", \"uuid\": \"" + requestParser.getUuid() +
				// "\"}<script src=\"http://192.168.130.118:8080/client/js/iframe.xss.response.js\"></script>");
				// }
				// else
				// {
				writer.print("{\"error\": \"" + failureReason + "\"}");
				// }
			}
		}
	}

	private class MergePartsException extends Exception {
		/**
		 * 
		 */
		private static final long serialVersionUID = 8116074454613205656L;

		MergePartsException(String message) {
			super(message);
		}
	}
}
