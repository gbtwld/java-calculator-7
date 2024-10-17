package calculator;

import camp.nextstep.edu.missionutils.Console;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;
import java.util.regex.Pattern;

public class Application {
    public static List<Character> sep = new ArrayList<>(Arrays.asList(',', ':'));

    /*
     * 입력에서 커스텀 구분자 설정을 확인해서 존재하면 구분자 리스트에 커스텀 구분자를 넣어준다.
     * 입력을 커스텀 구분자 이후의 문자열만 남겨서 반환한다.
     */
    public static String checkCustomSep(String input) {
        String regex = "^//.\\\\n.*";

        if ( Pattern.matches(regex, input)) {
            sep.add(input.charAt(2));
            input = input.substring(5);
        }

        return input;
    }

    /*
     * 스택에 저장된 숫자들을 자릿수에 맞게 더해 최종 숫자를 구하는 메서드.
     * 스택의 맨 위에서부터 차례로 꺼내며 자릿수(1의 자리, 10의 자리 등)를 계산하고,
     * 계산된 숫자를 모두 더해 최종 결과를 반환한다.
     */
    public static int getStackNum(Stack<Integer> stack) {
        int num = 0, index = 0;

        while (!stack.isEmpty()) {
            num += (int) (stack.pop() * Math.pow(10, index++));
        }

        return num;
    }

    /*
     * 입력 문자열을 한 글자씩 확인하여 처리하는 메서드.
     * 숫자일 경우 스택에 저장한다.
     * 구분자(쉼표, 콜론)를 만나면 스택에 쌓인 숫자를 getStackNum() 메서드로 계산하고 sum에 더한다.
     * 문자열 끝에 도달하면 스택에 남은 숫자를 계산해 sum에 더한다.
     * 숫자나 구분자가 아닌 경우 IllegalArgumentException을 발생시킨다.
     * 최종적으로 모든 숫자의 합을 반환한다.
     */
    public static int getSum(String input) throws IllegalArgumentException {
        int sum = 0;
        Stack<Integer> stack = new Stack<>();

        for (int i = 0; i < input.length(); i++) {
            char curVal = input.charAt(i);

            if (curVal >= '0' && curVal <= '9') {
                stack.push(curVal - '0');
            } else if (sep.contains(curVal)) {
                sum += getStackNum(stack);
            } else {
                throw new IllegalArgumentException("잘못된 입력입니다.");
            }
        }

        // 스택에 숫자가 남아있는 경우 남은 수를 구해 더해준다.
        if (!stack.isEmpty()) {
            sum += getStackNum(stack);
        }

        return sum;
    }

    public static void main(String[] args) {
        String input;

        System.out.println("덧셈할 문자열을 입력해 주세요.");
        input = Console.readLine();

        input = checkCustomSep(input);

        int result = getSum(input);
        System.out.printf("결과 : %d\n", result);

        Console.close();
    }
}
