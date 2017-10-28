/** Class to represent a token in Tokenizer class
*@author Khushbu Patil
*  Ver 1.0: 2017/10/27 
*/

package calc;
public class Token<T> {

	T element;				//a variable representing generic token 
	boolean isOperator;		//flag representing if the token is an operator or not

	Token(T c) {
		element = c;
		isOperator = false;
	}

	public String toString() {
		return this.element.toString();
	}
}