/**
 * 
 */
package mybatis.intercepts;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

import org.apache.ibatis.builder.xml.dynamic.ForEachSqlNode;
import org.apache.ibatis.executor.ErrorContext;
import org.apache.ibatis.executor.ExecutorException;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.ParameterMode;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.property.PropertyTokenizer;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.type.TypeHandler;
import org.apache.ibatis.type.TypeHandlerRegistry;

import mybatis.Pagination.Pagination;


/**
 * @author tsong_jp
 *
 */
@Intercepts({@Signature(type=StatementHandler.class,method="prepare",args={Connection.class})})  
public class PagingInterceptor implements Interceptor {

	/* (non-Javadoc)
	 * @see org.apache.ibatis.plugin.Interceptor#intercept(org.apache.ibatis.plugin.Invocation)
	 */
	@Override
	public Object intercept(Invocation inv) throws Throwable {

		Object target = inv.getTarget();
		if (target instanceof StatementHandler) {
			
			StatementHandler sh = (StatementHandler)target;
			
			Object delegate = getFieldObjectByFieldName(sh, "delegate");
			
			//PreparedStatementHandler psh = (PreparedStatementHandler)delegate;
            MappedStatement mappedStatement = (MappedStatement) getFieldObjectByFieldName(delegate, "mappedStatement");  
			RowBounds rbs = (RowBounds)getFieldObjectByFieldName(delegate, "rowBounds");
			
			if (rbs instanceof Pagination) {
				
				Pagination pgit = (Pagination)rbs;
				BoundSql boundSql = sh.getBoundSql();
				
				if (pgit.isTotol()) {
					Connection conn = (Connection)inv.getArgs()[0];
					String countSql = pgit.getCountSql(boundSql.getSql());
	                PreparedStatement countStmt = conn.prepareStatement(countSql);  
	                
	                Object parameterObject = boundSql.getParameterObject();
	                   
	                //List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
	                BoundSql countBS = new BoundSql(mappedStatement.getConfiguration(),countSql,boundSql.getParameterMappings(),parameterObject);  
                    
	                setParameters(countStmt,mappedStatement,countBS,parameterObject);  
	                ResultSet rs = countStmt.executeQuery();  
                    int count = 0;  
                    if (rs.next()) {  
                        count = rs.getInt(1);  
                    }  
                    rs.close();  
                    countStmt.close();
                    
                    pgit.setCount(count);
				
				}
				
				setFieldObjectByFieldName(boundSql, "sql", pgit.getPaginationSql(boundSql.getSql())); //将分页sql语句反射回BoundSql.   

			}
			
		}
		
		return inv.proceed();
	}

	/* (non-Javadoc)
	 * @see org.apache.ibatis.plugin.Interceptor#plugin(java.lang.Object)
	 */
	@Override
	public Object plugin(Object target) {

		return Plugin.wrap(target, this);
	}

	/* (non-Javadoc)
	 * @see org.apache.ibatis.plugin.Interceptor#setProperties(java.util.Properties)
	 */
	@Override
	public void setProperties(Properties props) {
		
	}
	
	/***
	 * 反射工具 
	 * @param obj
	 * @param fieldName
	 * @return
	 */
	private Object getFieldObjectByFieldName(Object obj, String fieldName) {
	    Field field = null;  
        Object value = null;  
		for (Class<?> superClass = obj.getClass(); superClass != Object.class; superClass = superClass
				.getSuperclass()) {
			try {
				field = superClass.getDeclaredField(fieldName);
			} catch (NoSuchFieldException e) {
			}
		}
        if(field!=null){  
            try {
	            if (field.isAccessible()) {  
						value = field.get(obj);
	            } else {  
	                field.setAccessible(true);  
	                value = field.get(obj);  
	                field.setAccessible(false);  
	            }  
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}  
        }  
        return value;  
	}

	/***
	 * 反射工具 
	 * @param obj
	 * @param fieldName
	 * @return
	 */
	private void setFieldObjectByFieldName(Object obj, String fieldName, Object value) {
	    Field field = null;  
		for (Class<?> superClass = obj.getClass(); superClass != Object.class; superClass = superClass
				.getSuperclass()) {
			try {
				field = superClass.getDeclaredField(fieldName);
			} catch (NoSuchFieldException e) {
			}
		}
        if(field!=null){  
            try {
	            if (field.isAccessible()) {  
						field.set(obj, value);
	            } else {  
	                field.setAccessible(true);  
					field.set(obj, value);
	                field.setAccessible(false);  
	            }  
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}  
        }  
	}

	
    /** 
     * 对SQL参数(?)设值,参考org.apache.ibatis.executor.parameter.DefaultParameterHandler 
     * @param ps 
     * @param mappedStatement 
     * @param boundSql 
     * @param parameterObject 
     * @throws SQLException 
     */  
    private void setParameters(PreparedStatement ps,MappedStatement mappedStatement,BoundSql boundSql,Object parameterObject) throws SQLException {  
        ErrorContext.instance().activity("setting parameters").object(mappedStatement.getParameterMap().getId());  
        List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();  
        if (parameterMappings != null) {  
            Configuration configuration = mappedStatement.getConfiguration();  
            TypeHandlerRegistry typeHandlerRegistry = configuration.getTypeHandlerRegistry();  
            MetaObject metaObject = parameterObject == null ? null: configuration.newMetaObject(parameterObject);  
            for (int i = 0; i < parameterMappings.size(); i++) {  
                ParameterMapping parameterMapping = parameterMappings.get(i);  
                if (parameterMapping.getMode() != ParameterMode.OUT) {  
                    Object value;  
                    String propertyName = parameterMapping.getProperty();  
                    PropertyTokenizer prop = new PropertyTokenizer(propertyName);  
                    if (parameterObject == null) {  
                        value = null;  
                    } else if (typeHandlerRegistry.hasTypeHandler(parameterObject.getClass())) {  
                        value = parameterObject;  
                    } else if (boundSql.hasAdditionalParameter(propertyName)) {  
                        value = boundSql.getAdditionalParameter(propertyName);  
                    } else if (propertyName.startsWith(ForEachSqlNode.ITEM_PREFIX)&& boundSql.hasAdditionalParameter(prop.getName())) {  
                        value = boundSql.getAdditionalParameter(prop.getName());  
                        if (value != null) {  
                            value = configuration.newMetaObject(value).getValue(propertyName.substring(prop.getName().length()));  
                        }  
                    } else {  
                        value = metaObject == null ? null : metaObject.getValue(propertyName);  
                    }  
                    TypeHandler typeHandler = parameterMapping.getTypeHandler();  
                    if (typeHandler == null) {  
                        throw new ExecutorException("There was no TypeHandler found for parameter "+ propertyName + " of statement "+ mappedStatement.getId());  
                    }  
                    typeHandler.setParameter(ps, i + 1, value, parameterMapping.getJdbcType());  
                }  
            }  
        }  
    }  

}
