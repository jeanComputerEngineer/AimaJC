
package aima.core.search.adversarial;

public class Action<S, A> {
    private S state;
    private A result;

    public Action(S state, A result) {
        this.state = state;
        this.result = result;
    }

    public S getState() {
        return state;
    }

    public A getResult() {
        return result;
    }
}