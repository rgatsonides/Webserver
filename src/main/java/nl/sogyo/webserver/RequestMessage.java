/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.sogyo.webserver;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author rgatsonides
 */
public class RequestMessage implements Request {

    private final String[] linepart;
    private final String[] resourceline;
    private String[] parameterline;
    private final HttpMethod http;
    private String parameterValue;
    private List<String> headerparameterNames;
    private String headerparameterValue;
    private List<String> Headerparameterlist = new ArrayList<>();
    private List<String> parameterlistNames;

    public RequestMessage(String line) {
        linepart = line.split(" ");
        resourceline = linepart[2].split("\\?");
        http = HttpMethod.valueOf(linepart[0]);
    }

    @Override
    public HttpMethod getHTTPMethod() {
        return http;
    }

    @Override
    public String getResourcePath() {
        return linepart[1];
    }

    public String getResource() {
        return resourceline[0];
    }

    @Override
    public List<String> getHeaderParameterNames() {
        List<String> parameterlistNames = new ArrayList<>();

        for (int i = 0; i < Headerparameterlist.size(); i++) {
            HeaderParameter headerparameter = new HeaderParameter(Headerparameterlist.get(i));
            parameterlistNames.add(headerparameter.getHeaderParameterName());
        }

        return parameterlistNames;
    }

    @Override
    public String getHeaderParameterValue(String name) {

        for (int i = 0; i < (Headerparameterlist.size()); i++) {
            HeaderParameter headerparameter = new HeaderParameter(Headerparameterlist.get(i));
            if (headerparameter.getHeaderParameterName().equals(name)) {
                headerparameterValue = headerparameter.getHeaderParameterValue();
            }
        }
        return headerparameterValue;
    }

    public List<String> NewLine(String line) {
        Headerparameterlist.add(line);
        return Headerparameterlist;
    }

    @Override
    public List<String> getParameterNames() {
        if (resourceline.length == 2) {
            parameterline = resourceline[1].split("&");
            for (String thirdlinepart1 : parameterline) {
                Parameter headerparameter = new Parameter(thirdlinepart1);
                parameterlistNames.add(headerparameter.getParameterName());
            }
        }
        System.out.println(parameterlistNames);
        return parameterlistNames;
    }

    @Override
    public String getParameterValue(String name) {
        if (resourceline.length == 2) {
            for (String thirdlinepart1 : parameterline) {
                Parameter headerparameter = new Parameter(thirdlinepart1);
                if (headerparameter.getParameterName().equals(name)) {
                    parameterValue = headerparameter.getParameterValue();
                }
            }
        }
        return parameterValue;
    }
}
