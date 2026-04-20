##定义初始变量
#set($controllerName = $tool.append($tableInfo.name,"AdminController"))
##设置回调
$!callback.setFileName($tool.append($controllerName, ".java"))
$!callback.setSavePath($tool.append($tableInfo.savePath, "/controller"))
##拿到主键
#if(!$tableInfo.pkColumn.isEmpty())
    #set($pk = $tableInfo.pkColumn.get(0))
#end

#if($tableInfo.savePackageName)package $!{tableInfo.savePackageName}.#{end}controller;

import $!{tableInfo.savePackageName}.service.admin.$!{tableInfo.name}AdminService;
import $!{tableInfo.savePackageName}.dto.$!{tableInfo.name}Dto;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import jakarta.validation.constraints.NotNull;

import com.jxqsq.qsq.infrastructure.model.ApiResponse;
import com.jxqsq.qsq.infrastructure.smart.table.TableRequestParam;
import com.jxqsq.qsq.infrastructure.smart.table.TableResultData;

import java.io.Serializable;

 /**
 * $!{tableInfo.comment}($!{tableInfo.name})表控制层
 * <p>创建时间: $time.currTime("yyyy/MM/dd") </p>
 *
 * @author <a href="mailto:$!email" rel="nofollow">$author</a>
 * @version v1.0
 */
@Tag(name = "运营管理/$!{tableInfo.comment}")
@SecurityRequirement(name = HttpHeaders.AUTHORIZATION)
@Validated
@RestController
@RequestMapping("/api/v1/admin/$tool.getJavaName($!{tableInfo.name})s")
@RequiredArgsConstructor
public class $!{controllerName} {
    private final $!{tableInfo.name}AdminService service;

    @Operation(summary = "查询$!{tableInfo.comment}列表")
    @PostMapping("/list")
    public ApiResponse<TableResultData<$!{tableInfo.name}Dto>> list$!{tableInfo.name}(@RequestBody TableRequestParam param) {
        var list = service.pageAdminList(param);
        return ApiResponse.ok(list);
    }

    @Operation(summary = "查询$!{tableInfo.comment}详情")
    @GetMapping("/{id}")
    public ApiResponse<$!{tableInfo.name}Dto> get$!{tableInfo.name}(@PathVariable @NotNull Serializable id) {
        var dto = service.get(id);
        return ApiResponse.ok(dto);
    }

    @Operation(summary = "新增$!{tableInfo.comment}")
    @PostMapping
    public void post$!{tableInfo.name}(@Validated @RequestBody $!{tableInfo.name}Dto dto) {
        service.save(dto);
    }

    @Operation(summary = "更新$!{tableInfo.comment}")
    @PutMapping
    public void put$!{tableInfo.name}(@Validated @RequestBody $!{tableInfo.name}Dto dto) {
        service.update(dto);
    }
    
}
