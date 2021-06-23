##定义初始变量
#set($controllerName = $tool.append($tableInfo.name, "Controller"))
##设置回调
$!callback.setFileName($tool.append($controllerName, ".java"))
$!callback.setSavePath($tool.append($tableInfo.savePath, "/controller"))
##拿到主键
#if(!$tableInfo.pkColumn.isEmpty())
    #set($pk = $tableInfo.pkColumn.get(0))
#end

#if($tableInfo.savePackageName)package $!{tableInfo.savePackageName}.#{end}controller;

import $!{tableInfo.savePackageName}.service.$!{tableInfo.name}Service;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.*;

/**
 * $!{tableInfo.comment}($!{tableInfo.name})表控制层
 *
 * @author $!author
 * @since $!time.currTime()
 */
@Api(tags = "$!{tableInfo.comment}")
@RestController
public class $!{controllerName} {

    private $!{tableInfo.name}Service service;
    public $!{controllerName}($!{tableInfo.name}Service service) {
        this.service = service;
    }
    
    // region 查询

    @Operation(summary = "列出费用定额标准")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "companyId", value = "公司ID"),
            @ApiImplicitParam(name = "year", value = "年份"),
            @ApiImplicitParam(name = "keyword", value = "关键字(编号、名称、所属类型)")
    })
    @GetMapping()
    public List<ExpenseQuotaStandardDTO> listQuotaStandard(String companyId, Integer year, String keyword) {
        return null;
    }

    @Operation(summary = "获取一个费用定额标准")
    @ApiImplicitParam(name = "id", value = "费用定额标准 id")
    @GetMapping("/{id}")
    public Object getQuotaStandard(@PathVariable("id") String id) {
        return null;
    }

    @Operation(summary = "删除费用定额标准前验证")
    @ApiImplicitParam(name = "id", value = "费用定额标准 id")
    @GetMapping("/{id}/deletable")
    public void verifyBeforeDeleting(@PathVariable("id") String id) {

    }

    // endregion

    // region 操作

    @Operation(summary = "新增一个费用定额标准")
    @PostMapping()
    public void insert(ExpenseQuotaStandardDTO dto) {

    }
    @Operation(summary = "更新一个费用定额标准")
    @PutMapping()
    public void update(ExpenseQuotaStandardDTO dto) {

    }
    @Operation(summary = "删除一个费用定额标准")
    @ApiImplicitParam(name = "id", value = "费用定额标准 id")
    @DeleteMapping()
    public void delete(String id) {

    }

    // endregion
}
