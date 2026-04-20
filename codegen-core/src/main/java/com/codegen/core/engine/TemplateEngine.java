package com.codegen.core.engine;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;

public class TemplateEngine {

    private final Configuration configuration;

    public TemplateEngine() {
        this.configuration = new Configuration(Configuration.VERSION_2_3_32);
        this.configuration.setDefaultEncoding("UTF-8");
        this.configuration.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        this.configuration.setLogTemplateExceptions(false);
        this.configuration.setWrapUncheckedExceptions(true);
        this.configuration.setFallbackOnNullLoopVariable(false);
    }

    public void setTemplateDirectory(String directoryPath) throws IOException {
        this.configuration.setDirectoryForTemplateLoading(new File(directoryPath));
    }

    public void setClassForTemplateLoading(Class<?> clazz, String pathPrefix) {
        this.configuration.setClassForTemplateLoading(clazz, pathPrefix);
    }

    public String renderToString(String templateName, Object dataModel) throws IOException, TemplateException {
        Template template = configuration.getTemplate(templateName);
        try (StringWriter writer = new StringWriter()) {
            template.process(dataModel, writer);
            return writer.toString();
        }
    }

    public void renderToFile(String templateName, Object dataModel, String outputPath) throws IOException, TemplateException {
        Template template = configuration.getTemplate(templateName);
        File outputFile = new File(outputPath);
        if (outputFile.getParentFile() != null && !outputFile.getParentFile().exists()) {
            outputFile.getParentFile().mkdirs();
        }
        try (Writer writer = new FileWriter(outputFile)) {
            template.process(dataModel, writer);
        }
    }
}