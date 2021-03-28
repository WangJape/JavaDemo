package intercetpor;

import org.apache.ibatis.executor.statement.RoutingStatementHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.RowBounds;

import java.lang.reflect.Field;
import java.sql.Connection;

/**
 * 在 mybatis 中， 可以拦截的方法包括
 * Executor (update, query, flushStatements, commit, rollback, getTransaction, close, isClosed)
 * ParameterHandler (getParameterObject, setParameters)
 * ResultSetHandler (handleResultSets, handleOutputParameters)
 * StatementHandler (prepare, parameterize, batch, update, query)
 * 但是接口只有一个 Interceptor， 因此， 需要使用注解 @Intercepts和 @Signature 来指定拦截的方法。
 */
/*@Intercepts({
        @Signature(
                type = Executor.class,
                method = "query",
                args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class}
        )
})*/
@Intercepts({
        @Signature(method = "prepare", type = StatementHandler.class, args = {Connection.class, Integer.class})})
public class PageInterceptor implements Interceptor {

    private static final ThreadLocal<RowBounds> LOCAL_PAGE = new ThreadLocal<>();

    public static void startPage(int pageNo, int pageSize) {
        RowBounds page = new RowBounds((pageNo - 1) * pageSize, pageSize);
        LOCAL_PAGE.set(page);
    }

    /**
     * 执行拦截逻辑的方法
     *
     * @param invocation 调用信息
     * @return 调用结果
     */
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        //  获取分页参数
        RowBounds rowBounds = LOCAL_PAGE.get();
        if (rowBounds != null) {
            RoutingStatementHandler handler = (RoutingStatementHandler) invocation.getTarget();
            BoundSql boundSql = handler.getBoundSql();
            String oldSql = boundSql.getSql();
            String pagingSql = assembleSql(oldSql, rowBounds);
            setFieldValue(boundSql, "sql", pagingSql);

            LOCAL_PAGE.remove();
        }

        return invocation.proceed();
    }


    //代理类
    //Object plugin(Object target);

    //根据配置来初始化 Interceptor 方法
    //void setProperties(Properties properties);


    public String assembleSql(String oldSql, RowBounds rowBounds) throws Exception {
        return oldSql + " limit " + rowBounds.getOffset() + "," + rowBounds.getLimit();
    }

    private Object getFieldValue(Object object, String fieldName) {
        Class<?> clazz = object.getClass();
        Field field;
        Object value = null;

        while (clazz != null) {
            try {
                field = clazz.getDeclaredField(fieldName);
                field.setAccessible(true);
                value = field.get(object);
                break;
            } catch (NoSuchFieldException e) {
                clazz = clazz.getSuperclass();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                break;
            }
        }
        return value;
    }

    private void setFieldValue(Object object, String fieldName, Object value) {
        Class<?> clazz = object.getClass();
        Field field = null;

        while (clazz != null) {
            try {
                field = clazz.getDeclaredField(fieldName);
                field.setAccessible(true);
                field.set(object, value);
                break;
            } catch (NoSuchFieldException e) {
                clazz = clazz.getSuperclass();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                break;
            }
        }
    }

}
