package com.wicketapp;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;

public class CalculatorPage extends WebPage {

    Calculation calculation = new Calculation();

    String expression;
    String result;

    public CalculatorPage(final PageParameters parameters) {

        if(parameters.getNamedKeys().contains("expression")){
            result = parameters.get("expression").toString();
        } else {
            result = "0";
        }

        addComponents();

    }

    private void addComponents() {
        final TextField<String> resultField = new TextField<String>("scoreboard", Model.<String>of(result));

        Form<?> form = new Form<Void>("form") {
            @Override
            protected void onSubmit() {
                if (result.charAt(0) == '-') {
                    expression = 0 + result + resultField.getValue();
                } else {
                    expression = result + resultField.getValue();
                }
                result = String.valueOf(calculation.calculateExpression(expression));
                PageParameters pageParameters = new PageParameters();
                pageParameters.add("expression", result);
                setResponsePage(CalculatorPage.class, pageParameters);
            }
        };

        form.add(resultField);

        add(new Button("+"));
        add(new Button("-"));
        add(new Button("/"));
        add(new Button("*"));
        add(new Button("="));

        add(new Button("."));
        add(new Button("C"));

        add(new Button("("));
        add(new Button(")"));

        add(new Button("0"));
        add(new Button("1"));
        add(new Button("2"));
        add(new Button("3"));
        add(new Button("4"));
        add(new Button("5"));
        add(new Button("6"));
        add(new Button("7"));
        add(new Button("8"));
        add(new Button("9"));

        add(form);

    }

}
