
package nl.sogyo.webserver;


public class Parameter {
    private String parameterName;
    private String parameterValue;
    private String [] line_name_value;
    
    public Parameter (String line){
        line_name_value = line.split("=");
        parameterName = line_name_value[0];
        parameterValue = line_name_value[1];
    }
    
    public String getParameterName(){
        return parameterName;
    }
    
    public String getParameterValue(){
        return parameterValue;
    }
}
