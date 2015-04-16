package kotlin.jvm.internal;

import kotlin.Function;
import kotlin.jvm.functions.*;

public abstract class FunctionImpl implements Function,
    Function0, Function1, Function2, Function3, Function4
    ,Function5, Function6, Function7, Function8/*, Function9,
    Function10, Function11, Function12, Function13, Function14,
    Function15, Function16, Function17, Function18, Function19,
    Function20, Function21, Function22 */
{
    public abstract int getArity();

    public Object invokeVararg(Object... p) {
        throw new UnsupportedOperationException();
    }

    private void checkArity(int expected) {
        if (getArity() != expected) {
            throwWrongArity(expected);
        }
    }

    private void throwWrongArity(int expected) {
        throw new IllegalStateException("Wrong function arity, expected: " + expected + ", actual: " + getArity());
    }

    @Override
    public Object invoke() {
        checkArity(0);
        return invokeVararg();
    }

    @Override
    public Object invoke(Object p1) {
        checkArity(1);
        return invokeVararg(p1);
    }

    @Override
    public Object invoke(Object p1, Object p2) {
        checkArity(2);
        return invokeVararg(p1, p2);
    }

    @Override
    public Object invoke(Object p1, Object p2, Object p3) {
        checkArity(3);
        return invokeVararg(p1, p2, p3);
    }

    @Override
    public Object invoke(Object p1, Object p2, Object p3, Object p4) {
        checkArity(4);
        return invokeVararg(p1, p2, p3, p4);
    }

    @Override
    public Object invoke(Object p1, Object p2, Object p3, Object p4, Object p5) {
        checkArity(5);
        return invokeVararg(p1, p2, p3, p4, p5);
    }

    @Override
    public Object invoke(Object p1, Object p2, Object p3, Object p4, Object p5, Object p6) {
        checkArity(6);
        return invokeVararg(p1, p2, p3, p4, p5, p6);
    }

    @Override
    public Object invoke(Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7) {
        checkArity(7);
        return invokeVararg(p1, p2, p3, p4, p5, p6, p7);
    }

    @Override
    public Object invoke(Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7, Object p8) {
        checkArity(8);
        return invokeVararg(p1, p2, p3, p4, p5, p6, p7, p8);
    }
}
