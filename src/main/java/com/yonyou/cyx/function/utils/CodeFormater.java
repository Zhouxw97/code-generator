package com.yonyou.cyx.function.utils;

import org.dom4j.DocumentHelper;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.ToolFactory;
import org.eclipse.jdt.core.formatter.CodeFormatter;
import org.eclipse.jdt.core.formatter.DefaultCodeFormatterConstants;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.Document;
import org.eclipse.jface.text.IDocument;
import org.eclipse.text.edits.TextEdit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.Map;

/**
 * java 文件格式化
 *
 * @author lijun
 */
public class CodeFormater {

    private static final Logger logger = LoggerFactory.getLogger(CodeFormater.class);

    private String lineSeparator;
    private String fileEncoding;

    private Map options;

    private CodeFormatter codeFormatter;

    public CodeFormater(String fileEncoding) {
        init();
        this.fileEncoding = fileEncoding;
        this.lineSeparator =  System.getProperty("line.separator");
    }

    public CodeFormater() {
        init();
    }

    private void init() {
        fileEncoding = "UTF-8";
        lineSeparator =  System.getProperty("line.separator");
        options = DefaultCodeFormatterConstants.getEclipseDefaultSettings();

        // initialize the compiler settings to be able to format 1.6 code
        options.put(JavaCore.COMPILER_COMPLIANCE, JavaCore.VERSION_1_8);
        options.put(JavaCore.COMPILER_CODEGEN_TARGET_PLATFORM, JavaCore.VERSION_1_8);
        options.put(JavaCore.COMPILER_SOURCE, JavaCore.VERSION_1_8);

        // change the option to wrap each enum constant on a new line
        options.put(DefaultCodeFormatterConstants.FORMATTER_ALIGNMENT_FOR_ENUM_CONSTANTS,
                DefaultCodeFormatterConstants.createAlignmentValue(
                        true,
                        DefaultCodeFormatterConstants.WRAP_ONE_PER_LINE,
                        DefaultCodeFormatterConstants.INDENT_ON_COLUMN));

        // instanciate the default code formatter with the given options
        codeFormatter = ToolFactory.createCodeFormatter(options);
    }

    /**
     * 文件格式化
     *
     * @param sourceFileName
     */
    public void formatFile(String sourceFileName) {
        BufferedReader in = null;
        BufferedWriter out = null;
        try {
            File file = new File(sourceFileName);

            String line;
            // retrieve the source
            in = new BufferedReader(new InputStreamReader(new FileInputStream(file), fileEncoding));
            StringBuffer sb = new StringBuffer();
            while ((line = in.readLine()) != null) {
                sb.append(line).append(lineSeparator);
            }
            in.close();
            in = null;
            String contents = sb.toString();

            String output = contents;

            if (sourceFileName.endsWith(".java")) {
                output = getJavaFormatStr(contents);
            } else if (sourceFileName.endsWith(".xml")) {
                output = getXmlFormatStr(contents);
            }

            // output
            out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), fileEncoding));
            out.write(output);
            out.flush();
        } catch (Exception e) {
            logger.error("format file exception:", e);
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
                if (out != null) {
                    out.close();
                }
            } catch (Exception e) {
                logger.error("format file exception:", e);
            }
        }
    }

    /**
     * java文件 格式化
     *
     * @param contents
     * @return
     */
    private String getJavaFormatStr(String contents) {
        try {
            IDocument doc = new Document(contents);
            // create delta
            TextEdit edit = codeFormatter.format(
                    // format a compilation unit
                    CodeFormatter.K_COMPILATION_UNIT,
                    // source to format
                    contents,
                    // starting position
                    0,
                    // length
                    contents.length(),
                    // initial indentation
                    0,
                    // line separator
                    lineSeparator);

            // apply changes to content
            edit.apply(doc);
            return doc.get();
        } catch (BadLocationException e) {
            logger.error("format java exception:", e);
            return contents;
        }
    }

    /**
     * xml文件 格式化
     *
     * @param contents
     * @return
     */
    private String getXmlFormatStr(String contents) {
        // 创建String输出流
        StringWriter out = new StringWriter();
        try {
            // 将字符串格式转换成document对象
            org.dom4j.Document document = DocumentHelper.parseText(contents);

            // 注意,用这种方式来创建指定格式的format
            OutputFormat format = OutputFormat.createPrettyPrint();
            format.setIndentSize(4);
            // 包装String流
            XMLWriter writer = new XMLWriter(out, format);

            // 将当前的document对象写入底层流out中
            writer.write(document);
            writer.close();

            return out.toString();
        } catch (Exception e) {
            logger.error("format xml exception:", e);
            return contents;
        }
    }
}
