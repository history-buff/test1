package cn.itcast.estore.utils;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ResourceBundle;

//抽取事物管理动态代理工具类
public class ProxyUtils<T> {
	private T target;

	public ProxyUtils(T t) {
		this.target = t;
	}

	@SuppressWarnings("unchecked")
	public T getProxy() {
		return (T) Proxy.newProxyInstance(target.getClass().getClassLoader(),
				target.getClass().getInterfaces(), new InvocationHandler() {

					@Override
					public Object invoke(Object proxy, Method method,
							Object[] args) throws Throwable {
						String method1 = ResourceBundle
								.getBundle("transaction").getString("save");
						String method2 = ResourceBundle
								.getBundle("transaction").getString("delete");
						String method3 = ResourceBundle
								.getBundle("transaction").getString("update");
						// TODO Auto-generated method stub
						if (method.getName().startsWith(method1)
								|| method.getName().startsWith(method2)
								|| method.getName().startsWith(method3)) {
							try {
								TransactionManager.begin();
								return method.invoke(target, args);

							} catch (Exception e) {
								// TODO: handle exception
								TransactionManager.rollback();
								throw new RuntimeException(e);
							} finally {
								TransactionManager.commit();
							}

						} else {
							return method.invoke(target, args);
						}
					}
				});

	}

}
