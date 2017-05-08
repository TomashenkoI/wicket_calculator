package com.wicketapp;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.validation.IValidatable;
import org.apache.wicket.validation.IValidator;
import org.apache.wicket.validation.ValidationError;

import java.util.Arrays;
import java.util.TreeSet;

public class CalculatorPage extends WebPage {

    @SpringBean(name = "calculator")
    private Calculator calculator;

    private String result;

    public CalculatorPage(final PageParameters parameters) {

        if(parameters.getNamedKeys().contains("result")){
            result = parameters.get("result").toString();
        } else {
            result = "0";
        }

        addComponents();

    }

    private void addComponents() {

        FeedbackPanel feedbackPanel = new FeedbackPanel("feedbackMsg");

        final TextField<String> scoreboard = new TextField<String>("scoreboard", Model.<String>of(result));
        scoreboard.add(new InsertDataValidator());

        Form<?> form = new Form<Void>("form") {
            @Override
            protected void onSubmit() {
                result = calculator.calculateExpression(result, scoreboard.getValue());
                PageParameters pageParameters = new PageParameters();
                pageParameters.add("result", result);
                setResponsePage(CalculatorPage.class, pageParameters);
            }
        };

        add(feedbackPanel);
        form.add(scoreboard);

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

    private class InsertDataValidator implements IValidator<String>{
        @Override
        public void validate(IValidatable<String> iValidatable) {
            String expression = iValidatable.getValue();

            TreeSet<Character> operators = new TreeSet<>(Arrays.asList(new Character[]{'+', '-', '/', '*'}));
            TreeSet<Character> symbols = new TreeSet<>(Arrays.asList(new Character[]{'+', '-', '/', '*', '0', '1', '2',
            '3', '4', '5', '6', '7', '8', '9', '(', ')', '.'}));

            if (operators.contains(expression.charAt(expression.length() - 1))) {
                iValidatable.error(new ValidationError().setMessage("missed operand"));
            }

            for (Character character : expression.toCharArray()) {
                if (!symbols.contains(character)) {
                    iValidatable.error(new ValidationError().setMessage("invalid values"));
                }
            }


        }
    }

    public void setCalculator(Calculator calculator) {
        this.calculator = calculator;
    }
}
