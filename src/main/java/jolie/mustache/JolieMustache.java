package jolie.mustache;

import com.github.mustachejava.Code;
import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;
import com.github.mustachejava.codes.DefaultCode;
import com.github.mustachejava.codes.JolieCode;
import jolie.runtime.JavaService;
import jolie.runtime.Value;
import jolie.runtime.embedding.RequestResponse;

import java.io.StringReader;
import java.util.Arrays;
import java.util.Iterator;


public class JolieMustache  extends JavaService {
    @RequestResponse String mustacheCompile (Value request){
        DefaultMustacheFactory defaultMustacheFactory = new DefaultMustacheFactory();
        Mustache mustache = defaultMustacheFactory.compile(new StringReader(request.getFirstChild("template").strValue()), "Jolie");
        StringBuilder sb = new StringBuilder();
        parseMustache(mustache , request.getFirstChild("data") ,sb  , false);
        return sb.toString();
    }

    private void parseMustache(Mustache mustache, Value value , StringBuilder sb , boolean isLast) {
        Iterator<Code> iterator = Arrays.stream(mustache.getCodes()).iterator();
        while (iterator.hasNext()) {
            DefaultCode code = (DefaultCode) iterator.next();
            JolieCode jolieCode = JolieCode.fromIterableCode(code);
            if (jolieCode.getType()!= null && "#".equals(jolieCode.getType())) {

                for (int counter = 0 ; counter < value.getChildren(jolieCode.getName()).size() ; counter++){

                    parseMustache(jolieCode.getMustache(), value.getChildren(jolieCode.getName()).get(counter), sb , counter ==value.getChildren(jolieCode.getName()).size()-1 );

                }
                sb.append(jolieCode.getAppend()!= null? jolieCode.getAppend() : "" ) ;


            }else if (jolieCode.getType()!= null && "^".equals(jolieCode.getType())){
                if (!"-last".equals(jolieCode.getName()) && (!value.getFirstChild(jolieCode.getName()).boolValue() ||  !value.hasChildren(jolieCode.getName()))){
                    parseMustache(jolieCode.getMustache(), value.getFirstChild(jolieCode.getName()), sb ,isLast);
                }else if  ("-last".equals(jolieCode.getName()) && !isLast){
                    parseMustache(jolieCode.getMustache(), value.getFirstChild(jolieCode.getName()), sb ,isLast);
                    sb.append(jolieCode.getAppend()!= null? jolieCode.getAppend() : "" ) ;
                }else if  ("-last".equals(jolieCode.getName()) && isLast) {
                    sb.append(jolieCode.getAppend()!= null? jolieCode.getAppend() : "" ) ;
                }

            }else if (jolieCode.getName() != null ){
                sb.append(value.getFirstChild(jolieCode.getName()).strValue() ).append(jolieCode.getAppend()!= null? jolieCode.getAppend() : "" ) ;

            }else if (jolieCode.getAppend()!= null){
                sb.append(jolieCode.getAppend());
            }

        }

    }
}
