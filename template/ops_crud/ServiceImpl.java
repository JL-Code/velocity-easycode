##定义初始变量
#set($serviceName = $tool.append($tableInfo.name, "AdminServiceImpl"))
##设置回调
$!callback.setFileName($tool.append($serviceName, ".java"))
$!callback.setSavePath($tool.append($tableInfo.savePath, "/service/admin/impl"))

##拿到主键
#if(!$tableInfo.pkColumn.isEmpty())
    #set($pk = $tableInfo.pkColumn.get(0))
#end

#if($tableInfo.savePackageName)package $!{tableInfo.savePackageName}.#{end}service.admin.impl;

import cn.hutool.core.bean.BeanUtil;
import $!{tableInfo.savePackageName}.entity.$!{tableInfo.name};
import $!{tableInfo.savePackageName}.dto.$!{tableInfo.name}Dto;
import $!{tableInfo.savePackageName}.mapper.$!{tableInfo.name}Mapper;
import $!{tableInfo.savePackageName}.service.admin.$!{tableInfo.name}AdminService;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jxqsq.qsq.domain.smart.service.SmartTableService;
import com.jxqsq.qsq.infrastructure.exception.BizException;
import org.springframework.stereotype.Service;
import com.jxqsq.qsq.infrastructure.smart.table.TableRequestParam;
import com.jxqsq.qsq.infrastructure.smart.table.TableResultData;


import java.io.Serializable;
import lombok.RequiredArgsConstructor;
/**
 * <p>描述: [$tableInfo.name 服务实现层] </p>
 * <p>创建时间: $time.currTime("yyyy/MM/dd") </p>
 *
 * @author <a href="mailto:$!email" rel="nofollow">$author</a>
 * @version v1.0
 */
@Service
@RequiredArgsConstructor
public class $!{serviceName} extends ServiceImpl<$!{tableInfo.name}Mapper, $!{tableInfo.name}> implements $!{tableInfo.name}AdminService {
    
    private final SmartTableService smartTableService;

    @Override
    public TableResultData<$!{tableInfo.name}Dto> pageAdminList(TableRequestParam param) {
        param.setSqlStatement("select * from $tableInfo.obj.name");
        return smartTableService.list(param, $!{tableInfo.name}Dto.class);
    }

    @Override
    public $!{tableInfo.name}Dto get(Serializable id) {
        var entity = additionalCheck(id);
        return BeanUtil.copyProperties(entity, $!{tableInfo.name}Dto.class);
    }

    @Override
    public void save($!{tableInfo.name}Dto dto) {
        var entity = BeanUtil.copyProperties(dto, $!{tableInfo.name}.class);
        save(entity);
    }

    @Override
    public void update($!{tableInfo.name}Dto dto) {
        additionalCheck(dto.getId());
        var updated = BeanUtil.copyProperties(dto, $!{tableInfo.name}.class);
        updateById(updated);
    }

    @Override
    public void remove(Serializable id) {
        additionalCheck(id);
        removeById(id);
    }

    private $!{tableInfo.name} additionalCheck(Serializable id) {
        var existing = getById(id);
        if (existing == null) {
            throw BizException.notFound();
        }
        return existing;
    }
}