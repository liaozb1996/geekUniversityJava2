package geek.java.week01.no2;

import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * @author: liaozibo
 * @date: 2021/3/20
 */
public class XClassLoader extends ClassLoader{

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        URL resource = getResource(name);
        String path = resource.getPath();
        if (path.contains(":")) {
            path = path.substring(1); // remove root path character '/'  from path on Windows
        }
        try {
            byte[] bytes = Files.readAllBytes(Paths.get(path));
            byte[] result = decode(bytes);
            return defineClass(null, result, 0, bytes.length);
        } catch (IOException e) {
            throw new ClassNotFoundException("loading class fail", e);
        }
    }

    private byte[] decode(byte[] source) {
        byte[] result = new byte[source.length];
        for (int i = 0; i < source.length; i++) {
            result[i] = (byte) (255 - source[i]);
        }
        return result;
    }

    public static void main(String[] args) {
        String name = "Hello.xlass";
        try {
            Class<?> helloClass = new XClassLoader().loadClass(name);
            Object object = helloClass.newInstance();
            Method method = helloClass.getMethod("hello");
            method.invoke(object);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
