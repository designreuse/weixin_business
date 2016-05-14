/**
 * @Title: CodeGenerator.java
 * @Package: nari.mip.backstage.util
 * @Description: 
 * @Author: 张凯(zhangkai3@sgepri.sgcc.com.cn)
 * @Date 2015-4-3 下午4:31:40
 * @Version: 1.0
 */
package com.cht.emm.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Arrays;

import javax.tools.Diagnostic;
import javax.tools.DiagnosticCollector;
import javax.tools.JavaCompiler;
import javax.tools.JavaCompiler.CompilationTask;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;

/**
 * @Class: CodeGenerator
 * @Author: 张凯 (zhangkai3@sgepri.sgcc.com.cn)
 * @Description:
 */
public class CodeGenerator {
	/**
	 * 编译java类
	 * 
	 * @param writerPath
	 */
	private String baseRoot;

	private String classPath;

	/**
	 * @return the baseRoot
	 */
	public String getBaseRoot() {
		return baseRoot;
	}

	/**
	 * @param baseRoot
	 *            the baseRoot to set
	 */
	public void setBaseRoot(String baseRoot) {
		this.baseRoot = baseRoot;
	}

	/**
	 * @return the classPath
	 */
	public String getClassPath() {
		return classPath;
	}

	/**
	 * @param classPath
	 *            the classPath to set
	 */
	public void setClassPath(String classPath) {
		this.classPath = classPath;
	}

	public String javac(String writerPath) {
		StringBuilder sb = new StringBuilder();
		// java编译器
		JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();

		String path = this.getClass().getResource("/").getPath();

		String classpath = null;
		try {
			classpath = getJarFiles(path + "../lib/");
			classpath += path + File.pathSeparator;
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		// path +=";"+path+"../lib/";
		Iterable<String> options = Arrays.asList("-d", path, "-classpath",
				classpath);

		// 文件管理器，参数1：diagnosticListener 监听器,监听编译过程中出现的错误
		StandardJavaFileManager manager = compiler.getStandardFileManager(null,
				null, null);
		// java文件转换到java对象，可以是多个文件
		Iterable<? extends JavaFileObject> it = manager
				.getJavaFileObjects(writerPath);
		DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<JavaFileObject>(); 
		// 编译任务,可以编译多个文件
		CompilationTask t = compiler.getTask(null, manager, diagnostics, options,
				null, it);

		// 执行任务
		boolean result = t.call();
		if(!result){
			for (Diagnostic dia : diagnostics.getDiagnostics()) {
					sb.append(dia.getKind().name()+": 第"+(dia.getLineNumber())+"行 : " +dia.getMessage(null)+"<p>");
			}
		}
		try {
			manager.close();
		} catch (IOException e) {
			e.printStackTrace();
			sb.append(e.getMessage());
		}
		return sb.toString();
	}

	/**
	 * 利用反射，实例化对象，此方法可指定class路径，放在classpath下可能会和jdk编译的文件冲突
	 * 
	 * @param packPath
	 */
	public void java(String packPath) {
		URL[] urls = null;
		try {
			// 类路径,url的本地文件格式需要加file:/
			urls = new URL[] { new URL("file:/"
					+ System.getProperty("user.dir") + "/src/") };
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		// 类加载器
		URLClassLoader url = new URLClassLoader(urls);
		Class clazz = null;
		try {
			// 加载到内存
			clazz = url.loadClass(packPath);
			// 实例化对象
			clazz.newInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 读文件
	 * 
	 * @param readerPath
	 * @return
	 */
	public BufferedReader fileReader(String readerPath) {
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(readerPath));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return br;
	}

	public BufferedReader StringReader(String content) {
		BufferedReader br = null;
		br = new BufferedReader(new java.io.StringReader(content));
		return br;

	}

	private String getJarFiles(String jarPath) throws Exception {
		File sourceFile = new File(jarPath);
		final StringBuilder jars = new StringBuilder();
		if (sourceFile.exists()) {
			if (sourceFile.isDirectory()) {
				File[] childrenFiles = sourceFile.listFiles(new FileFilter() {
					public boolean accept(File pathname) {
						if (pathname.isDirectory()) {
							return true;
						} else {
							String name = pathname.getName();
							if (name.endsWith(".jar")) {
								jars.append(pathname.getPath()
										+ File.pathSeparator);
								return true;
							}
							return false;
						}
					}
				});
			}
		}
		return jars.toString();
	}

	/**
	 * 写文件
	 * 
	 * @param br
	 * @param writerPath
	 */
	public String fileWriter(BufferedReader br, String writerPath) {
		StringBuilder exceptions = new StringBuilder();
		String line;
		BufferedWriter bw = null;
		try {
			line = br.readLine();
			bw = new BufferedWriter(new FileWriter(writerPath));
			while (line != null) {
				bw.write(line + "\r\n");
				line = br.readLine();
			}
		} catch (IOException e) {
			e.printStackTrace();
			exceptions.append(e.getMessage());
		} finally {
			try {
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					bw.flush();
					bw.close();
				} catch (IOException e) {
					e.printStackTrace();
					exceptions.append(e.getMessage());
				}
			}
		}
		return exceptions.toString();
	}

	public static void main(String[] args) {
		try {
			CodeGenerator code = new CodeGenerator();
			// 读文本文件

			BufferedReader br = code
					.fileReader("E:\\workspace\\nari.mip.emm3\\WebRoot\\resources\\template\\TestValidator.java");
			// 生成java类
			code.fileWriter(
					br,
					"E:\\workspace\\nari.mip.emm3\\WebRoot\\WEB-INF\\classes\\nari\\mip\\backstage\\rpc\\TestValidator.java");
			// 编译java类
			code.javac("E:\\workspace\\nari.mip.emm3\\WebRoot\\WEB-INF\\classes\\nari\\mip\\backstage\\rpc\\TestValidator.java");
			// 运行java类
			// code.java(PACK_PATH);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
