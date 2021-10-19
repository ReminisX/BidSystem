package com.nari.bidsystem.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.BlockAttackInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.OptimisticLockerInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author ZhangXD
 * @Date 2021/10/18 17:21
 * @Description
 */
@Configuration
@MapperScan("com.myspring.demo.mapper")
class MybatisPlusConfig {

    /**
     * 建议采用如下顺序依次进行设置
     * 多租户,动态表名
     * 分页,乐观锁
     * sql性能规范,防止全表更新与删除
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        // 多租户
//        interceptor.addInnerInterceptor(new TenantLineInnerInterceptor());
        //动态表名
//        interceptor.addInnerInterceptor(new DynamicTableNameInnerInterceptor());

        // 乐观锁，需要在实体类的数据上加@Version注解
        interceptor.addInnerInterceptor(new OptimisticLockerInnerInterceptor());
        // 新的分页插件,一缓和二缓遵循mybatis的规则,需要设置 MybatisConfiguration#useDeprecatedExecutor = false 避免缓存出现问题(该属性会在旧插件移除后一同移除)
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));

        // 防止全表更新和删除
        interceptor.addInnerInterceptor(new BlockAttackInnerInterceptor());
        // sql性能规范
//        interceptor.addInnerInterceptor(new IllegalSQLInnerInterceptor());
        return interceptor;
    }
}
