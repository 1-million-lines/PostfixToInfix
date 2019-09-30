import java.util.Stack;

public class PostfixToInfix {
	public static void main(final String... args) {
		for (String e : new String[]{"3 4 2 * 1 5 - 2 3 ^ ^ / +",
            "1 2 + 3 4 + ^ 5 6 + ^"}) {
            System.out.printf("Postfix : %s%n", e);
            System.out.printf("Infix : %s%n", postfixToInfix(e));
            System.out.println();
		}
	}

	public static String postfixToInfix(final String postfix) {
		Stack<Expression> expr = new Stack<>();
		for (String token : postfix.split("\\s+")) {
			char c = token.charAt(0);
			int idx = Expression.ops.indexOf(c);
			if (idx != - 1 && token.length() == 1) {

				Expression r = expr.pop();
				Expression l = expr.pop();

				int opPrec = idx / 2;

				if (l.prec < opPrec || (l.prec == opPrec && c == '^'))
					l.ex = '(' + l.ex + ')';

				if (r.prec < opPrec || (r.prec == opPrec && c != '^'))
					r.ex = '(' + r.ex + ')';

				expr.push(new Expression(l.ex, r.ex, token));
			} else {
				expr.push(new Expression(token));
			}
			System.out.printf("%s -> %s%n", token, expr);
		}
		return expr.peek().ex;
	}
}

class Expression {
	public final static String ops = "-+/*^";

	public String op, ex;
	public int prec = 3;

	public Expression(final String ex) {
		this.ex = ex;
	} 

	public Expression(final String e1, final String e2, final String o) {
		ex = String.format("%s %s %s", e1, o, e2);
		op = o;
		prec = ops.indexOf(o) / 2;
	}

	@Override
	public String toString() {
		return ex;
	}
}
