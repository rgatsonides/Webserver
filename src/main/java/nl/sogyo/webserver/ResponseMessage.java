
package nl.sogyo.webserver;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;


public class ResponseMessage implements Response {
    private RequestMessage req;
    private HttpStatusCode status;
    private String Content;

    public ResponseMessage (RequestMessage request){
        req=request;
    }
    
    @Override
    public HttpStatusCode getStatus(){
        return status;
    }
    
    @Override
    public void setStatus(HttpStatusCode Status){
        status =Status;
    }
    
    @Override
    public ZonedDateTime getDate(){
        return ZonedDateTime.now();
    }
    
    @Override
    public String getContent(){
        return Content;
    }
    
    @Override
    public void setContent(String content){
        Content = content;
    }
    
    @Override
    public String toString(){
        String source = req.getResource();
        return  source+getStatus() + "\r\n"+
                "Date: " + DateTimeFormatter.RFC_1123_DATE_TIME.format(getDate())+ "\r\n"+
                "Connection: close"+"\r\n"+
                "Content-Type: text/html; charset=UTF-8"+
                "\r\n"+
                getContent();
                
    }
 
}