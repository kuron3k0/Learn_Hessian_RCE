import com.caucho.hessian.io.HessianInput;
import com.caucho.hessian.io.HessianOutput;

import java.io.*;
import java.sql.SQLData;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SerialTest {

    public static <T> byte[] serialize(T t) {
        byte[] data = null;
        try {
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            HessianOutput output = new HessianOutput(os);
            output.writeObject(t);
            data = os.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }

    public static <T> T deserialize(byte[] data) {
        if (data == null) {
            return null;
        }
        Object result = null;
        try {
            ByteArrayInputStream is = new ByteArrayInputStream(data);
            HessianInput input = new HessianInput(is);
            result = input.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return (T) result;
    }

    public static void main(String[] args) throws IOException {
        //System.setProperty("com.sun.jndi.ldap.object.trustURLCodebase", "true");

        DataInputStream is = new DataInputStream(
                new BufferedInputStream(new FileInputStream(
                        "D:\\tools\\expxp2")));
        byte[] b =new byte[8000];
        is.read(b);
        is.close();
        System.out.println(new String(b));
        System.out.println("---------------hessian deserialize--------------");
        deserialize(b);

    }
}