import com.github.mustachejava.*;
import com.github.mustachejava.codes.DefaultCode;
import com.github.mustachejava.codes.JolieCode;
import jolie.runtime.Value;

import java.io.StringReader;

import java.util.Arrays;
import java.util.Iterator;

public class TestClass {
    public static void main(String[] args) {
        DefaultMustacheFactory defaultMustacheFactory = new DefaultMustacheFactory();
        Mustache mustache = defaultMustacheFactory.compile(new StringReader("{{#operations}}working on something {{hello}},{{world}}!{{/operations}}"), "helloworld");
        Value value = Value.create();
        value.getFirstChild("operations").getFirstChild("hello").setValue("ciao");
        value.getFirstChild("operations").getFirstChild("world").setValue("mondo");
        StringBuilder sb = new StringBuilder();
        parseCode(mustache, value , sb);
        System.out.println(sb.toString());

    }

    private static void parseCode(Mustache mustache, Value value , StringBuilder sb) {
        Iterator<Code> iterator = Arrays.stream(mustache.getCodes()).iterator();
        while (iterator.hasNext()) {
            DefaultCode code = (DefaultCode) iterator.next();
            JolieCode jolieCode = JolieCode.fromIterableCode(code);
            if (jolieCode.getType()!= null && "#".equals(jolieCode.getType())){

                parseCode(jolieCode.getMustache(), value.getFirstChild(jolieCode.getName()) , sb);

            }else if (jolieCode.getName() != null ){
                 sb.append(value.getFirstChild(jolieCode.getName()).strValue() ).append(jolieCode.getAppend()) ;
            }else if (jolieCode.getAppend()!= null){
                 sb.append(jolieCode.getAppend());
            }
        }

    }
}
