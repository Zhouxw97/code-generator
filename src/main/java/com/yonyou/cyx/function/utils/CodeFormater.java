package com.yonyou.cyx.function.utils;

import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.ToolFactory;
import org.eclipse.jdt.core.formatter.CodeFormatter;
import org.eclipse.jdt.core.formatter.DefaultCodeFormatterConstants;
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

    private String fileEncoding;

    private Map options;

    private CodeFormatter codeFormatter;

    public CodeFormater(String fileEncoding) {
        init();
        this.fileEncoding = fileEncoding;
    }

    public CodeFormater() {
        init();
    }

    private void init() {
        fileEncoding = "UTF-8";
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

    public void formatFile(String sourceFileName) {
        File file = new File(sourceFileName);
        BufferedReader in = null;
        BufferedWriter out = null;
        String line;
        String lineSeparator = System.getProperty("line.separator");
        try {
            // retrieve the source
            in = new BufferedReader(new InputStreamReader(new FileInputStream(file), fileEncoding));
            StringBuffer sb = new StringBuffer();
            ;
            while ((line = in.readLine()) != null) {
                sb.append(line);
                sb.append(lineSeparator);
            }
            in.close();
            in = null;
            String contents = sb.toString();

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

            // output
            out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), fileEncoding));
            String output = doc.get();
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

}
