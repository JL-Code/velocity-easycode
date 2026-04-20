##引入宏定义
$!define

#set($dtoName = $tool.append($tableInfo.name, "Dto"))
##设置回调
$!callback.setFileName($tool.append($dtoName, ".java"))
$!callback.setSavePath($tool.append($tableInfo.savePath, "/dto"))

##使用宏定义设置包后缀
#setPackageSuffix("dto")

##使用全局变量实现默认包导入
$!autoImport

## 定义变量
#set($excludeFields = ["version", "deleted", "modifier", "creator", "creatorId",  "modifierId"])
#set($auditFields = ["modifier", "creator", "creatorId",  "modifierId","createdAt","modifiedAt"])

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;
import org.hibernate.validator.constraints.Length;
import jakarta.validation.constraints.*;

import com.fasterxml.jackson.annotation.JsonInclude;

import static io.swagger.v3.oas.annotations.media.Schema.AccessMode.READ_ONLY;

##使用宏定义实现类注释信息
#tableComment("数据传输对象")
@Schema(description = "$!{tableInfo.comment} Dto")
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class $!{tableInfo.name}Dto implements Serializable {

#foreach($column in $tableInfo.fullColumn)

#if($excludeFields.contains($column.name))
#else
    /**
    * ${column.comment}
    */
#if($column.type.equals("java.lang.String") && $column.obj.dataType.length != -1)
    @Length(max=$column.obj.dataType.length)
#end
#if($column.obj.isNotNull() && $column.type.equals("java.lang.String") && !$auditFields.contains($column.name))
    @NotBlank
#elseif($column.obj.isNotNull() && !$auditFields.contains($column.name) && !$column.name.equals("id"))
    @NotNull
#end
#if($auditFields.contains($column.name) || $column.name.equals("id"))
    @Schema(description = "$!{column.comment}", accessMode = READ_ONLY)
#else
    @Schema(description = "$!{column.comment}")
#end
    private $!{tool.getClsNameByFullName($column.type)} #convertBooleanNamingStyle($column.name);
#end
#end
}