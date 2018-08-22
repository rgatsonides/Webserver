
package nl.sogyo.webserver;

public class HeaderParameter {
    String HeaderParameterName;
    String HeaderParameterValue;
    private String [] line_name_value;
    
    public HeaderParameter(String line){
         line_name_value = line.split(":");
         HeaderParameterName = line_name_value[0];
         HeaderParameterValue = line_name_value[1];
    }
    
    public String getHeaderParameterName(){
        return HeaderParameterName;
    }
    
    public String getHeaderParameterValue(){
        return HeaderParameterValue;
    }
    
}
