package cn.edu.ujn.eqnoxx.eqnocal;

import android.view.View;

public class KeyboardController extends ScreenController  {
    // functions
    public void about(View view) { startAbout(); }
    public void secondPage(View view) { changePage(); }

    // numbers
    public void one(View view) { appendText("1"); }
    public void two(View view) { appendText("2"); }
    public void three(View view) { appendText("3"); }
    public void four(View view) { appendText("4"); }
    public void five(View view) { appendText("5"); }
    public void six(View view) { appendText("6"); }
    public void seven(View view) { appendText("7"); }
    public void eight(View view) { appendText("8"); }
    public void nine(View view) { appendText("9"); }
    public void zero(View view) { appendText("0"); }
    public void point(View view) { appendText("."); }

    // operators
    public void plus(View view) { appendText("+"); }
    public void minus(View view) { appendText("-"); }
    public void multiply(View view) { appendText("×"); }
    public void divide(View view) { appendText("÷"); }
    public void percent(View view) { appendText("%"); }

    // math
    public void sin(View view) { appendText("sin("); }
    public void cos(View view) { appendText("cos("); }
    public void tan(View view) { appendText("tan("); }
    public void arcsin(View view) { appendText("arcsin("); }
    public void arccos(View view) { appendText("arccos("); }
    public void arctan(View view) { appendText("arctan("); }
    public void lg(View view) { appendText("lg("); }
    public void ln(View view) { appendText("ln("); }
    public void leftBracket(View view) { appendText("("); }
    public void rightBracket(View view) { appendText(")"); }
    public void power(View view) { appendText("^"); }
    public void squareRoot(View view) { appendText("√"); }
    public void factorial(View view) { appendText("!"); }
    public void reciprocal(View view) { appendText("^(-1)"); }

    // special_number
    public void pi(View view) { appendText("π"); }
    public void eulerNumber(View view) { appendText("e"); }

    // others
    public void ac(View view) { allClear(); }
    public void backspace(View view) { deleteText(); }
    public void equal(View view) { equalProcess(); }
}
