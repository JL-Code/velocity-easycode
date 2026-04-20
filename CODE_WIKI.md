# velocity-easycode Code Wiki

## 1. 项目概览

本仓库不是一个可直接编译运行的应用工程，而是一套用于“生成应用工程代码”的 Velocity 模板集合：

- 通过数据库表结构元信息（`$tableInfo`）生成典型 Spring + MyBatis-Plus 分层代码骨架：Controller / Service / ServiceImpl / Dao / Entity / DTO / Mapper.xml
- 通过全局宏（`global-config/define.java`）复用包名/输出路径/注释/布尔字段命名转换等逻辑

核心入口：

- 全局宏定义：[define.java](file:///Users/codeme/workspaces/velocity-easycode/global-config/define.java)
- 代码模板目录：[template](file:///Users/codeme/workspaces/velocity-easycode/template)

## 2. 仓库结构

```
.
├── CODE_WIKI.md
├── README.md
├── global-config
│   └── define.java
├── springboot-init-template
│   └── README.md
└── template
    ├── controller.java
    ├── dao.java
    ├── dto.java
    ├── entity.java
    ├── mapper.xml
    ├── service.java
    └── serviceImpl.java
```

## 3. 生成器上下文模型（模板运行时变量）

模板由外部代码生成器/IDE 插件渲染执行（仓库中不包含生成器本体）。从模板内容可推断常用上下文对象如下：

- `$tableInfo`：表元信息（表名、注释、列集合、主键列、输出包名、输出根路径等），用于决定类名/字段/注释/保存位置
- `$callback`：输出回调对象，用于设置生成文件名与保存目录（如 `setFileName` / `setSavePath`）
- `$tool`：工具对象（字符串拼接、首字母大小写、类型简化、序列号生成等）
- `$time`：时间工具（如 `currTime(...)`）
- `$define`：全局宏引用（见 [define.java](file:///Users/codeme/workspaces/velocity-easycode/global-config/define.java)）
- `$autoImport`：生成器提供的默认 import 注入点（见 [entity.java](file:///Users/codeme/workspaces/velocity-easycode/template/entity.java#L10-L15)、[dto.java](file:///Users/codeme/workspaces/velocity-easycode/template/dto.java#L12-L20)）
- `$mybatisSupport` / `$modulePath`：生成器提供的 MyBatis XML 支持与模块路径变量（见 [mapper.xml](file:///Users/codeme/workspaces/velocity-easycode/template/mapper.xml#L1-L9)）

上述对象的精确字段定义依赖你所使用的生成器实现；本 Wiki 以模板使用方式为准。

## 4. 目标工程架构（生成结果的分层与依赖）

模板面向常见的 Java Web 分层（生成后代码的逻辑结构）：

- Controller：接口层，依赖 Service，使用 Spring Web + Swagger 注解
- Service：业务抽象层，基于 MyBatis-Plus `IService<T>`
- ServiceImpl：业务实现层，基于 MyBatis-Plus `ServiceImpl<M, T>` 并组合 Dao
- Dao：数据访问层，基于 MyBatis-Plus `BaseMapper<T>`
- Entity：持久化模型，MyBatis-Plus `@TableName/@TableField` 映射 + Lombok `@Data`
- DTO：接口数据模型，Swagger `@ApiModel/@ApiModelProperty` + JSR-303 校验注解
- Mapper.xml：MyBatis 映射文件，生成 `resultMap`，把数据库列映射到实体属性

依赖关系（生成后代码的静态依赖）：

```
Controller  ──>  Service  ──>  Entity
                     ▲
                     │
ServiceImpl ──> Dao ─┘

Mapper.xml ──> Dao (namespace)
Mapper.xml ──> Entity (resultMap.type)

DTO（面向接口层，通常由 Controller/Service 使用，模板中不强绑定）
```

## 5. 全局宏与关键函数（global-config/define.java）

文件：[define.java](file:///Users/codeme/workspaces/velocity-easycode/global-config/define.java)

### 5.1 `#setTableSuffix($suffix)`

- 位置：[define.java:L4-L6](file:///Users/codeme/workspaces/velocity-easycode/global-config/define.java#L4-L6)
- 职责：设置 `$tableName = 表名 + 后缀`，用于可选的“按后缀生成”策略（具体是否使用取决于模板/生成器）。

### 5.2 `#setPackageSuffix($suffix)`

- 位置：[define.java:L9-L11](file:///Users/codeme/workspaces/velocity-easycode/global-config/define.java#L9-L11)
- 职责：按 `{savePackageName}.{suffix}` 生成 `package` 语句（suffix 为空时不输出 `package` 关键字）。

### 5.3 `#save($path, $fileName)`

- 位置：[define.java:L14-L17](file:///Users/codeme/workspaces/velocity-easycode/global-config/define.java#L14-L17)
- 职责：统一设置输出目录与文件名：
  - `savePath = tableInfo.savePath + path`
  - `fileName = tableInfo.name + fileName`

### 5.4 `#tableComment($desc)`

- 位置：[define.java:L20-L27](file:///Users/codeme/workspaces/velocity-easycode/global-config/define.java#L20-L27)
- 职责：生成 JavaDoc 注释块，包含表注释、作者、生成时间等。

### 5.5 `#getSetMethod($column)`

- 位置：[define.java:L30-L39](file:///Users/codeme/workspaces/velocity-easycode/global-config/define.java#L30-L39)
- 职责：生成 getter/setter（当前模板主要使用 Lombok `@Data`，此宏适用于不使用 Lombok 的扩展场景）。

### 5.6 `#convertBooleanNamingStyle($input)`

- 位置：[define.java:L42](file:///Users/codeme/workspaces/velocity-easycode/global-config/define.java#L42)
- 职责：布尔字段命名转换：把以 `is` 开头的属性名转为去掉 `is` 前缀并首字母小写的形式。
- 影响范围：
  - Entity 字段名（见 [entity.java](file:///Users/codeme/workspaces/velocity-easycode/template/entity.java#L31-L32)）
  - DTO 字段名（见 [dto.java](file:///Users/codeme/workspaces/velocity-easycode/template/dto.java#L37-L39)）
  - Mapper.xml `resultMap` 的 `property`（见 [mapper.xml](file:///Users/codeme/workspaces/velocity-easycode/template/mapper.xml#L19-L23)）

## 6. 主要模板模块（template/）

### 6.1 Controller（template/controller.java）

文件：[controller.java](file:///Users/codeme/workspaces/velocity-easycode/template/controller.java)

- 输出位置：`{savePath}/controller`（见 [controller.java](file:///Users/codeme/workspaces/velocity-easycode/template/controller.java#L3-L6)）
- 输出包名：`{savePackageName}.controller`（见 [controller.java](file:///Users/codeme/workspaces/velocity-easycode/template/controller.java#L11-L12)）
- 关键点：
  - 构造器注入 Service（见 [controller.java](file:///Users/codeme/workspaces/velocity-easycode/template/controller.java#L28-L31)）
  - Swagger `@Api(tags=...)` + Spring `@RestController`（见 [controller.java](file:///Users/codeme/workspaces/velocity-easycode/template/controller.java#L24-L26)）
- 当前模板生成 Controller 骨架，不包含具体路由方法（CRUD 需要自行扩展）。

### 6.2 Service（template/service.java）

文件：[service.java](file:///Users/codeme/workspaces/velocity-easycode/template/service.java)

- 输出位置：`{savePath}/service`（见 [service.java](file:///Users/codeme/workspaces/velocity-easycode/template/service.java#L3-L6)）
- 输出包名：`{savePackageName}.service`（见 [service.java](file:///Users/codeme/workspaces/velocity-easycode/template/service.java#L12-L13)）
- 关键点：
  - `interface XxxService extends IService<Xxx>`（见 [service.java](file:///Users/codeme/workspaces/velocity-easycode/template/service.java#L14-L25)）

### 6.3 ServiceImpl（template/serviceImpl.java）

文件：[serviceImpl.java](file:///Users/codeme/workspaces/velocity-easycode/template/serviceImpl.java)

- 输出位置：`{savePath}/service/impl`（见 [serviceImpl.java](file:///Users/codeme/workspaces/velocity-easycode/template/serviceImpl.java#L3-L6)）
- 输出包名：`{savePackageName}.service.impl`（见 [serviceImpl.java](file:///Users/codeme/workspaces/velocity-easycode/template/serviceImpl.java#L12-L13)）
- 关键点：
  - `@Service`（见 [serviceImpl.java](file:///Users/codeme/workspaces/velocity-easycode/template/serviceImpl.java#L27-L28)）
  - `extends ServiceImpl<XxxDao, Xxx> implements XxxService`（见 [serviceImpl.java](file:///Users/codeme/workspaces/velocity-easycode/template/serviceImpl.java#L28-L29)）
  - 构造器注入 Dao（见 [serviceImpl.java](file:///Users/codeme/workspaces/velocity-easycode/template/serviceImpl.java#L30-L34)）

### 6.4 Dao（template/dao.java）

文件：[dao.java](file:///Users/codeme/workspaces/velocity-easycode/template/dao.java)

- 输出位置：`{savePath}/dao`（见 [dao.java](file:///Users/codeme/workspaces/velocity-easycode/template/dao.java#L3-L6)）
- 输出包名：`{savePackageName}.dao`（见 [dao.java](file:///Users/codeme/workspaces/velocity-easycode/template/dao.java#L12-L13)）
- 关键点：
  - `@Repository`（见 [dao.java](file:///Users/codeme/workspaces/velocity-easycode/template/dao.java#L25-L27)）
  - `interface XxxDao extends BaseMapper<Xxx>`（见 [dao.java](file:///Users/codeme/workspaces/velocity-easycode/template/dao.java#L26-L27)）

### 6.5 Entity（template/entity.java）

文件：[entity.java](file:///Users/codeme/workspaces/velocity-easycode/template/entity.java)

- 输出位置：通过 `#save("/entity", ".java")`（见 [entity.java](file:///Users/codeme/workspaces/velocity-easycode/template/entity.java#L4-L6)）
- 输出包名：通过 `#setPackageSuffix("entity")`（见 [entity.java](file:///Users/codeme/workspaces/velocity-easycode/template/entity.java#L7-L9)）
- 关键点：
  - Lombok：`@Data`（见 [entity.java](file:///Users/codeme/workspaces/velocity-easycode/template/entity.java#L18-L21)）
  - MyBatis-Plus：`@TableName("$tableInfo.obj.name")`（见 [entity.java](file:///Users/codeme/workspaces/velocity-easycode/template/entity.java#L18-L20)）
  - 列遍历生成字段（见 [entity.java](file:///Users/codeme/workspaces/velocity-easycode/template/entity.java#L22-L32)）
  - Boolean 类型特殊映射：当列类型为 `java.lang.Boolean` 时输出 `@TableField("${column.obj.name}")`（见 [entity.java](file:///Users/codeme/workspaces/velocity-easycode/template/entity.java#L28-L31)）

### 6.6 DTO（template/dto.java）

文件：[dto.java](file:///Users/codeme/workspaces/velocity-easycode/template/dto.java)

- 输出位置：`{savePath}/dto`（见 [dto.java](file:///Users/codeme/workspaces/velocity-easycode/template/dto.java#L5-L8)）
- 输出包名：通过 `#setPackageSuffix("dto")`（见 [dto.java](file:///Users/codeme/workspaces/velocity-easycode/template/dto.java#L9-L11)）
- 关键点：
  - Swagger：`@ApiModel` / `@ApiModelProperty`（见 [dto.java](file:///Users/codeme/workspaces/velocity-easycode/template/dto.java#L23-L39)）
  - 校验注解（JSR-303 + Hibernate Validator）：
    - String 字段加 `@Length(max=...)`（见 [dto.java](file:///Users/codeme/workspaces/velocity-easycode/template/dto.java#L29-L32)）
    - 非空字段根据类型输出 `@NotBlank` / `@NotNull`（见 [dto.java](file:///Users/codeme/workspaces/velocity-easycode/template/dto.java#L32-L36)）
  - 分组校验接口：`Update` / `Insert`（见 [dto.java](file:///Users/codeme/workspaces/velocity-easycode/template/dto.java#L41-L43)）

### 6.7 Mapper.xml（template/mapper.xml）

文件：[mapper.xml](file:///Users/codeme/workspaces/velocity-easycode/template/mapper.xml)

- 输出位置：`${modulePath}/src/main/resources/mapper`（见 [mapper.xml](file:///Users/codeme/workspaces/velocity-easycode/template/mapper.xml#L6-L9)）
- 关键点：
  - `namespace = {savePackageName}.dao.XxxDao`（见 [mapper.xml](file:///Users/codeme/workspaces/velocity-easycode/template/mapper.xml#L16-L18)）
  - `resultMap.type = {savePackageName}.entity.Xxx`（见 [mapper.xml](file:///Users/codeme/workspaces/velocity-easycode/template/mapper.xml#L19-L23)）
  - `property` 使用 `convertBooleanNamingStyle`，`column` 使用 `$column.obj.name`（见 [mapper.xml](file:///Users/codeme/workspaces/velocity-easycode/template/mapper.xml#L19-L23)）

## 7. 生成代码的依赖清单（从模板反推）

仓库自身不携带构建文件（无 Maven/Gradle），但模板生成的代码会依赖以下常见库：

- Spring Web：`org.springframework.web.bind.annotation.*`（见 [controller.java](file:///Users/codeme/workspaces/velocity-easycode/template/controller.java#L13-L16)）
- Swagger：`io.swagger.annotations.*`（见 [controller.java](file:///Users/codeme/workspaces/velocity-easycode/template/controller.java#L13-L16)、[dto.java](file:///Users/codeme/workspaces/velocity-easycode/template/dto.java#L16-L18)）
- Lombok：`lombok.Data`（见 [entity.java](file:///Users/codeme/workspaces/velocity-easycode/template/entity.java#L12-L15)、[dto.java](file:///Users/codeme/workspaces/velocity-easycode/template/dto.java#L13-L16)）
- MyBatis-Plus：
  - `BaseMapper<T>`（见 [dao.java](file:///Users/codeme/workspaces/velocity-easycode/template/dao.java#L14-L17)）
  - `IService<T>` / `ServiceImpl<M, T>`（见 [service.java](file:///Users/codeme/workspaces/velocity-easycode/template/service.java#L14-L16)、[serviceImpl.java](file:///Users/codeme/workspaces/velocity-easycode/template/serviceImpl.java#L14-L18)）
  - `@TableName/@TableField`（见 [entity.java](file:///Users/codeme/workspaces/velocity-easycode/template/entity.java#L11-L15)）
- 校验（Bean Validation + Hibernate Validator）：
  - `javax.validation.constraints.*` / `org.hibernate.validator.constraints.Length`（见 [dto.java](file:///Users/codeme/workspaces/velocity-easycode/template/dto.java#L17-L20)）

## 8. 如何使用（运行/生成方式）

仓库内没有 `main`、没有构建入口，使用方式是把模板交给外部生成器渲染输出。通用步骤：

1. 在生成器里配置数据源，确保能读取表结构并填充 `$tableInfo`（表/列/主键/注释等）。
2. 把 [define.java](file:///Users/codeme/workspaces/velocity-easycode/global-config/define.java) 配置为全局宏（使 `$!define` 生效）。
3. 将 [template](file:///Users/codeme/workspaces/velocity-easycode/template) 下模板分别配置为要生成的文件模板，并设置输出根目录（`$tableInfo.savePath`）与基础包名（`$tableInfo.savePackageName`）。
4. 选择目标表并执行生成，得到 Controller/Service/Dao/Entity/DTO/Mapper.xml 文件。

注意事项：

- `README.md` 明确要求：模板内不要使用 tab 对齐，使用空格对齐，否则生成代码格式可能错乱（见 [README.md](file:///Users/codeme/workspaces/velocity-easycode/README.md#L24-L27)）。

## 9. springboot-init-template 子目录说明

目录：[springboot-init-template](file:///Users/codeme/workspaces/velocity-easycode/springboot-init-template)

- 当前仅包含一个占位 TODO 文档（见 [README.md](file:///Users/codeme/workspaces/velocity-easycode/springboot-init-template/README.md#L1-L4)），推断该目录计划用于放置“初始化工程模板/脚手架基础文件”相关内容，但尚未实现。

