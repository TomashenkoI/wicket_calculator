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
        final TextField<String> resultField = new TextField<String>("result", Model.<String>of(result));

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

        Button plusButton = new Button("+");
        Button minusButton = new Button("-");
        Button divideButton = new Button("/");
        Button multiplyButton = new Button("*");
        Button equalsButton = new Button("=");

        Button openBracketButton = new Button("(");
        Button closeBracketButton = new Button(")");

        Button button0 = new Button("0");
        Button button1 = new Button("1");
        Button button2 = new Button("2");
        Button button3 = new Button("3");
        Button button4 = new Button("4");
        Button button5 = new Button("5");
        Button button6 = new Button("6");
        Button button7 = new Button("7");
        Button button8 = new Button("8");
        Button button9 = new Button("9");
        Button dotButton = new Button(".");
        Button clearButton = new Button("C");

        form.add(resultField);
        form.add(equalsButton);
//        form.add(clearButton);

        add(form);
        add(plusButton);
        add(minusButton);
        add(divideButton);
        add(multiplyButton);
        add(button0);
        add(button1);
        add(button2);
        add(button3);
        add(button4);
        add(button5);
        add(button6);
        add(button7);
        add(button8);
        add(button9);
        add(openBracketButton);
        add(closeBracketButton);
        add(dotButton);
        add(clearButton);
//        add(.forCss(MyClass.class, "style.css"));
//        add(CSSPackageResource.getHeaderContribution(MyClass.class, "style.css"));


    }

}
