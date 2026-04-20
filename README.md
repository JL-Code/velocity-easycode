# FreeMarker Java CodeGen

基于 FreeMarker 的 Java 代码骨架生成器，提供：

- `codegen-core`：FreeMarker 渲染核心与元数据模型
- `codegen-web`：Spring Boot Web 服务，对外提供代码生成接口（下载 ZIP）
- `codegen-cli`：独立命令行工具，本地生成代码文件

## 项目目标

- 从零构建一套可扩展的模板化代码生成能力（不依赖历史 Velocity 体系）
- 支持两种使用方式：
  - Web：适合平台化/多人协作，生成结果以 ZIP 下载
  - CLI：适合本地开发、脚本化执行

## 架构概览

```
                 ┌────────────────────┐
HTTP(JSON) ────> │ codegen-web         │ ── ZIP ──> Client
                 │ (Spring Boot API)   │
                 └─────────┬──────────┘
                           │
                           ▼
                 ┌────────────────────┐
CLI args ──────> │ codegen-cli         │ ── Files ──> Local FS
                 │ (Picocli)           │
                 └─────────┬──────────┘
                           │
                           ▼
                 ┌────────────────────┐
                 │ codegen-core        │
                 │ - TemplateEngine    │
                 │ - TableInfo/Column  │
                 └────────────────────┘
```

## 模块说明

### codegen-core

- 作用：提供 FreeMarker 渲染引擎与基础数据模型
- 关键类：
  - [TemplateEngine](file:///Users/codeme/workspaces/velocity-easycode/codegen-core/src/main/java/com/codegen/core/engine/TemplateEngine.java)：加载模板并渲染为字符串/文件
  - [TableInfo](file:///Users/codeme/workspaces/velocity-easycode/codegen-core/src/main/java/com/codegen/core/model/TableInfo.java)、[ColumnInfo](file:///Users/codeme/workspaces/velocity-easycode/codegen-core/src/main/java/com/codegen/core/model/ColumnInfo.java)：生成数据模型

### codegen-web

- 作用：提供 HTTP API 触发生成，并将结果打包为 ZIP 返回
- 启动类：[Application](file:///Users/codeme/workspaces/velocity-easycode/codegen-web/src/main/java/com/codegen/web/Application.java)
- 生成接口：[GeneratorController](file:///Users/codeme/workspaces/velocity-easycode/codegen-web/src/main/java/com/codegen/web/controller/GeneratorController.java)
  - `POST /api/generator/generate`：请求体为 `TableInfo`，响应为 `application/zip`

### codegen-cli

- 作用：本地命令行生成工具，便于脚本化调用
- 入口：[Main](file:///Users/codeme/workspaces/velocity-easycode/codegen-cli/src/main/java/com/codegen/cli/Main.java)
- 参数：
  - `--table/-t`：表名
  - `--class/-c`：类名
  - `--output/-o`：输出目录
  - `--template/-T`：可选，本地模板文件路径（未传则使用内置模板）

## 快速开始

### 1) 构建

```bash
mvn clean package
```

### 2) 运行 Web 服务

```bash
mvn -pl codegen-web spring-boot:run
```

示例请求（生成并下载 ZIP）：

```bash
curl -X POST 'http://localhost:8080/api/generator/generate' \
  -H 'Content-Type: application/json' \
  --output codegen.zip \
  -d '{
    "tableName": "user",
    "className": "User",
    "comment": "User table",
    "columns": []
  }'
```

### 3) 运行 CLI

```bash
java -jar codegen-cli/target/codegen-cli-1.0.0-SNAPSHOT-jar-with-dependencies.jar \
  --table user \
  --class User \
  --output ./output
```

## 模板约定

- 默认内置模板：`templates/entity.ftl`
  - Web：`codegen-web/src/main/resources/templates/entity.ftl`
  - CLI：`codegen-cli/src/main/resources/templates/entity.ftl`
- 目前示例仅生成 Entity，可在后续扩展为 Controller/Service/DAO/Mapper 等多模板组合生成。

## 说明

- 仓库中仍存在历史 Velocity 模板目录（如 [template](file:///Users/codeme/workspaces/velocity-easycode/template)），当前 README 仅描述 FreeMarker 主线能力。
