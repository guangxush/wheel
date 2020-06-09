package rpc8.util;

import com.caucho.hessian.io.Hessian2Input;
import com.caucho.hessian.io.Hessian2Output;
import common.User;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

/**
 * @author: guangxush
 * @create: 2020/05/03
 */
public class HessianSDK {
    public static void main(String[] args) throws Exception {
        User user = new User(1, "Sandy");
        byte[] bytes = serialize(user);
        System.out.println(bytes.length);
        User u1 = (User)deserialize(bytes);
        System.out.println(u1);
    }

    public static byte[] serialize(Object o)throws Exception{
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Hessian2Output output = new Hessian2Output(baos);
        output.writeObject(o);
        output.flush();
        byte[] bytes = baos.toByteArray();
        baos.close();
        output.close();
        return bytes;
    }

    public static Object deserialize(byte[] bytes) throws Exception{
        ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
        Hessian2Input input = new Hessian2Input(bais);
        Object o = input.readObject();
        bais.close();
        input.close();
        return o;
    }
}
