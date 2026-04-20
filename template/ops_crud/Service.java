##定义初始变量
#set($serviceName = $tool.append($tableInfo.name, "AdminService"))
##设置回调
$!callback.setFileName($tool.append($serviceName, ".java"))
$!callback.setSavePath($tool.append($tableInfo.savePath, "/service/admin"))

##拿到主键
#if(!$tableInfo.pkColumn.isEmpty())
    #set($pk = $tableInfo.pkColumn.get(0))
#end

#if($tableInfo.savePackageName)package $!{tableInfo.savePackageName}.#{end}service.admin;

import com.baomidou.mybatisplus.extension.service.IService;
import $!{tableInfo.savePackageName}.dto.$!{tableInfo.name}Dto;
import $!{tableInfo.savePackageName}.entity.$!{tableInfo.name};
import com.jxqsq.qsq.infrastructure.smart.table.TableRequestParam;
import com.jxqsq.qsq.infrastructure.smart.table.TableResultData;

import java.io.Serializable;
/**
 * <p>描述: [$tableInfo.name 服务层] </p>
 * <p>创建时间: $time.currTime("yyyy/MM/dd") </p>
 *
 * @author <a href="mailto:$!email" rel="nofollow">$author</a>
 * @version v1.0
 */
public interface $!{serviceName} extends IService<$!{tableInfo.name}> {

    TableResultData<$!{tableInfo.name}Dto> pageAdminList(TableRequestParam param);

    /**
     * 获取单个$!{tableInfo.comment}信息
     * @param id 主键
     * @return Dto
     */
    $!{tableInfo.name}Dto get(Serializable id);

    /**
     * 新增$!{tableInfo.comment}信息
     * @param dto $!{tableInfo.comment}信息
     */
    void save($!{tableInfo.name}Dto dto);

    /**
     * 更新$!{tableInfo.comment}信息
     * @param dto $!{tableInfo.comment}信息
     */
    void update($!{tableInfo.name}Dto dto);

    /**
     * 删除$!{tableInfo.comment}信息
     * @param id 主键
     */
    void remove(Serializable id);

}