package jolie.mustache;


import jolie.runtime.Value;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

public class JolieMustacheTest {

    JolieMustache jolieMustache = new JolieMustache();
    @Test
    public void simpleSubstitution() throws IOException {
          String template = new String(getClass().getClassLoader().getResourceAsStream("simpleSubstitution/template.mustache").readAllBytes());
          Value request = Value.create();
          request.getFirstChild("template").setValue(template);
          request.getFirstChild("data").getFirstChild("hello").setValue("ciao");;
          request.getFirstChild("data").getFirstChild("world").setValue("mondo");
          String result = jolieMustache.mustacheCompile(request);
          String expectedResult = new String(getClass().getClassLoader().getResourceAsStream("simpleSubstitution/result.txt").readAllBytes());
          Assert.assertEquals(expectedResult , result );

    }

    @Test
    public void arraySubstitution() throws IOException {
        String template = new String(getClass().getClassLoader().getResourceAsStream("arraySubstitution/template.mustache").readAllBytes());
        Value request = Value.create();
        request.getFirstChild("template").setValue(template);
        Value language = Value.create();

        language.getFirstChild("hello").setValue("ciao");
        language.getFirstChild("world").setValue("mondo");
        request.getFirstChild("data").getChildren("languages").add(language);
        Value language1 = Value.create();
        language1.getFirstChild("hello").setValue("szia");
        language1.getFirstChild("world").setValue("világ");
        request.getFirstChild("data").getChildren("languages").add(language1);
        String result = jolieMustache.mustacheCompile(request);
        System.out.println(result);

        String expectedResult = new String(getClass().getClassLoader().getResourceAsStream("arraySubstitution/result.txt").readAllBytes());
        Assert.assertEquals(expectedResult , result );

    }


    @Test
    public void arraySubstitutionWithHeader() throws IOException {
        String template = new String(getClass().getClassLoader().getResourceAsStream("arraySubstitutionWithHeader/template.mustache").readAllBytes());
        Value request = Value.create();
        request.getFirstChild("template").setValue(template);
        Value language = Value.create();

        language.getFirstChild("hello").setValue("ciao");
        language.getFirstChild("world").setValue("mondo");
        request.getFirstChild("data").getChildren("languages").add(language);
        Value language1 = Value.create();
        language1.getFirstChild("hello").setValue("szia");
        language1.getFirstChild("world").setValue("világ");
        request.getFirstChild("data").getChildren("languages").add(language1);
        request.getFirstChild("data").getFirstChild("withHeader").setValue(Boolean.TRUE);
        String result = jolieMustache.mustacheCompile(request);
        System.out.println(result);

        String expectedResult = new String(getClass().getClassLoader().getResourceAsStream("arraySubstitutionWithHeader/result.txt").readAllBytes());
        Assert.assertEquals(expectedResult , result );

    }


    @Test
    public void negationSubstitution() throws IOException {
        String template = new String(getClass().getClassLoader().getResourceAsStream("negationSubstitution/template.mustache").readAllBytes());
        Value request = Value.create();
        request.getFirstChild("template").setValue(template);

        request.getFirstChild("data").getFirstChild("withoutComment").setValue(Boolean.FALSE);
        String result = jolieMustache.mustacheCompile(request);
        System.out.println(result);

        String expectedResult = new String(getClass().getClassLoader().getResourceAsStream("negationSubstitution//result.txt").readAllBytes());
        Assert.assertEquals(expectedResult , result );

    }


    @Test
    public void lastSubstitution() throws IOException {
        String template = new String(getClass().getClassLoader().getResourceAsStream("lastSubstitution/template.mustache").readAllBytes());
        Value request = Value.create();
        request.getFirstChild("template").setValue(template);

        Value value = Value.create();

        value.getFirstChild("nodeName").setValue("nodeName");
        value.getFirstChild("value").setValue("nodeValue");
        request.getFirstChild("data").getChildren("values").add(value);
        Value value1 = Value.create();

        value1.getFirstChild("nodeName").setValue("nodeName1");
        value1.getFirstChild("value").setValue("nodeValue1");
        request.getFirstChild("data").getChildren("values").add(value1);
        String result = jolieMustache.mustacheCompile(request);
        System.out.println(result);

        String expectedResult = new String(getClass().getClassLoader().getResourceAsStream("lastSubstitution//result.txt").readAllBytes());
        Assert.assertEquals(expectedResult , result );

    }

}