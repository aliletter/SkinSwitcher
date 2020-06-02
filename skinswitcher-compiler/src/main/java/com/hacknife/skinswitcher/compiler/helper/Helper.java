package com.hacknife.skinswitcher.compiler.helper;

import com.github.javaparser.ast.expr.Expression;
import java.lang.reflect.Method;
import javax.lang.model.element.Element;
import static com.github.javaparser.JavaParser.parseExpression;

/**
 * author  : Hacknife
 * e-mail  : hacknife@outlook.com
 * github  : http://github.com/hacknife
 * project : SkinSwitcher
 */
public class Helper {
    public static Expression[] parameter(Element element) {
        try {
            Method parameterFile = element.getClass().getDeclaredMethod("getParameters");
            String[] parameters = parameterFile.invoke(element).toString().split(",");
            Expression[] parameterExpression = new Expression[parameters.length];
            for (int i = 0; i < parameters.length; i++) {
                parameterExpression[i] = parseExpression(parameters[i]);
            }
            return parameterExpression;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Expression[]{};
    }
}