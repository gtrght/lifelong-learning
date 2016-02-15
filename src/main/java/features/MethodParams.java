package features;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * User: Vasily Vlasov
 * Date: 06.06.13
 */

interface MethodParamsInterface {
    void simpleMethod(int parm1, int parm2);
    void simpleMethod(int parm1, int parm2, int param3);
}

public class MethodParams implements MethodParamsInterface {
    public void simpleMethod(int parm1, int parm2) {
        //business logic to be put there
    }

    public void simpleMethod(int parm1, int parm2, int param3) {
        //business logic to be put there
    }

    public MethodParamsInterface wrappedInstance() throws Exception {
        Class<?> proxyClass = Proxy.getProxyClass(MethodParams.class.getClassLoader(), MethodParamsInterface.class);
        InvocationHandler invocationHandler = new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                Map<String, Object> params = new LinkedHashMap<String, Object>(args.length);
                for (int i = 0; i < args.length; i++)
                    params.put("arg" + i, args[i]);


                //printing out the parameters:
                for (Map.Entry<String, Object> paramValue : params.entrySet()) {
                    System.out.println(paramValue.getKey() + " : " + paramValue.getValue());
                }

                return MethodParams.this.getClass().getMethod(method.getName(), method.getParameterTypes()).invoke(MethodParams.this, args);
            }
        };
        return (MethodParamsInterface) proxyClass.getConstructor(new Class[]{InvocationHandler.class}).newInstance(invocationHandler);
    }


    public static void main(String[] args) throws Exception {
        MethodParams instance = new MethodParams();
        MethodParamsInterface wrapped = instance.wrappedInstance();

        System.out.println("First method call: ");

        wrapped.simpleMethod(10, 20);

        System.out.println("Another method call: ");

        wrapped.simpleMethod(10, 20, 30);
    }
}
