##定义初始变量
#set($serviceName = $tool.append($tableInfo.name, "Service"))
##设置回调
$!callback.setFileName($tool.append($serviceName, ".java"))
$!callback.setSavePath($tool.append($tableInfo.savePath, "/service"))

##拿到主键
#if(!$tableInfo.pkColumn.isEmpty())
    #set($pk = $tableInfo.pkColumn.get(0))
#end

#if($tableInfo.savePackageName)package $!{tableInfo.savePackageName}.#{end}service;

import $!{tableInfo.savePackageName}.entity.$!{tableInfo.name};
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>描述: [$tableInfo.name 服务层] </p>
 * <p>创建时间: $time.currTime("yyyy/MM/dd") </p>
 *
 * @author <a href="mailto:$!email" rel="nofollow">$author</a>
 * @version v1.0
 */
public interface $!{serviceName} extends IService<$!{tableInfo.name}> {

}