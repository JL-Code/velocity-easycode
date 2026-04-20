package com.codegen.web.controller;

import com.codegen.core.engine.TemplateEngine;
import com.codegen.core.model.TableInfo;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@RestController
@RequestMapping("/api/generator")
public class GeneratorController {

    private final TemplateEngine templateEngine;

    public GeneratorController() {
        this.templateEngine = new TemplateEngine();
        this.templateEngine.setClassForTemplateLoading(this.getClass(), "/templates");
    }

    @PostMapping("/generate")
    public void generate(@RequestBody TableInfo tableInfo, HttpServletResponse response) throws Exception {
        // Prepare data model
        Map<String, Object> dataModel = new HashMap<>();
        dataModel.put("table", tableInfo);
        dataModel.put("className", tableInfo.getClassName());
        dataModel.put("tableName", tableInfo.getTableName());
        dataModel.put("comment", tableInfo.getComment());
        dataModel.put("columns", tableInfo.getColumns());
        dataModel.put("primaryKey", tableInfo.getPrimaryKey());

        // We assume we have one template for now: entity.ftl
        // In a real scenario, we might iterate over a list of templates
        String entityCode = templateEngine.renderToString("entity.ftl", dataModel);

        response.setContentType("application/zip");
        response.setHeader("Content-Disposition", "attachment; filename=\"codegen.zip\"");

        try (ZipOutputStream zos = new ZipOutputStream(response.getOutputStream())) {
            // Add entity code to zip
            ZipEntry entry = new ZipEntry("src/main/java/com/example/entity/" + tableInfo.getClassName() + ".java");
            zos.putNextEntry(entry);
            zos.write(entityCode.getBytes(StandardCharsets.UTF_8));
            zos.closeEntry();
        }
    }
}
