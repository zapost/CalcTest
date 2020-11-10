package Calc;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Main {

    public static void main(String[] args) {
        // write your code here
        String[] arab = new String[]{"1", "2", "3", "4", "5", "6", "7", "8", "9"};
        String[] rim = new String[]{"I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX", "X"};

        //Ввод данных и перевод их в верхний регистр -------------------------------------------------------------------
        System.out.println("Введите выражение в виде сложения двух чисел от 0 до 10 арабскими или римскими цифрами:");
        Scanner in = new Scanner(System.in);
        String val = in.nextLine();
        String prim = val.toUpperCase();//Переводим все введеные символы в верхний регистр
        prim = prim.trim();//Избавляемся от пробелов в начале и конце строки
        //Закончился ввод данных----------------------------------------------------------------------------------------

        //Выполнение основных действий ---------------------------------------------------------------------------------
        ArabRimProverka pr = new ArabRimProverka(prim, arab, rim);//Проверка на арабские или римские цифры
        Oper op = new Oper(pr.getSt()); //Определяем знак операции в выражении. Возвращает элементы и знак
        if (pr.getV() == 'a'){//Если введены арабские цифры, то
            int a1 = Integer.parseInt(op.getS1());//получаем первое число,
            int a2 = Integer.parseInt(op.getS2());//получае второе число
            Result res = new Result(a1, a2, op.getOp());//Производим операцию над числами со знаком в op.getOp()
            System.out.println(res.getA());//Выводим результат на экран
        }else
            if (pr.getV() == 'r'){//Если введены римские цифры, то
                RimToInt a1 = new RimToInt(op.getS1());//переводим первое число из римских цифр в арабские
                RimToInt a2 = new RimToInt(op.getS2());//переводим второе число из римских цифр в арабские
                Result res = new Result(a1.getRtI(), a2.getRtI(), op.getOp());//Вычисляем выражение
                IntToRim itr = new IntToRim(res.getA());//Преобразуем ответ выражения из арабских цифр в римские
                System.out.println(itr.getIt());//Выводим результат на экран
            }
        in.close();
    }
}
//Конец класса Main-----------------------------------------------------------------------------------------------------

//Проверка введенный символов на принадлежность к арабским или римским цифрам
class ArabRimProverka {
    char ch;
    String s;
    String ar;
    ArabRimProverka(String str, String[] a, String[] b) {
        int k;
        Pattern pattern = Pattern.compile("\\+|\\-|\\*|\\/");
        String[] word = pattern.split(str);
        k = word[0].length();
        s = str.substring(k, k+1);
        ar = word[0] + s + word[1];
        if (word.length > 2){
            System.out.println("В выражении больше 2 элементов. Так нельзя");
        }
            for (int i = 0; i < a.length; i++) {
                if (str.substring(0, 1).equals(a[i])) {
                    ch = 'a';return;
                }
            }
            for (int i = 0; i < b.length; i++) {
                if (str.substring(0, 1).equals(b[i])) {
                    ch = 'r';
                }
            }
    }
    String getSt(){return ar;}
    char getV(){
        return ch;
    }
}
//Конец проверки (+, -, *, /)===========================================================================================

//Определение операции (+, -, *, /) ------------------------------------------------------------------------------------
class Oper {
    String s1, s2;
    char ch;
    int a, b;
    Oper(String st) {
        Pattern pattern = Pattern.compile("\\+|\\-|\\*|\\/");
        String[] word = pattern.split(st);
        s1 = word[0];
        s2 = word[1];
        if (st.contains("+")) {
            ch = '+';
        } else if (st.contains("-")) {
            ch = '-';
        } else if (st.contains("*")) {
            ch = '*';
        } else if (st.contains("/")) {
            ch = '/';
        }
    }
    String getS1(){return s1;}
    String getS2(){return s2;}
    char getOp () {return ch;}
}
//Определили тип операции над числами ----------------------------------------------------------------------------------

//Производим действия над элементами выражения согласно операции -------------------------------------------------------
class Result{
    int res = 0;
    Result(int t1, int t2, char ch) {
        switch (ch) {
            case '+':
                res = t1 + t2; return;
            case '-':
                res = t1 - t2; return;
            case '*':
                res = t1 * t2; return;
            case '/':
                res = t1 / t2;
        }
    }
    int getA () {
        return res;
    }
}
//Выполнили действия над выражением ====================================================================================

//Преобразование Римских цифр в число-----------------------------------------------------------------------------------
class RimToInt {
    int d;
    RimToInt(String str) {
        char[] x = str.toCharArray();
        int lenx = x.length;
        int[] masInt = new int[lenx];
        for (int i = 0; i < lenx; i++) {
            switch (x[i]) {
                case 'C':
                    d = 100; break;
                case 'L':
                    d = 50; break;
                case 'X':
                    d = 10; break;
                case 'V':
                    d = 5; break;
                case 'I':
                    d = 1; break;
            }
            masInt[i] = d;
        }
        d = masInt[0];
        for (int i = 1; i < lenx; i++) {
            if (masInt[i] <= masInt[i - 1]) {
                d = d + masInt[i];
            }
            else d = d + masInt[i] - masInt[i-1] * 2;
        }
    }
    int getRtI (){
        return d;
    }
}
//Конец преобразования римских цифр в число-----------------------------------------------------------------------------

//Перевод арабских цифр в римские --------------------------------------------------------------------------------------
class IntToRim{
    String[] rim = new String[]{"I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX"};
    String[] c = new String[]{"I", "V", "X", "L", "C"};
    String it = "";
    IntToRim(int num){
        int nc, nf, nt, nd;
        nc = num / 100; //количество сотен
        nd = num % 100; //остаток от сотки
        if (nc == 0) {//если число меньше сотки
            nf = num / 50;//узнаем есть ли полтишки
            if (nf == 0) {//если нет, то
                nt = num / 10;//узнаем сколько десяток
                nd = num % 10;//узнаем число меньше десятки
            }
            else {//если есть полтишки, то
                nt = num / 10;//определяем, сколько есть десяток
                nd = num % 10;//остаток от деления
            }
        }
        else {//если есть сотки
            nf = nd / 50;//определяем сколько полтишков
            if (nf == 0) {//если полтишков нет, то
                nt = nd / 10;//определяем сколько десяток
                nd = nd % 10;//находим остаток
            }
            else {//если есть полтишки
                nd = nd % 50;
                nt = nd / 10;
                nd = nd % 10;
            }
        }
        for (int i = 0; i < nc; i++){
            it = it + c[4];
        }
        if (nf == 1) {it = it + c[3];}
        if (nt == 4){it = it +  c[2] + c[3];}
        else {
            for (int i = 0; i < nt; i++) {
                it = it + c[2];
            }
        }
        if (nd != 0){it = it + rim[nd-1];}
    }
    String getIt(){return it;}
}
//Перевели в римские !!!------------------------------------------------------------------------------------------------