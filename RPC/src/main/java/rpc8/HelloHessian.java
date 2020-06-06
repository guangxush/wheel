package rpc8;

import common.User;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

/**
 * @author: guangxush
 * @create: 2020/05/03
 */
public class HelloHessian {
    public static void main(String[] args) {
        User user = new User(1, "Sandy");
        //System.out.println(serialize(user));
    }

//    public static byte[] serialize(Object o)throws Exception{
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        Hessian2Output output = new Hessian2OutPut(baos);
//        output.writeObject(o);
//        output.flush();
//        byte[] bytes = baos.toByteArray();
//        baos.close();
//        output.close();
//        return bytes;
//    }
//
//    public static Object deserialize(byte[] bytes) throws Exception{
//        ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
//        Hessian2Input input = new Hessian2Input(bais);
//        Object o = input.readObject();
//        bais.close();
//        input.close();
//        return o;
//    }
}
