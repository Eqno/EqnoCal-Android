package cn.edu.ujn.eqnoxx.eqnocal;

import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ScreenController extends AppCompatActivity {
    final private int MAXLEN = 100;
    final private String TRIGOFUNC = "(lg|ln|sin|cos|tan|arcsin|arccos|arctan)\\(";
    final private String[] INFO = {"EqnoCal-科学计算器\n\n作者：方年年\nQQ：2367542784\n" +
            "Email：Eqnoxx@163.com\nhttps://github.com/Eqno/EqnoCal", "这都能点等号？-_-!",
            "好吧你继续点……", "还真继续点啊？", "恭喜你发现彩蛋！"};
    private boolean easterEgg = false;
    private int infoIndex;
    private long clickCount;
    private long countTotalizer;
    private long countDistance;

    protected static boolean scientificMode = false;
    protected static String screenText = "0";
    protected static boolean start = false;
    protected static boolean forceStart = false;
    protected TextView screen;

    // functions
    protected void allClear() {
        dropText("0");
    }
    protected void startAbout() {
        infoIndex = 0;
        countTotalizer = 0;
        clickCount = 4;
        countDistance = 10;
        forceStart = true;
        dropText(INFO[infoIndex]);
    }
    protected void changePage() {
        Button sinButton = (Button) findViewById(R.id.sin);
        Button cosButton = (Button) findViewById(R.id.cos);
        Button tanButton = (Button) findViewById(R.id.tan);
        Button arcsinButton = (Button) findViewById(R.id.arcsin);
        Button arccosButton = (Button) findViewById(R.id.arccos);
        Button arctanButton = (Button) findViewById(R.id.arctan);
        if (sinButton.getVisibility() == View.VISIBLE) {
            sinButton.setVisibility(View.INVISIBLE);
            cosButton.setVisibility(View.INVISIBLE);
            tanButton.setVisibility(View.INVISIBLE);
            arcsinButton.setVisibility(View.VISIBLE);
            arccosButton.setVisibility(View.VISIBLE);
            arctanButton.setVisibility(View.VISIBLE);
        }
        else {
            sinButton.setVisibility(View.VISIBLE);
            cosButton.setVisibility(View.VISIBLE);
            tanButton.setVisibility(View.VISIBLE);
            arcsinButton.setVisibility(View.INVISIBLE);
            arccosButton.setVisibility(View.INVISIBLE);
            arctanButton.setVisibility(View.INVISIBLE);
        }
    }

    protected void equalProcess() {
        if (screenText.equals(INFO[infoIndex]) || easterEgg) {
            easterEgg = true;
            forceStart = true;
            if (infoIndex < INFO.length-1) {
                dropText(INFO[++ infoIndex]);
                return ;
            }
            if (infoIndex == 4) {
                clickCount ++;
                if (clickCount==5||clickCount==5+countTotalizer) {
                    dropText("再点"+countDistance+"次查看彩蛋。");
                    countTotalizer += countDistance;
                    countDistance += 10;
                }
                else if (clickCount%7==0) {
                    dropText("欢迎来反馈bug。");
                }
                else if (clickCount%17==0) {
                    dropText("找作者聊天也行。");
                }
                else {
                    dropText("你点了"+clickCount+"次等号。");
                }
            }
            return ;
        }

        // 运算符补全
        screenText = screenText.replaceAll("÷", "/");
        screenText = screenText.replaceAll("×", "*");
        screenText = screenText.replaceAll("([^%+\\-*/\\^√\\(])e($|[*/])", "$1*e$2");
        screenText = screenText.replaceAll("(\\)|π|[0-9]|!)π", "$1*π");
        screenText = screenText.replaceAll("(^|[+\\-*/])e(π|"+TRIGOFUNC+")", "$1e*$2");
        screenText = screenText.replaceAll("\\)([^+\\-*/!\\)\\^])", ")*$1");
        screenText = screenText.replaceAll("([^\\^+\\-*/√\\(ngs])\\(", "$1*(");
        screenText = screenText.replaceAll("([0-9])√", "$1*√");

        for (int i=0; i<screenText.length()-1; i++) {
            if ((screenText.charAt(i)=='e' && screenText.charAt(i+1)=='e') ||
                    (screenText.charAt(i)=='π' && screenText.charAt(i+1)=='π')) {
                screenText = screenText.substring(0, i+1)+"*"+screenText.substring(i+1);
            }
        }

        screenText = screenText.replaceAll("(^|[+\\-*/])(e|π)(e|π)($|[+\\-*/])", "$1$2*$3$4");
        screenText = screenText.replaceAll("(^|[+\\-*/])eπe($|[+\\-*/])", "$1e*π*e$4");
        screenText = screenText.replaceAll("(^|[+\\-*/])eπ", "$1e*π");
        screenText = screenText.replaceAll("πe($|[+\\-*/])", "π*e$1");
        screenText = screenText.replaceAll("%($|[+\\-*/\\^])", "/100$1");
        screenText = screenText.replaceAll("[\\.+\\-*/\\^√]$", "");

        // 括号补全
        int lb = 0, rb = 0;
        for (int i=0; i<screenText.length(); i++) {
            if (screenText.charAt(i) == '(') lb ++;
            if (screenText.charAt(i) == ')') rb ++;
        }
        for (; rb<lb; rb++) {
            screenText += ")";
        }

        // 不停按等号
        continuousEqual();
    }
    private void continuousEqual() {
        if (start) {
            dropText(calculate(screen.getText().toString()));
        }
        else {
            dropText(calculate(screenText));
        }
    }

    // append
    protected void appendText(String s) {
        if (forceStart) {
            if (judgeOpt(s)) {
                screenText = "0" + s;
            }
            else {
                screenText = s;
            }
        }
        else if (start) {
            if (judgeOpt(s)) {
                screenText += s;
            }
            else {
                screenText = s;
            }
        }
        else if ((screenText+s).length()<MAXLEN) {
            screenText += s;
        }
        start = false;
        forceStart = false;
        setText();
    }
    private void setText() {
        if (checkExpression(screenText)) {
            screen.setText(screenText);
            setFontSize();
        }
        else {
            screenText = screen.getText().toString();
        }
        easterEgg = false;
        changeClear();
    }
    private void dropText(String s) {
        screen.setText(s);
        screenText = s;
        setFontSize();
        changeClear();
        start = true;
    }
    private void changeClear() {
        Button clear = (Button) findViewById(R.id.ac);
        if (screen.getText().toString().equals("0")) { clear.setText("AC"); }
        else { clear.setText("C"); }
    }
    private boolean judgeOpt(String s) {
        return s.equals(".") || s.equals("^") || s.equals("!") || s.equals("^(-1)") || s.equals("%")
                || s.equals("+") || s.equals("-") || s.equals("×") || s.equals("÷");
    }
    private boolean checkExpression(String s) {
        String opt1 = "(\\+|\\-|×|÷|\\^|\\.)";
        String opt2 = "(\\+|\\-|×|÷|\\(|\\)|√|^)";
        String opt3 = "(\\+|\\-|×|÷)";
        String opt4 = "(\\+|\\-|×|÷|\\(|\\)|e|π|√)";
        String opt5 = "(\\+|\\-|×|÷|√)";
        String r1 = opt4+"\\.";
        String r2 = "\\."+opt4;
        String r3 = "\\(\\)";
        String r4 = opt2+"e[0-9]";
        String r5 = opt5+"\\^";
        String r6 = "\\^"+opt3;
        String r7 = "[0-9]*(\\.[0-9]*){2,}";
        String r8 = opt1+"{2,}";
        String r9 = "\\^{2,}";
        String r10 = "π[0-9]";
        String r11 = "[0-9]*(\\.[0-9]*)!";
        String r12 = "(\\(|√|π|e|[\\^+\\-*÷])!";
        String r13 = "[\\(√]%";
        String r14 = opt1+"%";
        String r15 = "%[!\\.]";

        Matcher m1 = Pattern.compile(r1).matcher(s);
        Matcher m2 = Pattern.compile(r2).matcher(s);
        Matcher m3 = Pattern.compile(r3).matcher(s);
        Matcher m4 = Pattern.compile(r4).matcher(s);
        Matcher m5 = Pattern.compile(r5).matcher(s);
        Matcher m6 = Pattern.compile(r6).matcher(s);
        Matcher m7 = Pattern.compile(r7).matcher(s);
        Matcher m8 = Pattern.compile(r8).matcher(s);
        Matcher m9 = Pattern.compile(r9).matcher(s);
        Matcher m10 = Pattern.compile(r10).matcher(s);
        Matcher m11 = Pattern.compile(r11).matcher(s);
        Matcher m12 = Pattern.compile(r12).matcher(s);
        Matcher m13 = Pattern.compile(r13).matcher(s);
        Matcher m14 = Pattern.compile(r14).matcher(s);
        Matcher m15 = Pattern.compile(r15).matcher(s);

        // 阻止括号
        int lb = 0, rb = 0;
        for (int i=0; i<s.length(); i++) {
            if (s.charAt(i) == '(') lb ++;
            if (s.charAt(i) == ')') rb ++;
        }
        // 输入阻止
        if (m1.find() || m2.find() || m3.find() || m4.find() || m5.find() || m6.find()
                || m7.find() || m9.find() || m10.find() || m11.find() || m12.find()
                || m13.find() || m15.find() || rb>lb) {
            return false;
        }
        // 运算符题替换
        if (m8.find() || m14.find()) {
            screenText = screenText.substring(0, screenText.length()-2)
                    + screenText.substring(screenText.length()-1);
        }
        return true;
    }

    // delete
    protected void deleteText() {
        if (screenText.equals("0") || forceStart) {
            dropText("0");
            forceStart = false;
            start = true;
            return ;
        }
        String r = TRIGOFUNC + "$";
        Matcher matcher = Pattern.compile(r).matcher(screenText);
        if (matcher.find()) {
            screenText = screenText.replaceAll(r, "");
        }
        else {
            screenText = screenText.substring(0, screenText.length()-1);
        }
        if (screenText.equals("")) {
            dropText("0");
            forceStart = false;
            start = true;
            return ;
        }
        forceStart = false;
        start = false;
        setText();
    }

    // set
    private void setFontSize() {
        String text = screen.getText().toString();
        int size = scientificMode ? 36 : 48;
        if (text.length() > (scientificMode ? 17 : 12)) {
            size = scientificMode ? 28 : 40;
        }
        if (text.length() > (scientificMode ? 22: 15)) {
            size = scientificMode ? 22 : 32;
        }
        screen.setTextSize(TypedValue.COMPLEX_UNIT_SP, size);
    }

    // calculate
    private String calculate(String expression) {
        Log.e("Eqnoxx", expression);
        try {
            String ans = "" + SyntaxTree.calculate(expression).doubleValue();
            return ans.replaceAll("\\.0$", "");
        }
        catch (Exception e) {
            String error = e.toString();
            forceStart = true;
            return error.substring(20);
        }
    }
}
