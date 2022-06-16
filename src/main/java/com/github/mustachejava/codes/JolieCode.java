package com.github.mustachejava.codes;
import com.github.mustachejava.*;
import com.github.mustachejava.codes.DefaultCode;

public class JolieCode  extends  DefaultCode {

    public JolieCode(TemplateContext tc, DefaultMustacheFactory df, Mustache mustache, String name, String type , String appended ) {
        super(tc, df, mustache, name, type);
        this.appended = appended;
    }

    public static JolieCode fromIterableCode (DefaultCode defaultCode) {
        return new JolieCode(defaultCode.tc, defaultCode.df, defaultCode.mustache, defaultCode.name, defaultCode.type ,defaultCode.appended);
    }
    public String  getType(){
        return this.type;
    }

    public  Mustache getMustache(){
         return  this.mustache;
    }

    public  String getAppend(){
        return this.appended;
    }

}
